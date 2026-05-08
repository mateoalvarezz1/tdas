package ar.edu.uns.cs.ed.tdas.tdamapeo;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidKeyException;

public class MapeoConHashAbierto<K,V> implements Map<K,V>{

    protected MapeoConLista<K,V> [] A;
    protected int n;
    protected int N;

    @SuppressWarnings("unchecked")
    public MapeoConHashAbierto(){
        A = new MapeoConLista[11];
        n=0;
        N=11;
        for(int i = 0; i < N; i++){
            A[i] = new MapeoConLista<>();
    }
    }
    @SuppressWarnings("unchecked")
    public MapeoConHashAbierto(int t){
        A = new MapeoConLista[t];
        N = t;
        n=0;
        for(int i = 0; i < N; i++){
            A[i] = new MapeoConLista<>();
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
    public V get(K key) {
        if(key == null) throw new InvalidKeyException("clave nula");
        return A[h(key)].get(key);
    }

    @Override
    public V put(K key, V value) {
        if(key == null) throw new InvalidKeyException("clave nula");
        if((double)n / N >= 0.9){
            int Nnuevo = siguientePrimo(2*N);
            MapeoConLista<K,V>[] B = new MapeoConLista[Nnuevo];

            for(int i=0;i<Nnuevo;i++){
                B[i] = new MapeoConLista<>();
            }

            for(int i=0;i<N;i++){
                for(Entry<K,V> e : A[i].entries()){
                    int pos = Math.abs(e.getKey().hashCode()) % Nnuevo;
                    B[pos].put(e.getKey(), e.getValue());
                }
            }
            A = B;
            N = Nnuevo;
        }
        V valor = A[h(key)].put(key, value);
            if(valor == null) n++;
                return valor;
    }

    @Override
    public V remove(K key) {
        if(key == null) throw new InvalidKeyException("clave nula");
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
    
    private int siguientePrimo(int n) {
        int candidato = 2 * n + 1;

        while (!esPrimo(candidato)) {
            candidato += 2;
        }

        return candidato;
    }

    private boolean esPrimo(int x) {

        if (x == 2) {
            return true;
        }

        // descarta pares
        if (x % 2 == 0) {
            return false;
        }

        // prueba solo divisores impares
        for (int i = 3; i * i <= x; i += 2) {

            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
