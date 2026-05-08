package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;

public class Entrada<K,V> implements Entry<K,V>{
    private K key;
    private V value;

    public Entrada(K llave, V valor){
        key = llave;
        value = valor;
    }

    @Override
    public K getKey() {
        return key;
    } 

    @Override
    public V getValue() {
        return value;
    }

    public void setKey( K clave ) { 
        key = clave;
    } 

    public void setValue(V valor) {
        value = valor;
    }

}
