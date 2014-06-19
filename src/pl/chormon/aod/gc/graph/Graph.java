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
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Chormon
 */
public class Graph {

    private final Map<Integer, Vertex> vertices;
    private final int size;

    public Graph(int size) {
        this.vertices = new TreeMap<>();
        for (int i = 0; i < size; i++) {
            Vertex node = new Vertex(i);
            this.vertices.put(i, node);
        }
        this.size = size;
    }

    /**
     *
     * @param v1
     * @param v2
     */
    public void addNeighbor(int v1, int v2) {
        addNeighbor(v1, v2, false);
    }

    /**
     *
     * @param v1
     * @param v2
     * @param undirect
     */
    public void addNeighbor(int v1, int v2, boolean undirect) {
        if (v1 < vertices.size() && v2 < vertices.size()) {
            vertices.get(v1).addNeightbour(vertices.get(v2));
            if (undirect) {
                vertices.get(v2).addNeightbour(vertices.get(v1));
            }
        } else {
            int v = v1 > v2 ? v1 : v2;
            System.out.println("Nie można dodać sąsiada! Wierzhołek o indeksie " + v + " nie istnieje!");
        }
    }

    public boolean uncolored() {
        for (Vertex v : vertices.values()) {
            if (v.getColor() == 0) {
                return true;
            }
        }
        return false;
    }

    public List<Vertex> getVertices() {
        List<Vertex> list = new ArrayList<>(vertices.values());
        return list;
    }

    public List<Vertex> getNeighbors(int v) {
        return vertices.get(v).getNeighbours();
    }

    public void Print() {
        System.out.println("Lista sąsiedztwa grafu:");
        for (Vertex v : vertices.values()) {
            System.out.print(v.getId());
            if (!v.getNeighbours().isEmpty()) {
                System.out.print("->");
            }
            ListIterator<Vertex> i = v.getNeighbours().listIterator();
            while (i.hasNext()) {
                System.out.print(i.next().getId());
                if (i.hasNext()) {
                    System.out.print("->");
                }
            }
            System.out.println("");
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isLegal() {
        for (Vertex v : vertices.values()) {
            int c = v.getColor();
            for (Vertex n : v.getNeighbours()) {
                if (n.getColor() == c) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetColors() {
        for (Vertex v : vertices.values()) {
            v.setColor(0);
        }
    }
}
