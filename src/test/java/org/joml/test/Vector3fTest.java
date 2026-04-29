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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Vector3f}.
 * @author Sebastian Fellner
 */
class Vector3fTest {
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
    void testVector3fMulTransposePositionWithMatrix4d() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        vector1.mulTransposePosition(NORMAL_MATRIX_4D);
        assertEquals(new Vector3f(64.0f, 192.0f, 320.0f), vector1);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector2 = new Vector3f(6, 8, 10);
        vector2.mulTransposePosition(PROPERTY_IDENTITY_MATRIX_4D);
        assertEquals(new Vector3f(6, 8, 10), vector2);
    }

    @Test
    void testVector3fOrthogonalize() {
        final Vector3f vector1 = new Vector3f(2, 3, 6);
        assertEquals(new Vector3f(0, -0.894427190999916f, 0.447213595499958f), vector1.orthogonalizeUnit(vector1));

        final Vector3f vector2 = new Vector3f(6, 3, 2);
        assertEquals(new Vector3f(-0.447213595499958f, 0.894427190999916f, 0), vector2.orthogonalizeUnit(vector2));
    }

    @Test
    void testVector3fMinComponent() {
        final Vector3f vector1 = new Vector3f(4, 5, 1);
        assertEquals(2, vector1.minComponent());

        final Vector3f vector2 = new Vector3f(0, 5, 1);
        assertEquals(0, vector2.minComponent());

        final Vector3f vector3 = new Vector3f(15, -5, 25);
        assertEquals(1, vector3.minComponent());
    }

    @Test
    void testVector3fMaxComponent() {
        final Vector3f vector1 = new Vector3f(13, 5, 1);
        assertEquals(0, vector1.maxComponent());

        final Vector3f vector2 = new Vector3f(3, 5, 1);
        assertEquals(1, vector2.maxComponent());

        final Vector3f vector3 = new Vector3f(15, 5, 25);
        assertEquals(2, vector3.maxComponent());
    }

    @Test
    void testVector3fGetComponent() {
        final Vector3f vector1 = new Vector3f(3, 5, 1);
        assertEquals(3, vector1.get(0));
        assertEquals(5, vector1.get(1));
        assertEquals(1, vector1.get(2));
        assertThrows(IllegalArgumentException.class, () -> vector1.get(3));
    }

    @Test
    void testVector3fEqualsWithVector3f() {
        final Vector3f vector1 = new Vector3f(3, 5, 1);
        assertEquals(new Vector3f(3, 5, 1), vector1);
        assertNotEquals(new Vector3f(-3, 5, 1), vector1);
        assertNotEquals(new Vector3f(3, -5, 1), vector1);
        assertNotEquals(new Vector3f(3, 5, -1), vector1);
        assertEquals(vector1, vector1);
        assertFalse(vector1.equals(null));
        assertFalse(vector1.equals(new Vector2i(3, 5)));
    }

    @Test
    void testVector3fEqualsWithVector3fWithDelta() {
        final Vector3f vector1 = new Vector3f(3, 5, 1);
        assertTrue(vector1.equals(new Vector3f(3, 5, 1), DELTA));
        assertFalse(vector1.equals(new Vector3f(-3, 5, 1), DELTA));
        assertFalse(vector1.equals(new Vector3f(3, -5, 1), DELTA));
        assertFalse(vector1.equals(new Vector3f(3, 5, -1), DELTA));
        assertFalse(vector1.equals(null, DELTA));
        assertTrue(vector1.equals(vector1, DELTA));
    }

    @Test
    void testVector3fEqualsWithCoordinates() {
        final Vector3f vector1 = new Vector3f(3, 5, 1);
        assertTrue(vector1.equals(3, 5, 1));
        assertFalse(vector1.equals(-3, 5, 1));
        assertFalse(vector1.equals(3, -5, 1));
        assertFalse(vector1.equals(3, 5, -1));
    }

    @Test
    void testVector3fMax() {
        final Vector3f vector1 = new Vector3f(3, 5, 1);
        final Vector3f vector2 = new Vector3f(10, 22, 3);
        vector1.max(vector2);
        assertEquals(new Vector3f(10, 22, 3), vector1);

        final Vector3f vector3 = new Vector3f(3, 5, 1);
        final Vector3f vector4 = new Vector3f(10, 22, 3);
        vector4.max(vector3);
        assertEquals(new Vector3f(10, 22, 3), vector4);
    }

    @Test
    void testVector3fMin() {
        final Vector3f vector1 = new Vector3f(3, 5, 1);
        final Vector3f vector2 = new Vector3f(10, 22, 3);
        vector1.min(vector2);
        assertEquals(new Vector3f(3, 5, 1), vector1);

        final Vector3f vector3 = new Vector3f(3, 5, 1);
        final Vector3f vector4 = new Vector3f(10, 22, 3);
        vector4.min(vector3);
        assertEquals(new Vector3f(3, 5, 1), vector4);
    }

    @Test
    void testVector3fRotateAxis() {
        final Vector3f vector1 = new Vector3f(3, 5, 1).rotateAxis(Math.PI_f, 1, 0, 0);
        assertTrue(vector1.equals(new Vector3f(3.0f, -5.0f, -1.0f), DELTA));

        final Vector3f vector2 = new Vector3f(3, 5, 1).rotateAxis(Math.PI_f, 0, 1, 0);
        assertTrue(vector2.equals(new Vector3f(-3.0f, 5.0f, -1.0f), DELTA));

        final Vector3f vector3 = new Vector3f(3, 5, 1).rotateAxis(Math.PI_f, 0, 0, 1);
        assertTrue(vector3.equals(new Vector3f(-3.0f, -5.0f, 1.0f), DELTA));

        final Vector3f vector4 = new Vector3f(3, 5, 1).rotateAxis(Math.PI_f, 1, 1, 1);
        assertTrue(vector4.equals(new Vector3f(9.0f, 3.0f, 15.0f), DELTA));

        final Vector3f vector5 = new Vector3f(3, 5, 1).rotateAxis(Math.PI_f, 3, 0, 0);
        assertTrue(vector5.equals(new Vector3f(27.0f, -45.0f, -9.0f), DELTA));

        final Vector3f vector6 = new Vector3f(3, 5, 1).rotateAxis(Math.PI_f, 0, 3, 0);
        assertTrue(vector6.equals(new Vector3f(-27.0f, 45.0f, -9.0f), DELTA));

        final Vector3f vector7 = new Vector3f(3, 5, 1).rotateAxis(Math.PI_f, 0, 0, 3);
        assertTrue(vector7.equals(new Vector3f(-27.0f, -45.0f, 9.0f), DELTA));

    }

    @Test
    void testVector3fMulPositionWithMatrix4x3fAndDestinationVector3f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        final Vector3f destVector = new Vector3f();
        vector1.mulPosition(NORMAL_MATRIX_4x3F, destVector);
        assertEquals(new Vector3f(128.0f, 160.0f, 192.0f), destVector);
        assertEquals(new Vector3f(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3F, destVector);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), destVector);
        assertEquals(new Vector3f(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3F, destVector);
        assertEquals(new Vector3f(6, 8, 10), destVector);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulPositionWithMatrix4x3f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4x3F);
        assertEquals(new Vector3f(128.0f, 160.0f, 192.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3F);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3F);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulPositionWithMatrix4x3dAndDestinationVector3f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        final Vector3f destVector = new Vector3f();
        vector1.mulPosition(NORMAL_MATRIX_4x3D, destVector);
        assertEquals(new Vector3f(128.0f, 160.0f, 192.0f), destVector);
        assertEquals(new Vector3f(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3D, destVector);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), destVector);
        assertEquals(new Vector3f(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3D, destVector);
        assertEquals(new Vector3f(6, 8, 10), destVector);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulPositionWithMatrix4x3d() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4x3D);
        assertEquals(new Vector3f(128.0f, 160.0f, 192.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4x3D);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4x3D);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulPositionWithMatrix4fAndDestinationVector3f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        final Vector3f destVector = new Vector3f();
        vector1.mulPosition(NORMAL_MATRIX_4F, destVector);
        assertEquals(new Vector3f(160.0f, 192.0f, 224.0f), destVector);
        assertEquals(new Vector3f(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4F, destVector);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), destVector);
        assertEquals(new Vector3f(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4F, destVector);
        assertEquals(new Vector3f(6, 8, 10), destVector);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulPositionWithMatrix4f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4F);
        assertEquals(new Vector3f(160.0f, 192.0f, 224.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4F);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4F);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulPositionWithMatrix4dAndDestinationVector3f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        final Vector3f destVector = new Vector3f();
        vector1.mulPosition(NORMAL_MATRIX_4D, destVector);
        assertEquals(new Vector3f(160.0f, 192.0f, 224.0f), destVector);
        assertEquals(new Vector3f(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4D, destVector);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), destVector);
        assertEquals(new Vector3f(7, 8, 9), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4D, destVector);
        assertEquals(new Vector3f(6, 8, 10), destVector);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulPositionWithMatrix4d() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        vector1.mulPosition(NORMAL_MATRIX_4D);
        assertEquals(new Vector3f(160.0f, 192.0f, 224.0f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulPosition(PROPERTY_TRANSLATION_MATRIX_4D);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), vector2);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulPosition(PROPERTY_IDENTITY_MATRIX_4D);
        assertEquals(new Vector3f(6, 8, 10), vector3);
    }

    @Test
    void testVector3fMulProjectWithMatrix4fAndDestinationVector3f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        final Vector3f destVector = new Vector3f();
        vector1.mulProject(NORMAL_MATRIX_4F, destVector);
        assertEquals(new Vector3f(0.625f, 0.75f, 0.875f), destVector);
        assertEquals(new Vector3f(3, 27, 1), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4F, destVector);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), destVector);
        assertEquals(new Vector3f(7, 8, 9), vector2);


        // A matrix with PROPERTY_AFFINE property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4F, destVector);
        assertEquals(new Vector3f(12.0f, 64.0f, 52.0f), destVector);
        assertEquals(new Vector3f(6, 8, 10), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector4 = new Vector3f(6, 8, 10);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4F, destVector);
        assertEquals(new Vector3f(6.0f, 8.0f, 10.0f), destVector);
        assertEquals(new Vector3f(6, 8, 10), vector4);
    }

    @Test
    void testVector3fMulProjectWithMatrix4f() {
        final Vector3f vector1 = new Vector3f(3, 27, 1);
        vector1.mulProject(NORMAL_MATRIX_4F);
        assertEquals(new Vector3f(0.625f, 0.75f, 0.875f), vector1);

        // A matrix with PROPERTY_TRANSLATION property.
        final Vector3f vector2 = new Vector3f(7, 8, 9);
        vector2.mulProject(PROPERTY_TRANSLATION_MATRIX_4F);
        assertEquals(new Vector3f(12.0f, 6.0f, 13.0f), vector2);

        // A matrix with PROPERTY_AFFINE property.
        final Vector3f vector3 = new Vector3f(6, 8, 10);
        vector3.mulProject(PROPERTY_AFFINE_MATRIX_4F);
        assertEquals(new Vector3f(12.0f, 64.0f, 52.0f), vector3);

        // A matrix with PROPERTY_IDENTITY property.
        final Vector3f vector4 = new Vector3f(6, 8, 10);
        vector4.mulProject(PROPERTY_IDENTITY_MATRIX_4F);
        assertEquals(new Vector3f(6.0f, 8.0f, 10.0f), vector4);
    }

    @Test
    void testVector3fSetComponentByComponentAndValue() {
        final Vector3f vector1 = new Vector3f(2, 3, 4);
        vector1.setComponent(0, 10);
        assertEquals(new Vector3f(10, 3, 4), vector1);

        vector1.setComponent(1, -6);
        assertEquals(new Vector3f(10, -6, 4), vector1);

        vector1.setComponent(2, 5);
        assertEquals(new Vector3f(10, -6, 5), vector1);

        assertThrows(IllegalArgumentException.class, () -> vector1.setComponent(3, 99));
    }

    @Test
    void testVector3fSetVector3f() {
        final Vector3f vector1 = new Vector3f(2, 3, 4);
        vector1.set(vector1);
        assertEquals(new Vector3f(2, 3, 4), vector1);

        final Vector3f vector2 = new Vector3f(5.0f);
        vector1.set(vector2);
        assertEquals(vector2, vector1);
    }

    @Test
    void testAngleVector3fVector3f() {
        Vector3f testVec1 = new Vector3f(2f, -9.37f, 5.892f);
        Vector3f testVec2 = new Vector3f();
        
        // angle(v, v) should give 0
        float angle = testVec1.angle(testVec1);
        assertEquals(0, angle, DELTA);
        
        // angle(v, -v) should give Math.PI
        testVec1.negate(testVec2);
        angle = testVec1.angle(testVec2);
        assertEquals(java.lang.Math.PI, angle, DELTA);
    }
}
