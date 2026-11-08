package servicio

import modelo.LineaPedido

data class ConfirmacionStock(
    val confirmado: Boolean,
    val mensaje: String
)

class Inventario(inicial: Map<String, Int>) {
    private val stock: MutableMap<String, Int> = inicial.toMutableMap()

    fun consultar(): Map<String, Int> = stock.toMap()

    fun faltantes(necesarios: Map<String, Int>): Map<String, Int> {
        val faltan = linkedMapOf<String, Int>()
        for ((nombre, requerido) in necesarios) {
            val disponible = stock[nombre] ?: 0
            if (disponible < requerido) {
                faltan[nombre] = requerido - disponible
            }
        }
        return faltan
    }

    fun evaluarDisponibilidad(necesarios: Map<String, Int>): String {
        val faltan = faltantes(necesarios)
        return when {
            faltan.isEmpty() -> "Hay ingredientes suficientes para preparar el pedido."
            faltan.size == 1 -> "Falta un ingrediente.\n${pasosReabastecer(faltan)}"
            else -> "Faltan varios ingredientes.\n${pasosReabastecer(faltan)}"
        }
    }

    fun confirmarSiHayStock(necesarios: Map<String, Int>): ConfirmacionStock {
        val faltan = faltantes(necesarios)
        val evaluacion = evaluarDisponibilidad(necesarios)
        val mensaje: String = if (faltan.isEmpty()) {
            for ((nombre, cantidad) in necesarios) {
                stock[nombre] = (stock[nombre] ?: 0) - cantidad
            }
            "Pedido confirmado. Inventario actualizado."
        } else {
            "El pedido no puede ser completado por falta de ingredientes."
        }
        return ConfirmacionStock(faltan.isEmpty(), "$evaluacion\n$mensaje")
    }

    private fun pasosReabastecer(faltan: Map<String, Int>): String {
        return faltan.entries.mapIndexed { indice, (nombre, cantidad) ->
            "${indice + 1}. Comprar al menos $cantidad de $nombre.\n   Registrar esa entrada en el inventario y volver a intentar el pedido."
        }.joinToString("\n")
    }
}

fun cantidadNecesariaParaLote(ingredientesPorUnidad: Map<String, Int>, lote: Int): Map<String, Int> {
    return ingredientesPorUnidad.mapValues { (_, cantidad) -> cantidad * lote }
}

fun consumoDeLineas(lineas: List<LineaPedido>): Map<String, Int> {
    val total = linkedMapOf<String, Int>()
    for (linea in lineas) {
        val necesarios = cantidadNecesariaParaLote(linea.producto.ingredientes, linea.cantidad)
        for ((ingrediente, cantidad) in necesarios) {
            total[ingrediente] = (total[ingrediente] ?: 0) + cantidad
        }
    }
    return total
}

fun actualizarInventarioPorLote(
    inventario: Inventario,
    ingredientesPorUnidad: Map<String, Int>,
    lote: Int
): ConfirmacionStock {
    val necesarios = cantidadNecesariaParaLote(ingredientesPorUnidad, lote)
    return inventario.confirmarSiHayStock(necesarios)
}
