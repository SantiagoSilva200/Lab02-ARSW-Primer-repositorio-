## Hecho por: Santiago Cordoba Y Santiago Silva

## Control de Hilos con Wait/Notify en PrimeFinder

Explicacion desarrollo ejercicio :


### Modificaciones en la Clase Control

Se agregó un objeto pauseLock de tipo Object que se utiliza como monitor para sincronizar los hilos.

Este objeto se pasa a cada instancia de PrimeFinderThread para que todos los hilos compartan el mismo monitor.

En el método run() de la clase Control, se implementó un ciclo infinito que cada TMILISECONDS realiza lo siguiente:

Detiene todos los hilos llamando al método pause() en cada instancia de PrimeFinderThread.

Calcula y muestra el número total de primos encontrados hasta el momento.

Espera a que el usuario presione ENTER para reanudar la ejecución.

Reanuda todos los hilos llamando al método resumeThread() y notificando a todos los hilos en espera con notifyAll().

### Modificaciones en la Clase PrimeFinderThread

Introducción de una Bandera de Pausa (paused):

Se agregó una variable booleana paused que indica si el hilo está en estado de pausa.

Esta bandera se utiliza en el método run para controlar cuándo el hilo debe esperar.

En el método run, antes de procesar cada número, el hilo verifica si está en estado de pausa. Si es así, entra en un bloque synchronized y llama a wait() en el objeto pauseLock, lo que suspende la ejecución del hilo hasta que sea notificado.

Los métodos pause() y resumeThread() se utilizan para cambiar el estado de la bandera paused y notificar a los hilos en espera.

### Ejecución del Programa

El programa comienza creando una instancia de Control y llamando a su método start(), lo que inicia la ejecución de todos los hilos.

Pausa y Reanudación Periódica:

Cada TMILISECONDS, el hilo principal (Control) detiene todos los hilos, muestra el número de primos encontrados, y espera a que el usuario presione ENTER para continuar.
