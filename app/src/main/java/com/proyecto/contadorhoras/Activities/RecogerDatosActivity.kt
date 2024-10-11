package com.proyecto.contadorhoras.Activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.proyecto.contadorhoras.Dia
import com.proyecto.contadorhoras.R
import com.proyecto.contadorhoras.models.TimePickerFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class RecogerDatosActivity : AppCompatActivity() {
    // modelo
        // Fecha
    lateinit var fecha_taker: Button
    private val calendar = Calendar.getInstance()
    lateinit var fechaTextView: TextView
        // Hora
    private lateinit var entradaTimePicker : Button
    lateinit var horaentradaTexView : TextView

    private lateinit var salidaTimePicker : Button
    lateinit var horasalidaTextView : TextView

    // Objeto donde almacenaremos todo
    lateinit var dia_trabajo : Dia


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoger_datos)

        dia_trabajo = Dia()

        // Recogedor de la fecha
        fecha_taker = findViewById<Button>(R.id.DatePicker)
        fechaTextView = findViewById<TextView>(R.id.fechaTV)

        fecha_taker.setOnClickListener {
            showDatePicker()
        }

        // Recogedor de la hora
        entradaTimePicker = findViewById<Button>(R.id.entradaTimePicker)
        horaentradaTexView = findViewById<TextView>(R.id.horaentradaTV)

        salidaTimePicker = findViewById<Button>(R.id.salidaTimePicker)
        horasalidaTextView = findViewById<TextView>(R.id.horasalidaTV)

        entradaTimePicker.setOnClickListener {showTimePickerDialogEntrada()}
        salidaTimePicker.setOnClickListener{showTimePickerDialogSalida()}
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
    private fun showTimePickerDialogEntrada() {
        val timePicker = TimePickerFragment{onTimeEntradaSelected(it)}
        timePicker.show(supportFragmentManager, "time")

    }

    private fun onTimeEntradaSelected(time:String) {
        horaentradaTexView.text = time
        dia_trabajo.horaEntrada = time
    }

    private fun showTimePickerDialogSalida() {
        val timePicker = TimePickerFragment{onTimeSalidaSelected(it)}
        timePicker.show(supportFragmentManager, "time")
    }

    private fun onTimeSalidaSelected(time: String) {

        dia_trabajo.horaSalida = time
        // Formato de las horas
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        try {
            // Convertir las horas de entrada y salida a Date
            val horaEntrada: Date? = dateFormat.parse(dia_trabajo.horaEntrada)
            val horaSalida: Date? = dateFormat.parse(dia_trabajo.horaSalida)

            // Comparar las horas
            if (horaEntrada != null && horaSalida != null && horaSalida.before(horaEntrada)) {
                // Mostrar advertencia si la hora de salida es anterior a la de entrada
                mostrarAdvertenciaHoraIncorrecta()
            } else {
                horasalidaTextView.text = time
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    // Función para mostrar una advertencia
    private fun mostrarAdvertenciaHoraIncorrecta() {
        // Puedes usar un Toast o un AlertDialog para mostrar el warning
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Advertencia")
        builder.setMessage("La hora de salida no puede ser anterior a la hora de entrada.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}