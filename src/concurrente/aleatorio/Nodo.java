package concurrente.aleatorio;

/**
 *
 * @author miguel
 */
public class Nodo {

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
