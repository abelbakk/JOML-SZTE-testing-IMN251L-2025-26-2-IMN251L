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
 * Test class for {@link Vector4L}.
 * @author Attila Udvardi
 */
class Vector4LTest {

    @Test
    void testVector4LGetComponent() {
        final Vector4L vector1 = new Vector4L(3, 5, 1, 10);
        assertEquals(3, vector1.get(0));
        assertEquals(5, vector1.get(1));
        assertEquals(1, vector1.get(2));
        assertEquals(10, vector1.get(3));
        assertThrows(IllegalArgumentException.class, () -> vector1.get(4));
    }

    @Test
    void testVector4LMax() {
        final Vector4L vector1 = new Vector4L(3, 5, 1, 7);
        final Vector4L vector2 = new Vector4L(10, 22, 3, 10);
        vector1.max(vector2);
        assertEquals(new Vector4L(10, 22, 3, 10), vector1);

        final Vector4L vector3 = new Vector4L(3, 5, 1, 7);
        final Vector4L vector4 = new Vector4L(10, 22, 3, 10);
        vector4.max(vector3);
        assertEquals(new Vector4L(10, 22, 3, 10), vector4);
    }

    @Test
    void testVector4LMin() {
        final Vector4L vector1 = new Vector4L(3, 5, 1, 7);
        final Vector4L vector2 = new Vector4L(10, 22, 3, 10);
        vector1.min(vector2);
        assertEquals(new Vector4L(3, 5, 1, 7), vector1);

        final Vector4L vector3 = new Vector4L(3, 5, 1, 7);
        final Vector4L vector4 = new Vector4L(10, 22, 3, 10);
        vector4.min(vector3);
        assertEquals(new Vector4L(3, 5, 1, 7), vector4);
    }

    @Test
    void testVector4LEqualsWithVector4L() {
        final Vector4L vector1 = new Vector4L(3, 5, 1, 7);
        assertEquals(new Vector4L(3, 5, 1, 7), vector1);
        assertNotEquals(new Vector4L(-3, 5, 1, 7), vector1);
        assertNotEquals(new Vector4L(3, -5, 1, 7), vector1);
        assertNotEquals(new Vector4L(3, 5, -1, 7), vector1);
        assertNotEquals(new Vector4L(3, 5, 1, -7), vector1);
        assertEquals(vector1, vector1);
        assertFalse(vector1.equals(null));
        assertFalse(vector1.equals(new Vector2i(3, 5)));
    }

    @Test
    void testVector4LEqualsWithCoordinates() {
        final Vector4L vector1 = new Vector4L(3, 5, 1, 7);
        assertTrue(vector1.equals(3, 5, 1, 7));
        assertFalse(vector1.equals(-3, 5, 1, 7));
        assertFalse(vector1.equals(3, -5, 1, 7));
        assertFalse(vector1.equals(3, 5, -1, 7));
        assertFalse(vector1.equals(3, 5, 1, -7));
    }

    @Test
    void testVector4LMinComponent() {
        final Vector4L vector1 = new Vector4L(7, 8, 9, 6);
        assertEquals(3, vector1.minComponent());

        final Vector4L vector2 = new Vector4L(8, 10, 6, 8);
        assertEquals(2, vector2.minComponent());

        final Vector4L vector3 = new Vector4L(10, 6, 8, 10);
        assertEquals(1, vector3.minComponent());

        final Vector4L vector4 = new Vector4L(6, 8, 10, 12);
        assertEquals(0, vector4.minComponent());
    }

    @Test
    void testVector4LMaxComponent() {
        final Vector4L vector1 = new Vector4L(12, 11, 10, 20);
        assertEquals(3, vector1.maxComponent());

        final Vector4L vector2 = new Vector4L(9, 8, 20, 6);
        assertEquals(2, vector2.maxComponent());

        final Vector4L vector3 = new Vector4L(8, 20, 6, 8);
        assertEquals(1, vector3.maxComponent());

        final Vector4L vector4 = new Vector4L(20, 6, 8, 10);
        assertEquals(0, vector4.maxComponent());
    }

    @Test
    void testVector4LSetComponentByComponentAndValue() {
        final Vector4L vector1 = new Vector4L(2, 3, 4, 5);
        vector1.setComponent(0, 10);
        assertEquals(new Vector4L(10, 3, 4, 5), vector1);

        vector1.setComponent(1, -6);
        assertEquals(new Vector4L(10, -6, 4, 5), vector1);

        vector1.setComponent(2, 5);
        assertEquals(new Vector4L(10, -6, 5, 5), vector1);

        vector1.setComponent(3, 11);
        assertEquals(new Vector4L(10, -6, 5, 11), vector1);

        assertThrows(IllegalArgumentException.class, () -> vector1.setComponent(4, 99));
    }
}
