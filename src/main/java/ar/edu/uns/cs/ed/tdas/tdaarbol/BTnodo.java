package ar.edu.uns.cs.ed.tdas.tdaarbol;
import ar.edu.uns.cs.ed.tdas.Position;

public class BTnodo<E> implements Position<E>{

    protected E elemento;
    protected BTnodo<E> padre;
    protected BTnodo<E> right;
    protected BTnodo<E> left;

    public BTnodo(E elem, BTnodo<E> papa, BTnodo<E> rig, BTnodo<E> lef){
        elemento = elem;
        padre = papa;
        right=rig;
        left=lef;
    }

    public BTnodo(E elem){
        elemento = elem;
        padre = null;
        right=null;
        left=null;
    }

    public void setElemento(E elem){
        elemento = elem;
    }
    
    public BTnodo<E> getPadre(){
        return padre;
    }

    public void setPadre(BTnodo<E> p){
        padre = p;
    }

    public BTnodo<E> getLeft(){
        return left;
    }

    public BTnodo<E> getRight(){
        return right;
    }

    public void setLeft(BTnodo<E> d){
        left=d;
    }

    public void setRight(BTnodo<E> r){
        right=r;
    }


    @Override
    public E element() {
        return elemento;
    }

}