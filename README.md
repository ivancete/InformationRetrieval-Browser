# Buscador de artículos científicos
- - -

## Resumen del proyecto

Este repositorio contiene el código fuente en JAVA de un buscador de documentos científicos procedentes de SCOPUS.

Este proyecto parte del anterior procesode indexación, detallado en [Repositorio con proyecto de Indexador](https://github.com/ivancete/InformationRetrieval-Indexer).

Para la finalización total del proyecto se realiza una interfaz de usuario para la búsqueda [imagen de la interfaz de usuario](https://github.com/ivancete/InformationRetrl-Browser/blob/master/Im%C3%A1genes/Iterfaz%20de%20usuario.PNG) con la librería Java FX.

En segundo lugar y más importante se realiza la implementación de las distintas consultas para la correcta obtención de los distintos artículos científicos. Esta es la parte más importante del buscador junto con el proceso de indexación, ya que dependiendo de estas dos acciones los usuarios obtendran unos artículos u otros en función de la consulta planteada.

Y por último se realiza la implementación de las distintas facetas analizadas a lo largo del proyecto.

## Índices

Los índices donde el buscador realizará las consultas se encuentran en los directorios:

[Índice Antiguo](https://github.com/ivancete/InformationRetrieval-Browser/tree/master/indice)

[Índice Nuevo](https://github.com/ivancete/InformationRetrieval-Browser/tree/master/indiceNuevo)

El índice antiguo está conformado por información de prueba, en donde simplemente se habñia indexado un total de 2.000 artículos, una cantidad ínfima para un buscador.

Y en cuanto al índice nuevo, nos encontramos con un total de 50.000 artículos indexados para la comprobación de rendimiento del propio buscador.

## Facetas

Para las facetas ocurre lo mismo que con los índices normales. Están repartidos en dos directorios:

[Índice Faceta Antiguo](https://github.com/ivancete/InformationRetrieval-Browser/tree/master/facetas)

[Índice Faceta Nuevo](https://github.com/ivancete/InformationRetrieval-Browser/tree/master/facetasNuevo)

## Bibliografía

>### Scopus
> https://www.scopus.com/freelookup/form/author.uri

>### Java FX
> http://www.oracle.com/technetwork/es/java/javafx/overview/index.html

>## Intellij IDEA
>https://www.jetbrains.com/idea/
