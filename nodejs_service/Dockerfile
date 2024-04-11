# Use the official Node.js image as the base image
FROM node:18

# Set the working directory in the container
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY ./nodejs_service/package*.json ./

# Install the required packages
RUN npm install

# Copy the Node.js service code
COPY ./nodejs_service .

# Expose the port that the Node.js service will run on
EXPOSE 3001

# Run the Node.js service
CMD ["npm", "start"]