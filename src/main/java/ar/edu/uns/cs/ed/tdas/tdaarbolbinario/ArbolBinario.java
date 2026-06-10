package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Dictionary;
import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.DiccionarioHashAbierto;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class ArbolBinario<E> implements BinaryTree<E>{

    protected Nodo<E> raiz;
    protected int size;

    public ArbolBinario(){
        raiz=null;
        size=0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public Iterator<E> iterator() {
        PositionList<E> elementos = new ListaDobleEnlazada<E>();

        for(Position<E> p : positions()){
            elementos.addLast(p.element());
        }

        return elementos.iterator(); 
    }

    @Override
    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> lista = new ListaDobleEnlazada<Position<E>>();

        if(!isEmpty()){
            preorden(raiz,lista);
        }

        return lista;
    }

    private void preorden(Nodo<E> nodo, PositionList<Position<E>> lista){
        lista.addLast(nodo);

        if(nodo.getLeft() != null){
            preorden(nodo.getLeft(), lista);
        }

        if(nodo.getRight() != null){
            preorden(nodo.getRight(), lista);
        }
    }

    @Override
    public E replace(Position<E> v, E e) {
        Nodo<E> nodo = checkPosition(v);

        E viejo = nodo.element();
        nodo.setElement(e);
        return viejo;
    }

    private Nodo<E> checkPosition(Position<E> p){
        try{
            if(p==null){
                throw new InvalidPositionException("posicion invalida");
            }
            if(p.element()==null){
                throw new InvalidPositionException("posicion invalida");
            }
            return(Nodo<E>) p;
        }catch (ClassCastException e){
            throw new InvalidPositionException("posicion invalida");
        }
    }

    @Override
    public Position<E> root() {
        if(isEmpty()){
            throw new EmptyTreeException("arbol vacio");
        }
        else
            return raiz;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        Nodo<E> nodo = checkPosition(v);
        if(raiz==nodo){
            throw new BoundaryViolationException("posicion pasada por parametro corresponde a raiz del arbol");
        }
        return nodo.parent;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> v) {
        Nodo<E> nodo = checkPosition(v);
        PositionList<Position<E>> lista = new ListaDobleEnlazada<Position<E>>();
        if(nodo.getLeft() != null){
            lista.addLast(nodo.getLeft());
        }

        if(nodo.getRight() != null){
            lista.addLast(nodo.getRight());
        }

        return lista;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        //es interno si tiene izquierdo o tiene derecho
        Nodo<E> nodo = checkPosition(v);

        return nodo.getLeft() != null || nodo.getRight() != null;
    }

    @Override
    public boolean isExternal(Position<E> v) {
        return !isInternal(v);
    }

    @Override
    public boolean isRoot(Position<E> v) {
        Nodo<E> nodo = checkPosition(v);
        return nodo==raiz;
    }

    @Override
    public void createRoot(E e) {
        if(raiz!=null){
            throw new InvalidOperationException("el arbol ya tiene una raiz");
        }
        raiz = new Nodo<>(e, null, null, null);
        size=1;
    }

    @Override
    public Position<E> addFirstChild(Position<E> p, E e) {
        return addLeft(p,e);
    }

    @Override
    public Position<E> addLastChild(Position<E> p, E e) {
        return addRight(p, e);
    }

    @Override
    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) {
        if(isEmpty()){
            throw new InvalidPositionException("Empty tree");
        }
        Nodo<E> padre = checkPosition(p);
        Nodo<E> hermanoDerecho = checkPosition(rb);

        if(hermanoDerecho.getParent() != padre){
            throw new InvalidOperationException("rb no es hijo de p");
        }

        if(padre.getLeft() != null && padre.getRight() != null){
            throw new InvalidOperationException("El nodo ya tiene dos hijos");
        }

        Nodo<E> nuevo = new Nodo<E>(e,padre,null,null);

        if(padre.getRight() == hermanoDerecho){
            //rb es el hijo del derecho, entonces el nuevo va a la izquierda
            padre.setLeft(nuevo);
        }
        else{
            //rb es el hijo izquierdo, el viejo izquierdo pasa a ser derecho
            padre.setRight(hermanoDerecho);
            padre.setLeft(nuevo);
        }

        size++;
        return nuevo;
    }

    @Override
    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }

        Nodo<E> padre = checkPosition(p);
        Nodo<E> hermanoIzquierdo = checkPosition(lb);

        if(hermanoIzquierdo.getParent()!= padre){
            throw new InvalidOperationException("LB no es hijo de p");
        }

        if(padre.getLeft() != null && padre.getRight() != null){
            throw new InvalidOperationException("Ya tiene dos hijos");
        }

        Nodo<E> nuevo = new Nodo<E>(e,padre,null,null);

        if(padre.getLeft() == hermanoIzquierdo){
            //lb es el hijo izquierdo, entonces el nuevo lo pongo a la derecha
            padre.setRight(nuevo);
        }
        else{
            //lb es el hijo derecho, entonces lo movemos a la izquiedo y ponemos el nuevo a la derecha
            padre.setLeft(hermanoIzquierdo);
            padre.setRight(nuevo);
        }

        size++;
        return nuevo;
    }

    @Override
    public void removeExternalNode(Position<E> p) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }
        Nodo<E> nodo = checkPosition(p);

        if(nodo.getLeft() != null || nodo.getRight() != null){  //podriamos usar el isInteral() pero estariamos validando dos veces con checkposition
            throw new InvalidOperationException("Es interno");
        }

        removeNode(p);
    }

    @Override
    public void removeInternalNode(Position<E> p) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }

        Nodo<E> nodo = checkPosition(p);

        if(nodo.getLeft() == null && nodo.getRight() == null){
            throw new InvalidOperationException("Es externo");
        }

        removeNode(p);
    }

    @Override
    public void removeNode(Position<E> p) {
        //si el arbol eta vacio, no hay ninguna posicion para eliminar
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio, no se puede eliminar");
        }

        //validamos la posicion recibida y la convertimos al nodo real 
        Nodo<E> nodo = checkPosition(p);

        //guardamos las referencias a sus posibles hijos
        Nodo<E> hijoIzquierdo = nodo.getLeft();
        Nodo<E> hijoDerecho = nodo.getRight();

        //si tiene dos hijos, no podemos eliminarlo con esta operacion, el padre del nodo eliminado solo puede reemplazarlo por una unica referencia
        if(hijoIzquierdo != null && hijoDerecho != null){
            throw new InvalidPositionException("No se puede eliminar un nodo con dos hijos, el padre no puede apuntar a dos nodos, solo tiene una unica referencia");
        }

        //determinamos cual es el unico hijo del nodo si existe. Si el nodo es hoja, hijo queda en null
        Nodo<E> hijo;
        if(hijoIzquierdo != null){
            hijo = hijoIzquierdo;
        }else{
            hijo = hijoDerecho;
        }

        //Caso 1: el nodo a eliminar es la raiz
        if(nodo == raiz){
            //Si hijo es null, el arbol queda vacio. Si hijo no es null, ese hijo pasa a ser la raiz
            raiz = hijo;

            if(hijo != null){
                hijo.setParent(null);
            }
        }

        //Caso 2: el nodo a eliminar no es la raiz
        else{
            //obtenemos el padre del nodo que voy a eliminar
            Nodo<E> padre = nodo.getParent();

            //si el nodo era hijo izquierdo de su padre, el padre ahora debe apuntar al hijo del nodo eliminado, si el nodo era hoja, hijo vale null
            if(padre.getLeft() == nodo){
                padre.setLeft(hijo);
            }

            //si el nodo era hijo derecho de su padre, el padre ahora debe apuntar al hijo del nodo eliminado, si el nodo era hoja, hijo vale null
            else if(padre.getRight() == nodo){
                padre.setRight(hijo);
            }

            //si no aparece ni como hijo izquierdo ni como derecho , entonces las referencias internas del arbol estan mal armadas
            else{
                throw new InvalidPositionException("La estructura del arbol es invalida");
            }

            //si el nodo eliminado tenia un hijo, ese hijo ahora pasa a depender del padre del nodo eliminado
            if(hijo != null){
                hijo.setParent(padre);
            }
        }
        //actualizamos la cantidad
        size--;
    }

    @Override
    public Position<E> left(Position<E> v) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }

        Nodo<E> nodo = checkPosition(v);

        if(nodo.getLeft() == null){
            throw new BoundaryViolationException("v no tiene hijo izquierdo");
        }

        return nodo.getLeft();
    }

    @Override
    public Position<E> right(Position<E> v) {
         if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }
        Nodo<E> nodo = checkPosition(v);

        if(nodo.getRight() == null){
            throw new BoundaryViolationException("v no tiene hijo derecho");
        }

        return nodo.getRight();
    }

    @Override
    public boolean hasLeft(Position<E> v) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol Vacio");
        }

        Nodo<E> nodo = checkPosition(v);

        return nodo.getLeft() != null;
    }

    @Override
    public boolean hasRight(Position<E> v) {
         if(isEmpty()){
            throw new InvalidPositionException("Arbol Vacio");
        }

        Nodo<E> nodo = checkPosition(v);

        return nodo.getRight() != null;
    }

    @Override
    public Position<E> addLeft(Position<E> v, E r) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }

        Nodo<E> padre = checkPosition(v);

        if(padre.getLeft() != null){
            throw new InvalidOperationException("ya tiene un hijo izquierdo");
        }

        Nodo<E> nuevo = new Nodo<E>(r, padre, null, null);
        padre.setLeft(nuevo);
        size++;

        return nuevo;
    }
    @Override
    public Position<E> addRight(Position<E> v, E r) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }

        Nodo<E> padre = checkPosition(v);

        if(padre.getRight() != null){
            throw new InvalidOperationException("ya tiene un hijo derecho");
        }

        Nodo<E> nuevo = new Nodo<E>(r,padre,null,null);
        padre.setRight(nuevo);
        size++;
        
        return nuevo;
    }

    @Override
    public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2) {
        if(isEmpty()){
            throw new InvalidPositionException("Arbol vacio");
        }

        Nodo<E> nodo = checkPosition(v);

        if(nodo.getLeft() != null || nodo.getRight() != null){
            throw new InvalidPositionException("La posicion es interna, necesitamos que sea hoja");
        }

        ArbolBinario<E> arbol1 = (ArbolBinario<E>) T1;
        ArbolBinario<E> arbol2 = (ArbolBinario<E>) T2;

        if(!arbol1.isEmpty()){
            nodo.setLeft(clonarSubArbol(arbol1.raiz, nodo));
            size += arbol1.size;
        }

        if(!arbol2.isEmpty()){
            nodo.setRight(clonarSubArbol(arbol2.raiz, nodo));
            size += arbol2.size;        
        }
    }

    private Nodo<E> clonarSubArbol(Nodo<E> nodoOriginal, Nodo<E> padreNuevo){
        //caso base de la recursion: si el nodo original es null, significa que no hay subarbol para copiar
        if(nodoOriginal == null){
            return null;
        }
        //creamos u nuevo nodo con el mismo elemento que el nodo original, el padre del nuevo nodo es padreNuevo.
        //sus hijos se inicializan en null. porque los vamos a copiar recursivamente despues
        Nodo<E> copia = new Nodo<E>(nodoOriginal.element(), padreNuevo, null, null);

        //copiamos recursivamente el subarbol izquierdo del nodo oridinal. La raiz de esa copia queda como hijo izquierdo del nodo copia
        //el padre de ese hijo izquierdo sera copia
        copia.setLeft(clonarSubArbol(nodoOriginal.getLeft(), copia));
        //copiamos recursivamente el subarbol derecho del nodo oridinal. La raiz de esa copia queda como hijo derecho del nodo copia
        //el padre de ese hijo izquierdo sera copia
        copia.setRight(clonarSubArbol(nodoOriginal.getRight(), copia));

        return copia;
    }

    //Ejercicio 2:Agregue un método a la clase árbol binario programada en el inciso anterior tal que recorra el árbol en pre-orden y
    //retorne un diccionario donde sus entradas tengan como clave al rótulo del padre y como valor a los rótulos de cada
    //uno de sus hijos. Los rótulos ubicados en hojas del árbol no deben pertenecer al diccionario, de esta forma en el
    //diccionario no pueden existir valores nulos.

    public DiccionarioHashAbierto<E,E> padreEhijos(){
        DiccionarioHashAbierto<E,E> dic = new DiccionarioHashAbierto<E,E>();

        if(!isEmpty()){
            padreEHijosEnPreorden(raiz,dic);
        }

        return dic;
    }

    private void padreEHijosEnPreorden(Position<E> p, DiccionarioHashAbierto<E,E> dic){
        //visitamos el nodo actual
        //para cdada hijo de p, agregamos la entrada:
        //clave = elemento del padre
        //valor = elemento del hijo
        for(Position<E> hijo : children(p)){
            dic.insert(p.element(), hijo.element());
        }

        //recorrido recursivo en preoden:
        //despues de visitar p, recorro cada hijo de p
        for(Position<E> hijo : children(p)){
            padreEHijosEnPreorden(hijo, dic);
        }
    }

    //Ejercicio 3: Un árbol binario de expresión es un árbol binario donde cada uno de sus nodos
    //internos están rotulados con operadores y sus hojas con operandos 
    //Dado un árbol binario A que representa una expresión aritmética, escriba un método
    //recursivo (es decir, no puede usar los iteradores) que retorne un iterable de caracteres
    //con la notación infija de la expresión que el árbol representa.

    public Iterable<Character> exprecionInfija(ArbolBinario<Character> a){
        PositionList<Character> resultado = new ListaDobleEnlazada<Character>();

        if(!a.isEmpty()){
            exprecionInfija(a,a.root(),resultado);
        }

        return resultado;
    }

    private void exprecionInfija(ArbolBinario<Character> a, Position<Character> p, PositionList<Character> resultado){
        //caso base: si p es una hoja, entonces es un operando, se agrega directamente al resultado
        if(a.isExternal(p)){
            resultado.addLast(p.element());
        }

        //caso recursivo: si p es interno, entonces contiene un operador (+, -, *, /).
        //para notacion infija recorremos:
        //subArbol izquierdo -> operador -> subArbol derecho
        else{
            //agregamos el parentecis de apertura para marcar el comienzo
            resultado.addLast('(');
            //recorremos recursivamente el subarbol izquiero
            exprecionInfija(a, a.left(p),resultado);
            //despues agregamos el elemento del nondo actual, como es interno, el elemento es un operador
            resultado.addLast(p.element());
            //recorremos recursivamente el subarbol derecho
            exprecionInfija(a,a.right(p), resultado);
            //agregamos el parentecis de cierre para marcar el final de la subexpresion
            resultado.addLast(')');
        }
    }

    //Ejercicio 4
    public void completarDerechos(E r, BinaryTree<E> t){
        if(isEmpty()){
            throw new EmptyTreeException("Arbol vacio");
        }

        completarDerechosAux(r,t,t.root());
    }

    private void completarDerechosAux(E r, BinaryTree<E> t, Position<E> p){
        if(t.hasLeft(p) && !t.hasRight(p)){
            t.addRight(p, r);
        }

        if(t.hasRight(p)){
            completarDerechosAux(r, t, t.right(p));
        }
    }





    
}
