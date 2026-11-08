# Pizzería en Kotlin

Aplicación de consola que gestiona clientes, productos, inventario, pedidos y repartidores. Se abre en IntelliJ IDEA y se ejecuta con `Main.kt` o con `gradlew.bat run`.

## Semana 1

Registra un cliente (nombre, dirección y teléfono) y muestra los datos en pantalla. Si el teléfono es nulo o incorrecto, usa `(000) 000-0000`. Calcula el total del pedido y aplica 10% de descuento cuando hay más de 3 pizzas. Antes de confirmar, comprueba el stock con un `if` como expresión: si alcanza, descuenta el inventario; si no, rechaza el pedido. El resumen final sale desde `Main.kt`.

## Semana 2

Evalúa los ingredientes con `when` y, si faltan, indica cómo reabastecerlos. Guarda los pedidos del día en un arreglo, los recorre con `for` y los separa en completados y pendientes con `filter`. Valida teléfono y correo, y arma el resumen con plantillas de strings. El total (subtotal, descuento e IVA del 19%) y el consumo de inventario están en funciones. El inventario es un `Map` y los repartidores un `listOf`; el pedido se asigna con `find`. `Cliente` y `Repartidor` heredan de `Usuario`. Los errores de precio o stock se capturan con `try-catch`, y las notas opcionales usan `?.`, `?:` y `let`.

## Semana 3

El correo debe cumplir `nombre@dominio.com`; si no, se vuelve a pedir. El teléfono válido se formatea a `+XX (XXX) XXX-XXXX`. La cantidad de productos debe estar entre 1 y 100, y las pizzas anotadas con `@Promocionable` reciben 15% extra si la fecha del pedido cae en el mes en curso. Reflection lista las propiedades y métodos de `Pedido`, `Producto` y `Cliente`. El operador `+` une dos pedidos y recalcula el total. `==`, `equals` y `hashCode` consideran iguales a dos clientes con el mismo nombre y correo, o a dos productos con el mismo nombre y categoría, y así se evitan duplicados. `Cliente` y `Pedido` se pueden desestructurar. La opción **5** del menú muestra juntas la combinación de pedidos, la desestructuración, los productos promocionables y la igualdad de objetos.
