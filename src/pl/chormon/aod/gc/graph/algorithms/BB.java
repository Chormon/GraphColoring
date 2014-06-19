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
package pl.chormon.aod.gc.graph.algorithms;

import java.util.List;
import pl.chormon.aod.gc.graph.Graph;
import pl.chormon.aod.gc.graph.Vertex;

/**
 *
 * @author Chormon
 */
public class BB implements Algorithm {

    /**
     * Przechowuje kolory wierzchołków najlepszego rozwiązania.
     */
    protected int[] solution;

    /**
     * Przechowuje liczbę użytych kolorów w najlepszych rozwiązaniu.
     */
    protected int best;

    /**
     * Pokolorowanie danego grafu algorytmem Branch and Bound.
     *
     * @param g graf do pokolorowania
     * @return Pokolorowany graf
     */
    @Override
    public Graph color(Graph g) {
        System.out.println("Kolorowanie grafu przy użyciu algorytmu Branch and Bound...");
        final int U = g.getSize();
        solution = new int[U];
        int[] tmp = new int[U];
        best = U + 1;
        /*  */
        for (int i = 0; i < U; i++) {
            processVertex(g.getVertices(), i, tmp, 1);
        }

        /* Pokolorowanie grafu najlepszym rozwiązaniem. */
        int i = 0;
        for (Vertex v : g.getVertices()) {
            v.setColor(solution[i++]);
            System.out.println("W:" + v.getId() + " K:" + v.getColor());
        }
        System.out.println("Użyte kolory: " + best);
        return g;
    }

    /**
     * Rekurencyjne kolorowanie wszystkich wariantów kolejności wierzchołków ze
     * startem w wierzchołku vertex.
     *
     * @param vertices lista wierzchołków w grafie
     * @param vertex wierzchołek do przetworzenia
     * @param tmp tablica z tymczasowym rozwiązaniem
     * @param cused ilość użytych kolorów
     */
    protected void processVertex(List<Vertex> vertices, int vertex, int[] tmp, int cused) {
        Vertex v = vertices.get(vertex);
        int color = 1;
        boolean colored;
        /* Kolorujemy zachłannie. */
        do {
            colored = true;
            for (Vertex n : v.getNeighbours()) {
                if (tmp[n.getId()] == color) {
                    colored = false;
                    break;
                }
            }
            if (!colored) {
                color++;
            }
            /* Jeśli musimy już użyć co najmniej tyle samo kolorów, jak w najlepszym rozwiązaniu, to obcinamy. */
            if (color >= best) {
                return;
            }
        } while (!colored);
        if (color > cused) {
            cused++;
        }
        tmp[vertex] = color;
        
        /* Jeśli pokoloraliśmy cały graf i użyliśmy mniej kolorów niż w najlepszym rozwiązaniu, to jest to najlepsze rozwiązanie. */
        if (arrayIsFull(tmp) && cused < best) {
            best = cused;
            solution = tmp.clone();
        }

        /* Rekurencyjnie wywołujemy każdy możliwy wierzchołek, który możemy następnie pokolorować. */
        for (int i = 0; i < tmp.length; i++) {
            if (i != vertex && tmp[i] == 0) {
                processVertex(vertices, i, tmp, cused);
            }
        }
        tmp[vertex] = 0;
    }

    /**
     * Sprawdza, czy dana tablica jest zapełniona.
     *
     * @param array input array
     * @return Czy tablica jest zapełniona
     */
    protected boolean arrayIsFull(int[] array) {
        if (array == null) {
            return false;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                return false;
            }
        }
        return true;
    }
}
