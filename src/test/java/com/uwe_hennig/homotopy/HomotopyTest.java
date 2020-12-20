/**
 * @(#)HomotopyTest.java
 * Copyright (c) 2020 Uwe Hennig
 * All rights reserved.
 */
package com.uwe_hennig.homotopy;

import static com.uwe_hennig.homotopy.Homotopy.homotopy;
import static org.junit.Assert.assertEquals;

import java.util.function.Function;
import java.util.stream.DoubleStream;

import org.junit.Test;

/**
 * HomotopyTest
 * 
 * @author Uwe Hennig
 */
public class HomotopyTest {

    @Test
    public void linearTest() {
        Function<Double, Double> f = x -> x * x;
        Function<Double, Double> g = x -> 2 * x + 1;

        Function<Double, Function<Double, Double>> homotopyFunction = homotopy(Homotopy::hLinear, f, g);
        Function<Double, Double> h0 = homotopyFunction.apply(0.0D);
        Function<Double, Double> h1 = homotopyFunction.apply(1.0D);
        Function<Double, Double> h05 = homotopyFunction.apply(0.5D);

        assertEquals("H0(x): Different values for x = 0.0D!", f.apply(0.0D), h0.apply(0.0D), 0.0001D);
        assertEquals("H0(x): Different values for x = 10.0D!", f.apply(10.0D), h0.apply(10.0D), 0.0001D);
        assertEquals("H0(x): Different values for x = 15.0D!", f.apply(15.0D), h0.apply(15.0D), 0.0001D);

        assertEquals("H1(x): Different values for x = 0.0D!", g.apply(0.0D), h1.apply(0.0D), 0.0001D);
        assertEquals("H1(x): Different values for x = 10.0D!", g.apply(10.0D), h1.apply(10.0D), 0.0001D);
        assertEquals("H1(x): Different values for x = 15.0D!", g.apply(15.0D), h1.apply(15.0D), 0.0001D);

        assertEquals("H05(x): Different values for x = 0.0D!", (g.apply(0.0D) + f.apply(0.0D)) / 2.0D, h05.apply(0.0D), 0.0001D);
        assertEquals("H05(x): Different values for x = 10.0D!", (g.apply(10.0D) + f.apply(10.0D)) / 2.0D, h05.apply(10.0D), 0.0001D);
        assertEquals("H05(x): Different values for x = 15.0D!", (g.apply(15.0D) + f.apply(15.0D)) / 2.0D, h05.apply(15.0D), 0.0001D);

        // print some values
        DoubleStream.iterate(0.0D, t -> t + 0.1D)
            .filter(t -> t <= 1.0D)
            .mapToObj(t -> homotopyFunction.apply(t))
            .forEach(HomotopyTest::print);
    }

    public static void print(Function<Double, Double> h) {
        System.out.println("\nNew function h");
        for (double x = 0.0D; x < 15.0D; x += 1.0D) {
            System.out.println(String.format("\th(%1.1f) = %1.1f", x, h.apply(x)));
        }
    }

}
