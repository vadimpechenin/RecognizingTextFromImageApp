

class ConvertPdfToTextCalculator:

    @staticmethod
    def calculate(dictOfProject, parameters):
        #
        image = ConvertPdfToTextCalculator.__getImage(dictOfProject)

        items, image = parameters.nndet.quolityClassification(image, parameters.classification_model_nn,
                                                              parameters.object_detection_model, parameters.classes,
                                                              parameters.output_layers, parameters.colors)
        result = {'result': (image is not None), 'text': items}
        return result

    @staticmethod
    def __getImage(dictOfProject):
        img = []
        for imageFromList in dictOfProject['InputDocument']:
            byteImage = bytes.fromhex(imageFromList)

            img.append(ImageConverting.getImageConverting(byteImage))

        return img
