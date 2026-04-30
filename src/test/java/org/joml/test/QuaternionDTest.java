/*
 * The MIT License
 *
 * Copyright (c) 2015-2026 JOML.
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
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.Arrays;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Quaterniond}.
 * @author Sebastian Fellner
 */
class QuaternionDTest {
    private static final float DELTA = TestUtil.MANY_OPS_AROUND_ZERO_PRECISION_FLOAT;

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    void testIsFinite(int index) {
        final float zero = 0;
        final float NaN = Float.POSITIVE_INFINITY * zero;
        final float[] quaternionValues = new float[] {1, 2, 3, 4};

        if (index < quaternionValues.length) {
            quaternionValues[index] = NaN;
        }

        final Quaterniond q = new Quaterniond(quaternionValues);

        if (index < quaternionValues.length) {
            assertFalse(q.isFinite());
        } else {
            assertTrue(q.isFinite());
        }
    }

    @Test
    void testQuaternionDNlerpIterativeWithWeights() {
        final Quaterniond q1 = new Quaterniond(new float[] {1, 2, 3, 4});
        final Quaterniond qDestination = new Quaterniond();
        final Quaterniondc result = q1.nlerpIterative(
                new Quaterniond[] {
                        new Quaterniond(new float[] {4, 3, 2, 1}),
                        new Quaterniond(new float[] {-0.4f, -0.3f, -0.2f, -0.1f})
                },
                new double[]{ 0.5f, 0.5f },
                0.5,
                qDestination
        );
        assertEquals(result, qDestination);
    }

    @Test
    void testQuaternionDNlerpIterative() {
        final Quaterniond q1 = new Quaterniond(new float[] {1, 2, 3, 4});
        q1.nlerpIterative(new Quaterniond(new float[] {4, 3, 2, 1}), 0.5, 0.5);
        assertTrue(q1.equals(new Quaterniond(new float[] {1, 2, 3, 4}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.nlerpIterative(new Quaterniond(new float[] {-0.4f, -0.3f, -0.2f, -0.1f}), -0.5, 0.5);
        assertTrue(q1.equals(new Quaterniond(new float[] {-0.571f, -0.450f, -0.502f, -0.467f}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.nlerpIterative(new Quaterniond(new float[] {0.4f, 0.3f, 0.2f, 0.1f}), 0.5, 0.5);
        assertTrue(q1.equals(new Quaterniond(new float[] {0.524f, 0.419f, 0.524f, 0.524f}), DELTA));
    }

    @Test
    void testQuaternionDNlerpWithWeights() {
        Quaterniond q1 = new Quaterniond(new float[] {1, 2, 3, 4});
        Quaterniond qDestination = new Quaterniond();
        Quaterniondc result = q1.nlerp(
                new Quaterniond[] {
                        new Quaterniond(new float[] {4, 3, 2, 1}),
                        new Quaterniond(new float[] {-0.4f, -0.3f, -0.2f, -0.1f})
                },
                new double[]{ 0.5f, 0.5f },
                qDestination
        );
        assertEquals(result, qDestination);
    }

    @Test
    void testQuaternionDNlerp() {
        final Quaterniond q1 = new Quaterniond(new float[] {1, 2, 3, 4});
        q1.nlerp(new Quaterniond(new float[] {4, 3, 2, 1}), 0.5);
        assertTrue(q1.equals(new Quaterniond(new float[] {0.5f, 0.5f, 0.5f, 0.5f}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.nlerp(new Quaterniond(new float[] {-0.4f, -0.3f, -0.2f, -0.1f}), -0.5);
        assertTrue(q1.equals(new Quaterniond(new float[] {-0.0764f, 0.0f, 0.5353f, 0.8411f}), DELTA));
    }

    @Test
    void testQuaternionDSlerpWithWeights() {
        Quaterniond q1 = new Quaterniond(new float[] {1, 2, 3, 4});
        Quaterniond qDestination = new Quaterniond();
        Quaterniondc result = q1.slerp(
                new Quaterniond[] {
                        new Quaterniond(new float[] {4, 3, 2, 1}),
                        new Quaterniond(new float[] {-0.4f, -0.3f, -0.2f, -0.1f})
                },
                new double[]{ 0.5f, 0.5f },
                qDestination
        );
        assertEquals(result, qDestination);
    }

    @Test
    void testQuaternionDSlerp() {
        final Quaterniond q1 = new Quaterniond(new float[] {1, 2, 3, 4});
        q1.slerp(new Quaterniond(new float[] {4, 3, 2, 1}), 0.5);
        assertTrue(q1.equals(new Quaterniond(new float[] {2.5f, 2.5f, 2.5f, 2.5f}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.slerp(new Quaterniond(new float[] {-0.4f, -0.3f, -0.2f, -0.1f}), -0.5);
        assertTrue(q1.equals(new Quaterniond(new float[] {-0.173f, -0.108f, 0.132f, 0.285f}), DELTA));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array that contains less than 4 elements.
     *
     * @param arraySize The size of the array that will be used to initialize the
     * an {@link Quaterniond} instance.
     */
    @ParameterizedTest
    @ValueSource(ints ={ 4, 3, 2, 1, 0 })
    void testQuaternionDConstructorByDoubleArray(int arraySize) {
        final double[] array = new double[arraySize];
        final int value = 5;
        Arrays.fill(array, value);

        // Check whether the instance could be created or not.
        assertDoesNotThrow(() -> new Quaterniond(array));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array and offset where the offset could read an element outside the bounds of the array.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 2, 3, 4, 5})
    void testQuaternionDConstructorByDoubleArrayAndOffset(int offset) {
        final double[] array = new double[] {1, 2, 3, 4};
        assertDoesNotThrow(() -> new Quaterniond(array, offset));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array and offset where the offset could read an element outside the bounds of the array.
     */
    @ParameterizedTest
    @ValueSource(ints = {4, 3, 2, 1, 0})
    void testQuaternionDConstructorByDoubleArrayLessThenFourElementAndOffset(int arraySize) {
        final double[] array = new double[arraySize];
        final int value = 5;
        Arrays.fill(array, value);
        assertDoesNotThrow(() -> new Quaterniond(array, 0));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array that contains less than 4 elements.
     *
     * @param arraySize The size of the array that will be used to initialize the
     * an {@link Quaterniond} instance.
     */
    @ParameterizedTest
    @ValueSource(ints ={ 4, 3, 2, 1, 0 })
    void testQuaternionDConstructorByFloatArray(int arraySize) {
        final float[] array = new float[arraySize];
        Arrays.fill(array, 5);
        assertDoesNotThrow(() -> new Quaterniond(array));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array and offset where the offset could read an element outside the bounds of the array.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 2, 3, 4, 5})
    void testQuaternionDConstructorByFloatArrayAndOffset(int offset) {
        final float[] array = new float[] {1, 2, 3, 4};
        assertDoesNotThrow(() -> new Quaterniond(array, offset));
    }

    @Test
    void testQuaternionDConstructorByQuaternionDInstance() {
        final Quaterniond testQuat = new Quaterniond(0, 1, 2, 3);
        assertEquals(new Quaterniond(testQuat), testQuat);
    }

    /**
     * Testing out whether the program is prepared to handle the case where
     * a null instance of {@link Quaterniond} is provided as a param to the constructor
     * of the {@link Quaterniond}.
     */
    @Test
    void testQuaternionDConstructorByNullQuaternionDInstance() {
        final Quaterniond testQuat = null;
        assertDoesNotThrow(() -> new Quaterniond(testQuat));
    }

    @Test
    void testQuaternionDConstructorByQuaternionfInstance() {
        final Quaternionf testQuaternionf = new Quaternionf(0, 1, 2, 3);
        final Quaterniond testQuaterniond = new Quaterniond(0, 1, 2, 3);
        assertEquals(new Quaterniond(testQuaternionf), testQuaterniond);
    }

    /**
     * Testing out whether the program is prepared to handle the case where
     * a null instance of {@link Quaterniond} is provided as a param to the constructor
     * of the {@link Quaterniond}.
     */
    @Test
    void testQuaternionDConstructorByNullQuaternionfInstance() {
        final Quaternionf testQuat = null;
        assertDoesNotThrow(() -> new Quaterniond(testQuat));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array that contains less than 4 elements.
     *
     * @param arraySize The size of the array that will be used to initialize the
     * an {@link Quaterniond} instance.
     */
    @ParameterizedTest
    @ValueSource(ints = { 4, 3, 2, 1, 0 })
    void testQuaterniondConstructorByDoubleBuffer(int arraySize) {
        final double[] array = new double[arraySize];
        Arrays.fill(array, arraySize);
        assertDoesNotThrow(() -> new Quaterniond(DoubleBuffer.wrap(array)));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, 1, 2, 3 })
    void testQuaterniondConstructorByDoubleBufferAndOffset(int offset) {
        final double[] array = new double[4];
        Arrays.fill(array, 5);
        assertDoesNotThrow(() -> new Quaterniond(offset, DoubleBuffer.wrap(array)));
    }

    @ParameterizedTest
    @ValueSource(ints = { 32, 24, 16, 8, 0 })
    void testQuaterniondConstructorByByteBuffer(int arraySize) {
        assertDoesNotThrow(() -> new Quaterniond(ByteBuffer.wrap(new byte[arraySize])));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -8, 8, 16, 24 })
    void testQuaterniondConstructorByByteBufferAndOffset(int offset) {
        assertDoesNotThrow(() -> new Quaterniond(offset, ByteBuffer.wrap(new byte[32])));
    }

    @Test
    void testQuaternionDAddCoordinatesMethod() {
        final Quaterniond testQuaterniond = new Quaterniond(0, 1, 2, 3);
        final Quaterniond finalQuaterniond = testQuaterniond.add(3, 3, 3, 3);
        assertEquals(3, finalQuaterniond.x());
        assertEquals(4, finalQuaterniond.y());
        assertEquals(5, finalQuaterniond.z());
        assertEquals(6, finalQuaterniond.w());
    }

    @Test
    void testQuaternionDAddQuaternionDMethod() {
        final Quaterniond testQuaterniond = new Quaterniond(0, 1, 2, 3);
        final Quaterniond finalQuaterniond = testQuaterniond.add(testQuaterniond);
        assertEquals(0, finalQuaterniond.x());
        assertEquals(2, finalQuaterniond.y());
        assertEquals(4, finalQuaterniond.z());
        assertEquals(6, finalQuaterniond.w());
    }

    @Test
    void testQuaternionDSubCoordinatesMethod() {
        final Quaterniond testQuaterniond = new Quaterniond(0, 1, 2, 3);
        final Quaterniond finalQuaterniond = testQuaterniond.sub(3, 3, 3, 3);
        assertEquals(-3, finalQuaterniond.x());
        assertEquals(-2, finalQuaterniond.y());
        assertEquals(-1, finalQuaterniond.z());
        assertEquals(0, finalQuaterniond.w());
    }

    @Test
    void testQuaternionDSubQuaternionDMethod() {
        final Quaterniond testQuaterniond = new Quaterniond(0, 1, 2, 3);
        final Quaterniond finalQuaterniond = testQuaterniond.sub(testQuaterniond);
        assertEquals(0, finalQuaterniond.x());
        assertEquals(0, finalQuaterniond.y());
        assertEquals(0, finalQuaterniond.z());
        assertEquals(0, finalQuaterniond.w());
    }

    @Test
    void testQuanternionDEqualsByQuaterniondInstanceAndDelta() throws CloneNotSupportedException {
        final Quaterniond testQuaterniond1 = new Quaterniond(0, 1, 2, 3);
        final Quaterniond testQuaterniond2 = new Quaterniond(0, 1, 2, 3);
        assertTrue(testQuaterniond1.equals(testQuaterniond2, DELTA));
        assertFalse(testQuaterniond1.equals(null, DELTA));
        assertTrue(testQuaterniond1.equals((Quaterniond) testQuaterniond1.clone(), DELTA));
        assertTrue(testQuaterniond1.equals(testQuaterniond1, DELTA));
        assertFalse(testQuaterniond1.equals(new Quaterniond(Integer.MAX_VALUE, 1, 2, 3), DELTA));
        assertFalse(testQuaterniond1.equals(new Quaterniond(0, Integer.MAX_VALUE, 2, 3), DELTA));
        assertFalse(testQuaterniond1.equals(new Quaterniond(0, 1, Integer.MAX_VALUE, 3), DELTA));
        assertFalse(testQuaterniond1.equals(new Quaterniond(0, 1, 2, Integer.MAX_VALUE), DELTA));
    }

    @Test
    void testQuanternionDEqualsByQuaterniondInstance() {
        final Quaterniond testQuaterniond1 = new Quaterniond(0, 1, 2, 3);
        final Quaterniond testQuaterniond2 = new Quaterniond(0, 1, 2, 3);
        assertEquals(testQuaterniond1, testQuaterniond2);
        assertFalse(testQuaterniond1.equals(null));
        assertNotEquals(testQuaterniond1, new Vector3d());
        assertEquals(testQuaterniond1, testQuaterniond1);
        assertNotEquals(new Quaterniond(Integer.MAX_VALUE, 1, 2, 3), testQuaterniond1);
        assertNotEquals(new Quaterniond(0, Integer.MAX_VALUE, 2, 3), testQuaterniond1);
        assertNotEquals(new Quaterniond(0, 1, Integer.MAX_VALUE, 3), testQuaterniond1);
        assertNotEquals(new Quaterniond(0, 1, 2, Integer.MAX_VALUE), testQuaterniond1);
    }

    @Test
    void testQuanternionDEqualsByCoordinates() {
        final Quaterniond testQuaterniond1 = new Quaterniond(0, 1, 2, 3);
        assertTrue(testQuaterniond1.equals(0, 1, 2, 3));
        assertFalse(testQuaterniond1.equals(Integer.MAX_VALUE, 1, 2, 3));
        assertFalse(testQuaterniond1.equals(0, Integer.MAX_VALUE, 2, 3));
        assertFalse(testQuaterniond1.equals(0, 1, Integer.MAX_VALUE, 3));
        assertFalse(testQuaterniond1.equals(0, 1, 2, Integer.MAX_VALUE));
    }

    @Test
    void testQuaternionDGetAxisAngle4f() {
        final AxisAngle4f axisAngle = new AxisAngle4f(0, 0, 0, 1);
        final Quaterniond quaterniond1 = new Quaterniond(0, 1, 2, 3);
        quaterniond1.get(axisAngle);
        assertEquals(axisAngle.x, quaterniond1.x(), 0);

        final Quaterniond quaterniond2 = new Quaterniond(0, 1, 2, -1);
        quaterniond2.get(axisAngle);
        assertEquals(axisAngle.x, quaterniond2.x(), 0);
    }

    @Test
    void testQuaternionDGetAxisAngle4d() {
        final AxisAngle4d axisAngle = new AxisAngle4d(0, 0, 0, 1);
        final Quaterniond quaterniond1 = new Quaterniond(0, 1, 2, 3);
        quaterniond1.get(axisAngle);
        assertEquals(axisAngle.x, quaterniond1.x(), 0);

        final Quaterniond quaterniond2 = new Quaterniond(0, 1, 2, -1);
        quaterniond2.get(axisAngle);
        assertEquals(axisAngle.x, quaterniond2.x(), 0);
    }

    @Test
    void testQuaternionDNormalize() {
        final Quaterniond quaterniond = new Quaterniond(0, 1, 2, 3);
        assertDoesNotThrow(() -> quaterniond.normalize());
    }

    @Test
    void testQuaternionDNormalizeWithNullInstance() {
        final Quaterniond quaterniond = null;
        assertDoesNotThrow(() -> quaterniond.normalize());
    }

    @Test
    void testQuaternionDNormalizeWithQuaterniondParam() {
        final Quaterniond quaterniond = new Quaterniond(0, 1, 2, 3);
        final Quaterniond quaterniondDestination = new Quaterniond(4, 5, 6, 3);
        assertDoesNotThrow(() -> quaterniond.normalize(quaterniondDestination));
    }

    @Test
    void testQuaternionDNormalizeWithQuaterniondParamWithNullInstance() {
        final Quaterniond quaterniond = null;
        final Quaterniond quaterniondDestination = new Quaterniond(4, 5, 6, 3);
        assertDoesNotThrow(() -> quaterniond.normalize(quaterniondDestination));
    }

    @Test
    void testQuaternionDGetDoubleArray() {
        final Quaterniond quaterniond = new Quaterniond(0, 1, 2, 3);
        final double[] array = quaterniond.get(new double[4]);
        assertEquals(0, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 2, 1, 0})
    void testQuaternionDGetDoubleArrayWithLessThanFourElements(int arraySize) {
        final Quaterniond quaterniond = new Quaterniond(0, 1, 2, 3);
        assertDoesNotThrow(() -> quaterniond.get(new double[arraySize]));
    }

    @Test
    void testQuaternionDGetFloatArray() {
        final Quaterniond quaterniond = new Quaterniond(0, 1, 2, 3);
        final float[] array = quaterniond.get(new float[4]);
        assertEquals(0, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 2, 1, 0})
    void testQuaternionDGetFloatArrayWithLessThanFourElements(int arraySize) {
        final Quaterniond quaterniond = new Quaterniond(0, 1, 2, 3);
        assertDoesNotThrow(() -> quaterniond.get(new float[arraySize]));
    }

    @Test
    void testIntegrate() {
        Quaterniond q = new Quaterniond();
        Quaterniond dest = new Quaterniond();
        Quaterniond res = q.integrate(1, 1, 1, 1, dest);
        assertEquals(res, dest);

        res = q.integrate(0, 0, 0, 1, dest);
        assertEquals(res, dest);
    }

    @Test
    void testSetFromNormalized() {
        assertTrue(new Quaterniond().setFromNormalized(new Matrix3d(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1})).equals(new Quaterniond(), DELTA));
        assertTrue(new Quaterniond().setFromNormalized(new Matrix3d(new float[]{1, 1, 1, 1, -4, 1, 1, 1, 2})).equals(new Quaterniond(0.408f, 0.408f, 1.224f, 0.0f), DELTA));
        assertTrue(new Quaterniond().setFromNormalized(new Matrix3d(new float[]{-1, 1, 1, 1, 1, 1, 1, 1, -1})).equals(new Quaterniond(0.5f, 1.0f, 0.5f, 0.0f), DELTA));
        assertTrue(new Quaterniond().setFromNormalized(new Matrix3d(new float[]{1, 1, 1, 1, -1, 1, 1, 1, -1})).equals(new Quaterniond(1.0f, 0.5f, 0.5f, 0.0f), DELTA));
    }

    @Test
    void testRotationToWithDoubleParam() {
        Quaterniond rotation = new Quaterniond();
        Quaterniond result = rotation.rotationTo(0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        assertEquals(rotation, result);

        rotation = new Quaterniond();
        result = rotation.rotationTo(1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f);
        assertEquals(rotation, result);

        rotation = new Quaterniond();
        result = rotation.rotationTo(0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f);
        assertEquals(rotation, result);
    }

    @Test
    void testMulQuaternionDQuaternionDQuaternionD() {
        // Multiplication with the identity quaternion should change nothing
        Quaterniond testQuat = new Quaterniond(1, 23.3, -7.57, 2.1);
        Quaterniond identityQuat = new Quaterniond().identity();
        Quaterniond resultQuat = new Quaterniond();

        testQuat.mul(identityQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_DOUBLE));

        identityQuat.mul(testQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_DOUBLE));

        // Multiplication with conjugate should give (0, 0, 0, dot(this, this))
        Quaterniond conjugate = new Quaterniond();
        testQuat.conjugate(conjugate);
        testQuat.mul(conjugate, resultQuat);

        Quaterniond wantedResultQuat = new Quaterniond(0, 0, 0, testQuat.dot(testQuat));
        assertTrue(quatEqual(resultQuat, wantedResultQuat, MANY_OPS_AROUND_ZERO_PRECISION_DOUBLE));
    }

    @Test
    void testRotationXYZ() {
        Quaterniond v = new Quaterniond().rotationXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationZYX() {
        Quaterniond v = new Quaterniond().rotationZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationYXZ() {
        Quaterniond v = new Quaterniond().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateXYZ() {
        Quaterniond v = new Quaterniond().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateZYX() {
        Quaterniond v = new Quaterniond().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateYXZ() {
        Quaterniond v = new Quaterniond().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateToReturnsDestination() {
        Quaterniondc rotation = new Quaterniond();
        Quaterniond destination = new Quaterniond();
        Quaterniondc result = rotation.rotateTo(0, 1, 0, 0, 1, 0, destination);
        assertSame(destination, result);

        result = rotation.rotateTo(1, 1, 0, -1, -1, 0, destination);
        assertSame(destination, result);

        result = rotation.rotateTo(0, 0, 1, 0, 0, -1, destination);
        assertSame(destination, result);
    }

    @Test
    void testFromAxisAngle() {
        Vector3d axis = new Vector3d(1.0, 0.0, 0.0);
        double angleDeg = 45.0;
        double angleRad = java.lang.Math.toRadians(angleDeg);
        Quaterniondc fromRad1 = new Quaterniond().fromAxisAngleRad(axis, angleRad);
        Quaterniondc fromRad2 = new Quaterniond().fromAxisAngleRad(axis.x(), axis.y(), axis.z(), angleRad);
        Quaterniondc fromDeg = new Quaterniond().fromAxisAngleDeg(axis, angleDeg);
        assertEquals(fromRad1, fromRad2);
        assertEquals(fromRad2, fromDeg);
    }

    @Test
    void testGetEulerAnglesXYZ() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateXYZ(x, y, z);
            Vector3d a = p.getEulerAnglesXYZ(new Vector3d());
            Quaterniond q = new Quaterniond().rotateX(a.x).rotateY(a.y).rotateZ(a.z);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testGetEulerAnglesZYX() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateZ(z).rotateY(y).rotateX(x);
            Vector3d a = p.getEulerAnglesZYX(new Vector3d());
            Quaterniond q = new Quaterniond().rotateZ(a.z).rotateY(a.y).rotateX(a.x);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testGetEulerAnglesZXY() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateZ(z).rotateX(x).rotateY(y);
            Vector3d a = p.getEulerAnglesZXY(new Vector3d());
            Quaterniond q = new Quaterniond().rotateZ(a.z).rotateX(a.x).rotateY(a.y);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testGetEulerAnglesYXZ() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            double x = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double y = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            double z = (rnd.nextFloat() * 2.0 - 1.0) * Math.PI;
            Quaterniond p = new Quaterniond().rotateY(y).rotateX(x).rotateZ(z);
            Vector3d a = p.getEulerAnglesYXZ(new Vector3d());
            Quaterniond q = new Quaterniond().rotateY(a.y).rotateX(a.x).rotateZ(a.z);
            Vector3d v = new Vector3d(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3d t1 = p.transform(v, new Vector3d());
            Vector3d t2 = q.transform(v, new Vector3d());
            if (!t1.equals(t2, 1E-10f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testLookAlong() {
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(-1, 1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(-1, 1, 1), new Vector3d(0, 1, 0))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(1, 1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(1, 1, 1), new Vector3d(0, 1, 0))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(1, -1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(1, -1, 1), new Vector3d(0, 1, 0))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(1, 1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(1, 1, 1), new Vector3d(0, 1, 0))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(1, 1, -1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(1, 1, -1), new Vector3d(0, 1, 0))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(1, 1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(1, 1, 1), new Vector3d(0, 1, 0))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(1, 1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(1, 1, 1), new Vector3d(1, -1, 1))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, 1), new Vector3d(1, 1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(1, 1, 1), new Vector3d(-1, 1, 1))), 1E-6);
        assertVector3dEquals(new Vector3d(0, 0, -1), new Vector3d(1, 1, 1).normalize().rotate(new Quaterniond().lookAlong(new Vector3d(-1, -1, -1), new Vector3d(1, -1, 1))), 1E-6);
    }
}
