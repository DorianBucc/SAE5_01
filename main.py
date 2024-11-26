from IA_function import *
from environnement import *
import os

if __name__ == '__main__':
    if not os.path.exists(PATH + MODEL_PTH + ".pth"):
        print("modele introuvable : création du nouveau modele")
        model = Renew_model()
    else:
        model = Load_model()
        print(1)
        if TRAIN:
            model = Model_train(model)
        if CONVERSION:
            conversion_pt(model,MODEL_PT)
    

    visualize_model_predictions(model, img_path='data/img/drink3.jpg')
    # visualize_model_predictions(model, img_path='data/hymenoptera_data/val/ants/161292361_c16e0bf57a.jpg')
    # visualize_model(model)