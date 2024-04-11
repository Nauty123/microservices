const { Product, sequelize } = require('../models/product');

const getAllProducts = async (req, res) => {
  try {
    const products = await Product.findAll();
    res.json(products);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
  }
};

const getProductById = async (req, res) => {
  const productId = req.params.id;
  try {
    const product = await Product.findByPk(productId);
    if (product) {
      res.json(product);
    } else {
      res.status(404).json({ error: 'Product not found' });
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
  }
};

const createProduct = async (req, res) => {
  const { name, description, price, stock_quantity } = req.body;
  try {
    const product = await Product.create({ name, description, price, stock_quantity });
    res.status(201).json(product);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
  }
};

const updateProduct = async (req, res) => {
  const productId = req.params.id;
  const { name, description, price, stock_quantity } = req.body;
  try {
    const [updatedRows] = await Product.update(
      { name, description, price, stock_quantity },
      { where: { id: productId }, returning: true }
    );
    if (updatedRows) {
      res.json(updatedRows[0]);
    } else {
      res.status(404).json({ error: 'Product not found' });
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
  }
};

const deleteProduct = async (req, res) => {
  const productId = req.params.id;
  try {
    const deletedRows = await Product.destroy({ where: { id: productId } });
    if (deletedRows) {
      res.json({ message: 'Product deleted successfully' });
    } else {
      res.status(404).json({ error: 'Product not found' });
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal server error' });
  }
};

module.exports = {
  getAllProducts,
  getProductById,
  createProduct,
  updateProduct,
  deleteProduct,
};
