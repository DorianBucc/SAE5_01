const express = require('express');
const app = express();
const port = 3000;

// Middleware pour servir les fichiers statiques (optionnel)
app.use(express.static('public'));

// Route de base
app.get('/', (req, res) => {
  res.send('Hello, World!');
});

// Démarrage du serveur
app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});
