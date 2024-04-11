from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    DATABASE_NAME: str
    DATABASE_HOST: str
    DATABASE_USER_NAME: str
    DATABASE_PORT: str
    DATABASE_PASSWORD: str
    PRODUCT_MICROSERVICE_NAME: str
    PRODUCT_MICROSERVICE_PORT: str

    class Config:
        env_file = ".env"


settings = Settings()
