/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente;

import java.util.Collections;
import java.util.Random;

/**
 *
 * @author miguel
 */
public class ArbolAleatorio1 {

    Nodo _raiz;

    class Nodo {

        private Nodo _izq, _der;
        private final Integer _valor;

        public Nodo(Integer valor) {
            _valor = valor;
            _der = null;
            _izq = null;
        }

        public Nodo(Integer valor, Nodo der, Nodo izq) {
            _valor = valor;
            _der = der;
            _izq = izq;
        }

        public Integer getValor() {
            return _valor;
        }

        public Nodo getIzquierdo() {
            return _izq;
        }

        public Nodo getDerecho() {
            return _der;
        }

        public void setIzquierdo(Nodo izq) {
            _izq = izq;
        }

        public void setDerecho(Nodo der) {
            _der = der;
        }
    }

    public ArbolAleatorio1() {
        _raiz = null;
    }

    public void agrega(Integer valor) {
        _raiz = _agrega(_raiz, valor);
    }

    public Nodo _agrega(Nodo nodo, Integer valor) {
        Random r = new Random();
        if (nodo == null) {
//            System.out.println("a");
            nodo = new Nodo(valor);
            return nodo;
        }
        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
//            System.out.println("b");
            if (r.nextBoolean()) {
                nodo.setDerecho(new Nodo(valor));
                return nodo;
            }
            nodo.setIzquierdo(new Nodo(valor));
            return nodo;
        } else if (nodo.getDerecho() != null && nodo.getIzquierdo() == null) {
//            System.out.println("c");
            nodo.setIzquierdo(new Nodo(valor));
            return nodo;
        } else if (nodo.getIzquierdo() != null && nodo.getIzquierdo() == null) {
//            System.out.println("d");
            nodo.setDerecho(new Nodo(valor));
            return nodo;
        } else {
//            System.out.println("e");
            if (r.nextBoolean()) {
                Nodo nuevo = _agrega(nodo.getDerecho(), valor);
                nodo.setDerecho(nuevo);
                return nodo;
            }
            Nodo nuevo = _agrega(nodo.getIzquierdo(), valor);
            nodo.setIzquierdo(nodo);
            return nodo;
        }

    }

    public String inorden(Nodo nodo, Integer profundidad) {
        if (nodo != null) {
            System.out.println("Valor: " + nodo.getValor() + " profundidad: " + profundidad);
            String izquierda = inorden(nodo.getIzquierdo(), profundidad + 1);
            System.out.println("Izquierdo");
            String valor = nodo.getValor().toString();
            String derecha = inorden(nodo.getDerecho(), profundidad + 1);
            String profundidadTabs = String.join("", Collections.nCopies(profundidad, "\t"));
            return String.format("%s\n%s%s\n%s", izquierda, profundidadTabs, valor, derecha);
        } else {
            return "";
        }
    }

    public String toString() {
        return inorden(_raiz, 0);
    }

    public static void main(String[] args) {
        Integer[] foo = {10, 20, 30, 50, 1, 2, 3, 4, 100, 200, 300, 400};
        ArbolAleatorio1 a = new ArbolAleatorio1();
        for (Integer val : foo) {
            a.agrega(val);
        }
        System.out.println(a);
    }

}
