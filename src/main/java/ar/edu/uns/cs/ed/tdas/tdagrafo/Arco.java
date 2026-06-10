package ar.edu.uns.cs.ed.tdas.tdagrafo;

import ar.edu.uns.cs.ed.tdas.Position;

@SuppressWarnings("all")
public class Arco<V, E> implements Edge<E> {
    
    // Atributos privados
    private E rotulo;
    private Vertice<V, E> v1;
    private Vertice<V, E> v2;
    
    // Punteros de posición usando tus nombres elegidos
    private Position<Arco<V, E>> posEnArcos;
    private Position<Arco<V, E>> posEnv1;
    private Position<Arco<V, E>> posEnv2;

    // Constructor
    public Arco(E rotulo, Vertice<V, E> v1, Vertice<V, E> v2) {
        this.rotulo = rotulo;
        this.v1 = v1;
        this.v2 = v2;
        this.posEnArcos = null;
        this.posEnv1 = null;
        this.posEnv2 = null;
    }

    // Método obligatorio de la interfaz Edge
    @Override
    public E element() {
        return rotulo;
    }

    // Getters para los vértices extremos
    public Vertice<V, E> getV1() {
        return v1;
    }

    public Vertice<V, E> getV2() {
        return v2;
    }

    // Getters y Setters para las posiciones
    public Position<Arco<V, E>> getPosEnArcos() {
        return posEnArcos;
    }

    public void setPosEnArcos(Position<Arco<V, E>> posEnArcos) {
        this.posEnArcos = posEnArcos;
    }

    public Position<Arco<V, E>> getPosEnv1() {
        return posEnv1;
    }

    public void setPosEnv1(Position<Arco<V, E>> posEnv1) {
        this.posEnv1 = posEnv1;
    }

    public Position<Arco<V, E>> getPosEnv2() {
        return posEnv2;
    }

    public void setPosEnv2(Position<Arco<V, E>> posEnv2) {
        this.posEnv2 = posEnv2;
    }
}