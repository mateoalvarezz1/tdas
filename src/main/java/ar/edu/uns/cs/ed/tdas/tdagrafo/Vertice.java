package ar.edu.uns.cs.ed.tdas.tdagrafo;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDobleEnlazada;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class Vertice<V,E> implements Vertex<V>{

    private V rotulo;
    private PositionList<Arco<V,E>> adyacentes;
    private Position<Vertice<V,E>> posicionEnVertices;

    public Vertice(V rot){
        rotulo = rot;
        posicionEnVertices=null;
        adyacentes = new ListaDobleEnlazada<>();
    }

    @Override
    public V element() {
        return rotulo;
    }
    
    public void setPosition(Position<Vertice<V,E>> p){
        posicionEnVertices=p;
    }

    public Position<Vertice<V,E>> getPosition(){
        return posicionEnVertices;
    }

    public PositionList<Arco<V,E>> getAdyacentes(){
        return adyacentes;
    }
}
