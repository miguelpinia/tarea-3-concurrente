package concurrente.aleatorio;

import concurrente.Nodo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miguel
 */
public class ArbolAleatorio2 {

    private final ReentrantLock _lock = new ReentrantLock();
    private Nodo _raiz;

    public void agrega(Integer valor) {
        _lock.lock();
        _raiz = _agrega(_raiz, valor, 0);
        _lock.unlock();
    }

    private Nodo _agrega(Nodo nodo, Integer valor, int profundidad) {
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
                Nodo nuevo = _agrega(nodo.getDerecho(), valor, profundidad + 1);
                nodo.setDerecho(nuevo);
                return nodo;
            }
            Nodo nuevo = _agrega(nodo.getIzquierdo(), valor, profundidad + 1);
            nodo.setIzquierdo(nuevo);
            return nodo;
        }

    }

    public String inorden(Nodo nodo, Integer profundidad) {
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

    public String toString() {
        return inorden(_raiz, 0);
    }

    public ArrayList<Integer> generaDatos() {
        ArrayList<Integer> datos = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            datos.add(r.nextInt(1000000000));
        }
        return datos;
    }

    public static void main(String[] args) {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(6);
            ArbolAleatorio2 a = new ArbolAleatorio2();
            ArrayList<Integer> foo = new ArrayList<>(Arrays.asList(new Integer[]{10, 20, 30, 50, 1, 2, 3, 4, 100, 200, 300, 400}));
            int size = foo.size();
            Random r = new Random();
//            ArrayList<Integer> foo = a.generaDatos();
//            int size = foo.size();
            while (!foo.isEmpty()) {
                executor.submit(() -> {
                    if (!foo.isEmpty()) {
                        int random = r.nextInt(size);
                        int aleatorio = foo.get(random % size);
                        foo.remove(random % size);
                        a.agrega(aleatorio);
                    }
                });
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);

            if (executor.isShutdown()) {
                System.out.println(a);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ArbolAleatorio2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
