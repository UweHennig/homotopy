/**
 * @(#)Homotopy.java
 * Copyright (c) 2020 Uwe Hennig
 * All rights reserved.
 */
package com.uwe_hennig.homotopy;

import java.util.function.Function;

/**
 * Homotopy
 * 
 * @author Uwe Hennig
 */
public class Homotopy {
	public static <T, U, V, R> Function<V, R> homotopy(TriFunction<T, U, V, R> h, T f, U g) {
		return z -> h.apply(f, g, z);
	}

	public static Function<Double, Double> hLinear(Function<Double, Double> f, Function<Double, Double> g, Double t) {
		return x -> (1.0 - t) * f.apply(x) + t * g.apply(x);
	}

	public static Function<Double, Double> hSquare(Function<Double, Double> f, Function<Double, Double> g, Double t) {
		return x -> (1.0 - t) * (1.0 - t) * f.apply(x) + t * t * g.apply(x);
	}

	public static Function<Double, Double> hTrig(Function<Double, Double> f, Function<Double, Double> g, Double t) {
		return x -> Math.cos(t * Math.PI / 2.0) * f.apply(x) + Math.sin(t * Math.PI / 2.0) * g.apply(x);
	}

	public static Function<Double, Double> hExp(Function<Double, Double> f, Function<Double, Double> g, Double t) {
		return x -> (Math.exp(1 - t) - 1.0) / (Math.E - 1.0) * f.apply(x)
		        + (Math.exp(t) - 1.0) / (Math.E - 1.0) * g.apply(x);
	}
}
