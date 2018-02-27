package concurrente.determinista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static concurrente.Utils.escribe;
import static concurrente.Utils.obtenDatos;

/**
 *
 * @author miguel
 */
public class ArbolDeterminista1 {

    private List<NodoDeterminista> _datos;
    private NodoDeterminista _raiz;

    public ArbolDeterminista1() {
        _datos = new ArrayList<>();
    }

    public void agrega(Integer val) {
        NodoDeterminista nodo = new NodoDeterminista(val);
        _datos.add(nodo);
        Collections.sort(_datos);
        _raiz = construye(_datos, 0, _datos.size() - 1);
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
        System.out.println("Ejecución de algoritmo determinista de 1 hilo.");
        System.out.println("Leyendo datos");
        List<Integer> datos = obtenDatos("/tmp/datos.txt");
        ArbolDeterminista1 a = new ArbolDeterminista1();
        System.out.println("# datos leídos: " + datos.size());
        long milis = System.currentTimeMillis();
        datos.forEach((val) -> {
            a.agrega(val);
        });
        milis = System.currentTimeMillis() - milis;
        double segundos = milis / 1000;
        escribe("/tmp/determinista1.txt", a.toString());
        System.out.println(String.format("\n\nTiempo de ejecución: %.2f segundos", segundos));
    }

}
