import modelo.CategoriaProducto
import modelo.Cliente
import modelo.ComboFamiliar
import modelo.EstadoPedido
import modelo.LineaPedido
import modelo.Pedido
import modelo.PizzaPepperoni
import modelo.Producto
import modelo.Repartidor
import servicio.DESCUENTO_POR_CANTIDAD
import servicio.FinDeEntrada
import servicio.IVA
import servicio.Inventario
import servicio.MINIMO_PIZZAS_DESCUENTO
import servicio.TELEFONO_DEFECTO
import servicio.actualizarInventarioPorLote
import servicio.calcularTotalesPedido
import servicio.consumoDeLineas
import servicio.descripcionPromocion
import servicio.esPromocionable
import servicio.fechaEnRangoDePromocion
import servicio.finPromocion
import servicio.formatearTelefono
import servicio.inicioPromocion
import servicio.leerCantidad
import servicio.leerCantidadInventario
import servicio.leerCorreo
import servicio.leerEntrada
import servicio.leerIndice
import servicio.leerPrecio
import servicio.leerTextoObligatorio
import servicio.listarMiembros
import java.time.LocalDate
import java.util.Locale
import kotlin.reflect.full.findAnnotation
import modelo.Promocionable

fun main() {
    val aplicacion = AplicacionPizzeria()
    try {
        aplicacion.ejecutar()
    } catch (fin: FinDeEntrada) {
        println("Entrada finalizada.")
    }
}

class AplicacionPizzeria {
    private val inventario: Inventario = inventarioInicial()
    private val catalogo: MutableList<Producto> = catalogoInicial()
    private val clientes: MutableList<Cliente> = mutableListOf()
    private val pedidosDelDia: MutableList<Pedido> = mutableListOf()
    private val repartidores: List<Repartidor> = listOf(
        Repartidor("Luis Soto", "+56 (911) 111-1111", "Centro", true, 2.5),
        Repartidor("María Díaz", "+56 (922) 222-2222", "Norte", true, 6.0)
    )

    fun ejecutar() {
        println("Pizzería — gestión de pedidos")
        while (true) {
            println(
                """
                |
                |1. Registrar cliente
                |2. Registrar producto
                |3. Crear pedido
                |4. Reporte del día
                |5. Demo semana 3
                |6. Ver resumen de un pedido
                |0. Salir
                """.trimMargin()
            )
            when (leerEntrada("Opción: ")) {
                "1" -> registrarCliente()
                "2" -> registrarProducto()
                "3" -> crearPedido()
                "4" -> reporteDelDia()
                "5" -> demoSemana3()
                "6" -> verResumen()
                "0" -> {
                    println("Hasta pronto.")
                    return
                }
                else -> println("Opción no válida.")
            }
        }
    }

    private fun registrarCliente() {
        val nombre: String = leerTextoObligatorio("Nombre: ")
        val direccion: String = leerTextoObligatorio("Dirección de entrega: ")
        print("Teléfono: ")
        val entradaTelefono: String? = readlnOrNull() ?: throw FinDeEntrada()
        val telefono: String = formatearTelefono(entradaTelefono)
        if (telefono == TELEFONO_DEFECTO) {
            println("Teléfono nulo o incorrecto. Se asignó $TELEFONO_DEFECTO.")
        } else {
            println("Teléfono formateado: $telefono")
        }
        val correo: String = leerCorreo()
        val zona: String = leerTextoObligatorio("Zona de entrega: ")
        val notasIngresadas = leerEntrada("Notas (opcional, Enter para omitir): ")
        val notas: String? = notasIngresadas.ifBlank { null }

        val nuevo = Cliente(nombre, direccion, telefono, correo, zona, notas)
        val duplicado = clientes.find { it == nuevo }
        if (duplicado != null) {
            println("Ya existe un cliente con el mismo nombre y correo (${duplicado.correo}). No se registró el duplicado.")
            return
        }
        clientes.add(nuevo)
        println("Cliente registrado:")
        println("Nombre: ${nuevo.nombre}")
        println("Dirección: ${nuevo.direccion}")
        println("Teléfono: ${nuevo.telefono}")
        println("Correo: ${nuevo.correo}")
        println("Notas: ${nuevo.notasVisibles()}")
        notificarRegistro(nuevo)
    }

    private fun notificarRegistro(cliente: Cliente?) {
        cliente?.let {
            println("Notificación enviada a ${it.nombre} (${it.correo}) al teléfono ${it.telefono}.")
        }
    }

    private fun registrarProducto() {
        val nombre = leerTextoObligatorio("Nombre del producto: ")
        val precio = leerPrecio()
        println("Categoría: 1) pizza  2) bebida  3) ingrediente")
        val indiceCategoria = leerIndice("Categoría: ", CategoriaProducto.entries.size)
        val categoria = CategoriaProducto.entries[indiceCategoria]
        val ingredientes = mutableMapOf<String, Int>()
        val distintos = leerCantidadInventario("¿Cuántos ingredientes distintos consume? (0 si ninguno): ")
        repeat(distintos) { numero ->
            val ingrediente = leerTextoObligatorio("Ingrediente ${numero + 1}: ")
            val porUnidad = leerCantidad("Cantidad por unidad (1-100): ")
            ingredientes[ingrediente] = porUnidad
        }
        val nuevo = Producto(nombre, precio, categoria, ingredientes)
        if (catalogo.any { it == nuevo }) {
            println("Ya existe un producto con el mismo nombre y categoría. No se duplicó.")
            return
        }
        catalogo.add(nuevo)
        println("Producto registrado: ${nuevo.detallarProducto()}")
    }

    private fun crearPedido() {
        if (clientes.isEmpty()) {
            println("Primero registra un cliente.")
            return
        }
        println("Clientes:")
        clientes.forEachIndexed { indice, cliente ->
            println("${indice + 1}. ${cliente.nombre} — zona ${cliente.zona}")
        }
        val cliente = clientes[leerIndice("Elige cliente: ", clientes.size)]
        val lineas = mutableListOf<LineaPedido>()
        while (true) {
            println("Productos:")
            catalogo.forEachIndexed { indice, producto ->
                val marca = if (esPromocionable(producto)) " [promoción]" else ""
                println("${indice + 1}. ${producto.detallarProducto()}$marca")
            }
            println("0. Terminar pedido")
            val indiceProducto = leerIndice("Producto: ", catalogo.size, permitirCero = true)
            if (indiceProducto == -1) {
                if (lineas.isEmpty()) {
                    println("El pedido debe incluir al menos un producto.")
                    continue
                }
                break
            }
            val producto = catalogo[indiceProducto]
            val cantidad = leerCantidad()
            lineas += LineaPedido(producto, cantidad)
        }
        val notasTexto = leerEntrada("Notas del pedido (Enter para omitir): ")
        val notas: String? = notasTexto.ifBlank { null }
        val pedido = Pedido(
            id = Pedido.siguienteId(),
            cliente = cliente,
            lineas = lineas.toList(),
            notas = notas,
            fecha = LocalDate.now()
        )
        val necesarios = consumoDeLineas(pedido.lineas)
        val confirmacion = actualizarInventarioPorLote(inventario, necesarios, lote = 1)
        println(confirmacion.mensaje)
        if (!confirmacion.confirmado) return

        val asignado = repartidores.find { it.disponible && it.zona.equals(cliente.zona, ignoreCase = true) }
            ?: repartidores.find { it.disponible }
        if (asignado != null) {
            asignado.disponible = false
            pedido.repartidor = asignado
            println(
                "Repartidor asignado: ${asignado.nombre}, zona ${asignado.zona}, a ${asignado.distanciaKm} km."
            )
        } else {
            println("No hay repartidores disponibles. El pedido queda sin repartidor.")
        }
        pedidosDelDia.add(pedido)
        imprimirResumen(pedido)
    }

    private fun reporteDelDia() {
        val arreglo: Array<Pedido> = pedidosDelDia.toTypedArray()
        if (arreglo.isEmpty()) {
            println("No hay pedidos registrados hoy.")
            return
        }
        println("=== REPORTE DEL DÍA ===")
        for (pedido in arreglo) {
            val totales = pedido.calcularTotales()
            val pizzas = pedido.lineas.joinToString(", ") { "${it.cantidad} x ${it.producto.nombre}" }
            println(
                "${pedido.cliente.nombre} | $pizzas | total ${dinero(totales.total)} | ${pedido.estado.etiqueta}"
            )
        }
        val completados = arreglo.filter {
            it.estado == EstadoPedido.LISTO || it.estado == EstadoPedido.ENTREGADO
        }
        val pendientes = arreglo.filter {
            it.estado == EstadoPedido.PREPARACION || it.estado == EstadoPedido.EN_CAMINO
        }
        println("Completados (${completados.size}):")
        if (completados.isEmpty()) println("  (ninguno)")
        completados.forEach { println("  - #${it.id} ${it.cliente.nombre} (${it.estado.etiqueta})") }
        println("Pendientes (${pendientes.size}):")
        if (pendientes.isEmpty()) println("  (ninguno)")
        pendientes.forEach { println("  - #${it.id} ${it.cliente.nombre} (${it.estado.etiqueta})") }
    }

    private fun demoSemana3() {
        println("=== DEMO SEMANA 3 ===")
        val rango = inicioPromocion()..finPromocion()
        println("Rango de promoción: $rango. ¿Hoy entra? ${fechaEnRangoDePromocion(LocalDate.now())}")

        val ana = Cliente(
            nombre = "Ana Ruiz",
            direccion = "Calle Demo 10",
            telefono = "+56 (911) 111-1111",
            correo = "ana.demo@pizzeria.com",
            zona = "Centro"
        )
        val anaDuplicada = Cliente(
            nombre = "Ana Ruiz",
            direccion = "Otra dirección",
            telefono = "+56 (900) 000-0000",
            correo = "ana.demo@pizzeria.com",
            zona = "Norte"
        )
        val pedro = Cliente(
            nombre = "Pedro López",
            direccion = "Calle Demo 20",
            telefono = "+56 (933) 333-3333",
            correo = "pedro@pizzeria.com",
            zona = "Norte"
        )
        println("Igualdad de clientes: Ana == duplicado → ${ana == anaDuplicada}")
        println("Igualdad de clientes: Ana == Pedro → ${ana == pedro}")
        if (clientes.any { it == ana }) {
            println("Ana demo ya estaba registrada. No se duplica.")
        } else {
            clientes.add(ana)
            println("Cliente de demostración registrado: $ana")
        }
        if (clientes.any { it == anaDuplicada }) {
            println("Alta duplicada rechazada: ${anaDuplicada.nombre} / ${anaDuplicada.correo}")
        }

        val pepperoni = PizzaPepperoni()
        val mismoPepperoni = Producto("Pepperoni", 1000.0, CategoriaProducto.PIZZA)
        val otraCategoria = Producto("Pepperoni", 8990.0, CategoriaProducto.BEBIDA)
        println("Igualdad de productos: Pepperoni == Pepperoni (pizza) → ${pepperoni == mismoPepperoni}")
        println("Igualdad de productos: Pepperoni pizza == Pepperoni bebida → ${pepperoni == otraCategoria}")

        val pedidoAna = Pedido(
            id = Pedido.siguienteId(),
            cliente = ana,
            lineas = listOf(LineaPedido(pepperoni, 4)),
            estadoInicial = EstadoPedido.LISTO
        )
        val pedidoPedro = Pedido(
            id = Pedido.siguienteId(),
            cliente = pedro,
            lineas = listOf(LineaPedido(ComboFamiliar(), 1), LineaPedido(catalogoBebida(), 1)),
            estadoInicial = EstadoPedido.PREPARACION
        )
        val aviso = if (pedidoAna.cliente == pedidoPedro.cliente) {
            "Ambos pedidos pertenecen al mismo cliente."
        } else {
            "Clientes distintos. Se conserva a ${pedidoAna.cliente.nombre} y se suman los productos."
        }
        val combinado = pedidoAna + pedidoPedro
        println(aviso)
        println("Pedidos combinados con el operador + (origen #${pedidoAna.id} + #${pedidoPedro.id} = #${combinado.id})")
        imprimirResumen(combinado)

        val (nombre, correo, telefono) = ana
        println("Cliente desestructurado: nombre=$nombre, correo=$correo, teléfono=$telefono")
        val (clientePedido, productos, total) = combinado
        val nombresProductos = productos.joinToString(", ") { "${it.cantidad} x ${it.producto.nombre}" }
        println(
            "Pedido desestructurado: cliente=${clientePedido.nombre}, productos=$nombresProductos, total=${dinero(total)}"
        )

        println("Productos promocionables (anotación @Promocionable):")
        for (producto in catalogo) {
            val anotacion = producto::class.findAnnotation<Promocionable>()
            if (anotacion != null) {
                println("  - ${producto.nombre}: ${anotacion.descripcion}")
            }
        }

        println()
        println(listarMiembros(Pedido::class))
        println()
        println(listarMiembros(Producto::class))
        println()
        println(listarMiembros(Cliente::class))

        val repartidorDemo = Repartidor("Camila Reyes", "+56 (944) 444-4444", "Norte", true, 4.2)
        println(repartidorDemo.entregarPedido(pedidoPedro))

        pedidosDelDia.add(pedidoAna)
        pedidosDelDia.add(pedidoPedro)
        pedidosDelDia.add(combinado)
        println("La demo agregó los pedidos de ejemplo al reporte del día.")
    }

    private fun verResumen() {
        if (pedidosDelDia.isEmpty()) {
            println("No hay pedidos para mostrar.")
            return
        }
        pedidosDelDia.forEach { pedido ->
            println("#${pedido.id} ${pedido.cliente.nombre} — ${pedido.estado.etiqueta}")
        }
        val id = leerEntrada("Id del pedido: ").toIntOrNull()
        val pedido = pedidosDelDia.find { it.id == id }
        if (pedido == null) {
            println("No existe un pedido con ese id.")
            return
        }
        imprimirResumen(pedido)
    }

    private fun catalogoBebida(): Producto {
        return catalogo.first { it.categoria == CategoriaProducto.BEBIDA }
    }
}

fun imprimirResumen(pedido: Pedido) {
    val nombre: String = pedido.cliente.nombre
    val direccion: String = pedido.cliente.direccion
    val telefono: String = pedido.cliente.telefono
    val correo: String = pedido.cliente.correo
    val nombrePizza: String = pedido.lineas.joinToString(", ") { it.producto.nombre }
    val precioUnitario: Double = pedido.lineas.firstOrNull()?.producto?.precio ?: 0.0
    val idPedido: Int = pedido.id
    val totales = calcularTotalesPedido(pedido)
    val cantidadPizzas: Int = totales.cantidadPizzas
    val totalPedido: Double = totales.total
    val estadoPedido: String = pedido.estado.etiqueta
    val repartidorAsignado: String = pedido.repartidor?.nombre ?: "Sin asignar"
    val notasPedido: String = pedido.notas?.takeIf { it.isNotBlank() } ?: "Sin notas"
    val porcentajeIva = (IVA * 100).toInt()
    val porcentajeCantidad = (DESCUENTO_POR_CANTIDAD * 100).toInt()

    println(
        """
        |
        |===== RESUMEN DEL PEDIDO #$idPedido =====
        |Cliente: $nombre
        |Dirección de entrega: $direccion
        |Teléfono: $telefono
        |Correo: $correo
        |Pizza: $nombrePizza
        |Precio de referencia: ${dinero(precioUnitario)}
        |Productos:
        """.trimMargin()
    )
    for (linea in pedido.lineas) {
        val importe = linea.producto.precio * linea.cantidad
        val promo = descripcionPromocion(linea.producto)?.let { " — promoción: $it" } ?: ""
        println(
            "- ${linea.producto.nombre} (${linea.producto.categoria.etiqueta}): " +
                "precio ${dinero(linea.producto.precio)} x ${linea.cantidad} = ${dinero(importe)}$promo"
        )
    }
    println("Cantidad de pizzas: $cantidadPizzas")
    println("Subtotal: ${dinero(totales.subtotal)}")
    println(
        "Descuento por cantidad (más de $MINIMO_PIZZAS_DESCUENTO pizzas, $porcentajeCantidad%): " +
            dinero(totales.descuentoCantidad)
    )
    println("Descuento de promoción: ${dinero(totales.descuentoPromocion)}")
    println("Impuestos (IVA $porcentajeIva%): ${dinero(totales.impuestos)}")
    println("Total del pedido: ${dinero(totalPedido)}")
    println("Estado del pedido: $estadoPedido")
    println("Repartidor asignado: $repartidorAsignado")
    println("Notas: $notasPedido")
    println("================================")
}

private fun inventarioInicial(): Inventario = Inventario(
    mapOf(
        "masa" to 40,
        "salsa" to 40,
        "queso" to 30,
        "pepperoni" to 20,
        "aceituna" to 12,
        "bebida" to 25
    )
)

private fun catalogoInicial(): MutableList<Producto> = mutableListOf(
    PizzaPepperoni(),
    Producto(
        nombre = "Cuatro Quesos",
        precio = 9990.0,
        categoria = CategoriaProducto.PIZZA,
        ingredientes = mapOf("masa" to 1, "salsa" to 1, "queso" to 3)
    ),
    ComboFamiliar(),
    Producto(
        nombre = "Bebida 500ml",
        precio = 1500.0,
        categoria = CategoriaProducto.BEBIDA,
        ingredientes = mapOf("bebida" to 1)
    )
)

fun dinero(valor: Double): String = "$" + String.format(Locale.US, "%.2f", valor)
