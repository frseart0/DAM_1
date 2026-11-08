# Semana 1
En esta primera semana realizarás una actividad formativa individual llamada "Mi primera app en Kotlin" donde, a través de una problemática planteada, deberás implementar una solución para una serie de requerimientos que se necesitan para crear una aplicación utilizando lenguaje Kotlin.

Deberás aplicar los conceptos como tipos de variables, operadores, utilidades, entre otros, y cumpliendo con una serie de requerimientos planteados en el caso de negocio.

## Descripción de la actividad

Una pizzería busca digitalizar su operación diaria mediante una aplicación móvil que facilite la gestión de pedidos, el registro de clientes, la coordinación de repartidores y el envío de notificaciones automáticas a los clientes. El objetivo es optimizar el servicio y mejorar la experiencia del cliente con una solución rápida y eficiente.

Para lograrlo, se te solicita desarrollar los fundamentos de una aplicación en Kotlin que permita:

- Registrar la información básica de los clientes, productos (pizzas) y pedidos.
- Calcular el total de cada pedido y aplicar descuentos cuando corresponda.
- Verificar la disponibilidad de ingredientes y gestionar el inventario.
- Coordinar la asignación de pedidos a repartidores de forma efectiva.

## Requerimientos

A continuación, se describen las tareas específicas a realizar para completar la actividad.

Usa IntelliJ IDEA para llevar a cabo las siguientes acciones en Kotlin.

### Paso 1: Declaración de variables y tipos de datos**

Define las variables necesarias para almacenar la información básica de los clientes, pizzas y pedidos. Considera los siguientes atributos para cada entidad:

- **Cliente**:
	- nombre: Nombre del cliente.
	- direccion: Dirección de entrega.
	- telefono: Número de contacto del cliente.
- **Pizza**:
	- nombrePizza: Nombre de la pizza (por ejemplo, “Pepperoni” o “Cuatro Quesos”).
	- precioUnitario: Precio de cada pizza.
	- ingredientesDisponibles: Cantidad disponible de ingredientes principales.
- **Pedido**:
	- idPedido: Identificador único del pedido.
	- cantidadPizzas: Cantidad de pizzas en el pedido.
	- totalPedido: Total calculado del pedido después de aplicar descuentos.
	- estadoPedido: Estado del pedido (por ejemplo, “Preparación”, “En Camino”, “Entregado”).

Selecciona el tipo de dato adecuado para cada variable y utiliza var o val según la necesidad de mutabilidad.

### Paso 2: Entrada y salida de datos (I/O)

- Permite la entrada de datos para registrar la información de un nuevo cliente.
- Solicita al usuario que ingrese el nombre del cliente, su dirección de entrega y su número de contacto. Luego, muestra la información registrada en pantalla.
- Gestiona la captura de errores en la entrada de datos, especialmente para datos como el teléfono. Asegúrate de que el sistema pueda manejar entradas nulas o incorrectas, asignando un valor predeterminado si es necesario.

### Paso 3: Cálculo del total del pedido con descuento

- Una vez ingresados los datos de la pizza y el pedido, calcula el total multiplicando el precio unitario por la cantidad solicitada.
- Aplica un descuento del 10% en pedidos que superen un total de tres pizzas. Define el cálculo de descuento utilizando expresiones en Kotlin. Evalúa la cantidad de pizzas solicitadas para decidir si el descuento aplica y actualiza el total del pedido en consecuencia.

### Paso 4: Verificación de stock

- La aplicación debe verificar que haya suficientes ingredientes para preparar el pedido antes de proceder. Si la cantidad de ingredientes disponibles es suficiente, reduce el inventario y confirma el pedido. Si no hay suficientes ingredientes, muestra un mensaje indicando que el pedido no puede ser completado.
- Usa una estructura de decisión (if como expresión) para evaluar la disponibilidad de ingredientes y para reducir el inventario en caso de que el pedido sea válido. Asegúrate de mostrar un mensaje adecuado tanto en caso de éxito como de falta de ingredientes.

### Paso 6: Resumen del Pedido

Finalmente, genera un resumen del pedido en Main.kt con la siguiente información:

- **Datos del cliente** (nombre, dirección de entrega y teléfono).
- **Información de la pizza** (nombre y precio).
- **Detalles del pedido** (cantidad de pizzas, total después del descuento, estado del pedido y repartidor asignado).

### Paso 7: Presentación y entrega

Muestra este resumen de forma ordenada y clara para que sirva como confirmación para el cliente y la Pizzería.

Como entrega, debes comprimir tu proyecto Kotlin en formato RAR o ZIP y subirlo en plataforma.

# Semana 2
En esta segunda semana, realizarás una actividad formativa individual, llamada “Aplicando utilidades de Kotlin”, en la cual deberás analizar un caso práctico de negocio y desarrollar una aplicación móvil utilizando las utilidades de Kotlin. La solución que implementes debe asegurar una lógica sólida y eficiente, cumpliendo con todos los requisitos del caso planteado.

Deberás aplicar los conceptos como tipos de variables, operadores, utilidades, entre otros, y cumpliendo con una serie de requerimientos planteados en el caso de negocio.

## Descripción de la actividad

Una pizzería local necesita desarrollar una aplicación móvil que gestione su operación.

El sistema deberá incluir funcionalidades que optimicen la gestión de pedidos, el registro de clientes, el manejo de inventarios y la coordinación de repartidores, aplicando herramientas como flujos de control, arreglos, strings, funciones, colecciones, programación orientada a objetos (POO) y manejo de errores.

## Requerimientos
A continuación, se describen las tareas específicas a realizar para completar la actividad.

### Paso 1: Flujos de control, arreglos y strings
Optimiza el procesamiento de datos relacionados con los clientes, productos y pedidos, utilizando estructuras iterativas y manipulaciones avanzadas de strings.

1. Flujos de Control:
    - Implementa un sistema que evalúe la disponibilidad de ingredientes antes de aceptar un pedido. Usa un when para verificar si los ingredientes necesarios están disponibles en el inventario. Si faltan ingredientes, muestra un mensaje indicando cuáles son insuficientes y detalla los pasos para reabastecerlos.
2. Arreglos:
    - Almacena todos los pedidos del día en un arreglo y utiliza un ciclo (for) para generar un reporte que incluya: cliente, pizzas pedidas, total del pedido y estado (En preparación, Listo, En camino). Además, agrupa los pedidos completados y pendientes utilizando métodos como filter.
3. Strings:
    - Valida el formato del número de teléfono y el correo electrónico del cliente al registrarlo. Si son incorrectos, utiliza un valor predeterminado (por ejemplo, "correo@invalido.com" o "(000) 000-0000"). Formatea la salida del resumen del pedido utilizando plantillas de strings ("$variable" y "${expresión}").

### Paso 2: Funciones y colecciones
Modulariza la lógica del sistema mediante funciones reutilizables y aprovecha las colecciones avanzadas para gestionar datos complejos.

1. Funciones:
    - Crea una función que calcule el total de un pedido, incluyendo impuestos y posibles descuentos (por ejemplo, un 10% si el cliente ordena más de 3 pizzas).
    - Diseña una función que, dada una lista de ingredientes, determine la cantidad necesaria para preparar un lote de pizzas y actualice automáticamente el inventario.
2. Colecciones:
    - Usa un Map para registrar la disponibilidad de ingredientes en el inventario (clave: nombre del ingrediente, valor: cantidad disponible).
    - Emplea un listOf para almacenar los repartidores disponibles y utiliza métodos como find para asignar un pedido al repartidor más cercano.

### Paso 3: Programación orientada a objetos (POO) en Kotlin
Refactoriza el sistema utilizando clases y objetos para modelar las entidades principales y encapsular la lógica asociada.

1. Clases y Objetos:
	- Define una clase Producto con atributos como nombre, precio e ingredientes. Incluye un método detallarProducto() que retorne una descripción completa del producto.
    - Implementa una clase Pedido con atributos como cliente, productos (lista de Producto), estado, y métodos para calcular el total y cambiar el estado del pedido.
2. Herencia y Polimorfismo:
    - Diseña una clase base Usuario con atributos comunes como nombre, contacto y especialízala en Cliente y Repartidor. Agrega métodos específicos como entregarPedido() en Repartidor.

### Paso 4: Manejo de errores y nulls
Asegura la robustez del sistema frente a errores de entrada y valores nulos.

1. Errores:
    - Utiliza try-catch para manejar errores al ingresar datos en el sistema, como un costo de pedido no válido o una cantidad negativa en el inventario. Muestra mensajes claros para que el usuario corrija los datos.
2. Nulls:
	- Emplea operadores seguros (?., ?:) para manejar campos opcionales como notas del pedido. Por ejemplo, si un cliente no agrega comentarios, usa "Sin notas" como predeterminado.
	- Utiliza let para ejecutar acciones solo cuando un valor no es nulo, como enviar notificaciones a clientes registrados.

### Paso 5: Resumen y ejecución
Al finalizar, El sistema deberá generar un resumen de cada pedido que incluya:

- **Datos del cliente:** Nombre, contacto (teléfono y correo electrónico).
- **Productos del pedido:** Nombre, precio, y cantidad.
- **Totales del pedido:** Subtotal, impuestos, descuentos aplicados y total final.
- **Estado del pedido:** En preparación, Listo, En camino.

### Paso 6: Presentación y entrega
Muestra este resumen de forma ordenada y clara para que sirva como confirmación para el cliente y la pizzería.
Como entrega debes comprimir tu proyecto Kotlin en formato RAR o ZIP y subirlo en plataforma.

# Semana 3
En esta tercera semana realizarás una actividad sumativa individual con encargo, llamada "Creando soluciones con Kotlin", donde utilizarás conceptos avanzados de Kotlin como programación orientada a objetos (POO), expresiones regulares (Regex), rangos (Ranges), anotaciones, etc., para crear una solución a través del análisis de un caso de negocio que te permita seleccionar la estructura más adecuada y entregar una aplicación que cumpla con los estándares de la industria.

## Descripción de la actividad
Esta evaluación es la continuación de los aprendizajes revisados en las semanas anteriores. Para esta actividad, ya cuentas con una aplicación básica funcional que permite gestionar entidades clave como clientes, productos (pizzas e ingredientes) y pedidos, utilizando estructuras fundamentales de Kotlin como flujos de control, arreglos, strings, funciones, colecciones y principios de programación orientada a objetos (POO).

Además, la aplicación debe incorporar manejo de errores y nulls, y ser capaz de validar datos, calcular totales con descuentos, y gestionar inventarios de manera básica.

## Requerimientos
A continuación, se describen las tareas específicas a realizar para completar la actividad.

### Paso 1: Regex y Ranges

**Regex:**
- Valida los correos electrónicos ingresados para los clientes, asegurándote de que siguen el formato estándar (nombre@dominio.com). Si el formato es incorrecto, muestra un mensaje de error y solicita al usuario que lo reingrese.
- Crea una función que procese números de teléfono de clientes y los formatee en un estilo uniforme (+XX (XXX) XXX-XXXX).

**Ranges:**
- Implementa un rango de fechas para verificar si un pedido se encuentra dentro de un periodo de promociones. Si está en el rango, aplica un descuento al total del pedido.
- Evalúa si el número ingresado de productos (por ejemplo, cantidad de pizzas o ingredientes adicionales) cae dentro de un rango permitido (1-100). Si no, muestra un mensaje de error y solicita al usuario que ingrese un valor válido.

### Paso 2: Anotaciones y reflection

**Anotaciones:**

- Crea una anotación personalizada @Promocionable para identificar pizzas o combos que califican para promociones especiales.
- Utiliza esta anotación para identificar dinámicamente cuáles productos son elegibles para promociones durante el procesamiento de pedidos.

**Reflection:**

- Implementa una funcionalidad que liste todos los métodos y propiedades de una clase (Pedido, Producto o Cliente) utilizando reflection. Esto permitirá analizar dinámicamente los atributos de las entidades clave de la pizzería.

### Paso 3: Operator Overloading

- Sobrecarga el operador + en la clase Pedido para combinar dos pedidos en uno solo, sumando los productos y recalculando el total, incluyendo posibles descuentos.

- Sobrecarga el operador == en la clase Producto para comparar si dos objetos representan el mismo producto, basado en su nombre y categoría (pizza, bebida o ingrediente).

### Paso 4: Declaraciones de desestructuración

- Implementa la funcionalidad para desestructurar un objeto Cliente y obtener directamente su nombre, correo electrónico y número de teléfono.
- Permite la desestructuración de un objeto Pedido para extraer el cliente, los productos incluidos y el total del pedido en variables individuales.

### Paso 5: Evaluaciones de igualdad de objetos

- Sobrescribe los métodos equals() y hashCode() en las clases Cliente y Producto para que dos objetos sean considerados iguales si sus atributos clave coinciden (por ejemplo, nombre y correo en Cliente o nombre y categoría en Producto).

- Utiliza estas evaluaciones para evitar duplicados al registrar clientes o productos en la aplicación.

### Paso 6: Resumen y ejecución

Genera un archivo de resumen en la consola que incluya:
- Pedidos combinados mediante la sobrecarga del operador +.
- Información de un cliente desestructurado (nombre, correo electrónico, teléfono).
- Productos identificados como promocionables mediante anotaciones.
- Validación de igualdad de objetos (clientes y productos)

Finalmente, ejecuta el programa principal (Main.kt) para demostrar cómo todas las funcionalidades trabajan en conjunto, integrando conceptos avanzados de Kotlin para una solución eficiente en la gestión de la pizzería.

### Paso 7: Presentación y entrega
Muestra este resumen de forma ordenada y clara para que sirva como confirmación para el cliente y la pizzería.
Como entrega debes comprimir tu proyecto Kotlin en formato RAR o ZIP y subirlo en plataforma.