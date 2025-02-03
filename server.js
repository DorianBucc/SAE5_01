const express = require('express');
const app = express();
const port = process.env.PORT || 3000;
const upload = require('./upload'); // Import du middleware

// Route POST pour uploader une image
app.post('/upload', upload.single('image'), (req, res) => {
    if (!req.file) {
        return res.status(400).json({ message: 'Aucun fichier envoyé' });
    }
    res.json({ message: 'Image envoyée avec succès', filename: req.file.filename });
});

app.use('/uploads', express.static('uploads'));

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
