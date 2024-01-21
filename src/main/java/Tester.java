import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal que sirve como punto de entrada para ejecutar pruebas
 * con StaticHashTable y HashTable. Permite al usuario interactuar con las tablas hash
 * a través de una interfaz de menú en la consola.
 */
public class Tester {
    /**
     * Método principal que proporciona una interfaz de usuario para probar las funcionalidades
     * de las tablas hash. Permite insertar, reemplazar, eliminar, obtener y imprimir pares clave-valor,
     * así como ejecutar pruebas de eficiencia y pruebas predeterminadas.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el tamaño de la tabla hash:");
        int size = scanner.nextInt();
        StaticHashTable hashTable = new StaticHashTable(size, 0.7);
        int option;

        do {
            System.out.println("\nMENU");
            System.out.println("1. Insertar un par clave-valor");
            System.out.println("2. Reemplazar un par clave-valor existente");
            System.out.println("3. Eliminar un par clave-valor");
            System.out.println("4. Obtener el valor asociado a una clave");
            System.out.println("5. Imprimir todos los pares clave-valor");
            System.out.println("6. Testear la eficiencia de acceso a memoria");
            System.out.println("7. CORRER TEST DE TODO PREDETERMINADO");
            System.out.println("8. Comparar tiempos de búsqueda en fichero");
            System.out.println("9. Salir");
            System.out.println("Seleccione una opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    scanner.nextLine(); // Limpiar buffer
                    System.out.println("Ingrese la clave:");
                    String keyPut = scanner.nextLine();
                    System.out.println("Ingrese el valor:");
                    String valuePut = scanner.nextLine();
                    boolean putSuccess = hashTable.put(keyPut, valuePut);
                    if (putSuccess) {
                        System.out.println("Par clave-valor insertado con éxito.");
                    } else {
                        System.out.println("La tabla está llena o la inserción falló.");
                    }
                    break;

                case 2:
                    scanner.nextLine(); // Limpiar buffer
                    System.out.println("Ingrese la clave a reemplazar:");
                    String keyReplace = scanner.nextLine();
                    System.out.println("Ingrese el nuevo valor:");
                    String valueReplace = scanner.nextLine();
                    boolean replaceSuccess = hashTable.replace(new StaticHashTable.KeyValue(keyReplace, valueReplace));
                    if (replaceSuccess) {
                        System.out.println("Par clave-valor reemplazado con éxito.");
                    } else {
                        System.out.println("La clave no se encuentra en la tabla.");
                    }
                    break;

                case 3:
                    scanner.nextLine(); // Limpiar buffer
                    System.out.println("Ingrese la clave del par clave-valor a eliminar:");
                    String keyRemove = scanner.nextLine();
                    boolean removeSuccess = hashTable.remove(keyRemove);
                    if (removeSuccess) {
                        System.out.println("Par clave-valor eliminado con éxito.");
                    } else {
                        System.out.println("La clave no se encuentra en la tabla.");
                    }
                    break;

                case 4:
                    scanner.nextLine(); // Limpiar buffer
                    System.out.println("Ingrese la clave para obtener su valor:");
                    String keyGet = scanner.nextLine();
                    Object valueGet = hashTable.get(keyGet);
                    if (valueGet != null) {
                        System.out.println("Valor asociado a la clave '" + keyGet + "': " + valueGet);
                    } else {
                        System.out.println("La clave no se encuentra en la tabla.");
                    }
                    break;

                case 5:
                    hashTable.printAll();
                    break;
                case 6:
                    runEfficiencyTest();
                    break;
                case 7:
                    testStaticHashTable();
                    testDinamicHashTable();
                    break;
                case 8:
                    testFileSearchPerformance();
                    break;
                case 9:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        } while (option != 6);

        scanner.close();
    }

    /**
     * Método principal que proporciona una interfaz de usuario para probar las funcionalidades
     * de las tablas hash. Permite insertar, reemplazar, eliminar, obtener y imprimir pares clave-valor,
     * así como ejecutar pruebas de eficiencia y pruebas predeterminadas.
     *
     */
    private static void testStaticHashTable() {
        System.out.println("TESTING STATIC HASH TABLE");
        StaticHashTable hashTable = new StaticHashTable(10, 0.7);

        // Prueba de put y get
        hashTable.put("clave1", "valor1");
        hashTable.put("clave2", "valor2");
        hashTable.put("clave3", "valor3");
        hashTable.printAll();

        System.out.println("Prueba de put y get:");
        System.out.println("Clave1: " + hashTable.get("clave1"));
        System.out.println("Clave2: " + hashTable.get("clave2"));
        System.out.println("Clave3: " + hashTable.get("clave3"));
        hashTable.printAll();

        // Prueba de put con clave duplicada
        boolean result = hashTable.put("clave1", "nuevo_valor");
        System.out.println("\nPrueba de put con clave duplicada:");
        System.out.println("Resultado: " + result);
        System.out.println("Clave1 después de duplicada: " + hashTable.get("clave1"));
        hashTable.printAll();

        // Prueba de remove
        hashTable.remove("clave2");
        System.out.println("\nPrueba de remove:");
        System.out.println("Clave2 después de remove: " + hashTable.get("clave2"));

        // Prueba de replace
        hashTable.replace(new StaticHashTable.KeyValue("clave3", "nuevo_valor3"));
        System.out.println("\nPrueba de replace:");
        System.out.println("Clave3 después de replace: " + hashTable.get("clave3"));

        // Prueba de printAll
        System.out.println("\nPrueba de printAll:");
        hashTable.printAll();
    }

    /**
     * Ejecuta pruebas predeterminadas en HashTable para demostrar sus funcionalidades
     * y rendimiento. Incluye pruebas de inserción, obtención, reemplazo y eliminación de elementos,
     * así como impresión del estado actual de la tabla hash.
     */
    private static void testDinamicHashTable() {
        // Crear una HashTable con capacidad inicial y factor de carga máxima
        HashTable ht = new HashTable(5, 0.75);

        // Insertar elementos en la tabla hash
        System.out.println("Insertando elementos...");
        ht.put(new KeyValue("clave1", new Moto("jaja equis", "negro")));
        ht.put(new KeyValue("clave2", "valor2"));
        ht.put(new KeyValue("clave3", "valor3"));
        ht.put(new KeyValue("clave4", "valor4"));

        // Imprimir todos los elementos
        System.out.println("Elementos después de las inserciones:");
        ht.printAll();

        // Reemplazar un elemento
        System.out.println("Reemplazando 'clave2'...");
        ht.replace(new KeyValue("clave2", "valorNuevo2"));

        // Imprimir todos los elementos
        System.out.println("Elementos después del reemplazo:");
        ht.printAll();

        // Obtener un elemento
        System.out.println("Obteniendo el valor para 'clave3': " + ht.get("clave3"));

        // Eliminar un elemento
        System.out.println("Eliminando 'clave3'...");
        ht.remove("clave3");

        // Imprimir todos los elementos
        System.out.println("Elementos después de la eliminación:");
        ht.printAll();

        // Probar el rehashing insertando más elementos para superar el factor de carga
        System.out.println("Insertando más elementos para probar el rehashing...");
        ht.put(new KeyValue("clave5", "valor5"));
        ht.put(new KeyValue("clave6", "valor6")); // Esto debería provocar el rehashing

        // Imprimir todos los elementos
        System.out.println("Elementos después del rehashing:");
        ht.printAll();

        // Serializar y deserializar la tabla hash
        ht.serializarFichero();
        ht.deserializarFichero();
        System.out.println("Elementos después de la deserialización:");
        ht.printAll();

        // Escribir y leer la tabla hash en/desde un archivo de texto
        ht.escribirFicheroTexto();
        ht.leerFicheroTexto();
        System.out.println("Elementos después de leer el archivo de texto:");
        ht.printAll();
    }

    /**
     * Ejecuta una serie de pruebas de eficiencia en StaticHashTable y HashTable,
     * comparando el tiempo y la memoria utilizados en operaciones de inserción (put), reemplazo (replace),
     * obtención (get) y eliminación (remove) de elementos. También genera gráficos para visualizar
     * el rendimiento de las tablas hash en función del tamaño de las mismas.
     *
     * Realiza pruebas incrementando el tamaño de la tabla hash y mide los pasos de tiempo y memoria
     * para cada tamaño. Genera gráficos lineales para cada método (put, replace, get, remove) mostrando
     * la relación entre el tamaño de la tabla hash y los pasos de tiempo y memoria.
     */
    private static void runEfficiencyTest() {
        int hashTableSize = 10000;
        HashTable dinamicHashTable = new HashTable(100, 0.7);
        StaticHashTable staticHashTable = new StaticHashTable(100, 0.7);

        int[] randomKeys = populateRandomNumbers(hashTableSize);
        int[] randomNumbers = populateRandomNumbers(hashTableSize);
        int key, value;

        System.out.println("COMPARACIÓN METODO PUT --------------------------------------------------");
        for (int i = 0; i < randomKeys.length; i++) {
            key = randomKeys[i];
            value = randomNumbers[i];
            dinamicHashTable.put(new KeyValue(String.valueOf(key), value));
            staticHashTable.put(String.valueOf(key), value);
        }
        System.out.println("La lista dinámica ha necesitado: " + dinamicHashTable.getMemory() + " pasos de memoria y " + dinamicHashTable.getTime() + " pasos de tiempo ");
        System.out.println("La lista dinámica ha necesitado: " + staticHashTable.getMemory() + " pasos de memoria y " + staticHashTable.getTime() + " pasos de tiempo ");

        System.out.println("COMPARACIÓN METODO REPLACE --------------------------------------------------");
        randomNumbers = populateRandomNumbers(hashTableSize);
        for (int i = 0; i < randomKeys.length; i++) {
            key = randomKeys[i];
            value = randomNumbers[i];
            dinamicHashTable.replace(new KeyValue(String.valueOf(key), value));
            staticHashTable.replace(new StaticHashTable.KeyValue(String.valueOf(key), value));
        }
        System.out.println("La lista dinámica ha necesitado: " + dinamicHashTable.getMemory() + " pasos de memoria y " + dinamicHashTable.getTime() + " pasos de tiempo ");
        System.out.println("La lista dinámica ha necesitado: " + staticHashTable.getMemory() + " pasos de memoria y " + staticHashTable.getTime() + " pasos de tiempo ");

        System.out.println("COMPARACIÓN METODO GET --------------------------------------------------");
        int[] randomKeys2 = populateRandomNumbers(hashTableSize);
        for (int i = 0; i < randomKeys.length; i++) {
            key = randomKeys2[i];
            dinamicHashTable.get(String.valueOf(key));
            staticHashTable.get(String.valueOf(key));
        }
        System.out.println("La lista dinámica ha necesitado: " + dinamicHashTable.getMemory() + " pasos de memoria y " + dinamicHashTable.getTime() + " pasos de tiempo ");
        System.out.println("La lista dinámica ha necesitado: " + staticHashTable.getMemory() + " pasos de memoria y " + staticHashTable.getTime() + " pasos de tiempo ");

        System.out.println("COMPARACIÓN METODO REMOVE --------------------------------------------------");
        for (int i = 0; i < randomKeys.length; i++) {
            key = randomKeys[i];
            dinamicHashTable.remove(String.valueOf(key));
            staticHashTable.remove(String.valueOf(key));
        }
        System.out.println("La lista dinámica ha necesitado: " + dinamicHashTable.getMemory() + " pasos de memoria y " + dinamicHashTable.getTime() + " pasos de tiempo ");
        System.out.println("La lista dinámica ha necesitado: " + staticHashTable.getMemory() + " pasos de memoria y " + staticHashTable.getTime() + " pasos de tiempo ");

        System.out.println("A CONTINUACIÓN GENERANDO PRUEBAS EN PROFUNDIDAD");

        int initialSize = 100;  // tamaño inicial del array
        int maxSize = hashTableSize;    // tamaño máximo del array
        int stepSize = (int) Math.sqrt(hashTableSize) / 10;     // incremento del tamaño del array en cada iteración

        ListaDinamica timeStepsDynamic = new ListaDinamica(), memoryStepsDynamic = new ListaDinamica(), timeStepsStatic = new ListaDinamica(), memoryStepsStatic = new ListaDinamica();

        // TESTING PUT
        for (hashTableSize = initialSize; hashTableSize <= maxSize; hashTableSize += stepSize) {
            dinamicHashTable = new HashTable(100, 0.7);
            staticHashTable = new StaticHashTable(100, 0.7);

            randomKeys = populateRandomNumbers(hashTableSize);
            randomNumbers = populateRandomNumbers(hashTableSize);

            for (int i = 0; i < randomKeys.length; i++) {
                key = randomKeys[i];
                value = randomNumbers[i];
                dinamicHashTable.put(new KeyValue(String.valueOf(key), value));
                staticHashTable.put(String.valueOf(key), value);
            }
            timeStepsDynamic.addUltimo(dinamicHashTable.getTime());
            memoryStepsDynamic.addUltimo(dinamicHashTable.getMemory());
            timeStepsStatic.addUltimo(staticHashTable.getTime());
            memoryStepsStatic.addUltimo(staticHashTable.getMemory());
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        makeGraph(hashTableSize, timeStepsDynamic, memoryStepsDynamic, timeStepsStatic, memoryStepsStatic, dataset);

        // Crear el gráfico
        JFreeChart lineChart = ChartFactory.createLineChart("Comparación de Rendimiento Método: PUT", "Tamaño", "Pasos", dataset, PlotOrientation.VERTICAL, true, true, false);
// Configurar el eje Y como logarítmico
        CategoryPlot plot = (CategoryPlot) lineChart.getPlot();
        LogarithmicAxis logAxis = new LogarithmicAxis("Pasos (Log)");
        logAxis.setStrictValuesFlag(false); // Esto permite valores no estrictamente positivos
        plot.setRangeAxis(logAxis);
        // Configurar el eje X para que se ajuste al tamaño de la gráfica
        CategoryAxis xAxis = new CategoryAxis("Tamaño");
        xAxis.setCategoryMargin(0.0); // Espacio entre las categorías
        xAxis.setLowerMargin(0.0); // Margen izquierdo
        xAxis.setUpperMargin(0.0); // Margen derecho
        plot.setDomainAxis(xAxis);
        // Mostrar el gráfico en una ventana
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.add(chartPanel);
        frame.setTitle("Análisis de Rendimiento de Tablas Hash");
        frame.setSize(800, 600);
        frame.pack();
        frame.setVisible(true);

        // TESTING REPLACE
        timeStepsDynamic = new ListaDinamica();
        memoryStepsDynamic = new ListaDinamica();
        timeStepsStatic = new ListaDinamica();
        memoryStepsStatic = new ListaDinamica();

        for (hashTableSize = initialSize; hashTableSize <= maxSize; hashTableSize += stepSize) {
            dinamicHashTable = new HashTable(100, 0.7);
            staticHashTable = new StaticHashTable(100, 0.7);

            randomKeys = populateRandomNumbers(hashTableSize);
            randomNumbers = populateRandomNumbers(hashTableSize);

            for (int i = 0; i < randomKeys.length; i++) {
                key = randomKeys[i];
                value = randomNumbers[i];
                dinamicHashTable.replace(new KeyValue(String.valueOf(key), value));
                staticHashTable.replace(new StaticHashTable.KeyValue(String.valueOf(key), value));
            }
            timeStepsDynamic.addUltimo(dinamicHashTable.getTime());
            memoryStepsDynamic.addUltimo(dinamicHashTable.getMemory());
            timeStepsStatic.addUltimo(staticHashTable.getTime());
            memoryStepsStatic.addUltimo(staticHashTable.getMemory());
        }
        dataset = new DefaultCategoryDataset();

        makeGraph(hashTableSize, timeStepsDynamic, memoryStepsDynamic, timeStepsStatic, memoryStepsStatic, dataset);
        // Crear el gráfico
        lineChart = ChartFactory.createLineChart("Comparación de Rendimiento Método: REPLACE", "Tamaño", "Pasos", dataset, PlotOrientation.VERTICAL, true, true, false);
        // Configurar el eje Y como logarítmico
        plot = (CategoryPlot) lineChart.getPlot();
        logAxis = new LogarithmicAxis("Pasos (Log)");
        logAxis.setStrictValuesFlag(false); // Esto permite valores no estrictamente positivos
        plot.setRangeAxis(logAxis);
        // Configurar el eje X para que se ajuste al tamaño de la gráfica
        xAxis = new CategoryAxis("Tamaño");
        xAxis.setCategoryMargin(0.0); // Espacio entre las categorías
        xAxis.setLowerMargin(0.0); // Margen izquierdo
        xAxis.setUpperMargin(0.0); // Margen derecho
        plot.setDomainAxis(xAxis);
        // Mostrar el gráfico en una ventana
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame = new javax.swing.JFrame();
        frame.add(chartPanel);
        frame.setTitle("Análisis de Rendimiento de Tablas Hash");
        frame.setSize(800, 600);
        frame.pack();
        frame.setVisible(true);

        // TESTING GET
        timeStepsDynamic = new ListaDinamica();
        memoryStepsDynamic = new ListaDinamica();
        timeStepsStatic = new ListaDinamica();
        memoryStepsStatic = new ListaDinamica();

        for (hashTableSize = initialSize; hashTableSize <= maxSize; hashTableSize += stepSize) {
            dinamicHashTable = new HashTable(100, 0.7);
            staticHashTable = new StaticHashTable(100, 0.7);

            randomKeys = populateRandomNumbers(hashTableSize);

            for (int i = 0; i < randomKeys.length; i++) {
                key = randomKeys[i];
                dinamicHashTable.get(String.valueOf(key));
                staticHashTable.get(String.valueOf(key));
            }
            timeStepsDynamic.addUltimo(dinamicHashTable.getTime());
            memoryStepsDynamic.addUltimo(dinamicHashTable.getMemory());
            timeStepsStatic.addUltimo(staticHashTable.getTime());
            memoryStepsStatic.addUltimo(staticHashTable.getMemory());
        }
        dataset = new DefaultCategoryDataset();

        makeGraph(hashTableSize, timeStepsDynamic, memoryStepsDynamic, timeStepsStatic, memoryStepsStatic, dataset);
        // Crear el gráfico
        lineChart = ChartFactory.createLineChart("Comparación de Rendimiento Método: GET", "Tamaño", "Pasos", dataset, PlotOrientation.VERTICAL, true, true, false);
        // Configurar el eje X para que se ajuste al tamaño de la gráfica
        xAxis = new CategoryAxis("Tamaño");
        xAxis.setCategoryMargin(0.0); // Espacio entre las categorías
        xAxis.setLowerMargin(0.0); // Margen izquierdo
        xAxis.setUpperMargin(0.0); // Margen derecho
        // Configurar el eje Y como logarítmico
        plot = (CategoryPlot) lineChart.getPlot();
        logAxis = new LogarithmicAxis("Pasos (Log)");
        logAxis.setStrictValuesFlag(false); // Esto permite valores no estrictamente positivos
        plot.setRangeAxis(logAxis);
        plot.setDomainAxis(xAxis);
        // Mostrar el gráfico en una ventana
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame = new javax.swing.JFrame();
        frame.add(chartPanel);
        frame.setTitle("Análisis de Rendimiento de Tablas Hash");
        frame.setSize(800, 600);
        frame.pack();
        frame.setVisible(true);

        // TESTING REMOVE

        timeStepsDynamic = new ListaDinamica();
        memoryStepsDynamic = new ListaDinamica();
        timeStepsStatic = new ListaDinamica();
        memoryStepsStatic = new ListaDinamica();
        for (hashTableSize = initialSize; hashTableSize <= maxSize; hashTableSize += stepSize) {
            dinamicHashTable = new HashTable(100, 0.7);
            staticHashTable = new StaticHashTable(100, 0.7);

            randomKeys = populateRandomNumbers(hashTableSize);
            randomNumbers = populateRandomNumbers(hashTableSize);
            for (int i = 0; i < randomKeys.length; i++) {
                key = randomKeys[i];
                dinamicHashTable.remove(String.valueOf(key));
                staticHashTable.remove(String.valueOf(key));
            }
            for (int i = 0; i < randomKeys.length; i++) {
                key = randomKeys[i];
                value = randomNumbers[i];
                dinamicHashTable.put(new KeyValue(String.valueOf(key), value));
                staticHashTable.put(String.valueOf(key), value);
            }
            timeStepsDynamic.addUltimo(dinamicHashTable.getTime());
            memoryStepsDynamic.addUltimo(dinamicHashTable.getMemory());
            timeStepsStatic.addUltimo(staticHashTable.getTime());
            memoryStepsStatic.addUltimo(staticHashTable.getMemory());
        }
        dataset = new DefaultCategoryDataset();

        makeGraph(hashTableSize, timeStepsDynamic, memoryStepsDynamic, timeStepsStatic, memoryStepsStatic, dataset);
        // Crear el gráfico
        lineChart = ChartFactory.createLineChart("Comparación de Rendimiento Método: REMOVE", "Tamaño", "Pasos", dataset, PlotOrientation.VERTICAL, true, true, false);
// Configurar el eje X para que se ajuste al tamaño de la gráfica
        xAxis = new CategoryAxis("Tamaño");
        xAxis.setCategoryMargin(0.0); // Espacio entre las categorías
        xAxis.setLowerMargin(0.0); // Margen izquierdo
        xAxis.setUpperMargin(0.0); // Margen derecho
        // Configurar el eje Y como logarítmico
        plot = (CategoryPlot) lineChart.getPlot();
        logAxis = new LogarithmicAxis("Pasos (Log)");
        logAxis.setStrictValuesFlag(false); // Esto permite valores no estrictamente positivos
        plot.setRangeAxis(logAxis);
        plot.setDomainAxis(xAxis);
        // Mostrar el gráfico en una ventana
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        frame = new javax.swing.JFrame();
        frame.add(chartPanel);
        frame.setTitle("Análisis de Rendimiento de Tablas Hash");
        frame.setSize(800, 600);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Prueba y compara el rendimiento de búsqueda en una tabla hash en memoria y después de leer los datos desde un fichero.
     * Este método llena una tabla hash con datos de prueba, los escribe en un fichero,
     * y luego realiza búsquedas para comparar el tiempo que tarda en buscar una clave específica
     * en la tabla hash en memoria y después de leer los datos desde el fichero.
     *
     * Se asume que la clase HashTable tiene métodos para escribir y leer de fichero.
     */
    private static void testFileSearchPerformance() {
        HashTable hashTable = new HashTable(100, 0.7);

        // Llenar la tabla hash y escribir los datos en un fichero.
        for (int i = 0; i < 100; i++) {
            KeyValue kv = new KeyValue("clave" + i, "valor" + i); // Crear un nuevo objeto KeyValue
            hashTable.put(kv); // Insertar el objeto KeyValue en la tabla hash
        }
        hashTable.escribirFicheroTexto(); // Escribir en un fichero.

        // Leer los datos del fichero y buscar.
        hashTable.leerFicheroTexto(); // Leer de un fichero.

        // Medir el tiempo de búsqueda en memoria.
        long startTimeInMemory = System.nanoTime();
        hashTable.get("clave50"); // Buscar una clave específica.
        long endTimeInMemory = System.nanoTime();

        // Medir el tiempo de búsqueda después de leer del fichero.
        long startTimeInFile = System.nanoTime();
        hashTable.leerFicheroTexto(); // Leer de nuevo del fichero.
        hashTable.get("clave50"); // Buscar la misma clave.
        long endTimeInFile = System.nanoTime();

        // Comparar y mostrar los tiempos de búsqueda.
        System.out.println("Tiempo de búsqueda en memoria: " + (endTimeInMemory - startTimeInMemory) + " ns");
        System.out.println("Tiempo de búsqueda después de leer del fichero: " + (endTimeInFile - startTimeInFile) + " ns");
    }


    /**
     * Rellena un objeto DefaultCategoryDataset con los datos de pasos de tiempo y memoria
     * para dos tipos de tablas hash (dinámica y estática). Los datos son usados para generar
     * gráficos que muestran la comparación del rendimiento entre las dos implementaciones.
     *
     * @param hashTableSize      El tamaño de las tablas hash a graficar.
     * @param timeStepsDynamic   ListaDinamica que contiene los pasos de tiempo para la tabla hash dinámica.
     * @param memoryStepsDynamic ListaDinamica que contiene los pasos de memoria para la tabla hash dinámica.
     * @param timeStepsStatic    ListaDinamica que contiene los pasos de tiempo para la tabla hash estática.
     * @param memoryStepsStatic  ListaDinamica que contiene los pasos de memoria para la tabla hash estática.
     * @param dataset            El conjunto de datos (DefaultCategoryDataset) que se va a llenar con los datos de rendimiento.
     */
    private static void makeGraph(int hashTableSize, ListaDinamica timeStepsDynamic, ListaDinamica memoryStepsDynamic, ListaDinamica timeStepsStatic, ListaDinamica memoryStepsStatic, DefaultCategoryDataset dataset) {
        for (int i = 0; i < hashTableSize; i++) {
            dataset.addValue((Integer) (timeStepsDynamic.get(i)), "Tiempo Dinámica", "" + i);
            dataset.addValue((Integer) (memoryStepsDynamic.get(i)), "Memoria Dinámica", "" + i);
            dataset.addValue((Integer) timeStepsStatic.get(i), "Tiempo Estática", "" + i);
            dataset.addValue((Integer) memoryStepsStatic.get(i), "Memoria Estática", "" + i);
        }
    }

    /**
     * Genera un arreglo de números enteros aleatorios.
     *
     * @param count La cantidad de números aleatorios a generar.
     * @return Un arreglo que contiene 'count' números enteros aleatorios.
     */
    public static int[] populateRandomNumbers(int count) {
        int[] arr = new int[count];
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            arr[i] = rand.nextInt(); // Populate the array with random numbers
        }

        return arr;
    }
}
