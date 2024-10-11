package com.proyecto.contadorhoras

data class Dia(
    var diaMes: Int = 0,
    var diaSemana: Int = 0,
    var mes: Int = 0,
    var anyo: Int = 0,
    var horasExtra: Int = 0,
    var horasTotales: Int = 0,
    var horaEntrada: String = "",
    var horaSalida: String = ""
)
