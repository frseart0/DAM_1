package servicio

import modelo.Producto
import kotlin.reflect.KClass
import kotlin.reflect.KVisibility
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import modelo.Promocionable

fun esPromocionable(producto: Producto): Boolean =
    producto::class.findAnnotation<Promocionable>() != null

fun descripcionPromocion(producto: Producto): String? =
    producto::class.findAnnotation<Promocionable>()?.descripcion

fun listarMiembros(clase: KClass<*>): String {
    val propiedades = clase.memberProperties
        .filter { it.visibility == KVisibility.PUBLIC }
        .sortedBy { it.name }
        .joinToString("\n") { "  - ${it.name}: ${it.returnType}" }
        .ifBlank { "  (sin propiedades públicas)" }
    val metodos = clase.memberFunctions
        .filter { it.visibility == KVisibility.PUBLIC }
        .sortedBy { it.name }
        .joinToString("\n") { "  - ${it.name}()" }
        .ifBlank { "  (sin métodos públicos)" }
    return "Clase ${clase.simpleName}\nPropiedades:\n$propiedades\nMétodos:\n$metodos"
}
