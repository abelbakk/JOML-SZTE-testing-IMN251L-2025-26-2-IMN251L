/*
 * The MIT License
 *
 * Copyright (c) 2015-2026  JOML.
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

import org.joml.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector3L}.
 * @author Attila Udvardi
 */
class Vector3LTest {

    @Test
    void testVector3LGetComponent() {
        final Vector3L v = new Vector3L(3, 5, 7);
        assertEquals(3, v.get(0));
        assertEquals(5, v.get(1));
        assertEquals(7, v.get(2));
        assertThrows(IllegalArgumentException.class, () -> v.get(3));
    }

    @Test
    void testVector3LSetComponent() {
        final Vector3L v = new Vector3L(1, 2, 3);
        v.setComponent(0, 10);
        assertEquals(new Vector3L(10, 2, 3), v);
        v.setComponent(1, -6);
        assertEquals(new Vector3L(10, -6, 3), v);
        v.setComponent(2, 5);
        assertEquals(new Vector3L(10, -6, 5), v);
        assertThrows(IllegalArgumentException.class, () -> v.setComponent(3, 99));
    }

    @Test
    void testMaxMinComponentAndTies() {
        final Vector3L a = new Vector3L(5, 5, 5);
        assertEquals(0, a.maxComponent());
        final Vector3L b = new Vector3L(3, 6, 6);
        assertEquals(1, b.maxComponent());
        final Vector3L c = new Vector3L(3, 3, 7);
        assertEquals(2, c.maxComponent());

        final Vector3L d = new Vector3L(5, 5, 5);
        assertEquals(2, d.minComponent());
        final Vector3L e = new Vector3L(1, 5, 5);
        assertEquals(0, e.minComponent());
        final Vector3L f = new Vector3L(5, 1, 5);
        assertEquals(1, f.minComponent());
    }

    @Test
    void testAbsoluteAndToString() {
        final Vector3L v = new Vector3L(-3, -5, 1);
        v.absolute();
        assertEquals(new Vector3L(3, 5, 1), v);

        final java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        final String expected = "(" + nf.format(3) + " " + nf.format(5) + " " + nf.format(1) + ")";
        assertEquals(expected, v.toString(nf));
    }

    @Test
    void testEqualsVariants() {
        final Vector3L v = new Vector3L(3, 5, 7);
        assertEquals(new Vector3L(3, 5, 7), v);
        assertNotEquals(new Vector3L(-3, 5, 7), v);
        assertNotEquals(new Vector3L(3, -5, 7), v);
        assertNotEquals(new Vector3L(3, 5, -7), v);
        assertEquals(v, v);
        assertFalse(v.equals(null));
        assertFalse(v.equals(new Vector2i(3, 5)));
        assertTrue(v.equals(3, 5, 7));
        assertFalse(v.equals(1, 5, 7));
    }
}
