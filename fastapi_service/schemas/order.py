from pydantic import BaseModel
from typing import List

class OrderItem(BaseModel):
    product_id: int
    quantity: int

class OrderCreate(BaseModel):
    items: List[OrderItem]

class Order(BaseModel):
    id: int
    user_id: int
    order_date: str
    total_amount: float
    items: List[OrderItem]

    class Config:
        orm_mode = True
