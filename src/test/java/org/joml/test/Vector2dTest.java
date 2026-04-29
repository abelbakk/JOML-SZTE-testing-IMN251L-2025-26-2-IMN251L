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
 * Test class for {@link Vector2d}.
 * @author Sebastian Fellner
 */
class Vector2dTest {
    @Test
    void testVector2dEqualsWithVector2dAndDelta() {
        final float delta = MANY_OPS_AROUND_ZERO_PRECISION_FLOAT;
        final Vector2d v1 = new Vector2d(5, 15);
        assertTrue(v1.equals(new Vector2d(5, 15), delta));
        assertFalse(v1.equals(new Vector2d(5, -15), delta));
        assertFalse(v1.equals(new Vector2d(-5, 15), delta));
        assertFalse(v1.equals(null, delta));
        assertTrue(v1.equals(v1, delta));
    }

    @Test
    void testVector2dEqualsWithVector2d() {
        final Vector2d v1 = new Vector2d(5, 15);
        assertTrue(v1.equals(new Vector2d(5, 15)));
        assertFalse(v1.equals(new Vector3d(5, 15, 0)));
        assertFalse(v1.equals(new Vector2d(5, -15)));
        assertFalse(v1.equals(new Vector2d(-5, 15)));
        assertFalse(v1.equals(null));
        assertTrue(v1.equals(v1));
    }

    @Test
    void testVector2dEqualsWithCoordinates() {
        final Vector2d v1 = new Vector2d(5, 15);
        assertTrue(v1.equals(5, 15));
        assertFalse(v1.equals(5, -15));
        assertFalse(v1.equals(-5, -15));
    }

    @Test
    void testVector2dAbsoluteMinComponent() {
        assertEquals(0, new Vector2d(5, -15).minComponent());
        assertEquals(1, new Vector2d(-35, 2).minComponent());
    }

    @Test
    void testVector2dAbsoluteMaxComponent() {
        assertEquals(1, new Vector2d(5, -15).maxComponent());
        assertEquals(0, new Vector2d(-35, 2).maxComponent());
    }

    @Test
    void testVector2dMax() {
        final Vector2d v1 = new Vector2d(5, 15);
        assertEquals(new Vector2d(5, 15), v1.max(new Vector2d(-15, -8)));

        final Vector2d v2 = new Vector2d(6, 16);
        assertEquals(new Vector2d(35, 42), v2.max(new Vector2d(35, 42)));
    }

    @Test
    void testVector2dMin() {
        final Vector2d v1 = new Vector2d(5, 15);
        assertEquals(new Vector2d(-15, -8), v1.min(new Vector2d(-15, -8)));

        final Vector2d v2 = new Vector2d(6, 16);
        assertEquals(new Vector2d(6, 16), v2.min(new Vector2d(35, 42)));
    }

    @Test
    void testVector2dSetComponentByParameter() {
        final Vector2d v1 = new Vector2d(5, 15);
        assertEquals(new Vector2d(-22, 15), v1.setComponent(0, -22));
        assertEquals(new Vector2d(-22, 35), v1.setComponent(1, 35));
        assertThrows(IllegalArgumentException.class,() -> v1.setComponent(2, 10));
    }

    @Test
    void testVector2dGetComponentByParameter() {
        final Vector2d v1 = new Vector2d(5, 15);
        assertEquals(5, v1.get(0));
        assertEquals(15, v1.get(1));
        assertThrows(IllegalArgumentException.class, () -> v1.get(2));
    }

    @Test
    void testVector2dConstruction() {
        final Vector2d v1 = new Vector2d();
        assertEquals(new Vector2d(0, 0), v1);

        final Vector2d v2 = new Vector2d(20);
        assertEquals(new Vector2d(20, 20), v2);

        final Vector2d v3 = new Vector2d(v2);
        assertEquals(new Vector2d(20, 20), v3);
    }

    @Test
    void testAngleVector2dVector2d() {
        Vector2d testVec1 = new Vector2d(-9.37, 5.892);
        Vector2d testVec2 = new Vector2d();
        
        // angle(v, v) should give 0
        double angle = testVec1.angle(testVec1);
        assertEquals(0, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
    }

    @Test
    void testPerpendicular(){
        Vector2d testVec1 = new Vector2d(-9.37, 5.892);
        assertVector2dEquals(new Vector2d(testVec1).perpendicular(),new Vector2d(5.892,9.37),0.000001);
    }
}
