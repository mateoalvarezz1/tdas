package ar.edu.uns.cs.ed.tdas.tdalista;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaDobleEnlazada<E> implements PositionList<E> {

    @SuppressWarnings("FieldMayBeFinal")
    private DNodo<E> head;
    @SuppressWarnings("FieldMayBeFinal")
    private DNodo<E> tail;
    private int cant;

    public ListaDobleEnlazada(){
        cant=0;
        head = new DNodo<>(null);
        tail = new DNodo<>(null, null, head);
        head.setSiguiente(tail);
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
    public Position<E> first() {
        if(isEmpty())
            throw new EmptyListException("Lista vacia");
        return head.getSiguiente();
    }

    @Override
    public Position<E> last() {
        if(isEmpty())
            throw new EmptyListException("Lista vacia");
        return tail.getAnterior();
    }

    @Override
    public Position<E> next(Position<E> p) {
        DNodo<E> nodo = checkPosition(p);
        if(nodo.getSiguiente()== tail)
            throw new BoundaryViolationException("Estas pidiendo el siguiente al ultimo");
        return nodo.getSiguiente();
    }

    @Override
    public Position<E> prev(Position<E> p) {
        DNodo<E> nodo = checkPosition(p);
        if(nodo.getAnterior()==head)
            throw new BoundaryViolationException("Estas pidiendo el anterior al primero");
        return nodo.getAnterior();
    }

    @Override
    public void addFirst(E element) {
        DNodo<E> pviejo = head.getSiguiente();
        DNodo<E> nuevo = new DNodo(element,pviejo,head);
        pviejo.setAnterior(nuevo);
        head.setSiguiente(nuevo);
        cant++;
    }

    @Override
    public void addLast(E element) {
        DNodo<E> pviejo = tail.getAnterior();
        DNodo<E> nuevo = new DNodo(element,tail,pviejo);
        pviejo.setSiguiente(nuevo);
        tail.setAnterior(nuevo);
        cant++;
    }

    @Override
    public void addAfter(Position<E> p, E element) {
        DNodo<E> ag = checkPosition(p);
        DNodo<E> nuevo = new DNodo(element, ag.getSiguiente(), ag);
        ag.getSiguiente().setAnterior(nuevo);
        ag.setSiguiente(nuevo);
        cant++;
    }

    @Override
    public void addBefore(Position<E> p, E element) {
        DNodo ag = checkPosition(p);
        DNodo nuevo = new DNodo(element,ag,ag.getAnterior());
        ag.getAnterior().setSiguiente(nuevo);
        ag.setAnterior(nuevo);
        cant++;
    }

    @Override
    public E remove(Position<E> p) {
        DNodo<E> nodo = checkPosition(p);
        nodo.getAnterior().setSiguiente(nodo.getSiguiente());
        nodo.getSiguiente().setAnterior(nodo.getAnterior());
        E elemento = nodo.getElemento();
        nodo.setElemento(null);
        cant--;
        return  elemento;
    }

    @Override
    public E set(Position<E> p, E element) {
        DNodo<E> nodo = checkPosition(p);
        E elementoviejo = nodo.getElemento();
        nodo.setElemento(element);
        return elementoviejo;
    }

    @Override
    public Iterator<E> iterator() {
       return new ElementoIterator(this);
    }

    @Override
    public Iterable<Position<E>> positions() {
        ListaDobleEnlazada<Position<E>> l2 = new ListaDobleEnlazada();
        DNodo<E> actual= head.getSiguiente();
        while(actual != tail){
            l2.addLast(actual);
            actual= actual.getSiguiente(); 
        }
        return l2;
    }

    private DNodo<E> checkPosition(Position<E> p) {
        try {
            if (p == null)
                throw new InvalidPositionException("posicion nula");
            if (p.element() == null)
                throw new InvalidPositionException("posicion eliminada previamente");
            return (DNodo<E>) p;
        } catch (ClassCastException e) {
            throw new InvalidPositionException("p no es un nodo de lista");
        }
    
    }
    @SuppressWarnings("unused")
    private void elementounoydos(E e1, E e2){
        DNodo<E> ne1= new DNodo(e1);
        DNodo<E> ne2= new DNodo(e2);

        //si tiene varios elementos
        if(size()>1){
            DNodo<E> primero = head.getSiguiente();
            DNodo<E> segundo = primero.getSiguiente();
            DNodo<E> ultimo = tail.getAnterior();
            DNodo<E> anteultimo = ultimo.getAnterior();
 
            primero.setSiguiente(ne1);
            ne1.setAnterior(primero);
            ne1.setSiguiente(segundo);
            segundo.setAnterior(ne1);

            ultimo.setAnterior(ne2);
            ne2.setSiguiente(ultimo);
            ne2.setAnterior(anteultimo);
            anteultimo.setSiguiente(ne2);

        }
        //si tiene un elemento
        if(size()==1){
            head.getSiguiente().setSiguiente(ne1);
            tail.setAnterior(ne2);
            ne1.setSiguiente(ne2);
            ne1.setAnterior(head.getSiguiente());
            ne2.setAnterior(ne1);
            ne2.setSiguiente(tail);
        }
        //lista vacia
        if(isEmpty()){
            head.setSiguiente(ne1);
            tail.setAnterior(ne2);
            ne1.setSiguiente(ne2);
            ne2.setAnterior(ne1);
            ne1.setAnterior(head);
            ne2.setSiguiente(tail);
        }
        cant +=2;
    }

    public boolean contiene(E e1){
        Iterator<E> it = this.iterator();
        while(it.hasNext()) {
            E elem =  it.next();
            if(elem.equals(e1))
                return true;
            
        }
        return false;
    }

    public int cantveces(E e1){
        int cveces =0;
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            E elem =  it.next();
            if(elem.equals(e1))
                cveces++;
        }
        return cveces;
    }

    public boolean almenosn(E e1, int n){
        int cveces=0;
        Iterator<E> it=this.iterator();
        while(it.hasNext() && cveces<n){
            E elem = it.next();
            if(elem==e1)
                cveces++;
        }
        return cveces>=n;
    }

    public PositionList<E> repetido(PositionList<E> l){
        PositionList<E> nueva = new ListaDobleEnlazada();

        for(E elem: l){
            nueva.addLast(elem);
            nueva.addLast(elem);
        }

        return nueva;
    }

    public Iterable<E> interseccion(PositionList<E> l1, PositionList<E> l2){
        //utilizar metodo contiene casteando o recorriendo manualmente con un iterador
        PositionList<E> eliminados = new ListaDobleEnlazada();
        for(Position<E> p : l2.positions()){
            E elem = p.element();
            //preguntar por casteo
            if(((ListaDobleEnlazada) l1).contiene(elem)){
                eliminados.addLast(elem);
                l2.remove(p);
            }
        }
        return eliminados;
    }


    //clase dentro de clase
    public class ElementoIterator implements Iterator<E>{

    @SuppressWarnings("FieldMayBeFinal")
    private PositionList<E> lista;
    private  Position<E> cursor;

    public ElementoIterator(PositionList<E> l){
        lista = l;
        if(l.isEmpty())
            cursor = null;
        else
            cursor = l.first();
    }

    @Override
    public boolean hasNext() {
        return cursor!=null;
    }

    @Override
    public E next() {
        if(cursor==null) throw new NoSuchElementException("no hay mas elementos");

        E element= cursor.element();

        if(cursor!= last()) 
            cursor = lista.next(cursor);
        else 
            cursor = null;

        return element;

    }
    
}

}