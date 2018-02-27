package concurrente.aleatorio;

import concurrente.Utils;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static concurrente.Utils.escribe;

/**
 *
 * @author miguel
 */
public class ArbolAleatorio1 {

    private Nodo _raiz;

    public ArbolAleatorio1() {
        _raiz = null;
    }

    public void agrega(Integer valor) {
        _raiz = _agrega(_raiz, valor, 0);
    }

    public Nodo _agrega(Nodo nodo, Integer valor, int profundidad) {
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

    public String toString() {
        return inorden(_raiz, 0);
    }

    public static void main(String[] args) {
        System.out.println("---------------------------------------------------------");
        System.out.println("Ejecución de algoritmo aleatorio de 1 hilo.");
        System.out.println("Leyendo datos");
        List<Integer> datos = Utils.obtenDatos("/tmp/datos.txt");
        System.out.println("# datos leídos: " + datos.size());
        ArbolAleatorio1 a = new ArbolAleatorio1();
        long milis = System.currentTimeMillis();
        datos.forEach((val) -> {
            a.agrega(val);
        });
        milis = System.currentTimeMillis() - milis;
        double segundos = milis / 1000;
        escribe("/tmp/aleatorio1.txt", a.toString());
        System.out.println(String.format("\n\nTiempo de ejeución: %d milisegundos", milis));
    }

}
