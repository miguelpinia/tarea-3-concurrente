/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrente.determinista;

/**
 *
 * @author miguel
 */
public class NodoDeterminista implements Comparable<NodoDeterminista> {

    private Integer _valor;
    private NodoDeterminista _izquierdo;
    private NodoDeterminista _derecho;

    public NodoDeterminista(int valor) {
        _valor = valor;
        _izquierdo = null;
        _derecho = null;
    }

    public NodoDeterminista(int valor, NodoDeterminista izquierdo, NodoDeterminista derecho) {
        _valor = valor;
        _izquierdo = izquierdo;
        _derecho = derecho;
    }

    public Integer getValor() {
        return _valor;
    }

    public NodoDeterminista getIzquierdo() {
        return _izquierdo;
    }

    public NodoDeterminista getDerecho() {
        return _derecho;
    }

    public void setValor(Integer valor) {
        _valor = valor;
    }

    public void setIzquierdo(NodoDeterminista izquierdo) {
        _izquierdo = izquierdo;
    }

    public void setDerecho(NodoDeterminista derecho) {
        _derecho = derecho;
    }

    @Override
    public int compareTo(NodoDeterminista nodo) {
        return _valor.compareTo(nodo.getValor());
    }
}
