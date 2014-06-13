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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import pl.chormon.aod.gc.graph.Graph;
import pl.chormon.aod.gc.graph.Vertex;

/**
 *
 * @author Chormon
 */
public class SLF implements Algorithm {
    
    @Override
    public Graph color(Graph g) {
        int colors = 1;
        while(g.uncolored()) {
            Vertex node = null;
            int currSdeg = 0;
            for(Vertex v : g.getVertices()) {
                if(v.getColor() != 0)
                    continue;
                List<Integer> sdeg = new ArrayList<>();
                for(Vertex n : v.getNeighbours()) {
                    if((sdeg.isEmpty() || !sdeg.contains(n.getColor())) && n.getColor() != 0)
                        sdeg.add(n.getColor());
                }
                if(node == null) {
                    node = v;
                    currSdeg = sdeg.size();
                } else if(sdeg.size() > currSdeg) {
                    node = v;
                    currSdeg = sdeg.size();
                } else if (sdeg.size() == currSdeg && node.getNeighbours().size() < v.getNeighbours().size()) {                    
                    node = v;
                    currSdeg = sdeg.size();
                }
            }
            int color = 1;
            ListIterator<Vertex> i = node.getNeighbours().listIterator();
            while(i.hasNext()) {
                if(i.next().getColor() == color) {
                    color++;
                    i = node.getNeighbours().listIterator();
                }
            }
            g.getVertices().get(node.getId()).setColor(color);
            System.out.println("V:" + node.getId() + " C:" + color);
            if(color > colors)
                colors = color;
        }
        System.out.println("Colors used: " + colors);
        return g;
    }
    
}
