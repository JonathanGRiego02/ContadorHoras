package com.proyecto.contadorhoras

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class VerDatosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_datos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datosEditText = findViewById<EditText>(R.id.misdatos)

        // Abrimos el archivo y creamos un parser para que los datos se vean bonitos
        val xml_data = assets.open("datos.xml")
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()

        // Pasamos el xml al parser
        parser.setInput(xml_data, null)

        var event = parser.eventType

        // Bucle para recoger el xml
        while (event != XmlPullParser.END_DOCUMENT) {
            val tag_name = parser.name
            when(event) {
                XmlPullParser.END_TAG -> {
                    if (tag_name == "dia") {
                        val datos = "\nFecha: " + parser.getAttributeValue(0) +
                                "\nHora de entrada: " + parser.getAttributeValue(1) +
                                "\nHora de salida: " + parser.getAttributeValue(2) +
                                "\nHoras totales: " + parser.getAttributeValue(3) +
                                "\nHoras extras: " + parser.getAttributeValue(4) +
                                "\n"
                        datosEditText.append(datos)
                    }
                }
            }
            event = parser.next()
        }

    }
}
