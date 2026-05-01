/*
 * The MIT License
 *
 * Copyright (c) 2020-2026  JOML.
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
package org.joml.test;

import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.junit.jupiter.api.Test;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mhameed
 */
class Matrix3dTest {
    @Test
    void testSetRow_4args() {
        int row = 0;
        float x = 0.0F;
        float y = 1.0F;
        float z = 2.0F;
        Matrix3d instance = new Matrix3d();
        Vector3d inRow = new Vector3d(x, y, z);
        Vector3d outRow = new Vector3d();
        Matrix3d result = instance.setRow(row, x, y, z);
        result.getRow(row, outRow);
        assertEquals(inRow, outRow);
    }

    @Test
    void testGet() {
        Matrix3d m = new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, 9);
        for (int c = 0; c < 3; c++)
            for (int r = 0; r < 3; r++)
                assertEquals(c*3+r+1, m.get(c, r), 0);
    }

    @Test
    void testSet() {
        assertMatrix3dEquals(new Matrix3d().zero().set(0, 0, 3), new Matrix3d(3, 0, 0, 0, 0, 0, 0, 0, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(0, 1, 3), new Matrix3d(0, 3, 0, 0, 0, 0, 0, 0, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(0, 2, 3), new Matrix3d(0, 0, 3, 0, 0, 0, 0, 0, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(1, 0, 3), new Matrix3d(0, 0, 0, 3, 0, 0, 0, 0, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(1, 1, 3), new Matrix3d(0, 0, 0, 0, 3, 0, 0, 0, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(1, 2, 3), new Matrix3d(0, 0, 0, 0, 0, 3, 0, 0, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(2, 0, 3), new Matrix3d(0, 0, 0, 0, 0, 0, 3, 0, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(2, 1, 3), new Matrix3d(0, 0, 0, 0, 0, 0, 0, 3, 0), 0);
        assertMatrix3dEquals(new Matrix3d().zero().set(2, 2, 3), new Matrix3d(0, 0, 0, 0, 0, 0, 0, 0, 3), 0);
    }
    
    @Test
    void testEquals() {
    	// arrange
    	Matrix3d m1 = new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Matrix3d m2 = new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, 9);

		// act & assert
		assertTrue(m1.equals(m1));
		assertFalse(m1.equals(null));
		assertFalse(m1.equals("something"));
		assertTrue(m1.equals(m2));
		assertFalse(m1.equals(new Matrix3d(0, 2, 3, 4, 5, 6, 7, 8, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 0, 3, 4, 5, 6, 7, 8, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 2, 0, 4, 5, 6, 7, 8, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 2, 3, 0, 5, 6, 7, 8, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 2, 3, 4, 0, 6, 7, 8, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 2, 3, 4, 5, 0, 7, 8, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 2, 3, 4, 5, 6, 0, 8, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 2, 3, 4, 5, 6, 7, 0, 9)));
		assertFalse(m1.equals(new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, 0)));
    }
    
    @Test
    void testEqualsDelta() {
    	// arrange
    	Matrix3d m1 = new Matrix3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
		Matrix3d m2 = new Matrix3d(1.01, 2.01, 3.01, 4.01, 5.01, 6.01, 7.01, 8.01, 9.01);
		double delta = 0.05;

		// act & assert
		assertTrue(m1.equals(m1, delta));
		assertFalse(m1.equals(null, delta));
		assertTrue(m1.equals(m2, delta));
		assertFalse(m1.equals(new Matrix3d(1.1, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.1, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.0, 3.1, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.0, 3.0, 4.1, 5.0, 6.0, 7.0, 8.0, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.0, 3.0, 4.0, 5.1, 6.0, 7.0, 8.0, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.1, 7.0, 8.0, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.1, 8.0, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.1, 9.0), delta));
		assertFalse(m1.equals(new Matrix3d(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.1), delta));
    }
    
    @Test
	void testIsFinite() {
		assertTrue(new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, 9).isFinite());
		assertFalse(new Matrix3d(Double.NaN, 2, 3, 4, 5, 6, 7, 8, 9).isFinite());
		assertFalse(new Matrix3d(1, Double.NaN, 3, 4, 5, 6, 7, 8, 9).isFinite());
		assertFalse(new Matrix3d(1, 2, Double.NaN, 4, 5, 6, 7, 8, 9).isFinite());
		assertFalse(new Matrix3d(1, 2, 3, Double.NaN, 5, 6, 7, 8, 9).isFinite());
		assertFalse(new Matrix3d(1, 2, 3, 4, Double.NaN, 6, 7, 8, 9).isFinite());
		assertFalse(new Matrix3d(1, 2, 3, 4, 5, Double.NaN, 7, 8, 9).isFinite());
		assertFalse(new Matrix3d(1, 2, 3, 4, 5, 6, Double.NaN, 8, 9).isFinite());
		assertFalse(new Matrix3d(1, 2, 3, 4, 5, 6, 7, Double.NaN, 9).isFinite());
		assertFalse(new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, Double.NaN).isFinite());
		assertFalse(new Matrix3d(1, 2, 3, 4, Double.POSITIVE_INFINITY, 6, 7, 8, 9).isFinite());
	}
    
}
