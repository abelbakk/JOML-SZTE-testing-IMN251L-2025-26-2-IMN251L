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

import org.joml.AxisAngle4d;
import org.joml.Math;
import org.joml.Quaterniond;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AxisAngle4dTest {
    @Test
    void testAxisAngleFromIdentityQuaternion() {
        AxisAngle4d a = new AxisAngle4d().set(new Quaterniond());
        assertEquals(new AxisAngle4d(0, 0, 0, 1), a);
        a = new AxisAngle4d().set(new Quaterniond(2.035E-9, 4.715E-10, -9.166E-11, 1.000E+0));
        assertEquals(new AxisAngle4d(0, 0, 0, 1), a);
    }

    @Test
    void testAngleNormalization() {
        AxisAngle4d a1 = new AxisAngle4d(Math.toRadians(20), 1.0, 0.0, 0.0);
        AxisAngle4d a2 = new AxisAngle4d(Math.toRadians(380), 1.0, 0.0, 0.0);
        assertEquals(a1.angle, a2.angle, 1E-5);

        a1 = new AxisAngle4d(Math.toRadians(-20), 1.0, 0.0, 0.0);
        a2 = new AxisAngle4d(Math.toRadians(-380.0), 1.0, 0.0, 0.0);
        assertEquals(a1.angle, a2.angle, 1E-5);

        a1 = new AxisAngle4d(Math.toRadians(-20.0) * 10.0, 1.0, 0.0, 0.0);
        a2 = new AxisAngle4d(Math.toRadians(-380.0) * 10.0, 1.0, 0.0, 0.0);
        assertEquals(a1.angle, a2.angle, 1E-5);
    }
}
