package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> Favoris= DummyNeighbourGenerator.Favos();
    private Neighbour fav;


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }
    public List<Neighbour> getFav() {
        return Favoris;
    }

    public void addFav(Neighbour y) {
        Favoris.add(y);
    }
    public void removeFav (Neighbour y) {
        Favoris.remove(y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
        if (Favoris.contains(neighbour)){
            Favoris.remove(neighbour);
        }
    }
}
