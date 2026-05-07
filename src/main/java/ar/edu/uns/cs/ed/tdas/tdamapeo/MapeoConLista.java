package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;

public class MapeoConLista<K, V> implements Map<K, V>{

    protected ListaDobleEnlazada<Entry<K,V>> S;
    private int n;

    public MapeoConLista(){
        S = new ListaDobleEnlazada<>();
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
    public V get(K key) {
        for(Entry<K,V> e : S){
            if(e.getKey().equals(key))
                return e.getValue();
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        for(Entry<K,V> e : S){
            if(e.getKey().equals(key)){
                e.setValue(value);
            }
        }
    }

    @Override
    public V remove(K key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Iterable<K> keys() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keys'");
    }

    @Override
    public Iterable<V> values() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'values'");
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'entries'");
    }
    
}
