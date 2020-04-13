EJEMPLO MINIMO 1

La EETAC está interesada en construir un juego que permita la gestión de
jugadores y objetos (escudos, espadas, cofres, monedas, etc) . Por este
motivo, propone a sus alumnos de DSA la construcción de una prueba piloto
con las siguientes operaciones:
- Listado de usuarios ordenado alfabéticamente
- Añadir un usuario
- Modificar un usuario
- Consultar el número de usuarios que hay en el sistema
- Consultar información de un usuario (identificador, nombre, apellidos)
- Añadir un objeto sobre un usuario
- Consultar los objetos de un usuario (orden de inserción)
- Consultar el número de objetos de un usuario

SE PIDE:

PARTE I: 6 puntos
1.- Especificación del componente que implementará las operaciones descritas
anteriormente: (GameManager.java , interfaz Java)
2.- Implementación de una Fachada (patrón de diseño) que implemente el
interfaz definido previamente (GameManagerImpl.java, clase Java).
2.1 Elección de las estructuras de datos: HashMap para usuarios y una
implementación de List para los objetos de un usuario
2.3 La fachada deberá implementarse como un Singleton.
2.4 Todos los métodos deberán tener una TRAZA (a nivel de INFO) de
LOG4J que muestre el valor de los parámetros al inicio de los métodos y
al final. También debe contemplarse trazas de otros niveles (ERROR o
FATAL)
3.- Implementación de un test (JUNIT) sobre el componente desarrollado con
los siguientes puntos:
- método setUp que inicializa la estructura de datos
- método tearDown que libera los recursos. Tened en cuenta que un
singleton obliga a liberar los recursos de manera explícita (por ejemplo método
clear()) .
- método de test para añadir un usuario en el sistema y verificar el
número de usuarios.
- método de test para añadir un objeto asociado a un usuario y verificar
el número de objetos asociados a un usuario

PARTE II: 4 puntos
1.- Implementar un servicio REST que permita realizar las siguientes
operaciones:
• lista de usuarios
• añadir un usuario
• modificar un usuario
• consultar la información de un usuario
• consultar los objetos de un usuario
• añadir un objeto sobre un usuario
NOTA: El servicio debe utilizar el componente construido en el punto anterior
(GameManager)
2.- Validar con swagger el funcionamiento de la REST API
