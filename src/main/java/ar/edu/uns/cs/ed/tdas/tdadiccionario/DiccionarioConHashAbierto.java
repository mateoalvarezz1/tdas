package ar.edu.uns.cs.ed.tdas.tdadiccionario;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Entrada;

public class DiccionarioConHashAbierto<K,V> implements Dictionary<K, V>{

    protected PositionList<Entry<K,V>> [] A;
    protected int n;
    protected int N;

    public DiccionarioConHashAbierto(){
        A = (PositionList<Entry<K,V>>[]) new ListaDobleEnlazada[11];
        N = 11;
        n=0;
        for(int i=0; i < N;i++){
            A[i]= new ListaDobleEnlazada<>();
        }
    }

    public DiccionarioConHashAbierto(int T){
        A = (PositionList<Entry<K,V>>[]) new ListaDobleEnlazada[T];
        N = T;
        n=0;
        for(int i=0; i < N;i++){
            A[i]= new ListaDobleEnlazada<>();
        }
    }


    @Override
    public int size() {
       return n;
    }

    @Override
    public boolean isEmpty() {
        return n==0;
    }

    @Override
    public Entry<K, V> find(K key) {
        for(Entry<K,V> e : A[h(key)]){
            if(e.getKey().equals(key))
                return e;
        }
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> findAll(K key) {
        ListaDobleEnlazada<Entry<K,V>> lista = new ListaDobleEnlazada<>();
        for(Entry<K,V> e : A[h(key)]){
            if(e.getKey().equals(key)){
                lista.addLast(e);
            }
        }
        return lista;
    }

    @Override
    public Entry<K, V> insert(K key, V value) {
        Entry<K,V> var = new Entrada<>(key,value);
        A[h(key)].addLast(var);
        n++;
        return var;
    }

    @Override
    public Entry<K, V> remove(Entry<K, V> e) {
        for(Position<Entry<K,V>> p : A[h(e.getKey())].positions()){
            if(p.element()==e){
                A[h(e.getKey())].remove(p);
                n--;
                return e;
            }
        }
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        ListaDobleEnlazada<Entry<K,V>> lista = new ListaDobleEnlazada<>();
        for(int i=0; i < N;i++){
            for(Entry<K,V> e : A[i])
                lista.addLast(e);
        }
        return lista;
    }

    private int h(K key) {
        return Math.abs(key.hashCode()) % N;
    }

    public Iterable<Entry<K,V>> eliminarTodas(K c,V v) throws InvalidKeyException{
        if(c==null) throw new InvalidKeyException("clave nula");
        ListaDobleEnlazada<Entry<K,V>> eliminadas = new ListaDobleEnlazada<>();
        for(Position<Entry<K,V>> e: A[h(c)].positions()){
            if(e.element().getKey().equals(c) && e.element().getValue().equals(v)){
                eliminadas.addLast(e.element());
                A[h(c)].remove(e);
                n--;
            }
        }
        return eliminadas;
    }

}
1