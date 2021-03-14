# jobTest

## tl;dr
- Shell: patrón de diseño command + patrón de comportamiento chain of responsibility
- Filesystem: estructura de nodos con hijos administrada por la clase Filesystem. Provee las primitivas del FS para los comandos.
- Unit Tests: coverage > 80%
- Extras: se persiste la estructura del FS en un archivo de texto
- Project: estructura como lo descargué de hackerrank, Maven, importado en IntelliJ IDEA
- Faltantes 
  - Mejorar el sistema de errores basados en strings 
  - Un poco de refactoring para evitar unos cuantos IFs pero mayormente son causados por
la necesidad de dar un output de que está sucediendo y centralizarlo en la respuesta del main.
  - Encapsular mejor la cadena de responsabilidad

## Detalles

Los strings ingresados son parseados por el mismo main, aunque podría haberse encapsulado esta lógica aparte.

El comando y los argumentos parseados son enviados al primer objeto de la cadena de responsabilidad (Shell).

Los comandos implementan una interface con un método parse donde encapsulan el comportamiento propio y al mismo tiempo 
identifican si fueron llamados o deben pasar la decisión al siguiente en la lista de comandos.

Algunos comandos tienen una referencia al FS ya que necesitan acceso al mismo

El FS tiene una estructura un poco precaria (para el ejercicio está bien, para la práctica no, se suelen usar otras estructuras mas performantes) de Nodos enlazados. 
Cada Nodo sabe cual es su padre y mantiene un listado de hijos.

Los tests se podrían mejorar generando valores aleatorios en cada pasada.
