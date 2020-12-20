/**
 * @(#)TriFunction.java
 * Copyright (c) 2020 Uwe Hennig
 * All rights reserved.
 */
package com.uwe_hennig.homotopy;

/**
 * TriFunction
 * 
 * @author Uwe Hennig
 */
@FunctionalInterface
public interface TriFunction<T, F, G, R> {
	R apply(T a, F b, G c);
}
