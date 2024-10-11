package com.proyecto.contadorhoras.Activities

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.contadorhoras.Dia
import com.proyecto.contadorhoras.R
import com.proyecto.contadorhoras.models.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Locale
import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment



class RecogerDatosActivity : AppCompatActivity() {
    // modelo
        // Fecha
    lateinit var fecha_taker: EditText
    private val calendar = Calendar.getInstance()
    lateinit var fechaTextView: TextView
        // Hora
    lateinit var time_taker : EditText
    lateinit var horaTextView : TextView

    // Objeto donde almacenaremos todo
    lateinit var dia_trabajo : Dia


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoger_datos)

        dia_trabajo = Dia()

        // Recogedor de la fecha
        fecha_taker = findViewById<EditText>(R.id.DatePicker)
        fechaTextView = findViewById<TextView>(R.id.fechaTV)

        fecha_taker.setOnClickListener {
            showDatePicker()
        }

        // Recogedor de la hora
        time_taker = findViewById<EditText>(R.id.TimePicker)
        horaTextView = findViewById<TextView>(R.id.horaTV)

        time_taker.setOnClickListener {showTimePickerDialog()}
    }

    // Calendario para recoger la fecha
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                // Formatear la fecha seleccionada
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                fechaTextView.text = formattedDate

                // Obtener el día de la semana
                val dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK)
                dia_trabajo.diaSemana = dayOfWeek
                dia_trabajo.diaMes = calendar.get(Calendar.DAY_OF_MONTH)
                dia_trabajo.mes = calendar.get(Calendar.MONTH)
                dia_trabajo.anyo = calendar.get(Calendar.YEAR)


            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    // Reloj para recoger la hora
    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment{onTimeSelected(it)}
        timePicker.show(supportFragmentManager, "time")

    }

    private fun onTimeSelected(time:String) {
        horaTextView.text = time
        dia_trabajo.horaEntrada = time
    }
}