package modelo

class Cliente(
    nombre: String,
    val direccion: String,
    val telefono: String,
    val correo: String,
    val zona: String,
    val notas: String? = null
) : Usuario(nombre, telefono) {

    fun notasVisibles(): String = notas ?: "Sin notas"

    operator fun component1(): String = nombre

    operator fun component2(): String = correo

    operator fun component3(): String = telefono

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cliente) return false
        return nombre == other.nombre && correo == other.correo
    }

    override fun hashCode(): Int {
        var resultado = nombre.hashCode()
        resultado = 31 * resultado + correo.hashCode()
        return resultado
    }

    override fun toString(): String = "$nombre <$correo>"
}
