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

import org.joml.AxisAngle4f;
import org.joml.Matrix3d;
import org.joml.Matrix3f;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Math;
import org.joml.Quaternionf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link AxisAngle4f} class.
 * 
 * @author Kai Burjack
 */
class AxisAngle4fTest {
    @Test
    void testAxisAngleFromIdentityQuaternion() {
        AxisAngle4f a = new AxisAngle4f().set(new Quaternionf());
        assertEquals(new AxisAngle4f(0, 0, 0, 1), a);
        a = new AxisAngle4f().set(new Quaternionf(2.035E-9,4.715E-10,-9.166E-11,1.000E+0));
        assertEquals(new AxisAngle4f(0, 0, 0, 1), a);
    }

    @Test
    void testAngleNormalization() {
        AxisAngle4f a1 = new AxisAngle4f(Math.toRadians(20), 1.0f, 0.0f, 0.0f);
        AxisAngle4f a2 = new AxisAngle4f(Math.toRadians(380), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AxisAngle4f(Math.toRadians(-20), 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f(Math.toRadians(-380.0f), 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);

        a1 = new AxisAngle4f(Math.toRadians(-20.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        a2 = new AxisAngle4f(Math.toRadians(-380.0f) * 10.0f, 1.0f, 0.0f, 0.0f);
        assertEquals(a1.angle, a2.angle, 1E-5f);
    }

    @Test
    void testSetFromMatrix3fAnd3dIdentityAndPi() {
        AxisAngle4f a = new AxisAngle4f();
        // identity -> angle == 0 branch
        a.set(new Matrix3f());
        assertEquals(0.0f, a.angle, 1E-6f);
        assertEquals(0.0f, a.x, 1E-6f);
        assertEquals(0.0f, a.y, 1E-6f);
        assertEquals(1.0f, a.z, 1E-6f);

        // 180 degree rotation around X axis -> PI branch
        Matrix3f rotX180 = new Matrix3f(
                1f, 0f, 0f,
                0f, -1f, 0f,
                0f, 0f, -1f);
        a.set(rotX180);
        assertEquals(Math.PI_f, a.angle, 1E-5f);
        assertEquals(1.0f, Math.abs(a.x), 1E-5f);

        // double-precision variants
        AxisAngle4f b = new AxisAngle4f();
        b.set(new Matrix3d());
        assertEquals(0.0f, b.angle, 1E-6f);
        Matrix3d rotX180d = new Matrix3d(1.0, 0.0, 0.0,
                                         0.0, -1.0, 0.0,
                                         0.0, 0.0, -1.0);
        b.set(rotX180d);
        assertEquals((float) Math.PI, b.angle, 1E-6f);
    }

    @Test
    void testSetFromMatrix4fAnd4d() {
        AxisAngle4f a = new AxisAngle4f();
        // identity 4x4
        a.set(new Matrix4f());
        assertEquals(0.0f, a.angle, 1E-6f);

        AxisAngle4f b = new AxisAngle4f();
        b.set(new Matrix4d());
        assertEquals(0.0f, b.angle, 1E-6f);
    }

    @Test
    void testSetFloatNormalization() {
        AxisAngle4f a = new AxisAngle4f();
        // negative angle should be normalized
        a.set(-1.0f, 0.0f, 0.0f, 1.0f);
        float expected = (Math.PI_TIMES_2_f + (-1.0f) % Math.PI_TIMES_2_f) % Math.PI_TIMES_2_f;
        assertEquals(expected, a.angle, 1E-6f);
    }

    @Test
    void testRotateWrapAround() {
        AxisAngle4f a = new AxisAngle4f();
        a.set(3.0f, 0.0f, 0.0f, 1.0f);
        a.rotate(5.0f);
        float wrapped = (3.0f + 5.0f);
        wrapped = (wrapped < 0.0 ? Math.PI_TIMES_2_f + wrapped % Math.PI_TIMES_2_f : wrapped) % Math.PI_TIMES_2_f;
        assertEquals(wrapped, a.angle, 1E-6f);
    }

    @Test
    void testHashCodeEqualsConsistency() {
        AxisAngle4f a1 = new AxisAngle4f();
        AxisAngle4f a2 = new AxisAngle4f();
        a1.set(-1.0f, 0f, 0f, 1f);
        a2.set(Math.PI_TIMES_2_f - 1.0f, 0f, 0f, 1f);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertTrue(a1.equals(a2));
    }
}
