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
        System.out.println("linearTest :");
        DoubleStream.iterate(0.0D, t -> t + 0.1D)
            .limit(10)
            .mapToObj(t -> homotopyFunction.apply(t))
            .forEach(HomotopyTest::print);
    }
    
    @Test
    public void expTest() {
        Function<Double, Double> g = x -> 0.0D;
        Function<Double, Double> f = x -> Math.exp(x);
        
        Function<Double, Function<Double, Double>> homotopyFunction = homotopy(Homotopy::hTrig, g, f);
        assertEquals("H0 is not constant 0!", 0.0D, homotopyFunction.apply(0.0D).apply(10.0D), 0.0001D);
        assertEquals("H1 is not exp(x)!", Math.exp(10.0D), homotopyFunction.apply(1.0D).apply(10.0D), 0.0001D);
        
        // print some values
        for(double t=0.0D;t <= 1.0D; t+=0.2D) {
            System.out.println(String.format("expTest H%1.1f(f,g)", t));
            Function<Double, Double> h = homotopyFunction.apply(t);
            print(h);
        }
    }
    
    @Test
    public void simpleTest() {
        Function<Double, Double> g = x -> 0.0D;
        Function<Double, Double> f = x -> 1.0D;
        
        Function<Double, Function<Double, Double>> homotopyFunction = homotopy(Homotopy::hExp, g, f);
        assertEquals("H0 is not constant 0!", 0.0D, homotopyFunction.apply(0.0D).apply(5.0D), 0.0001D);
        assertEquals("H1 is not constant 1!", 1.0D, homotopyFunction.apply(1.0D).apply(5.0D), 0.0001D);
        
        assertEquals("H0 is not constant 0!", 0.0D, homotopyFunction.apply(0.0D).apply(-5.0D), 0.0001D);
        assertEquals("H1 is not constant 1!", 1.0D, homotopyFunction.apply(1.0D).apply(-5.0D), 0.0001D);
        
        // print some values
        for(double t=0.0D;t<=1.0D; t+=0.2D) {
            System.out.println(String.format("simpleTest H%1.1f(f,g)", t));
            Function<Double, Double> h = homotopyFunction.apply(t);
            print(h);
        }
    }
    
    @Test
    public void jumpTest() {
        Function<Double, Double> g = x -> x >= 0.0D? 1.0D : 0.0D;
        Function<Double, Double> f = x -> x >= 0.0D? 0.0D : 1.0D;
        
        Function<Double, Function<Double, Double>> homotopyFunction = homotopy(Homotopy::hLinear, g, f);
        
        assertEquals("H0", 1.0D, homotopyFunction.apply(0.0D).apply(5.0D), 0.0001D);
        assertEquals("H0", 0.0D, homotopyFunction.apply(0.0D).apply(-5.0D), 0.0001D);
        
        assertEquals("H1", 0.0D, homotopyFunction.apply(1.0D).apply(5.0D), 0.0001D);
        assertEquals("H1", 1.0D, homotopyFunction.apply(1.0D).apply(-5.0D), 0.0001D);
        
        // print some values
        for(double t=0.0D;t <= 1.0D; t+=0.2D) {
            System.out.println(String.format("jumpTest H%1.1f(f,g)", t));
            Function<Double, Double> h = homotopyFunction.apply(t);
            print(h);
        }
    }

    public static void print(Function<Double, Double> h) {
        System.out.println("\tNew function h");
        for (double x = -5.0D; x < 5.0D; x += 1.0D) {
            System.out.println(String.format("\t\tH(%1.1f) = %1.1f", x, h.apply(x)));
        }
        System.out.println();
    }

}
