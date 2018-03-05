/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente.aleatorio;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static concurrente.Utils.escribe;
import static concurrente.Utils.obtenDatos;

/**
 *
 * @author miguel
 */
public class ArbolAleatorio2_2 {

    private final Random _random;
    private final int[] _nodos;
    private ReentrantLock _lock = new ReentrantLock();

    public ArbolAleatorio2_2(int tamaño) {
        _nodos = new int[tamaño];
        for (int i = 0; i < tamaño; i++) {
            _nodos[i] = -1;
        }
        _random = new Random();
    }

    public void agrega(int valor) {
        _lock.lock();
        int aleatorio = _random.nextInt(_nodos.length);
        if (_nodos[aleatorio] == -1) {
            _nodos[aleatorio] = valor;
        } else {
            int val = (aleatorio + 1) % _nodos.length;
            while (_nodos[val] != -1 && val != aleatorio) {
                val = (val + 1) % _nodos.length;
            }
            _nodos[val] = valor;
        }
        _lock.unlock();
    }

    private int hijoIzq(int i) {
        return 2 * i + 1;
    }

    private int hijoDer(int i) {
        return 2 * i + 2;
    }

    private String inorden(int nodo, int profundidad) {
        if (nodo < _nodos.length) {
            if (_nodos[nodo] != -1) {
                String izquierda = inorden(hijoIzq(nodo), profundidad + 1);
                String valor = Integer.toString(_nodos[nodo]);
                String derecha = inorden(hijoDer(nodo), profundidad + 1);
                String profundidadTabs = String.join("", Collections.nCopies(profundidad, "\t"));
                return String.format("\n%s\n%s%s\n%s\n", derecha, profundidadTabs, valor, izquierda);
            } else {
                return "";
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return inorden(0, 0);
    }


    public static void main(String[] args) {
        try {

            final List<Integer> datos = obtenDatos("/tmp/datos.txt");
            ArbolAleatorio2_2 a = new ArbolAleatorio2_2(datos.size());
            int n = datos.size();
            final List<Integer> d1 = datos.subList(0, n / 6);
            final List<Integer> d2 = datos.subList(n / 6, (2 * n) / 6);
            final List<Integer> d3 = datos.subList((2 * n) / 6, (3 * n) / 6);
            final List<Integer> d4 = datos.subList((3 * n) / 6, (4 * n) / 6);
            final List<Integer> d5 = datos.subList((4 * n) / 6, (5 * n) / 6);
            final List<Integer> d6 = datos.subList((5 * n) / 6, n);
            Thread t1 = new Thread(() -> {
                d1.forEach((dato) -> {
                    a.agrega(dato);
                });
            });
            Thread t2 = new Thread(() -> {
                d2.forEach((dato) -> {
                    a.agrega(dato);
                });
            });
            Thread t3 = new Thread(() -> {
                d3.forEach((dato) -> {
                    a.agrega(dato);
                });
            });
            Thread t4 = new Thread(() -> {
                d4.forEach((dato) -> {
                    a.agrega(dato);
                });
            });
            Thread t5 = new Thread(() -> {
                d5.forEach((dato) -> {
                    a.agrega(dato);
                });
            });
            Thread t6 = new Thread(() -> {
                d6.forEach((dato) -> {
                    a.agrega(dato);
                });
            });
            long milis = System.currentTimeMillis();
            System.out.println("Iniciando");
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            milis = System.currentTimeMillis() - milis;
            System.out.println(String.format("Ejecución en %d ms", milis));
            escribe("/tmp/aleatorio2.txt", a.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(ArbolAleatorio2_2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
