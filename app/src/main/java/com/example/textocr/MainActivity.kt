package com.example.textocr

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.lang.Exception
import java.lang.NumberFormatException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var nid_no: EditText
    lateinit var dateof_birth: EditText
    lateinit var textrec: Button
    lateinit var selectImg: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        nid_no = findViewById(R.id.nid_no)
        textrec = findViewById(R.id.textrec)
        selectImg = findViewById(R.id.selectImg)
        dateof_birth = findViewById(R.id.dateof_birth)

        selectImg.setOnClickListener {
            startRecognizing(imageView)
            //dateprint()
        }

        selectImg.setOnClickListener {
            selectImage()
        }

        //CustomFunction.dateParse("15Jan2004")


    }

    fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            imageView.setImageURI(data!!.data)

        }
    }

    fun startRecognizing(v: View) {
        if (imageView.drawable != null) {
            nid_no.setText("")
            v.isEnabled = false
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

            detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    v.isEnabled = true
                    processResultText(firebaseVisionText)
                }
                .addOnFailureListener {
                    v.isEnabled = true
                    nid_no.setText("Failed")
                }
        } else {
            Toast.makeText(this, "Select an Image First", Toast.LENGTH_LONG).show()
        }

    }


    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == 0) {
            nid_no.setText("No Text Found")
            return
        }
        for (block in resultText.textBlocks) {
            val blockText = block.text

            // Log.e("blockText", blockText.toString())
            // editText.setText(blockText)
            /* if (block.text.matches()) {
                 Log.e("blockText", block.text.toString())
             }*/

            val value = blockText!!.replace("[-+^:*#_/, IDNOdatefbirhoB]".toRegex(), "")
            val valuemonth = blockText!!.replace("[-+^:*#_/, ]".toRegex(), "")
            Log.e("blockText", blockText.toString())

            if (isNumeric(value)) {
                nid_no.setText(value)
            }

            var dateValue = CustomFunction.dateParse(valuemonth)

            if (CustomFunction.isValidDate(dateValue)) {
                dateof_birth.setText(dateValue)
            }


        }

    }

    fun isNumeric(strNum: String?): Boolean {
        if (strNum == null) {
            return false
        }
        try {
            val d = strNum.toDouble()
        } catch (nfe: NumberFormatException) {
            return false
        }
        return true
    }


}