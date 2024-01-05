package com.github.gzqyl.rnimageocr;

import android.net.Uri
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap
import com.github.gzqyl.rnuserdefault.RNUserDataStore
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import okio.IOException


class RNOCRModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext){

    override fun getName() = "RNOCRModule"

    @ReactMethod fun recognizeImage(url: String, promise: Promise){

        val uri: Uri = Uri.parse(url)

        try {

            val image = InputImage.fromFilePath(reactContext, uri)
            val recognizer = when(RNUserDataStore(reactContext).getMLkitLang()){

                "zh" -> {
                    TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())
                }
                "ja" -> {
                    TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())
                }
                "ko" -> {
                    TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
                }
                else -> {
                    TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                }

            }

            recognizer.process(image)
                .addOnSuccessListener { visionText ->

                    var res = Arguments.createMap()
                    var blockArr = Arguments.createArray()

                    for (block in visionText.textBlocks) {
                        val blockText = block.text
                        var blockMap = Arguments.createMap()
                        blockMap.putString("text", blockText)
                        blockArr.pushMap(blockMap)
                    }

                    res.putArray("blocks", blockArr)
                    promise.resolve(res)

                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    promise.resolve(writeErrRes())
                }


        } catch (e: IOException) {
            e.printStackTrace()
            promise.resolve(writeErrRes())
        }

    }

    private fun writeErrRes(): WritableMap {

        var res = Arguments.createMap()
        var blockArr = Arguments.createArray()
        var parcelMap = Arguments.createMap()
        parcelMap.putString("text","You see this, means something is wrong, hope that you send us feedback by comments on the Store")
        blockArr.pushMap(parcelMap)
        res.putArray("blocks", blockArr)

        return res

    }


}
