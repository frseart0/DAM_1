package modelo

class Repartidor(
    nombre: String,
    contacto: String,
    val zona: String,
    var disponible: Boolean = true,
    val distanciaKm: Double
) : Usuario(nombre, contacto) {

    fun entregarPedido(pedido: Pedido): String {
        pedido.repartidor = this
        pedido.cambiarEstado(EstadoPedido.EN_CAMINO)
        disponible = false
        return "$nombre sale a entregar el pedido #${pedido.id} en la zona $zona (${distanciaKm} km). Estado: ${pedido.estado.etiqueta}."
    }
}
