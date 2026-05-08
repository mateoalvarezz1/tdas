package ar.edu.uns.cs.ed.tdas.tdamapeo;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.Entry;

public class MapeoConHashAbierto<K,V> implements Map<K,V>{

    protected MapeoConLista<K,V> [] A;
    protected int n;
    protected int N;

    @SuppressWarnings("unchecked")
    public MapeoConHashAbierto(){
        A = new MapeoConLista[11];
        n=0;
        N=11;
    }
    @SuppressWarnings("unchecked")
    public MapeoConHashAbierto(int t){
        A = new MapeoConLista[t];
        N = t;
        n=0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public boolean isEmpty() {
        return n==0;
    }

    @Override
    public V get(K key) {
        return A[h(key)].get(key);
    }

    @Override
    public V put(K key, V value) {
        V valor = A[h(key)].put(key, value);
        if(valor == null) n++;
        return valor;
    }

    @Override
    public V remove(K key) {
        V valor = A[h(key)].remove(key);
        if(valor!=null) n--;
        return valor;
    }

    @Override
    public Iterable<K> keys() {
        @SuppressWarnings("Convert2Diamond")
        ListaDobleEnlazada<K> lista = new ListaDobleEnlazada<K>();
        for(int i=0;i<N;i++){
            for(K key : A[i].keys()){
                lista.addLast(key);
            }
        }
        return lista;
    }

    @Override
    public Iterable<V> values() {
        @SuppressWarnings("Convert2Diamond")
        ListaDobleEnlazada<V> lista = new ListaDobleEnlazada<V>();
        for(int i=0; i<N;i++){
        for(V valor : A[i].values()){
            lista.addLast(valor);
        }
       }
       return lista;
    }

    @Override
    public Iterable<Entry<K,V>> entries() {
        @SuppressWarnings("Convert2Diamond")
        ListaDobleEnlazada<Entry<K,V>> lista = new ListaDobleEnlazada<Entry<K,V>>();
        for(int i=0;i<N;i++){
            for(Entry<K,V> e : A[i].entries()){
                lista.addLast(e);
            }
        }
        return lista;
    }

    private int h(K key) {
        return Math.abs(key.hashCode()) % N;
    }
    
}
