package modelo

enum class CategoriaProducto(val etiqueta: String) {
    PIZZA("pizza"),
    BEBIDA("bebida"),
    INGREDIENTE("ingrediente")
}

open class Producto(
    val nombre: String,
    val precio: Double,
    val categoria: CategoriaProducto,
    val ingredientes: Map<String, Int> = emptyMap()
) {
    fun detallarProducto(): String {
        val detalleIngredientes = if (ingredientes.isEmpty()) {
            "sin ingredientes"
        } else {
            ingredientes.entries.joinToString(", ") { "${it.key} x${it.value}" }
        }
        return "$nombre (${categoria.etiqueta}) — precio ${"%.0f".format(precio)} — $detalleIngredientes"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Producto) return false
        return nombre == other.nombre && categoria == other.categoria
    }

    override fun hashCode(): Int = 31 * nombre.hashCode() + categoria.hashCode()

    override fun toString(): String = "$nombre (${categoria.etiqueta})"
}

@Promocionable(descripcion = "Pizza Pepperoni en promoción del mes")
class PizzaPepperoni(
    precio: Double = 8990.0
) : Producto(
    nombre = "Pepperoni",
    precio = precio,
    categoria = CategoriaProducto.PIZZA,
    ingredientes = mapOf(
        "masa" to 1,
        "salsa" to 1,
        "queso" to 1,
        "pepperoni" to 2
    )
)

@Promocionable(descripcion = "Combo familiar con bebida incluida")
class ComboFamiliar(
    precio: Double = 12990.0
) : Producto(
    nombre = "Combo Familiar",
    precio = precio,
    categoria = CategoriaProducto.PIZZA,
    ingredientes = mapOf(
        "masa" to 2,
        "salsa" to 2,
        "queso" to 2,
        "pepperoni" to 2,
        "bebida" to 2
    )
)
