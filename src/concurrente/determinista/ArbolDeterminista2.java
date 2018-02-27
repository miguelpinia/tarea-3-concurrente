/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente.determinista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class ArbolDeterminista2 {

    private final ReentrantLock _lock;
    private final List<NodoDeterminista> _datos;
    private NodoDeterminista _raiz;

    public ArbolDeterminista2() {
        _datos = new ArrayList<>();
        _lock = new ReentrantLock();
    }

    public void agrega(Integer val) {
        _lock.lock();
        try {
            NodoDeterminista nodo = new NodoDeterminista(val);
            _datos.add(nodo);
            Collections.sort(_datos);
            _raiz = construye(_datos, 0, _datos.size() - 1);
        } finally {
            _lock.unlock();
        }
    }

    private NodoDeterminista construye(List<NodoDeterminista> nodos, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        NodoDeterminista n = nodos.get(mid);
        n.setIzquierdo(construye(nodos, start, mid - 1));
        n.setDerecho(construye(nodos, mid + 1, end));
        return n;
    }

    private String inorden(NodoDeterminista nodo, Integer profundidad) {
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
        System.out.println("Ejecución de algoritmo determinista de 6 hilos.");
        ExecutorService executor = Executors.newFixedThreadPool(6);
        ArbolDeterminista2 a = new ArbolDeterminista2();
        System.out.println("Leyendo datos");
        List<Integer> datos = obtenDatos("/tmp/datos.txt");
        List<Future<Runnable>> futures = new ArrayList<>();
        System.out.println("# datos leídos: " + datos.size());
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
                Logger.getLogger(ArbolDeterminista2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });


        executor.shutdownNow();
        milis = System.currentTimeMillis() - milis;

        if (executor.isShutdown()) {
            double segundos = milis / 1000;
            escribe("/tmp/determinista2.txt", a.toString());
            System.out.println(String.format("\n\nTiempo de ejecución: %.2f segundos", segundos));
        }
    }

}
