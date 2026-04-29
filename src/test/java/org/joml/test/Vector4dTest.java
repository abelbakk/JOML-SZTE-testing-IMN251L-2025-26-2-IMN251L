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
import org.joml.Math;
import org.junit.jupiter.api.Test;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector4d}.
 * @author Sebastian Fellner
 */
class Vector4dTest {
    private static final Matrix4f NORMAL_MATRIX_4F = new Matrix4f(new float[] {
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12,
            13, 14, 15, 16});
    private static final Matrix4d NORMAL_MATRIX_4D = new Matrix4d(NORMAL_MATRIX_4F);
    private static final Matrix4f PROPERTY_TRANSLATION_MATRIX_4F = new Matrix4f(new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            5, -2, 4, 1});
    private static final Matrix4d PROPERTY_TRANSLATION_MATRIX_4D = new Matrix4d(PROPERTY_TRANSLATION_MATRIX_4F);
    private static final Matrix4f PROPERTY_AFFINE_MATRIX_4F = new Matrix4f(new float[]{
            2, 0, 2, 0,
            0,-2, 0, 0,
            0, 8, 4, 0,
            0, 0, 0, 1});
    private static final Matrix4d PROPERTY_AFFINE_MATRIX_4D = new Matrix4d(PROPERTY_AFFINE_MATRIX_4F);
    private static final Matrix4f PROPERTY_IDENTITY_MATRIX_4F = new Matrix4f(new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1});
    private static final Matrix4d PROPERTY_IDENTITY_MATRIX_4D = new Matrix4d(PROPERTY_IDENTITY_MATRIX_4F);
    private static final Matrix4x3f NORMAL_MATRIX_4x3F = new Matrix4x3f(new float[] {
            1, 2, 3,
            4, 5, 6,
            7, 8, 9,
            10, 11, 12});
    private static final Matrix4x3d NORMAL_MATRIX_4x3D = new Matrix4x3d(NORMAL_MATRIX_4x3F);
    private static final Matrix4x3f PROPERTY_TRANSLATION_MATRIX_4x3F = new Matrix4x3f(new float[]{
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,
            5, -2, 4});
    private static final Matrix4x3d PROPERTY_TRANSLATION_MATRIX_4x3D = new Matrix4x3d(PROPERTY_TRANSLATION_MATRIX_4x3F);
    private static final Matrix4x3f PROPERTY_IDENTITY_MATRIX_4x3F = new Matrix4x3f(new float[]{
            1, 0, 0,
            0, 1, 0,
            0, 0, 1,
            0, 0, 0});
    private static final Matrix4x3d PROPERTY_IDENTITY_MATRIX_4x3D = new Matrix4x3d(PROPERTY_IDENTITY_MATRIX_4x3F);
    private static final float DELTA = TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT;

    @Test
    void testVector4SetByVector4() {
        final Vector4d vector1 = new Vector4d();
        final Vector4d vector2 = new Vector4d(2, 3, 4, 5);
        vector1.set(vector2);
        assertEquals(vector2, vector1);

        vector1.set(vector1);
        assertEquals(vector2, vector1);
    }

    @Test
    void testVector4dMulTransposeWithMatrix4f() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        vector1.mulTranspose(NORMAL_MATRIX_4D);
        assertEquals(new Vector4d(80.0f, 224.0f, 368.0f, 512.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mulTranspose(PROPERTY_TRANSLATION_MATRIX_4D);
        assertEquals(new Vector4d(7.0f, 8.0f, 9.0f, 65.0f), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector4d vector3 = new Vector4d(6, 8, 10, 12);
        vector3.mulTranspose(PROPERTY_AFFINE_MATRIX_4D);
        assertEquals(new Vector4d(32.0f, -16.0f, 104.0f, 12.0f), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector4 = new Vector4d(6, 8, 10,12);
        vector4.mulTranspose(PROPERTY_IDENTITY_MATRIX_4D);
        assertEquals(new Vector4d(6.0f, 8.0f, 10.0f, 12.0f), vector4);
    }

    @Test
    void testVector4dRotateAxis() {
        final Vector4d vector1 = new Vector4d(3, 5, 1, 1).rotateAxis(Math.PI_f, 1, 0, 0);
        assertTrue(vector1.equals(new Vector4d(3.0f, -5.0f, -1.0f, 1.0f), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));

        final Vector4d vector2 = new Vector4d(3, 5, 1, 1).rotateAxis(Math.PI_f, 0, 1, 0);
        assertTrue(vector2.equals(new Vector4d(-3.0f, 5.0f, -1.0f, 1.0f), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));

        final Vector4d vector3 = new Vector4d(3, 5, 1, 1).rotateAxis(Math.PI_f, 0, 0, 1);
        assertTrue(vector3.equals(new Vector4d(-3.0f, -5.0f, 1.0f, 1.0f), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));

        final Vector4d vector4 = new Vector4d(3, 5, 1, 1).rotateAxis(Math.PI_f, 1, 1, 1);
        assertTrue(vector4.equals(new Vector4d(9.0f, 3.0f, 15.0f, 1.0f), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));

        final Vector4d vector5 = new Vector4d(3, 5, 1, 1).rotateAxis(Math.PI_f, 3, 0, 0);
        assertTrue(vector5.equals(new Vector4d(27.0f, -45.0f, -9.0f, 1.0f), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));

        final Vector4d vector6 = new Vector4d(3, 5, 1, 1).rotateAxis(Math.PI_f, 0, 3, 0);
        assertTrue(vector6.equals(new Vector4d(-27.0f, 45.0f, -9.0f, 1.0f), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));

        final Vector4d vector7 = new Vector4d(3, 5, 1, 1).rotateAxis(Math.PI_f, 0, 0, 3);
        assertTrue(vector7.equals(new Vector4d(-27.0f, -45.0f, 9.0f, 1.0f), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
    }

    @Test
    void testVector4dGetComponent() {
        final Vector4d vector1 = new Vector4d(3, 5, 1, 10);
        assertEquals(3, vector1.get(0));
        assertEquals(5, vector1.get(1));
        assertEquals(1, vector1.get(2));
        assertEquals(10, vector1.get(3));
        assertThrows(IllegalArgumentException.class, () -> vector1.get(4));
    }

    @Test
    void testVector4dMax() {
        final Vector4d vector1 = new Vector4d(3, 5, 1, 7);
        final Vector4d vector2 = new Vector4d(10, 22, 3, 10);
        vector1.max(vector2);
        assertEquals(new Vector4d(10, 22, 3, 10), vector1);

        final Vector4d vector3 = new Vector4d(3, 5, 1, 7);
        final Vector4d vector4 = new Vector4d(10, 22, 3, 10);
        vector4.max(vector3);
        assertEquals(new Vector4d(10, 22, 3, 10), vector4);
    }

    @Test
    void testVector4dMin() {
        final Vector4d vector1 = new Vector4d(3, 5, 1, 7);
        final Vector4d vector2 = new Vector4d(10, 22, 3, 10);
        vector1.min(vector2);
        assertEquals(new Vector4d(3, 5, 1, 7), vector1);

        final Vector4d vector3 = new Vector4d(3, 5, 1, 7);
        final Vector4d vector4 = new Vector4d(10, 22, 3, 10);
        vector4.min(vector3);
        assertEquals(new Vector4d(3, 5, 1, 7), vector4);
    }

    @Test
    void testVector4dEqualsWithVector4dWithDelta() {
        final Vector4d vector1 = new Vector4d(3, 5, 1, 7);
        assertTrue(vector1.equals(new Vector4d(3, 5, 1, 7), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
        assertFalse(vector1.equals(new Vector4d(-3, 5, 1, 7), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
        assertFalse(vector1.equals(new Vector4d(3, -5, 1, 7), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
        assertFalse(vector1.equals(new Vector4d(3, 5, -1, 7), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
        assertFalse(vector1.equals(new Vector4d(3, 5, 1, -7), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
        assertFalse(vector1.equals(null, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
        assertTrue(vector1.equals(vector1, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
    }

    @Test
    void testVector4dEqualsWithVector4d() {
        final Vector4d vector1 = new Vector4d(3, 5, 1, 7);
        assertEquals(new Vector4d(3, 5, 1, 7), vector1);
        assertNotEquals(new Vector4d(-3, 5, 1, 7), vector1);
        assertNotEquals(new Vector4d(3, -5, 1, 7), vector1);
        assertNotEquals(new Vector4d(3, 5, -1, 7), vector1);
        assertNotEquals(new Vector4d(3, 5, 1, -7), vector1);
        assertEquals(vector1, vector1);
        assertFalse(vector1.equals(null));
        assertFalse(vector1.equals(new Vector2i(3, 5)));
    }

    @Test
    void testVector4dEqualsWithCoordinates() {
        final Vector4d vector1 = new Vector4d(3, 5, 1, 7);
        assertTrue(vector1.equals(3, 5, 1, 7));
        assertFalse(vector1.equals(-3, 5, 1, 7));
        assertFalse(vector1.equals(3, -5, 1, 7));
        assertFalse(vector1.equals(3, 5, -1, 7));
        assertFalse(vector1.equals(3, 5, 1, -7));
    }

    @Test
    void testVector4dMinComponent() {
        final Vector4d vector1 = new Vector4d(7, 8, 9, 6);
        assertEquals(3, vector1.minComponent());

        final Vector4d vector2 = new Vector4d(8, 10, 6, 8);
        assertEquals(2, vector2.minComponent());

        final Vector4d vector3 = new Vector4d(10, 6, 8, 10);
        assertEquals(1, vector3.minComponent());

        final Vector4d vector4 = new Vector4d(6, 8, 10, 12);
        assertEquals(0, vector4.minComponent());
    }

    @Test
    void testVector4dMaxComponent() {
        final Vector4d vector1 = new Vector4d(12, 11, 10, 20);
        assertEquals(3, vector1.maxComponent());

        final Vector4d vector2 = new Vector4d(9, 8, 20, 6);
        assertEquals(2, vector2.maxComponent());

        final Vector4d vector3 = new Vector4d(8, 20, 6, 8);
        assertEquals(1, vector3.maxComponent());

        final Vector4d vector4 = new Vector4d(20, 6, 8, 10);
        assertEquals(0, vector4.maxComponent());
    }

    @Test
    void testVector4dMulWithMatrix4x3f() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        vector1.mul(NORMAL_MATRIX_4x3F);
        assertEquals(new Vector4d(168.0f, 204.0f, 240.0f, 5.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mul(PROPERTY_TRANSLATION_MATRIX_4x3F);
        assertEquals(new Vector4d(57.0f, -12.0f, 49.0f, 10.0f), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector3 = new Vector4d(6, 8, 10,12);
        vector3.mul(PROPERTY_IDENTITY_MATRIX_4x3F);
        assertEquals(new Vector4d(6.0f, 8.0f, 10.0f, 12.0f), vector3);
    }

    @Test
    void testVector4dMulWithMatrix4f() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        vector1.mul(NORMAL_MATRIX_4F);
        assertEquals(new Vector4d(212.0f, 248.0f, 284.0f, 320.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mul(PROPERTY_TRANSLATION_MATRIX_4F);
        assertEquals(new Vector4d(57.0f, -12.0f, 49.0f, 10.0f), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector4d vector3 = new Vector4d(6, 8, 10, 12);
        vector3.mul(PROPERTY_AFFINE_MATRIX_4F);
        assertEquals(new Vector4d(12.0f, 64.0f, 52.0f, 12.0f), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector4 = new Vector4d(6, 8, 10,12);
        vector4.mul(PROPERTY_IDENTITY_MATRIX_4F);
        assertEquals(new Vector4d(6.0f, 8.0f, 10.0f, 12.0f), vector4);
    }

    @Test
    void testVector4dMulWithMatrix4x3d() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        vector1.mul(NORMAL_MATRIX_4x3D);
        assertEquals(new Vector4d(168.0f, 204.0f, 240.0f, 5.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mul(PROPERTY_TRANSLATION_MATRIX_4x3D);
        assertEquals(new Vector4d(57.0f, -12.0f, 49.0f, 10.0f), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector3 = new Vector4d(6, 8, 10,12);
        vector3.mul(PROPERTY_IDENTITY_MATRIX_4x3D);
        assertEquals(new Vector4d(6.0f, 8.0f, 10.0f, 12.0f), vector3);
    }

    @Test
    void testVector4dMulWithMatrix4d() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        vector1.mul(NORMAL_MATRIX_4D);
        assertEquals(new Vector4d(212.0f, 248.0f, 284.0f, 320.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mul(PROPERTY_TRANSLATION_MATRIX_4D);
        assertEquals(new Vector4d(57.0f, -12.0f, 49.0f, 10.0f), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector4d vector3 = new Vector4d(6, 8, 10, 12);
        vector3.mul(PROPERTY_AFFINE_MATRIX_4D);
        assertEquals(new Vector4d(12.0f, 64.0f, 52.0f, 12.0f), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector4 = new Vector4d(6, 8, 10,12);
        vector4.mul(PROPERTY_IDENTITY_MATRIX_4D);
        assertEquals(new Vector4d(6.0f, 8.0f, 10.0f, 12.0f), vector4);
    }

    @Test
    void testVector4dMulProjectWithMatrix4fAndDestinationVector3d() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        final Vector3d destVector = new Vector3d();
        vector1.mulProject(NORMAL_MATRIX_4D, destVector);
        assertTrue(destVector.equals(new Vector3d(0.6625f, 0.77500004f, 0.8875f), DELTA));
        assertEquals(new Vector4d(3, 27, 1, 5), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4D, destVector);
        assertTrue(destVector.equals(new Vector3d(5.7000003f, -1.2f, 4.9f), DELTA));
        assertEquals(new Vector4d(7, 8, 9, 10), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector4d vector3 = new Vector4d(6, 8, 10, 12);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4D, destVector);
        assertTrue(destVector.equals(new Vector3d(1.0f, 5.3333335f, 4.3333335f), DELTA));
        assertEquals(new Vector4d(6, 8, 10,12), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector4 = new Vector4d(6, 8, 10, 12);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4D, destVector);
        assertEquals(new Vector3d(6.0f, 8.0f, 10.0f), destVector);
        assertEquals(new Vector4d(6, 8, 10, 12), vector4);
    }

    @Test
    void testVector4dMulProjectWithMatrix4fAndDestinationVector4d() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        final Vector4d destVector = new Vector4d();
        vector1.mulProject(NORMAL_MATRIX_4D, destVector);
        assertTrue(destVector.equals(new Vector4d(0.6625f, 0.77500004f, 0.8875f, 1.0f), DELTA));
        assertEquals(new Vector4d(3, 27, 1, 5), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4D, destVector);
        assertTrue(destVector.equals(new Vector4d(5.7000003f, -1.2f, 4.9f, 1.0f), DELTA));
        assertEquals(new Vector4d(7, 8, 9, 10), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector4d vector3 = new Vector4d(6, 8, 10, 12);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4D, destVector);
        assertTrue(destVector.equals(new Vector4d(1.0f, 5.3333335f, 4.3333335f, 1.0f), DELTA));
        assertEquals(new Vector4d(6, 8, 10,12), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector4 = new Vector4d(6, 8, 10, 12);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4D, destVector);
        assertEquals(new Vector4d(6.0f, 8.0f, 10.0f, 12.0f), destVector);
        assertEquals(new Vector4d(6, 8, 10, 12), vector4);
    }

    @Test
    void testVector4dMulProjectWithMatrix4f() {
        final Vector4d vector1 = new Vector4d(3, 27, 1, 5);
        vector1.mulProject(NORMAL_MATRIX_4D);
        assertTrue(vector1.equals(new Vector4d(0.6625f, 0.77500004f, 0.8875f, 1.0f), DELTA));

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector4d vector2 = new Vector4d(7, 8, 9, 10);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4D);
        assertTrue(vector2.equals(new Vector4d(5.7000003f, -1.2f, 4.9f, 1.0f), DELTA));

        // A matrix with PROPERTY_AFFINE property.
        final Vector4d vector3 = new Vector4d(6, 8, 10, 12);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4D);
        assertTrue(vector3.equals(new Vector4d(1.0f, 5.3333335f, 4.3333335f, 1.0f), DELTA));

        // A matrix with PROPERTY_IDENTITY property.
        final Vector4d vector4 = new Vector4d(6, 8, 10,12);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4D);
        assertEquals(new Vector4d(6.0f, 8.0f, 10.0f, 12.0f), vector4);
    }

    @Test
    void testVector4dSetComponentByComponentAndValue() {
        final Vector4d vector1 = new Vector4d(2, 3, 4, 5);
        vector1.setComponent(0, 10);
        assertEquals(new Vector4d(10, 3, 4, 5), vector1);

        vector1.setComponent(1, -6);
        assertEquals(new Vector4d(10, -6, 4, 5), vector1);

        vector1.setComponent(2, 5);
        assertEquals(new Vector4d(10, -6, 5, 5), vector1);

        vector1.setComponent(3, 11);
        assertEquals(new Vector4d(10, -6, 5, 11), vector1);

        assertThrows(IllegalArgumentException.class, () -> vector1.setComponent(4, 99));
    }

    @Test
    void testAngleVector4dVector4d() {
        Vector4d testVec1 = new Vector4d(2, -9.37, 5.892, -12.5);
        Vector4d testVec2 = new Vector4d();
        
        // angle(v, v) should give 0
        double angle = testVec1.angle(testVec1);
        assertEquals(0, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
    }

    @Test
    void testMulByScalarIntoDest() {
        Vector4d testVec1 = new Vector4d(2, 2, 2, 2);
        Vector4d destVec = new Vector4d(0, 0, 0, 0);

        testVec1.mul(2, destVec);
        assertEquals(new Vector4d(4, 4, 4, 4), destVec);
        assertEquals(new Vector4d(2, 2, 2, 2), testVec1);
    }

    @Test
    void testDivByScalarIntoDest() {
        Vector4d testVec1 = new Vector4d(2, 2, 2, 2);
        Vector4d destVec = new Vector4d(0, 0, 0, 0);

        testVec1.div(2, destVec);
        assertEquals(new Vector4d(1, 1, 1, 1), destVec);
        assertEquals(new Vector4d(2, 2, 2, 2), testVec1);
    }
}
