package ar.edu.uns.cs.ed.tdas.tdaarbol;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import java.util.Iterator;

public class  Arbol<E> implements Tree<E>{

    protected NodoArbol<E> raiz;
    protected int cant;

    public Arbol(){
        raiz = null;
        cant = 0;
    }
    
    public Arbol(NodoArbol<E> nodo){
        raiz = nodo;
        cant = 1;
    }

    @Override
    public int size() {
        return cant;
    }

    @Override
    public boolean isEmpty() {
        return cant==0;
    }

    @Override
    public Iterator<E> iterator() {
        PositionList<E> ld = new ListaDobleEnlazada<>();
        for(Position<E> p : positions())
            ld.addLast(p.element());
        return ld.iterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
      PositionList<Position<E>> lista = new ListaDobleEnlazada<>();
      if(!isEmpty()) preorden(raiz, lista);
      return lista;
    }

    @Override
    public E replace(Position<E> v, E e) {
        NodoArbol<E> b = checkPosition(v);
        E elemento = b.element();
        b.setElemento(e);
        return elemento;
    }

    @Override
    public Position<E> root() {
        if(raiz==null) throw new EmptyTreeException("Arbol vacio");
        return raiz;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        NodoArbol<E> ve = checkPosition(v);
        if(ve==raiz) throw new BoundaryViolationException("La raiz no tiene padre");
        return ve.getPadre();
    }

    @Override
    public Iterable<Position<E>> children(Position<E> v) {
        NodoArbol<E> ve = checkPosition(v);
        ListaDobleEnlazada<Position<E>> lista = new ListaDobleEnlazada<>();
        for(NodoArbol<E> n : ve.getHijos()){
            lista.addLast(n);
        }
        return lista;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        NodoArbol<E> ve = checkPosition(v);
        return !ve.getHijos().isEmpty();
    }

    @Override
    public boolean isExternal(Position<E> v) {
        NodoArbol<E> ve = checkPosition(v);
        return ve.getHijos().isEmpty();
    }

    @Override
    public boolean isRoot(Position<E> v) {
        NodoArbol<E> ve = checkPosition(v);
        return ve==raiz;
    }

    @Override
    public void createRoot(E e) {
        if(raiz !=null || e==null) throw new InvalidOperationException("Ya tiene raiz");
        raiz = new NodoArbol<>(e);
        cant++;
    }

    @Override
    public Position<E> addFirstChild(Position<E> p, E e) {
        NodoArbol<E> ve = checkPosition(p);
        NodoArbol<E> hijo = new NodoArbol<>(e,ve);
        ve.getHijos().addFirst(hijo);
        cant++;
        return hijo;
    }

    @Override
    public Position<E> addLastChild(Position<E> p, E e) {
        NodoArbol<E> ve = checkPosition(p);
        NodoArbol<E> hijo = new NodoArbol<>(e,ve);
        ve.getHijos().addLast(hijo);
        cant++;
        return hijo;
    }

    @Override
    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) {
        NodoArbol<E> padre = checkPosition(p);
        NodoArbol<E> antes = checkPosition(rb);
        NodoArbol<E> insertar = new NodoArbol<>(e,padre);
        cant++;
        for(Position<NodoArbol<E>> n : padre.getHijos().positions()){
            if(n.element()==antes){
                padre.getHijos().addBefore(n, insertar);
            }
        }
        return insertar;
    }

    @Override
    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) {
        NodoArbol<E> padre = checkPosition(p);
        NodoArbol<E> desp = checkPosition(lb);
        NodoArbol<E> insertar = new NodoArbol<>(e,padre);
        cant++;
        for(Position<NodoArbol<E>> n : padre.getHijos().positions()){
            if(n.element()==desp){
                padre.getHijos().addAfter(n, insertar);
            }
        }
        return insertar;
    }

    @Override
    public void removeExternalNode(Position<E> p) {
        NodoArbol<E> pe = checkPosition(p);
        if(pe==raiz){
            raiz=null;
            cant--;
            return;
        }
        if(pe.getHijos().isEmpty()){
            for(Position<NodoArbol<E>> nodo : pe.getPadre().getHijos().positions()){
                if(nodo.element()==pe){
                    pe.getPadre().getHijos().remove(nodo);
                    cant--;
                }
            }
        }
    }

    @Override
    public void removeInternalNode(Position<E> p) {
        NodoArbol<E> pe = checkPosition(p);
         if(pe.getHijos().isEmpty()) throw new InvalidPositionException("Es externo");
         if(pe==raiz && pe.getHijos().size() > 1) throw new InvalidPositionException("La raiz tiene mas de un hijo");
         if(pe==raiz && pe.getHijos().size() == 1){
            raiz= pe.getHijos().first().element();
            raiz.setPadre(null);
            cant--;
         }
         if(pe!=raiz){
            //busco la posicion del nodo en la lista de hijos del padre e inserto todos los hijos del nodo a eliminar luego borro el nodo y decremento cant
           PositionList<NodoArbol<E>> hijoss = pe.getPadre().getHijos();
           for(Position<NodoArbol<E>> buscar : hijoss.positions()){
                if(buscar.element()==pe){
                    for(NodoArbol<E> h : pe.getHijos()){
                        hijoss.addBefore(buscar, h);
                        h.setPadre(pe.getPadre());
                    }
                    cant--;
                    hijoss.remove(buscar);
                    break;
                }
           }
         }
    }

    @Override
    public void removeNode(Position<E> p) {
        if(isExternal(p)) removeExternalNode(p);
        else removeInternalNode(p);
    }
    
    private NodoArbol<E> checkPosition(Position<E> p){
        if(p==null) throw new InvalidPositionException("p nulo");
        try {
            return (NodoArbol<E>) p;
        } catch(ClassCastException e) {
            throw new InvalidPositionException("posicion invalida");
        }
    }
    private void preorden(NodoArbol<E> nodo, PositionList<Position<E>> lista){
        lista.addLast(nodo);
        for(NodoArbol<E> p : nodo.getHijos()){
            preorden(p, lista);
        } 
    }

}