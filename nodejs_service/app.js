const express = require('express');
const app = express();
const productRoutes = require('./routes/productRoutes');

app.use(express.json());
app.use('/', productRoutes);

const PORT = 3001;
// app.listen(PORT, () => {
//   console.log(`Server is running on port ${PORT}`);
// });

const { sequelize } = require('./models/product');

// Sync the database models
sequelize.sync()
  .then(() => {
    console.log('Database models synchronized successfully');

    // Start the server after syncing the models
    app.listen(PORT, () => {
      console.log(`Server is running on port ${PORT}`);
    });
  })
  .catch((err) => {
    console.error('Error synchronizing database models:', err);
  });
