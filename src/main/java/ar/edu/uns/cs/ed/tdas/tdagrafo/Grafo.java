package ar.edu.uns.cs.ed.tdas.tdagrafo;

import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class Grafo<V,E> implements Graph<V,E>{
    private PositionList<Vertice<V,E>> vertices;
    private PositionList<Arco<V,E>> arcos;

    public Grafo(){
        vertices= new ListaDobleEnlazada<>();
        arcos= new ListaDobleEnlazada<>();
    }

    @Override
    public Vertice<V,E> insertVertex(V rotulo){
        Vertice<V,E> v1 = new Vertice<>(rotulo);
        vertices.addLast(v1);
        v1.setPosition(vertices.last());
        return v1;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        PositionList<Vertex<V>> listaNueva = new ListaDobleEnlazada<>();
        for (Vertice<V, E> v : vertices) {
            listaNueva.addLast(v);
        }
        return listaNueva;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        PositionList<Edge<E>> listaNueva = new ListaDobleEnlazada<>();
        for (Arco<V, E> a : arcos) {
            listaNueva.addLast(a);
        }
        return listaNueva;
    }

    @Override
    public Iterable<Edge<E>> incidentEdges(Vertex<V> v) {
        
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'opposite'");
    }

    @Override
    public Vertex<V>[] endvertices(Edge<E> e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endvertices'");
    }

    @Override
    public boolean areAdjacent(Vertex<V> v, Vertex<V> w) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'areAdjacent'");
    }

    @Override
    public V replace(Vertex<V> v, V x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public E replace(Edge<E> e, E x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) {
        Vertice<V,E> vv = (Vertice<V,E>) v;
        Vertice<V,E> ww = (Vertice<V,E>) w;
        Arco<V,E> arco = new Arco<>(e, vv, ww);
        arcos.addLast(arco);
        arco.setPosEnArcos(arcos.last());

        vv.getAdyacentes().addLast(arco);
        arco.setPosEnv1(vv.getAdyacentes().last());

        ww.getAdyacentes().addLast(arco);
        arco.setPosEnv2(ww.getAdyacentes().last());

        return arco;
        
    }

    @Override
    public V removeVertex(Vertex<V> v) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeVertex'");
    }

    @Override
    public E removeEdge(Edge<E> e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEdge'");
    }
}