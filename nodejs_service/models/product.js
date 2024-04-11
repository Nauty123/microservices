const dotenv = require('dotenv');

// Load environment variables from .env file
dotenv.config();

const config = require('../config/config.js').development;

const Sequelize = require('sequelize');

console.log(config);
// const config = require('../config/config.json').development;

const sequelize = new Sequelize(config.database, config.username, config.password, {
  host: config.host,
  dialect: config.dialect,
});

const Product = sequelize.define('product', {
  id: {
    type: Sequelize.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
  name: {
    type: Sequelize.STRING,
    allowNull: false,
  },
  description: {
    type: Sequelize.STRING,
    allowNull: false,
  },
  price: {
    type: Sequelize.DECIMAL(10, 2),
    allowNull: false,
  },
  stock_quantity: {
    type: Sequelize.INTEGER,
    allowNull: false,
  },
});

module.exports = { Product, sequelize };