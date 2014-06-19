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

import pl.chormon.aod.gc.graph.Graph;
import pl.chormon.aod.gc.graph.algorithms.Algorithm;
import pl.chormon.aod.gc.graph.algorithms.Algorithm.Alg;
import pl.chormon.aod.gc.graph.algorithms.BB;
import pl.chormon.aod.gc.graph.algorithms.SLF;

/**
 *
 * @author Chormon
 */
public class GC {

    private static final String SEED_KEY = "-s";
    private static final String ALGORITHM_KEY = "-a";

    /**
     * @param args argumenty wiersza poleceń
     */
    public static void main(String[] args) {

        int V, E, MAX_E;
        float p, G;
        long seed = System.currentTimeMillis();
        Alg alg = Alg.SLF;
        Algorithm algorithm = null;

        /* Parsowanie argumentów */
        if (args.length == 1 && args[0].equalsIgnoreCase("?")) {
            printHelp();
            return;
        }

        if (args.length < 2) {
            printUsage();
            return;
        } else {
            try {
                V = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.out.println(args[0] + " nie jest liczbą całkowitą!");
                printUsage();
                return;
            }
            //E = Integer.parseInt(args[1]);
            try {
                G = Float.parseFloat(args[1]);
            } catch (NumberFormatException ex) {
                System.out.println(args[0] + " nie jest liczbą rzeczywistą!");
                printUsage();
                return;
            }
            G = G < 0 ? 0 : G;
            G = G > 1 ? 1 : G;
        }
        if (V < 1) {
            System.out.println("V " + V + " musi być większe od zera!");
            printUsage();
            return;
        }
        if (G < 0) {
            System.out.println("G " + G + " musi być większe lub równe zero!");
            printUsage();
            return;
        }
        //MAX_E = V * (V - 1) / 2;
        //p = (float) E / MAX_E;
        p = G;
        for (int i = 2; i < args.length; i += 2) {
            String key = args[i];
            String value = args[i + 1];
            switch (key) {
                case SEED_KEY:
                    try {
                        seed = Long.parseLong(value);
                    } catch (NumberFormatException ex) {
                        System.out.println(args[0] + " nie jest liczbą całkowitą!");
                        printUsage();
                        return;
                    }
                    break;
                case ALGORITHM_KEY:
                    try {
                        alg = Alg.valueOf(value.toUpperCase());
                    } catch (IllegalArgumentException ex) {
                        System.out.println(value.toUpperCase() + " nie jest poprawnym algorytmem!");
                        printUsage();
                        return;
                    }
                    break;
            }
        }
        /* Generowanie grafu i wyświetlenie listy sąsiedztwa */
        Graph g = Generator.createGraphByEdgeProbability(V, p, seed);
        g.print();
        switch (alg) {
            case BB:
                algorithm = new BB();
                break;
            case SLF:
                algorithm = new SLF();
                break;
        }
        /* Kolorowanie grafu i walidacja */
        Graph colored = algorithm.color(g);
        if (colored.isLegal()) {
            System.out.println("SUKCES! Graf został pokolorowany legalnie!");
        } else {
            System.out.println("PORAŻKA! Graf został niepoprawnie pokolorowany!");
        }
    }

    /**
     * Wypisuje użycie programu.
     */
    protected static void printUsage() {
        System.err.println("Użycie: java -jar GraphColoring.jar <V> <G> [-s <seed>] [-a <SLF/BB>]");
        System.out.println("Aby zobaczyć pomoc uruchom: java -jar GraphColoring.jar ?");
    }

    /**
     * Wypisuje pomoc.
     */
    protected static void printHelp() {
        System.out.println("Pomoc:");
        System.out.println("  Argumenty:");
        System.out.println("    Wymagane");
        System.out.println("      V - ilość wierzholków w grafie");
        System.out.println("      G - współczynnik gronowania");
        System.out.println("    Opcjonalne:");
        System.out.println("       -s ZIARNO - ziarno generatora grafu");
        System.out.println("       -a ALGORYTM - algorytm kolorowania grafu (SLF/BB). Domyślnie - BB");
    }
}
