package modelo

import servicio.Totales
import servicio.calcularTotalesPedido
import java.time.LocalDate

enum class EstadoPedido(val etiqueta: String) {
    PREPARACION("Preparación"),
    LISTO("Listo"),
    EN_CAMINO("En camino"),
    ENTREGADO("Entregado")
}

data class LineaPedido(
    val producto: Producto,
    val cantidad: Int
)

class Pedido(
    val id: Int,
    val cliente: Cliente,
    val lineas: List<LineaPedido>,
    estadoInicial: EstadoPedido = EstadoPedido.PREPARACION,
    var repartidor: Repartidor? = null,
    val notas: String? = null,
    val fecha: LocalDate = LocalDate.now()
) {
    var estado: EstadoPedido = estadoInicial
        private set

    fun calcularTotales(): Totales = calcularTotalesPedido(this)

    fun cambiarEstado(nuevo: EstadoPedido) {
        estado = nuevo
    }

    operator fun plus(otro: Pedido): Pedido {
        val fusionadas = mutableListOf<LineaPedido>()
        for (linea in lineas + otro.lineas) {
            val indice = fusionadas.indexOfFirst { it.producto == linea.producto }
            if (indice >= 0) {
                val actual = fusionadas[indice]
                fusionadas[indice] = actual.copy(cantidad = actual.cantidad + linea.cantidad)
            } else {
                fusionadas += linea
            }
        }
        val notasCombinadas = listOfNotNull(notas, otro.notas)
            .filter { it.isNotBlank() }
            .joinToString(" | ")
            .ifBlank { null }
        return Pedido(
            id = siguienteId(),
            cliente = cliente,
            lineas = fusionadas,
            estadoInicial = EstadoPedido.PREPARACION,
            repartidor = repartidor,
            notas = notasCombinadas,
            fecha = fecha
        )
    }

    operator fun component1(): Cliente = cliente

    operator fun component2(): List<LineaPedido> = lineas

    operator fun component3(): Double = calcularTotales().total

    companion object {
        private var secuencia: Int = 1

        fun siguienteId(): Int = secuencia++
    }
}
