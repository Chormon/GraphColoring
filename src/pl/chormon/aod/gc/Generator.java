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
package pl.chormon.aod.gc;

import java.util.Random;
import pl.chormon.aod.gc.graph.Graph;

/**
 *
 * @author Chormon
 */
public class Generator {

    /**
     * Generowanie grafu na podstawie ilości wierzchołków, prawdopodobieństwa wystąpienia krawędzi oraz ziarna.
     * @param V ilość wierzchołków
     * @param probability prawdopodobieństwo wystąpienia krawędzi pomiędzy wierzchołkami
     * @param seed ziarno generatora
     * @return Wygenerowany graf
     */
    public static Graph createGraphByEdgeProbability(int V, float probability, long seed) {
        if (probability > 1) {
            probability = 1;
        } else if (probability < 0) {
            probability = 0;
        }
        System.out.println("Generowanie grafu z " + V + " wierzchołkami i ziarnistością " + probability + " z użyciem ziarna " + seed + "...");
        int edgeCount = 0;
        Graph g = new Graph(V);
        Random generator;
        generator = new Random(seed);
        /* Generowanie krawędzi */
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < i; j++) {
                float next = generator.nextFloat();
                if (next <= probability) {
                    g.addNeighbor(i, j, true);
                    edgeCount++;
                }
            }
        }
        System.out.println("Stworzono graf z " + V + " wierzchołkami i " + edgeCount + " krawędziami.");
        return g;
    }

}
