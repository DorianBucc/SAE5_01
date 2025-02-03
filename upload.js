const multer = require('multer');
const path = require('path');

// Définir le stockage des fichiers
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/'); // Dossier où enregistrer les images
    },
    filename: (req, file, cb) => {
        cb(null, Date.now() + path.extname(file.originalname)); // Nom unique du fichier
    }
});

// Filtrer les fichiers (ex : uniquement images)
const fileFilter = (req, file, cb) => {
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
    if (allowedTypes.includes(file.mimetype)) {
        cb(null, true);
    } else {
        cb(new Error('Format d’image non supporté'), false);
    }
};

// Middleware Multer
const upload = multer({ storage, fileFilter });

module.exports = upload;