from IA_function import *
from environnement import *
import os

if __name__ == '__main__':
    try:
        if not os.path.exists(PATH + MODEL_PTH + ".pth"):
            print("modele introuvable : création du nouveau modele")
            model = Renew_model()
        else:
            model = Load_model()
            if TRAIN:
                model = Model_train(model)
            if CONVERSION:
                conversion_pt(model,MODEL_PT)
        

        visualize_model_predictions(model, img_path='data/img/drink2.jpg')
        visualize_model(model)
    except ValueError as e:
        print(f"Erreur : {e}")
    except FileNotFoundError as e:
        print(f"Erreur : {e}")
    