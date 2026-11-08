package servicio

import modelo.CategoriaProducto
import modelo.Pedido
import java.time.LocalDate

const val IVA = 0.19
const val DESCUENTO_POR_CANTIDAD = 0.10
const val MINIMO_PIZZAS_DESCUENTO = 3
const val DESCUENTO_PROMOCION = 0.15

data class Totales(
    val subtotal: Double,
    val descuentoCantidad: Double,
    val descuentoPromocion: Double,
    val impuestos: Double,
    val total: Double,
    val cantidadPizzas: Int
)

fun inicioPromocion(): LocalDate = LocalDate.now().withDayOfMonth(1)

fun finPromocion(): LocalDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())

fun fechaEnRangoDePromocion(fecha: LocalDate): Boolean {
    val rangoPromocion: ClosedRange<LocalDate> = inicioPromocion()..finPromocion()
    return fecha in rangoPromocion
}

fun calcularTotalesPedido(pedido: Pedido): Totales {
    val subtotal = pedido.lineas.sumOf { it.producto.precio * it.cantidad }
    val cantidadPizzas = pedido.lineas
        .filter { it.producto.categoria == CategoriaProducto.PIZZA }
        .sumOf { it.cantidad }
    val descuentoCantidad = if (cantidadPizzas > MINIMO_PIZZAS_DESCUENTO) {
        subtotal * DESCUENTO_POR_CANTIDAD
    } else {
        0.0
    }
    val basePromocionable = pedido.lineas
        .filter { esPromocionable(it.producto) && fechaEnRangoDePromocion(pedido.fecha) }
        .sumOf { it.producto.precio * it.cantidad }
    val despuesCantidad = (subtotal - descuentoCantidad).coerceAtLeast(0.0)
    val descuentoPromocion = (basePromocionable * DESCUENTO_PROMOCION).coerceAtMost(despuesCantidad)
    val baseImponible = despuesCantidad - descuentoPromocion
    val impuestos = baseImponible * IVA
    val total = baseImponible + impuestos
    return Totales(
        subtotal = subtotal,
        descuentoCantidad = descuentoCantidad,
        descuentoPromocion = descuentoPromocion,
        impuestos = impuestos,
        total = total,
        cantidadPizzas = cantidadPizzas
    )
}

fun calcularTotal(
    precioUnitario: Double,
    cantidad: Int,
    promocionable: Boolean,
    fecha: LocalDate = LocalDate.now()
): Double {
    val subtotal = precioUnitario * cantidad
    val descuentoCantidad = if (cantidad > MINIMO_PIZZAS_DESCUENTO) subtotal * DESCUENTO_POR_CANTIDAD else 0.0
    val despuesCantidad = subtotal - descuentoCantidad
    val descuentoPromocion = if (promocionable && fechaEnRangoDePromocion(fecha)) {
        despuesCantidad * DESCUENTO_PROMOCION
    } else {
        0.0
    }
    val baseImponible = despuesCantidad - descuentoPromocion
    return baseImponible + baseImponible * IVA
}
