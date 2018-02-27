package concurrente.aleatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static concurrente.Utils.escribe;
import static concurrente.Utils.obtenDatos;

/**
 *
 * @author miguel
 */
public class ArbolAleatorio2 {

    private final ReentrantLock _lock = new ReentrantLock();
    private Nodo _raiz;

    /**
     * Agrega un valor al árbol.
     *
     * @param valor El valor a agregar.
     */
    public void agrega(Integer valor) {
        _lock.lock();
        try {
            _raiz = _agrega(_raiz, valor);
        } finally {
            _lock.unlock();
        }
    }

    /**
     * Función auxiliar para agregar valor VALOR al árbol definido por el nodo
     * NODO.
     *
     * @param nodo El nodo raíz.
     * @param valor EL valor a agregar.
     * @return EL nodo con el valor agregado.
     */
    private Nodo _agrega(Nodo nodo, Integer valor) {
        Random r = new Random();
        if (nodo == null) {
            nodo = new Nodo(valor);
            return nodo;
        }
        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
            if (r.nextBoolean()) {
                nodo.setDerecho(new Nodo(valor));
                return nodo;
            }
            nodo.setIzquierdo(new Nodo(valor));
            return nodo;
        } else if (nodo.getDerecho() != null && nodo.getIzquierdo() == null) {
            nodo.setIzquierdo(new Nodo(valor));
            return nodo;
        } else if (nodo.getIzquierdo() != null && nodo.getIzquierdo() == null) {
            nodo.setDerecho(new Nodo(valor));
            return nodo;
        } else {
            if (r.nextBoolean()) {
                Nodo nuevo = _agrega(nodo.getDerecho(), valor);
                nodo.setDerecho(nuevo);
                return nodo;
            }
            Nodo nuevo = _agrega(nodo.getIzquierdo(), valor);
            nodo.setIzquierdo(nuevo);
            return nodo;
        }

    }

    /**
     * Guarda en una cadena el recorrido en Inorden sobre el nodo Nodo, así como
     * indicar la profundidad del nodo.
     *
     * @param nodo EL nodo sobre el que se va a realizar el recorrido en
     * Inorden.
     * @param profundidad La profundidad a la que se encuentra el nodo.
     * @return La cadena con la representación del recorrido en Inorden.
     */
    private String inorden(Nodo nodo, Integer profundidad) {
        if (nodo != null) {
            String izquierda = inorden(nodo.getIzquierdo(), profundidad + 1);
            String valor = nodo.getValor().toString();
            String derecha = inorden(nodo.getDerecho(), profundidad + 1);
            String profundidadTabs = String.join("", Collections.nCopies(profundidad, "\t"));
            return String.format("%s\n%s%s\n%s\n", izquierda, profundidadTabs, valor, derecha);
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return inorden(_raiz, 0);
    }

    public static void main(String[] args) {
        System.out.println("---------------------------------------------------------");
        System.out.println("Ejecución de algoritmo aleatorio de 6 hilos.");
        ExecutorService executor = Executors.newFixedThreadPool(6);
        ArbolAleatorio2 a = new ArbolAleatorio2();
        System.out.println("Leyend datos");
        List<Integer> datos = obtenDatos("/tmp/datos.txt");
        System.out.println("# datos leídos: " + datos.size());
        List<Future<Runnable>> futures = new ArrayList<>();
        long milis = System.currentTimeMillis();
        datos.forEach((dato) -> {
            Future f = executor.submit(() -> {
                a.agrega(dato);
            });
            futures.add(f);
        });
        futures.forEach((f) -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ArbolAleatorio2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        executor.shutdownNow();

        if (executor.isShutdown()) {
            milis = System.currentTimeMillis() - milis;
            escribe("/tmp/aleatorio2.txt", a.toString());
            System.out.println(String.format("\n\nTiempo de ejeución: %d milisegundos", milis));
        }
    }

}
