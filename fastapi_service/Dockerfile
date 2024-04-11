# Use the official Python image as the base image
FROM python:3.9

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file
COPY ./fastapi_service/requirements.txt .

# Install the required packages
RUN pip install --no-cache-dir -r requirements.txt

# Copy the FastAPI service code
COPY ./fastapi_service .

# Expose the port that the FastAPI service will run on
EXPOSE 8000

# Run the FastAPI service with Uvicorn
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]