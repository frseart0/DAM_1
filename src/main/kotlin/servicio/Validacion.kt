package servicio

const val TELEFONO_DEFECTO = "(000) 000-0000"
const val CORREO_DEFECTO = "correo@invalido.com"

private val CORREO_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val RANGO_CANTIDAD = 1..100

class FinDeEntrada : RuntimeException()

fun leerEntrada(prompt: String): String {
    print(prompt)
    val linea = readlnOrNull() ?: throw FinDeEntrada()
    return linea.trim()
}

fun leerTextoObligatorio(prompt: String): String {
    while (true) {
        val texto = leerEntrada(prompt)
        if (texto.isNotEmpty()) return texto
        println("Este dato es obligatorio.")
    }
}

fun correoValido(correo: String): Boolean = CORREO_REGEX.matches(correo.trim())

fun formatearTelefono(entrada: String?): String {
    if (entrada.isNullOrBlank()) return TELEFONO_DEFECTO
    val digitos = entrada.filter { it.isDigit() }
    if (digitos.length < 12) return TELEFONO_DEFECTO
    val digitosUniformes = digitos.take(12)
    val pais = digitosUniformes.substring(0, 2)
    val area = digitosUniformes.substring(2, 5)
    val central = digitosUniformes.substring(5, 8)
    val final = digitosUniformes.substring(8, 12)
    return "+$pais ($area) $central-$final"
}

fun leerCorreo(): String {
    while (true) {
        val correo = leerEntrada("Correo: ")
        if (correo.isEmpty()) {
            println("Correo vacío. Se usará $CORREO_DEFECTO.")
            return CORREO_DEFECTO
        }
        if (correoValido(correo)) return correo
        println("El correo no tiene el formato nombre@dominio.com. Vuelve a ingresarlo.")
    }
}

fun leerCantidad(prompt: String = "Cantidad (1-100): "): Int {
    while (true) {
        val texto = leerEntrada(prompt)
        try {
            val cantidad = texto.toInt()
            if (cantidad !in RANGO_CANTIDAD) {
                println("La cantidad debe estar entre 1 y 100. Ingresa un valor válido.")
                continue
            }
            return cantidad
        } catch (error: NumberFormatException) {
            println("Error: cantidad no válida. Ingresa un número entero. (${error.message})")
        }
    }
}

fun leerPrecio(): Double {
    while (true) {
        val texto = leerEntrada("Precio: ")
        try {
            val precio = texto.toDouble()
            if (precio < 0) {
                throw IllegalArgumentException("El costo no puede ser negativo.")
            }
            return precio
        } catch (error: NumberFormatException) {
            println("Error: costo no válido. Ingresa un número. (${error.message})")
        } catch (error: IllegalArgumentException) {
            println("Error: ${error.message} Corrige el dato.")
        }
    }
}

fun leerCantidadInventario(prompt: String): Int {
    while (true) {
        val texto = leerEntrada(prompt)
        try {
            val cantidad = texto.toInt()
            if (cantidad < 0) {
                throw IllegalArgumentException("La cantidad de inventario no puede ser negativa.")
            }
            return cantidad
        } catch (error: NumberFormatException) {
            println("Error: cantidad no válida. Ingresa un número entero. (${error.message})")
        } catch (error: IllegalArgumentException) {
            println("Error: ${error.message} Corrige el dato.")
        }
    }
}

fun leerIndice(prompt: String, tamano: Int, permitirCero: Boolean = false): Int {
    while (true) {
        val texto = leerEntrada(prompt)
        try {
            val numero = texto.toInt()
            if (permitirCero && numero == 0) return -1
            if (numero in 1..tamano) return numero - 1
            println("Opción fuera de rango.")
        } catch (error: NumberFormatException) {
            println("Ingresa un número. (${error.message})")
        }
    }
}
