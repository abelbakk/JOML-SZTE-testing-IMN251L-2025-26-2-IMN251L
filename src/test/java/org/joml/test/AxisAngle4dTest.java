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
import org.joml.Matrix3d;
import org.joml.Matrix4d;
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

    @Test
    void testSetFromMatrix3f() {
        double angle = Math.toRadians(37.0);
        org.joml.Matrix3f m = new org.joml.Matrix3f().rotationX((float) angle);
        AxisAngle4d a = new AxisAngle4d().set(m);
        assertEquals(angle, a.angle, 1E-5);
        assertEquals(1.0, a.x, 1E-5);
        assertEquals(0.0, a.y, 1E-5);
        assertEquals(0.0, a.z, 1E-5);
    }

    @Test
    void testSetFromMatrix3d() {
        double angle = Math.toRadians(53.0);
        org.joml.Matrix3d m = new org.joml.Matrix3d().rotationX(angle);
        AxisAngle4d a = new AxisAngle4d().set(m);
        assertEquals(angle, a.angle, 1E-8);
        assertEquals(1.0, a.x, 1E-8);
        assertEquals(0.0, a.y, 1E-8);
        assertEquals(0.0, a.z, 1E-8);
    }

    @Test
    void testSetFromMatrix4f() {
        double angle = Math.toRadians(12.5);
        org.joml.Matrix4f m = new org.joml.Matrix4f().rotationX((float) angle);
        AxisAngle4d a = new AxisAngle4d().set(m);
        assertEquals(angle, a.angle, 1E-5);
        assertEquals(1.0, a.x, 1E-5);
        assertEquals(0.0, a.y, 1E-5);
        assertEquals(0.0, a.z, 1E-5);
    }

    @Test
    void testSetFromMatrix4x3f() {
        double angle = Math.toRadians(80.0);
        org.joml.Matrix4x3f m = new org.joml.Matrix4x3f().rotationX((float) angle);
        AxisAngle4d a = new AxisAngle4d().set(m);
        assertEquals(angle, a.angle, 1E-5);
        assertEquals(1.0, a.x, 1E-5);
        assertEquals(0.0, a.y, 1E-5);
        assertEquals(0.0, a.z, 1E-5);
    }

    @Test
    void testSetFromMatrix4d() {
        double angle = Math.toRadians(20.0);
        org.joml.Matrix4d m = new org.joml.Matrix4d().rotationX(angle);
        AxisAngle4d a = new AxisAngle4d().set(m);
        assertEquals(angle, a.angle, 1E-8);
        assertEquals(1.0, a.x, 1E-8);
        assertEquals(0.0, a.y, 1E-8);
        assertEquals(0.0, a.z, 1E-8);
    }

    @Test
    void testSetFromIdentityMatrix3d() {
        AxisAngle4d a = new AxisAngle4d().set(new Matrix3d());
        assertEquals(0.0, a.angle, 1E-8);
        assertEquals(0.0, a.x, 1E-8);
        assertEquals(0.0, a.y, 1E-8);
        assertEquals(1.0, a.z, 1E-8);
    }

    @Test
    void testSetFromRotationYMatrix3d() {
        double angle = Math.toRadians(90.0);
        Matrix3d m = new Matrix3d().rotationY(angle);
        AxisAngle4d a = new AxisAngle4d().set(m);
        assertEquals(angle, a.angle, 1E-8);
        assertEquals(0.0, a.x, 1E-8);
        assertEquals(1.0, a.y, 1E-8);
        assertEquals(0.0, a.z, 1E-8);
    }

    @Test
    void testSetFromRotationZMatrix4d() {
        double angle = Math.toRadians(135.0);
        Matrix4d m = new Matrix4d().rotationZ(angle);
        AxisAngle4d a = new AxisAngle4d().set(m);
        assertEquals(angle, a.angle, 1E-8);
        assertEquals(0.0, a.x, 1E-8);
        assertEquals(0.0, a.y, 1E-8);
        assertEquals(1.0, a.z, 1E-8);
    }

    @Test
    void testSetFromMatrix4dIdentityKeepsUnitAxis() {
        AxisAngle4d a = new AxisAngle4d(1.0, 1.0, 2.0, 3.0).set(new Matrix4d());
        assertEquals(0.0, a.angle, 1E-8);
        assertEquals(0.0, a.x, 1E-8);
        assertEquals(0.0, a.y, 1E-8);
        assertEquals(1.0, a.z, 1E-8);
    }

    @Test
    void testSetFromMatrix3dEdgeCases() {
        AxisAngle4d identity = new AxisAngle4d().set(new Matrix3d());
        assertEquals(0.0, identity.angle, 1E-8);
        assertEquals(0.0, identity.x, 1E-8);
        assertEquals(0.0, identity.y, 1E-8);
        assertEquals(1.0, identity.z, 1E-8);

        double angle = Math.PI;
        AxisAngle4d aroundX = new AxisAngle4d().set(new Matrix3d().rotationX(angle));
        assertEquals(angle, aroundX.angle, 1E-8);
        assertEquals(1.0, Math.abs(aroundX.x), 1E-8);
        assertEquals(0.0, Math.abs(aroundX.y), 1E-8);
        assertEquals(0.0, Math.abs(aroundX.z), 1E-8);

        AxisAngle4d aroundY = new AxisAngle4d().set(new Matrix3d().rotationY(angle));
        assertEquals(angle, aroundY.angle, 1E-8);
        assertEquals(0.0, Math.abs(aroundY.x), 1E-8);
        assertEquals(1.0, Math.abs(aroundY.y), 1E-8);
        assertEquals(0.0, Math.abs(aroundY.z), 1E-8);

        AxisAngle4d aroundZ = new AxisAngle4d().set(new Matrix3d().rotationZ(angle));
        assertEquals(angle, aroundZ.angle, 1E-8);
        assertEquals(0.0, Math.abs(aroundZ.x), 1E-8);
        assertEquals(0.0, Math.abs(aroundZ.y), 1E-8);
        assertEquals(1.0, Math.abs(aroundZ.z), 1E-8);
    }

    @Test
    void testSetFromMatrix4dcEdgeCases() {
        AxisAngle4d identity = new AxisAngle4d().set(new Matrix4d());
        assertEquals(0.0, identity.angle, 1E-8);
        assertEquals(0.0, identity.x, 1E-8);
        assertEquals(0.0, identity.y, 1E-8);
        assertEquals(1.0, identity.z, 1E-8);

        double angle = Math.PI;
        AxisAngle4d aroundX = new AxisAngle4d().set(new Matrix4d().rotationX(angle));
        assertEquals(angle, aroundX.angle, 1E-8);
        assertEquals(1.0, Math.abs(aroundX.x), 1E-8);
        assertEquals(0.0, Math.abs(aroundX.y), 1E-8);
        assertEquals(0.0, Math.abs(aroundX.z), 1E-8);

        AxisAngle4d aroundY = new AxisAngle4d().set(new Matrix4d().rotationY(angle));
        assertEquals(angle, aroundY.angle, 1E-8);
        assertEquals(0.0, Math.abs(aroundY.x), 1E-8);
        assertEquals(1.0, Math.abs(aroundY.y), 1E-8);
        assertEquals(0.0, Math.abs(aroundY.z), 1E-8);

        AxisAngle4d aroundZ = new AxisAngle4d().set(new Matrix4d().rotationZ(angle));
        assertEquals(angle, aroundZ.angle, 1E-8);
        assertEquals(0.0, Math.abs(aroundZ.x), 1E-8);
        assertEquals(0.0, Math.abs(aroundZ.y), 1E-8);
        assertEquals(1.0, Math.abs(aroundZ.z), 1E-8);
    }

    @Test
    void testSetFromMatrix3fEdgeCases() {
        AxisAngle4d identity = new AxisAngle4d().set(new org.joml.Matrix3f());
        assertEquals(0.0, identity.angle, 1E-8);
        assertEquals(0.0, identity.x, 1E-8);
        assertEquals(0.0, identity.y, 1E-8);
        assertEquals(1.0, identity.z, 1E-8);

        double angle = Math.PI;
        AxisAngle4d aroundX = new AxisAngle4d().set(new org.joml.Matrix3f().rotationX((float) angle));
        assertEquals(angle, aroundX.angle, 1E-5);
        assertEquals(1.0, Math.abs(aroundX.x), 1E-5);
        assertEquals(0.0, Math.abs(aroundX.y), 1E-5);
        assertEquals(0.0, Math.abs(aroundX.z), 1E-5);

        AxisAngle4d aroundY = new AxisAngle4d().set(new org.joml.Matrix3f().rotationY((float) angle));
        assertEquals(angle, aroundY.angle, 1E-5);
        assertEquals(0.0, Math.abs(aroundY.x), 1E-5);
        assertEquals(1.0, Math.abs(aroundY.y), 1E-5);
        assertEquals(0.0, Math.abs(aroundY.z), 1E-5);

        AxisAngle4d aroundZ = new AxisAngle4d().set(new org.joml.Matrix3f().rotationZ((float) angle));
        assertEquals(angle, aroundZ.angle, 1E-5);
        assertEquals(0.0, Math.abs(aroundZ.x), 1E-5);
        assertEquals(0.0, Math.abs(aroundZ.y), 1E-5);
        assertEquals(1.0, Math.abs(aroundZ.z), 1E-5);
    }

    @Test
    void testSetFromMatrix4fEdgeCases() {
        AxisAngle4d identity = new AxisAngle4d().set(new org.joml.Matrix4f());
        assertEquals(0.0, identity.angle, 1E-8);
        assertEquals(0.0, identity.x, 1E-8);
        assertEquals(0.0, identity.y, 1E-8);
        assertEquals(1.0, identity.z, 1E-8);

        double angle = Math.PI;
        AxisAngle4d aroundX = new AxisAngle4d().set(new org.joml.Matrix4f().rotationX((float) angle));
        assertEquals(angle, aroundX.angle, 1E-5);
        assertEquals(1.0, Math.abs(aroundX.x), 1E-5);
        assertEquals(0.0, Math.abs(aroundX.y), 1E-5);
        assertEquals(0.0, Math.abs(aroundX.z), 1E-5);

        AxisAngle4d aroundY = new AxisAngle4d().set(new org.joml.Matrix4f().rotationY((float) angle));
        assertEquals(angle, aroundY.angle, 1E-5);
        assertEquals(0.0, Math.abs(aroundY.x), 1E-5);
        assertEquals(1.0, Math.abs(aroundY.y), 1E-5);
        assertEquals(0.0, Math.abs(aroundY.z), 1E-5);

        AxisAngle4d aroundZ = new AxisAngle4d().set(new org.joml.Matrix4f().rotationZ((float) angle));
        assertEquals(angle, aroundZ.angle, 1E-5);
        assertEquals(0.0, Math.abs(aroundZ.x), 1E-5);
        assertEquals(0.0, Math.abs(aroundZ.y), 1E-5);
        assertEquals(1.0, Math.abs(aroundZ.z), 1E-5);
    }

    @Test
    void testSetFromMatrix4x3fEdgeCases() {
        AxisAngle4d identity = new AxisAngle4d().set(new org.joml.Matrix4x3f());
        assertEquals(0.0, identity.angle, 1E-8);
        assertEquals(0.0, identity.x, 1E-8);
        assertEquals(0.0, identity.y, 1E-8);
        assertEquals(1.0, identity.z, 1E-8);

        double angle = Math.PI;
        AxisAngle4d aroundX = new AxisAngle4d().set(new org.joml.Matrix4x3f().rotationX((float) angle));
        assertEquals(angle, aroundX.angle, 1E-5);
        assertEquals(1.0, Math.abs(aroundX.x), 1E-5);
        assertEquals(0.0, Math.abs(aroundX.y), 1E-5);
        assertEquals(0.0, Math.abs(aroundX.z), 1E-5);

        AxisAngle4d aroundY = new AxisAngle4d().set(new org.joml.Matrix4x3f().rotationY((float) angle));
        assertEquals(angle, aroundY.angle, 1E-5);
        assertEquals(0.0, Math.abs(aroundY.x), 1E-5);
        assertEquals(1.0, Math.abs(aroundY.y), 1E-5);
        assertEquals(0.0, Math.abs(aroundY.z), 1E-5);

        AxisAngle4d aroundZ = new AxisAngle4d().set(new org.joml.Matrix4x3f().rotationZ((float) angle));
        assertEquals(angle, aroundZ.angle, 1E-5);
        assertEquals(0.0, Math.abs(aroundZ.x), 1E-5);
        assertEquals(0.0, Math.abs(aroundZ.y), 1E-5);
        assertEquals(1.0, Math.abs(aroundZ.z), 1E-5);
    }
}
