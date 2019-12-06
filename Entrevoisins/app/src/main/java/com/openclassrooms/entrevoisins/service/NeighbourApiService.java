package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

public interface NeighbourApiService {

    List<Neighbour> getNeighbours();
    List<Neighbour> getFav();
    void addFav(Neighbour y);
    void removeFav(Neighbour y);

    void  deleteNeighbour(Neighbour neighbour);
}
