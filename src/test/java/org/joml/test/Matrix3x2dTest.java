/*
 * The MIT License
 *
 * Copyright (c) 2017-2026  JOML.
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

import org.joml.Matrix3x2d;
import org.joml.Vector2d;
import org.junit.jupiter.api.Test;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class Matrix3x2dTest {
    @Test
    void testInvert() {
        Matrix3x2d m = new Matrix3x2d(1, 2, 4, 5, -0.5, -2);
        Vector2d v = new Vector2d(4, 0.5);
        m.transformPosition(v);
        Vector2d v2 = new Vector2d(v);
        m.invert();
        m.transformPosition(v2);
        assertVector2dEquals(new Vector2d(4, 0.5), v2, 1E-6);
    }

    @Test
    void testView() {
        Matrix3x2d m = new Matrix3x2d().view(-4, 0.5, -2, 3);
        Vector2d v = new Vector2d(-4, -2);
        m.transformPosition(v);
        assertVector2dEquals(new Vector2d(-1, -1), v, 1E-12);
        v.set(0.5, 3);
        m.transformPosition(v);
        assertVector2dEquals(new Vector2d(1, 1), v, 1E-12);
    }

    @Test
    void testUnproject() {
        Matrix3x2d m = new Matrix3x2d().view(-3, 2, -4, 1);
        assertVector2dEquals(new Vector2d(-3, -4), m.unproject(0, 0, new int[] {0, 0, 800, 600}, new Vector2d()), 1E-6);
        assertVector2dEquals(new Vector2d(2, 1), m.unproject(800, 600, new int[] {0, 0, 800, 600}, new Vector2d()), 1E-6);
    }

    @Test
    void testTestPoint() {
        Matrix3x2d m = new Matrix3x2d().view(-4, 2, -3, 10);
        assertTrue(m.testPoint(0, 0));
        assertTrue(m.testPoint(-4, -2.9));
        assertFalse(m.testPoint(-4.01, -2.9));
        assertFalse(m.testPoint(-3.9, -3.01));
        assertTrue(m.testPoint(0, 9.99));
        assertFalse(m.testPoint(0, 10.01));

        m.setView(-2, 2, -2, 2).rotate(Math.toRadians(45));
        double[] area = m.viewArea(new double[4]);
        assertTrue(m.testPoint(area[0], 0));
        assertFalse(m.testPoint(area[0] - 0.01, 0));
        assertTrue(m.testPoint(area[2] - 0.1, 0));
        assertFalse(m.testPoint(area[2] + 0.01, 0));
    }
}
