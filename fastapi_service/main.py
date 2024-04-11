from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
import requests

from models import models
from models.database import SessionLocal, engine
from schemas.user import User, UserBase, UserCreate
from schemas.order import Order, OrderCreate
from utils.security import get_password_hash, verify_password
from typing import List
from config import settings

models.Base.metadata.create_all(bind=engine)

app = FastAPI()

def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


# User endpoints
@app.get("/users", response_model=List[User])
def get_users(db: Session = Depends(get_db), auth: bool = False):
    if not auth:
        raise HTTPException(status_code=401, detail="Unauthorized")
    users = db.query(models.User).all()
    return users


@app.get("/users/{user_id}", response_model=User)
def get_user(user_id: int, db: Session = Depends(get_db)):
    user = db.query(models.User).filter(models.User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    return user


@app.post("/users", response_model=User, status_code=201)
def create_user(user_data: UserCreate, db: Session = Depends(get_db)):
    hashed_password = get_password_hash(user_data.password)
    user = models.User(
        name=user_data.name, email=user_data.email, password=hashed_password
    )
    db.add(user)
    db.commit()
    db.refresh(user)
    return user


@app.put("/users/{user_id}", response_model=User)
def update_user(
    user_id: int, user_data: UserBase, db: Session = Depends(get_db), auth: bool = False
):
    if not auth:
        raise HTTPException(status_code=401, detail="Unauthorized")
    user = db.query(models.User).filter(models.User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    user.name = user_data.name
    user.email = user_data.email
    db.commit()
    db.refresh(user)
    return user


@app.delete("/users/{user_id}", status_code=204)
def delete_user(user_id: int, db: Session = Depends(get_db), auth: bool = False):
    if not auth:
        raise HTTPException(status_code=401, detail="Unauthorized")
    user = db.query(models.User).filter(models.User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    db.delete(user)
    db.commit()
    return {"message": "User deleted successfully"}


# Order endpoints
@app.get("/orders", response_model=List[Order])
def get_orders(user_id: int, db: Session = Depends(get_db)):
    orders = db.query(models.Order).filter(models.Order.user_id == user_id).all()
    order_list = []
    for order in orders:
        order_items = [
            {
                "product_id": item.product_id,
                "quantity": item.quantity
            } for item in order.items
        ]
        order_list.append(Order(id=order.id, user_id=order.user_id, order_date=order.order_date.isoformat(), total_amount=order.total_amount, items=order_items))
    return order_list

@app.post("/orders", status_code=201)
def create_order(order_data: OrderCreate, user_id: int, db: Session = Depends(get_db)):
    total_amount = 0
    items = []
    for item in order_data.items:
        product_url = f"http://{settings.PRODUCT_MICROSERVICE_NAME}:{settings.PRODUCT_MICROSERVICE_PORT}/products/{item.product_id}"  # Update the port if needed
        response = requests.get(product_url)
        if response.status_code == 200:
            product = response.json()
            print(type(product["price"]))
            item_total = ((float(product["price"])) * item.quantity)
            total_amount += item_total
            items.append({"product_id": item.product_id, "quantity": item.quantity})
        else:
            raise HTTPException(
                status_code=404, detail=f"Product not found with id {item.product_id}"
            )

    order = models.Order(user_id=user_id, total_amount=total_amount)
    db.add(order)
    db.commit()
    db.refresh(order)

    for item in items:
        order_item = models.OrderItem(
            order_id=order.id, product_id=item["product_id"], quantity=item["quantity"]
        )
        db.add(order_item)
    db.commit()

    return {"message": "Order placed successfully", "order_id": order.id}
