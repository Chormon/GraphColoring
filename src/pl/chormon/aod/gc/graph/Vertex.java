/*
 * The MIT License
 *
 * Copyright 2014 Chormon.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.chormon.aod.gc.graph;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chormon
 */


public class Vertex {

    /**
     * Kolor wierzchołka.
     */
    protected int color;

    /**
     * ID wierzchołka
     */
    protected final int id;

    /**
     * Lista sąsiadów wierzchołka.
     */
    protected List<Vertex> neighbours;

    /**
     * Tworzy wierzchołek o podanym ID.
     * @param index ID nowego wierzchołka
     */
    public Vertex(int index) {
        this.id = index;
        this.color = 0;
        this.neighbours = new ArrayList<>();
    }

    /**
     * Pobranie ID wierzchołka.
     * @return ID wierzchołka
     */
    public int getId() {
        return id;
    }
    
    /**
     * Dodanie krawędzi do wierzchołka v.
     * @param v wierzchołek, do którego będzie prowadziła krawędź
     */
    public void addNeightbour(Vertex v) {
        neighbours.add(v);
    }

    /**
     * Pobranie koloru wierzchołka.
     * @return kolor
     */
    public int getColor() {
        return color;
    }

    /**
     * Ustawienie koloru wierzchołka.
     * @param color kolor
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Pobranie listy wierzchołków sąsiednich.
     * @return Lista wierzchołków
     */
    public List<Vertex> getNeighbours() {
        return neighbours;
    }
}
