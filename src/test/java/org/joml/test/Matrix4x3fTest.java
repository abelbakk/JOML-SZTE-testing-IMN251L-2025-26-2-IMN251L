/*
 * The MIT License
 *
 * Copyright (c) 2016-2026  JOML.
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
import org.joml.Math;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Matrix4x3f} class.
 * 
 * @author Kai Burjack
 */
class Matrix4x3fTest {
    private static final float[] NORMAL_MATRIX_4x3F_CONTENT = new float[] {
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12};
    private static final Matrix4x3f NORMAL_MATRIX_4x3F = new Matrix4x3f(NORMAL_MATRIX_4x3F_CONTENT);
    private static final Matrix4x3f PROPERTY_TRANSLATION_MATRIX_4x3F = new Matrix4x3f(new float[]{
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 5, -2, 4});
    private static final Matrix4x3f PROPERTY_IDENTITY_MATRIX_4x3F = new Matrix4x3f(new float[]{
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 0, 0, 0});
    private static final float DELTA = TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT;
    private static final float NAN = Float.POSITIVE_INFINITY * 0;

    @Test
    void testRotateTranslation() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        final Matrix4x3f dest = new Matrix4x3f();
        m1.rotateTranslation(Math.toRadians(90.0f), 0, 0, 1, dest);
        assertNotEquals(dest, m1);

        m1.rotateTranslation(Math.toRadians(90.0f), 0, 0, 2, dest);
        assertNotEquals(dest, m1);

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateTranslation(Math.toRadians(90.0f), 0, 1, 0, dest);
        assertNotEquals(dest, m1);

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateTranslation(Math.toRadians(90.0f), 0, 2, 0, dest);
        assertNotEquals(dest, m1);

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateTranslation(Math.toRadians(90.0f), 1, 0, 0, dest);
        assertNotEquals(dest, m1);

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateTranslation(Math.toRadians(90.0f), 2, 0, 0, dest);
        assertNotEquals(dest, m1);

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateTranslation(Math.toRadians(90.0f), 1, 1, 1, dest);
        assertNotEquals(dest, m1);
    }

    @Test
    void testRotate() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.rotate(Math.toRadians(90.0f), 0, 0, 1);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 4, 5, 6, -1, -2, -3, 7, 8, 9, 10, 11, 12 }), DELTA));

        m1.rotate(Math.toRadians(90.0f), 0, 0, 2);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ -2, -4, -6, -8, -10, -12, 28, 32, 36, 10, 11, 12 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotate(Math.toRadians(90.0f), 0, 1, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ -7, -8, -9, 4, 5, 6, 1, 2, 3, 10, 11, 12 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotate(Math.toRadians(90.0f), 0, 2, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ -14, -16, -18, 16, 20, 24, 2, 4, 6, 10, 11, 12 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotate(Math.toRadians(90.0f), 1, 0, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 1, 2, 3, 7, 8, 9, -4, -5, -6, 10, 11, 12 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotate(Math.toRadians(90.0f), 2, 0, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 4, 8, 12, 14, 16, 18, -8, -10, -12, 10, 11, 12 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotate(Math.toRadians(90.0f), 1, 1, 1);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 9, 12, 15, 18, 21, 24, 9, 12, 15, 10, 11, 12}), DELTA));
    }

    @Test
    void testRotateLocal() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.rotateLocal(Math.toRadians(90.0f), 0, 0, 1);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ -2, 1, 3, -5, 4, 6, -8, 7, 9, -11, 10, 12 }), DELTA));

        m1.rotateLocal(Math.toRadians(90.0f), 0, 0, 2);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ -2, -4, 12, -8, -10, 24, -14, -16, 36, -20, -22, 48 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateLocal(Math.toRadians(90.0f), 0, 1, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 3, 2, -1, 6, 5, -4, 9, 8, -7, 12, 11, -10 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateLocal(Math.toRadians(90.0f), 0, 2, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 6, 8, -2, 12, 20, -8, 18, 32, -14, 24, 44, -20 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateLocal(Math.toRadians(90.0f), 1, 0, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 1, -3, 2, 4, -6, 5, 7, -9, 8, 10, -12, 11 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateLocal(Math.toRadians(90.0f), 2, 0, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 4, -6, 4, 16, -12, 10, 28, -18, 16, 40, -24, 22 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotateLocal(Math.toRadians(90.0f), 1, 1, 1);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 7, 4, 7, 16, 13, 16, 25, 22, 25, 34, 31, 34 }), DELTA));
    }

    @Test
    void testGetRowError() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        final Vector4f v1 = new Vector4f();
        assertThrows(IndexOutOfBoundsException.class, () -> m1.getRow(3, v1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    void testSetRow(int columnIndex) {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        final Vector4f v1 = new Vector4f(2, 2, 2, 2);
        final Vector4f v2 = new Vector4f();

        if (columnIndex < 3) {
            m1.setRow(columnIndex, v1);
            m1.getRow(columnIndex, v2);
            assertEquals(v1, v2);
        } else {
            assertThrows(IndexOutOfBoundsException.class, () -> m1.setRow(columnIndex, v2));
        }
    }

    @Test
    void testGetColumnError() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        final Vector3f v1 = new Vector3f();
        assertThrows(IndexOutOfBoundsException.class, () -> m1.getColumn(4, v1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testSetColumn(int columnIndex) {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        final Vector3f v1 = new Vector3f(2, 2, 2);
        final Vector3f v2 = new Vector3f();

        if (columnIndex < 4) {
            m1.setColumn(columnIndex, v1);
            m1.getColumn(columnIndex, v2);
            assertEquals(v1, v2);
        } else {
            assertThrows(IndexOutOfBoundsException.class, () -> m1.setColumn(columnIndex, v2));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    void testFrustumPlane(int planeIndex) {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        final Vector4f dest = new Vector4f();

        if (planeIndex < 6) {
            m1.frustumPlane(planeIndex, dest);
            assertNotEquals(new Vector4f(), dest);
        } else {
            assertThrows(IllegalArgumentException.class, () -> m1.frustumPlane(planeIndex, dest));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    void testEqualsObject(int index) {
        final float[] array = NORMAL_MATRIX_4x3F_CONTENT.clone();

        if (index < array.length) {
            array[index] = Integer.MAX_VALUE;
        }

        final Matrix4x3f m1 = new Matrix4x3f(array);

        if (index < array.length) {
            assertNotEquals(NORMAL_MATRIX_4x3F, m1);
        } else {
            assertEquals(NORMAL_MATRIX_4x3F, m1);
        }
    }

    @Test
    void testEqualsMatrix4x3fWithDeltaSpecialCases() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F_CONTENT);
        assertFalse(m1.equals(null, DELTA));
        assertTrue(m1.equals(m1, DELTA));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    void testEqualsMatrix4x3fWithDelta(int index) {
        final float[] array = NORMAL_MATRIX_4x3F_CONTENT.clone();

        if (index < array.length) {
            array[index] = Integer.MAX_VALUE;
        }

        final Matrix4x3f m1 = new Matrix4x3f(array);

        if (index < array.length) {
            assertFalse(m1.equals(NORMAL_MATRIX_4x3F, DELTA));
        } else {
            assertTrue(m1.equals(NORMAL_MATRIX_4x3F, DELTA));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    void testIsFinite(int index) {
        final float[] array = NORMAL_MATRIX_4x3F_CONTENT.clone();

        if (index < array.length) {
            array[index] = NAN;
        }

        final Matrix4x3f m1 = new Matrix4x3f(array);

        if (index < array.length) {
            assertFalse(m1.isFinite());
        } else {
            assertTrue(m1.isFinite());
        }
    }

    @Test
    void testRotation() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.rotation(Math.toRadians(90.0f), 0, 0, 1);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 0, 1, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0 }), DELTA));

        m1.rotation(Math.toRadians(90.0f), 0, 0, 2);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 0, 2, 0, -2, 0, 0, 0, 0, 4, 0, 0, 0 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotation(Math.toRadians(90.0f), 0, 1, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 0, 0, -1, 0, 1, 0, 1, 0, 0, 0, 0, 0 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotation(Math.toRadians(90.0f), 0, 2, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 0, 0, -2, 0, 4, 0, 2, 0, 0, 0, 0, 0 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotation(Math.toRadians(90.0f), 1, 0, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 1, 0, 0, 0, 0, 1, 0, -1, 0, 0, 0, 0 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotation(Math.toRadians(90.0f), 2, 0, 0);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 4, 0, 0, 0, 0, 2, 0, -2, 0, 0, 0, 0 }), DELTA));

        m1.set(NORMAL_MATRIX_4x3F);
        m1.rotation(Math.toRadians(90.0f), 1, 1, 1);
        assertTrue(m1.equals(new Matrix4x3f(new float[]{ 1, 2, 0, 0, 1, 2, 2, 0, 1, 0, 0, 0 }), DELTA));
    }

    @Test
    void testMulWithMatrix4x3f() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.mul(NORMAL_MATRIX_4x3F);

        final Matrix4x3f m2 = new Matrix4x3f(PROPERTY_TRANSLATION_MATRIX_4x3F);
        m2.mul(PROPERTY_TRANSLATION_MATRIX_4x3F);

        final Matrix4x3f m3 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m3.mul(PROPERTY_IDENTITY_MATRIX_4x3F);

        final Matrix4x3f m4 = new Matrix4x3f(PROPERTY_IDENTITY_MATRIX_4x3F);
        m4.mul(PROPERTY_IDENTITY_MATRIX_4x3F);
    }

    @Test
    void testSetMatrix4x3f() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.set(m1);
        assertEquals(NORMAL_MATRIX_4x3F, m1);
    }

    @Test
    void testIdentity() {
        final Matrix4x3f m1 = PROPERTY_IDENTITY_MATRIX_4x3F.identity();
        assertEquals(PROPERTY_IDENTITY_MATRIX_4x3F, m1);
    }

    @Test
    void testM00() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m00(0.0f);
        assertEquals(0.0f, m1.m00(), DELTA);

        m1.m00(1.0f);
        assertEquals(1.0f, m1.m00(), DELTA);
    }

    @Test
    void testM01() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m01(0.0f);
        assertEquals(0.0f, m1.m01(), DELTA);

        m1.m01(1.0f);
        assertEquals(1.0f, m1.m01(), DELTA);
    }

    @Test
    void testM02() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m02(0.0f);
        assertEquals(0.0f, m1.m02(), DELTA);

        m1.m02(1.0f);
        assertEquals(1.0f, m1.m02(), DELTA);
    }

    @Test
    void testM10() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m10(0.0f);
        assertEquals(0.0f, m1.m10(), DELTA);

        m1.m10(1.0f);
        assertEquals(1.0f, m1.m10(), DELTA);
    }

    @Test
    void testM11() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m11(0.0f);
        assertEquals(0.0f, m1.m11(), DELTA);

        m1.m11(1.0f);
        assertEquals(1.0f, m1.m11(), DELTA);
    }

    @Test
    void testM12() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m12(0.0f);
        assertEquals(0.0f, m1.m12(), DELTA);

        m1.m12(1.0f);
        assertEquals(1.0f, m1.m12(), DELTA);
    }

    @Test
    void testM20() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m20(0.0f);
        assertEquals(0.0f, m1.m20(), DELTA);

        m1.m20(1.0f);
        assertEquals(1.0f, m1.m20(), DELTA);
    }

    @Test
    void testM21() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m21(0.0f);
        assertEquals(0.0f, m1.m21(), DELTA);

        m1.m21(1.0f);
        assertEquals(1.0f, m1.m21(), DELTA);
    }

    @Test
    void testM22() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m22(0.0f);
        assertEquals(0.0f, m1.m22(), DELTA);

        m1.m22(1.0f);
        assertEquals(1.0f, m1.m22(), DELTA);
    }

    @Test
    void testM30() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m30(0.0f);
        assertEquals(0.0f, m1.m30(), DELTA);

        m1.m30(1.0f);
        assertEquals(1.0f, m1.m30(), DELTA);
    }

    @Test
    void testM31() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m31(0.0f);
        assertEquals(0.0f, m1.m31(), DELTA);

        m1.m31(1.0f);
        assertEquals(1.0f, m1.m31(), DELTA);
    }

    @Test
    void testM32() {
        final Matrix4x3f m1 = new Matrix4x3f(NORMAL_MATRIX_4x3F);
        m1.m32(0.0f);
        assertEquals(0.0f, m1.m32(), DELTA);

        m1.m32(1.0f);
        assertEquals(1.0f, m1.m32(), DELTA);
    }

    @Test
    void testLookAt() {
        Matrix4x3f m1, m2;
        m1 = new Matrix4x3f().lookAt(0, 2, 3, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3)).rotateX(
                Math.atan2(2, 3));
        assertMatrix4x3fEquals(m1, m2, 1E-5f);
        m1 = new Matrix4x3f().lookAt(3, 2, 0, 0, 0, 0, 0, 1, 0);
        m2 = new Matrix4x3f().translate(0, 0, -(float) Math.sqrt(2 * 2 + 3 * 3))
                .rotateX(Math.atan2(2, 3)).rotateY(Math.toRadians(-90));
        assertMatrix4x3fEquals(m1, m2, 1E-4f);
    }

    @Test
    void testPositiveXRotateY() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateY(Math.toRadians(90));
        m.positiveX(dir);
        assertVector3fEquals(new Vector3f(0, 0, 1), dir, 1E-7f);
    }

    @Test
    void testPositiveYRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateX(Math.toRadians(90));
        m.positiveY(dir);
        assertVector3fEquals(new Vector3f(0, 0, -1), dir, 1E-7f);
    }

    @Test
    void testPositiveZRotateX() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateX(Math.toRadians(90));
        m.positiveZ(dir);
        assertVector3fEquals(new Vector3f(0, 1, 0), dir, 1E-7f);
    }

    @Test
    void testPositiveXRotateXY() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .rotateY(Math.toRadians(90)).rotateX(Math.toRadians(45));
        m.positiveX(dir);
        assertVector3fEquals(new Vector3f(0, 1, 1).normalize(), dir, 1E-7f);
    }

    @Test
    void testPositiveXYZLookAt() {
        Vector3f dir = new Vector3f();
        Matrix4x3f m = new Matrix4x3f()
                .lookAt(0, 0, 0, -1, 0, 0, 0, 1, 0);
        m.positiveX(dir);
        assertVector3fEquals(new Vector3f(0, 0, -1).normalize(), dir, 1E-7f);
        m.positiveY(dir);
        assertVector3fEquals(new Vector3f(0, 1, 0).normalize(), dir, 1E-7f);
        m.positiveZ(dir);
        assertVector3fEquals(new Vector3f(1, 0, 0).normalize(), dir, 1E-7f);
    }

    @Test
    void testPositiveXYZSameAsInvert() {
        Vector3f dir = new Vector3f();
        Vector3f dir2 = new Vector3f();
        Matrix4x3f m = new Matrix4x3f().rotateXYZ(0.12f, 1.25f, -2.56f);
        Matrix4x3f inv = new Matrix4x3f(m).invert();
        m.positiveX(dir);
        inv.transformDirection(dir2.set(1, 0, 0));
        assertVector3fEquals(dir2, dir, 1E-6f);
        m.positiveY(dir);
        inv.transformDirection(dir2.set(0, 1, 0));
        assertVector3fEquals(dir2, dir, 1E-6f);
        m.positiveZ(dir);
        inv.transformDirection(dir2.set(0, 0, 1));
        assertVector3fEquals(dir2, dir, 1E-6f);
    }

    @Test
    void testNormal() {
        Matrix4x3f r = new Matrix4x3f().rotateY((float) Math.PI / 2);
        Matrix4x3f s = new Matrix4x3f(r).scale(0.2f);
        Matrix4x3f n = new Matrix4x3f();
        s.normal(n);
        n.normalize3x3();
        assertMatrix4x3fEquals(r, n, 1E-8f);
    }

    @Test
    void testInvert() {
        Matrix4x3f invm = new Matrix4x3f();
        Matrix4x3f m = new Matrix4x3f();
        m.rotateX(1.2f).rotateY(0.2f).rotateZ(0.1f).translate(1, 2, 3).invert(invm);
        Vector3f orig = new Vector3f(4, -6, 8);
        Vector3f v = new Vector3f();
        Vector3f w = new Vector3f();
        m.transformPosition(orig, v);
        invm.transformPosition(v, w);
        assertVector3fEquals(orig, w, 1E-6f);
        invm.invert();
        assertMatrix4x3fEquals(m, invm, 1E-6f);
    }

    @Test
    void testRotateXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateXYZ(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateZYX() {
        Matrix4x3f m = new Matrix4x3f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateZYX(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateYXZ(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateAffineXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotateX(0.12f).rotateY(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateXYZ(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateAffineZYX() {
        Matrix4x3f m = new Matrix4x3f().rotateZ(1.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateZYX(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateAffineYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotateY(1.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotateYXZ(1.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationXYZ() {
        Matrix4x3f m = new Matrix4x3f().rotationX(0.32f).rotateY(0.5623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationXYZ(0.32f, 0.5623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationZYX() {
        Matrix4x3f m = new Matrix4x3f().rotationZ(0.12f).rotateY(0.0623f).rotateX(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationZYX(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationYXZ() {
        Matrix4x3f m = new Matrix4x3f().rotationY(0.12f).rotateX(0.0623f).rotateZ(0.95f);
        Matrix4x3f n = new Matrix4x3f().rotationYXZ(0.12f, 0.0623f, 0.95f);
        assertMatrix4x3fEquals(m, n, 1E-6f);
    }

}
