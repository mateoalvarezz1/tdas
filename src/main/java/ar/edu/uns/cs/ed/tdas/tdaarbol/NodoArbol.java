package ar.edu.uns.cs.ed.tdas.tdaarbol;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class NodoArbol<E> implements Position<E>{

    protected E elemento;
    protected NodoArbol<E> padre;
    protected PositionList<NodoArbol<E>> hijos;

    public NodoArbol(E elem, NodoArbol<E> papa){
        elemento = elem;
        padre = papa;
        hijos = new ListaDobleEnlazada<>();
    }

    public NodoArbol(E elem){
        elemento = elem;
        padre = null;
        hijos = new ListaDobleEnlazada<>();
    }

    public void setElemento(E elem){
        elemento = elem;
    }
    
    public NodoArbol<E> getPadre(){
        return padre;
    }

    public void setPadre(NodoArbol<E> p){
        padre = p;
    }

    public PositionList<NodoArbol<E>> getHijos(){
        return hijos;
    }


    @Override
    public E element() {
        return elemento;
    }

}