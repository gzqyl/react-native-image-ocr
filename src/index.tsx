import {NativeModules} from 'react-native';

const { RNOCRModule } = NativeModules;

export type MLKitLangCode = "en" | "zh" | "ja" | "ko"

type OCRDataBlockType = {
  text: string
}
type OCRDataType = {
  blocks: OCRDataBlockType[]
}
export const recognizeImage = async (url: string): Promise<OCRDataType> => {
  return RNOCRModule.recognizeImage(url);
};