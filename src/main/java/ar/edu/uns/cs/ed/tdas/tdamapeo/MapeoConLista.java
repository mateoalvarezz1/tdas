package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;

public class MapeoConLista<K, V> implements Map<K, V>{

    protected ListaDobleEnlazada<Entrada<K,V>> S;
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
        for(Entrada<K,V> e : S){
            if(e.getKey().equals(key))
                return e.getValue();
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        for(Entrada<K,V> e : S){
            if(e.getKey().equals(key)){
                V valor = e.getValue();
                e.setValue(value);
                return valor;
            }
        }
        S.addLast(new Entrada<>(key, value));
        n++;
        return null;

    }

    @Override
    public V remove(K key) {
        for(Position<Entrada<K,V>> p : S.positions()){
            if(p.element().getKey().equals(key)){
                V valor = p.element().getValue();
                S.remove(p);
                n--;
                return valor;
            }
        }
        return null;
    }

    @Override
    public Iterable<K> keys() {
        ListaDobleEnlazada<K> llaves = new ListaDobleEnlazada<>();
        for(Entrada<K,V> e : S){
            llaves.addLast(e.getKey());
        }

        return llaves;
    }

    @Override
    public Iterable<V> values() {
        ListaDobleEnlazada<V> valor = new ListaDobleEnlazada<>();
        for(Entrada<K,V> e : S){
            valor.addLast(e.getValue());
        }

        return valor;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        ListaDobleEnlazada<Entry<K,V>> lista = new ListaDobleEnlazada<>();
        for(Entrada<K,V> e : S){
            lista.addLast(e);
        }
        return lista;
    }
    
}
