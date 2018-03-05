package concurrente.aleatorio;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static concurrente.Utils.escribe;
import static concurrente.Utils.obtenDatos;

/**
 *
 * @author miguel
 */
public class ArbolAleatorio2_1 {

    private final Random _random;
    private final int[] _nodos;

    public ArbolAleatorio2_1(int tamaño) {
        _nodos = new int[tamaño];
        for (int i = 0; i < tamaño; i++) {
            _nodos[i] = -1;
        }
        _random = new Random();
    }

    public void agrega(int valor) {
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
        List<Integer> datos = obtenDatos("/tmp/datos.txt");
        ArbolAleatorio2_1 arbol = new ArbolAleatorio2_1(datos.size());
        System.out.println("Insertando");
        long milis = System.currentTimeMillis();
        datos.forEach((dato) -> {
            arbol.agrega(dato);
        });
        milis = System.currentTimeMillis() - milis;
        System.out.println("Tiempo " + milis + " ms");
        escribe("/tmp/aleatorio1.txt", arbol.toString());
    }
}
