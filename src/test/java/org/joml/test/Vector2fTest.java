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

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector2f}.
 * @author Sebastian Fellner
 */
class Vector2fTest {
    @Test
    void testVector2fEqualsWithVector2fAndDelta() {
        final float delta = MANY_OPS_AROUND_ZERO_PRECISION_FLOAT;
        final Vector2f v1 = new Vector2f(5, 15);
        assertTrue(v1.equals(new Vector2f(5, 15), delta));
        assertFalse(v1.equals(new Vector2f(5, -15), delta));
        assertFalse(v1.equals(new Vector2f(-5, 15), delta));
        assertFalse(v1.equals(null, delta));
        assertTrue(v1.equals(v1, delta));
    }

    @Test
    void testVector2fEqualsWithVector2f() {
        final Vector2f v1 = new Vector2f(5, 15);
        assertTrue(v1.equals(new Vector2f(5, 15)));
        assertFalse(v1.equals(new Vector3d(5, 15, 0)));
        assertFalse(v1.equals(new Vector2f(5, -15)));
        assertFalse(v1.equals(new Vector2f(-5, 15)));
        assertFalse(v1.equals(null));
        assertTrue(v1.equals(v1));
    }

    @Test
    void testVector2fEqualsWithCoordinates() {
        final Vector2f v1 = new Vector2f(5, 15);
        assertTrue(v1.equals(5, 15));
        assertFalse(v1.equals(5, -15));
        assertFalse(v1.equals(-5, -15));
    }

    @Test
    void testVector2fAbsoluteMinComponent() {
        assertEquals(0, new Vector2f(5, -15).minComponent());
        assertEquals(1, new Vector2f(-35, 2).minComponent());
    }

    @Test
    void testVector2fAbsoluteMaxComponent() {
        assertEquals(1, new Vector2f(5, -15).maxComponent());
        assertEquals(0, new Vector2f(-35, 2).maxComponent());
    }

    @Test
    void testVector2fMax() {
        final Vector2f v1 = new Vector2f(5, 15);
        assertEquals(new Vector2f(5, 15), v1.max(new Vector2f(-15, -8)));

        final Vector2f v2 = new Vector2f(6, 16);
        assertEquals(new Vector2f(35, 42), v2.max(new Vector2f(35, 42)));
    }

    @Test
    void testVector2fMin() {
        final Vector2f v1 = new Vector2f(5, 15);
        assertEquals(new Vector2f(-15, -8), v1.min(new Vector2f(-15, -8)));

        final Vector2f v2 = new Vector2f(6, 16);
        assertEquals(new Vector2f(6, 16), v2.min(new Vector2f(35, 42)));
    }

    @Test
    void testVector2fSetComponentByParameter() {
        final Vector2f v1 = new Vector2f(5, 15);
        assertEquals(new Vector2f(-22, 15), v1.setComponent(0, -22));
        assertEquals(new Vector2f(-22, 35), v1.setComponent(1, 35));
        assertThrows(IllegalArgumentException.class,() -> v1.setComponent(2, 10));
    }

    @Test
    void testVector2fGetComponentByParameter() {
        final Vector2f v1 = new Vector2f(5, 15);
        assertEquals(5, v1.get(0));
        assertEquals(15, v1.get(1));
        assertThrows(IllegalArgumentException.class, () -> v1.get(2));
    }

    @Test
    void testVector2fConstruction() {
        final Vector2f v1 = new Vector2f();
        assertEquals(new Vector2f(0, 0), v1);

        final Vector2f v2 = new Vector2f(20);
        assertEquals(new Vector2f(20, 20), v2);

        final Vector2f v3 = new Vector2f(v2);
        assertEquals(new Vector2f(20, 20), v3);
    }

    @Test
    void testAngleVector2fVector2f() {
        Vector2f testVec1 = new Vector2f(-9.37f, 5.892f);
        Vector2f testVec2 = new Vector2f();
        
        // angle(v, v) should give 0
        float angle = testVec1.angle(testVec1);
        assertEquals(0, angle, MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, MANY_OPS_AROUND_ZERO_PRECISION_FLOAT);
    }

    @Test
    void testPerpendicular(){
        Vector2f testVec1 = new Vector2f(-9.37f, 5.892f);
        assertVector2fEquals(new Vector2f(testVec1).perpendicular(),new Vector2f(5.892f,9.37f),0.000001f);
    }
}
