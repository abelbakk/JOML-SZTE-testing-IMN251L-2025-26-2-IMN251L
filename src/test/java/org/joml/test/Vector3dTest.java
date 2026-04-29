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

import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Matrix4x3f;
import org.joml.Matrix4x3d;
import org.joml.Math;
import org.junit.jupiter.api.Test;

import static org.joml.test.TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector3d}.
 * @author Sebastian Fellner
 */
class Vector3dTest {
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

    @Test
    void testVector3DOrthogonalize() {
        final Vector3d vector1 = new Vector3d(2, 3, 6);
        assertEquals(new Vector3d(0, -0.894427190999916, 0.447213595499958), vector1.orthogonalizeUnit(vector1));

        final Vector3d vector2 = new Vector3d(6, 3, 2);
        assertEquals(new Vector3d(-0.447213595499958, 0.894427190999916, 0), vector2.orthogonalizeUnit(vector2));
    }

    @Test
    void testVector3DMinComponent() {
        final Vector3d vector1 = new Vector3d(4, 5, 1);
        assertEquals(2, vector1.minComponent());

        final Vector3d vector2 = new Vector3d(0, 5, 1);
        assertEquals(0, vector2.minComponent());

        final Vector3d vector3 = new Vector3d(15, -5, 25);
        assertEquals(1, vector3.minComponent());
    }

    @Test
    void testVector3DMaxComponent() {
        final Vector3d vector1 = new Vector3d(13, 5, 1);
        assertEquals(0, vector1.maxComponent());

        final Vector3d vector2 = new Vector3d(3, 5, 1);
        assertEquals(1, vector2.maxComponent());

        final Vector3d vector3 = new Vector3d(15, 5, 25);
        assertEquals(2, vector3.maxComponent());
    }

    @Test
    void testVector3dGetComponent() {
        final Vector3d vector1 = new Vector3d(3, 5, 1);
        assertEquals(3, vector1.get(0));
        assertEquals(5, vector1.get(1));
        assertEquals(1, vector1.get(2));
        assertThrows(IllegalArgumentException.class, () -> vector1.get(3));
    }

    @Test
    void testVector3DEqualsWithVector3d() {
        final Vector3d vector1 = new Vector3d(3, 5, 1);
        assertEquals(new Vector3d(3, 5, 1), vector1);
        assertNotEquals(new Vector3d(-3, 5, 1), vector1);
        assertNotEquals(new Vector3d(3, -5, 1), vector1);
        assertNotEquals(new Vector3d(3, 5, -1), vector1);
        assertEquals(vector1, vector1);
        assertFalse(vector1.equals(null));
        assertFalse(vector1.equals(new Vector2i(3, 5)));
    }

    @Test
    void testVector3DEqualsWithVector3dWithDelta() {
        final Vector3d vector1 = new Vector3d(3, 5, 1);
        assertTrue(vector1.equals(new Vector3d(3, 5, 1), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
        assertFalse(vector1.equals(new Vector3d(-3, 5, 1), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
        assertFalse(vector1.equals(new Vector3d(3, -5, 1), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
        assertFalse(vector1.equals(new Vector3d(3, 5, -1), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
        assertFalse(vector1.equals(null, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
        assertTrue(vector1.equals(vector1, TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
    }

    @Test
    void testVector3DEqualsWithCoordinates() {
        final Vector3d vector1 = new Vector3d(3, 5, 1);
        assertTrue(vector1.equals(3, 5, 1));
        assertFalse(vector1.equals(-3, 5, 1));
        assertFalse(vector1.equals(3, -5, 1));
        assertFalse(vector1.equals(3, 5, -1));
    }

    @Test
    void testVector3DMax() {
        final Vector3d vector1 = new Vector3d(3, 5, 1);
        final Vector3d vector2 = new Vector3d(10, 22, 3);
        vector1.max(vector2);
        assertEquals(new Vector3d(10, 22, 3), vector1);

        final Vector3d vector3 = new Vector3d(3, 5, 1);
        final Vector3d vector4 = new Vector3d(10, 22, 3);
        vector4.max(vector3);
        assertEquals(new Vector3d(10, 22, 3), vector4);
    }

    @Test
    void testVector3DMin() {
        final Vector3d vector1 = new Vector3d(3, 5, 1);
        final Vector3d vector2 = new Vector3d(10, 22, 3);
        vector1.min(vector2);
        assertEquals(new Vector3d(3, 5, 1), vector1);

        final Vector3d vector3 = new Vector3d(3, 5, 1);
        final Vector3d vector4 = new Vector3d(10, 22, 3);
        vector4.min(vector3);
        assertEquals(new Vector3d(3, 5, 1), vector4);
    }

    @Test
    void testVector3DRotateAxis() {
        final Vector3d vector1 = new Vector3d(3, 5, 1).rotateAxis(Math.PI, 1, 0, 0);
        assertTrue(vector1.equals(new Vector3d(3.0, -5.0, -1.0), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));

        final Vector3d vector2 = new Vector3d(3, 5, 1).rotateAxis(Math.PI, 0, 1, 0);
        assertTrue(vector2.equals(new Vector3d(-3.0, 5.0, -1.0), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));

        final Vector3d vector3 = new Vector3d(3, 5, 1).rotateAxis(Math.PI, 0, 0, 1);
        assertTrue(vector3.equals(new Vector3d(-3.0, -5.0, 1.0), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));

        final Vector3d vector4 = new Vector3d(3, 5, 1).rotateAxis(Math.PI, 1, 1, 1);
        assertTrue(vector4.equals(new Vector3d(9.0, 3.0, 15.0), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));

        final Vector3d vector5 = new Vector3d(3, 5, 1).rotateAxis(Math.PI, 3, 0, 0);
        assertTrue(vector5.equals(new Vector3d(27.0, -45.0, -9.0), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));

        final Vector3d vector6 = new Vector3d(3, 5, 1).rotateAxis(Math.PI, 0, 3, 0);
        assertTrue(vector6.equals(new Vector3d(-27.0, 45.0, -9.0), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));

        final Vector3d vector7 = new Vector3d(3, 5, 1).rotateAxis(Math.PI, 0, 0, 3);
        assertTrue(vector7.equals(new Vector3d(-27.0, -45.0, 9.0), TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));

    }

    @Test
    void testVector3DMulPositionWithMatrix4x3fAndDestinationVector3d() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        final Vector3d destVector = new Vector3d();
        vector1.mulPosition(NORMAL_MATRIX_4x3F, destVector);
        assertEquals(new Vector3d(128.0, 160.0, 192.0), destVector);
        assertEquals(new Vector3d(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3F, destVector);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), destVector);
        assertEquals(new Vector3d(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3F, destVector);
        assertEquals(new Vector3d(6, 8, 10), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulPositionWithMatrix4x3f() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4x3F);
        assertEquals(new Vector3d(128.0, 160.0, 192.0), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3F);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3F);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulPositionWithMatrix4x3dAndDestinationVector3D() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        final Vector3d destVector = new Vector3d();
        vector1.mulPosition(NORMAL_MATRIX_4x3D, destVector);
        assertEquals(new Vector3d(128.0, 160.0, 192.0), destVector);
        assertEquals(new Vector3d(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3D, destVector);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), destVector);
        assertEquals(new Vector3d(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3D, destVector);
        assertEquals(new Vector3d(6, 8, 10), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulPositionWithMatrix4x3d() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4x3D);
        assertEquals(new Vector3d(128.0, 160.0, 192.0), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3D);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3D);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulPositionWithMatrix4fAndDestinationVector3D() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        final Vector3d destVector = new Vector3d();
        vector1.mulPosition(NORMAL_MATRIX_4F, destVector);
        assertEquals(new Vector3d(160.0, 192.0, 224.0), destVector);
        assertEquals(new Vector3d(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4F, destVector);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), destVector);
        assertEquals(new Vector3d(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4F, destVector);
        assertEquals(new Vector3d(6, 8, 10), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulPositionWithMatrix4f() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4F);
        assertEquals(new Vector3d(160.0, 192.0, 224.0), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4F);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4F);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulPositionWithMatrix4dAndDestinationVector3D() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        final Vector3d destVector = new Vector3d();
        vector1.mulPosition(NORMAL_MATRIX_4D, destVector);
        assertEquals(new Vector3d(160.0, 192.0, 224.0), destVector);
        assertEquals(new Vector3d(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4D, destVector);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), destVector);
        assertEquals(new Vector3d(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4D, destVector);
        assertEquals(new Vector3d(6, 8, 10), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulPositionWithMatrix4d() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4D);
        assertEquals(new Vector3d(160.0, 192.0, 224.0), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4D);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4D);
        assertEquals(new Vector3d(6, 8, 10), vector3);
    }

    @Test
    void testVector3DMulProjectWithMatrix4fAndDestinationVector3d() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        final Vector3d destVector = new Vector3d();
        vector1.mulProject(NORMAL_MATRIX_4F, destVector);
        assertEquals(new Vector3d(0.625, 0.75, 0.875), destVector);
        assertEquals(new Vector3d(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4F, destVector);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), destVector);
        assertEquals(new Vector3d(7, 8, 9), vector2);


        // A matrix with PROPERTY_AFFINE property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4F, destVector);
        assertEquals(new Vector3d(12.0, 64.0, 52.0), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector4 = new Vector3d(6, 8, 10);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4F, destVector);
        assertEquals(new Vector3d(6.0, 8.0, 10.0), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector4);
    }

    @Test
    void testVector3DMulProjectWithMatrix4f() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        vector1.mulProject(NORMAL_MATRIX_4F);
        assertEquals(new Vector3d(0.625, 0.75, 0.875), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4F);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4F);
        assertEquals(new Vector3d(12.0, 64.0, 52.0), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector4 = new Vector3d(6, 8, 10);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4F);
        assertEquals(new Vector3d(6.0, 8.0, 10.0), vector4);
    }

    @Test
    void testVector3DMulProjectWithMatrix4dAndDestinationVector3d() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        final Vector3d destVector = new Vector3d();
        vector1.mulProject(NORMAL_MATRIX_4D, destVector);
        assertEquals(new Vector3d(0.625, 0.75, 0.875), destVector);
        assertEquals(new Vector3d(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4D, destVector);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), destVector);
        assertEquals(new Vector3d(7, 8, 9), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4D, destVector);
        assertEquals(new Vector3d(12.0, 64.0, 52.0), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector4 = new Vector3d(6, 8, 10);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4D, destVector);
        assertEquals(new Vector3d(6.0, 8.0, 10.0), destVector);
        assertEquals(new Vector3d(6, 8, 10), vector4);
    }

    @Test
    void testVector3DMulProjectWithMatrix4d() {
        final Vector3d vector1 = new Vector3d(3, 27, 1);
        vector1.mulProject(NORMAL_MATRIX_4D);
        assertEquals(new Vector3d(0.625, 0.75, 0.875), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3d vector2 = new Vector3d(7, 8, 9);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4D);
        assertEquals(new Vector3d(12.0, 6.0, 13.0), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector3d vector3 = new Vector3d(6, 8, 10);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4D);
        assertEquals(new Vector3d(12.0, 64.0, 52.0), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3d vector4 = new Vector3d(6, 8, 10);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4D);
        assertEquals(new Vector3d(6.0, 8.0, 10.0), vector4);
    }

    @Test
    void testVector3DSetComponentByComponentAndValue() {
        final Vector3d vector1 = new Vector3d(2, 3, 4);
        vector1.setComponent(0, 10);
        assertEquals(new Vector3d(10, 3, 4), vector1);

        vector1.setComponent(1, -6);
        assertEquals(new Vector3d(10, -6, 4), vector1);

        vector1.setComponent(2, 5);
        assertEquals(new Vector3d(10, -6, 5), vector1);

        assertThrows(IllegalArgumentException.class, () -> vector1.setComponent(3, 99));
    }

    @Test
    void testVector3dSetVector3d() {
        final Vector3d vector1 = new Vector3d(2, 3, 4);
        vector1.set(vector1);
        assertEquals(new Vector3d(2, 3, 4), vector1);

        final Vector3d vector2 = new Vector3d(5.0);
        vector1.set(vector2);
        assertEquals(vector2, vector1);
    }

    @Test
    void testAngleVector3dVector3d() {
        Vector3d testVec1 = new Vector3d(2, -9.37, 5.892);
        Vector3d testVec2 = new Vector3d();
        
        // angle(v, v) should give 0
        double angle = testVec1.angle(testVec1);
        assertEquals(0, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE);
    }
}
