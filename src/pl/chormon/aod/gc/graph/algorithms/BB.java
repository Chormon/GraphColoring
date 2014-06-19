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

    private int[] solution;
    private int best;

    @Override
    public Graph color(Graph g) {
        System.out.println("Kolorowanie grafu przy użyciu algorytmu Branch and Bound...");
        final int U = g.getSize();
        solution = new int[U];
        int[] tmp = new int[U];
        best = U + 1;
        for (int i = 0; i < U; i++) {
            processVertex(g.getVertices(), i, tmp, 1, 0);
        }

        int i = 0;
        for (Vertex v : g.getVertices()) {
            v.setColor(solution[i++]);
            System.out.println("W:" + v.getId() + " K:" + v.getColor());
        }
        System.out.println("Użyte kolory: " + best);
        return g;
    }

    private void processVertex(List<Vertex> vertices, int vertex, int[] tmp, int cused, int step) {
        Vertex v = vertices.get(vertex);
        int color = 1;
        boolean colored;
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
            if (color >= best) {
                return;
            }
        } while (!colored);
        if (color > cused) {
            cused++;
        }
        tmp[vertex] = color;

        if (arrayIsFull(tmp) && cused < best) {
            best = cused;
            solution = tmp.clone();
        }

        for (int i = 0; i < tmp.length; i++) {
            if (i != vertex && tmp[i] == 0) {
                processVertex(vertices, i, tmp, cused, step + 1);
            }
        }
        tmp[vertex] = 0;
    }

    private boolean arrayIsFull(int[] array) {
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
