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
import java.nio.FloatBuffer;
import java.util.Arrays;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Quaternionf}.
 * @author Sebastian Fellner
 */
class QuaternionfTest {
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

        final Quaternionf q = new Quaternionf(quaternionValues);

        if (index < quaternionValues.length) {
            assertFalse(q.isFinite());
        } else {
            assertTrue(q.isFinite());
        }
    }

    @Test
    void testQuaternionfNlerpIterativeWithWeights() {
        final Quaternionf q1 = new Quaternionf(new float[] {1, 2, 3, 4});
        final Quaternionf qDestination = new Quaternionf();
        final Quaternionfc result = q1.nlerpIterative(
                new Quaternionf[] {
                        new Quaternionf(new float[] {4, 3, 2, 1}),
                        new Quaternionf(new float[] {-0.4f, -0.3f, -0.2f, -0.1f})
                },
                new float[]{ 0.5f, 0.5f },
                0.5f,
                qDestination
        );
        assertEquals(result, qDestination);
    }

    @Test
    void testQuaternionfNlerpIterative() {
        final Quaternionf q1 = new Quaternionf(new float[] {1, 2, 3, 4});
        q1.nlerpIterative(new Quaternionf(new float[] {4, 3, 2, 1}), 0.5f, 0.5f);
        assertTrue(q1.equals(new Quaternionf(new float[] {1, 2, 3, 4}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.nlerpIterative(new Quaternionf(new float[] {-0.4f, -0.3f, -0.2f, -0.1f}), -0.5f, 0.5f);
        assertTrue(q1.equals(new Quaternionf(new float[] {-0.571f, -0.450f, -0.502f, -0.467f}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.nlerpIterative(new Quaternionf(new float[] {0.4f, 0.3f, 0.2f, 0.1f}), 0.5f, 0.5f);
        assertTrue(q1.equals(new Quaternionf(new float[] {0.524f, 0.419f, 0.524f, 0.524f}), DELTA));
    }

    @Test
    void testQuaternionfNlerpWithWeights() {
        Quaternionf q1 = new Quaternionf(new float[] {1, 2, 3, 4});
        Quaternionf qDestination = new Quaternionf();
        Quaternionfc result = q1.nlerp(
                new Quaternionf[] {
                        new Quaternionf(new float[] {4, 3, 2, 1}),
                        new Quaternionf(new float[] {-0.4f, -0.3f, -0.2f, -0.1f})
                },
                new float[]{ 0.5f, 0.5f },
                qDestination
        );
        assertEquals(result, qDestination);
    }

    @Test
    void testQuaternionfNlerp() {
        final Quaternionf q1 = new Quaternionf(new float[] {1, 2, 3, 4});
        q1.nlerp(new Quaternionf(new float[] {4, 3, 2, 1}), 0.5f);
        assertTrue(q1.equals(new Quaternionf(new float[] {0.5f, 0.5f, 0.5f, 0.5f}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.nlerp(new Quaternionf(new float[] {-0.4f, -0.3f, -0.2f, -0.1f}), -0.5f);
        assertTrue(q1.equals(new Quaternionf(new float[] {-0.0764f, 0.0f, 0.5353f, 0.8411f}), DELTA));
    }

    @Test
    void testQuaternionfSlerpWithWeights() {
        Quaternionf q1 = new Quaternionf(new float[] {1, 2, 3, 4});
        Quaternionf qDestination = new Quaternionf();
        Quaternionfc result = q1.slerp(
                new Quaternionf[] {
                        new Quaternionf(new float[] {4f, 3f, 2f, 1f}),
                        new Quaternionf(new float[] {-0.4f, -0.3f, -0.2f, -0.1f})
                },
                new float[]{ 0.5f, 0.5f },
                qDestination
        );
        assertEquals(result, qDestination);
    }

    @Test
    void testQuaternionfSlerp() {
        final Quaternionf q1 = new Quaternionf(new float[] {1, 2, 3, 4});
        q1.slerp(new Quaternionf(new float[] {4, 3, 2, 1}), 0.5f);
        assertTrue(q1.equals(new Quaternionf(new float[] {2.5f, 2.5f, 2.5f, 2.5f}), DELTA));

        q1.set(new float[] {0.1f, 0.1f, 0.3f, 0.4f});
        q1.slerp(new Quaternionf(new float[] {-0.4f, -0.3f, -0.2f, -0.1f}), -0.5f);
        assertTrue(q1.equals(new Quaternionf(new float[] {-0.173f, -0.108f, 0.132f, 0.285f}), DELTA));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array that contains less than 4 elements.
     *
     * @param arraySize The size of the array that will be used to initialize the
     * an {@link Quaternionf} instance.
     */
    @ParameterizedTest
    @ValueSource(ints ={ 4, 3, 2, 1, 0 })
    void testQuaternionfConstructorByFloatArray(int arraySize) {
        final float[] array = new float[arraySize];
        final int value = 5;
        Arrays.fill(array, value);

        // Check whether the instance could be created or not.
        assertDoesNotThrow(() -> new Quaternionf(array));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array and offset where the offset could read an element outside the bounds of the array.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 2, 3, 4, 5})
    void testQuaternionfConstructorByFloatArrayAndOffset(int offset) {
        final float[] array = new float[] {1, 2, 3, 4};
        assertDoesNotThrow(() -> new Quaternionf(array, offset));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array and offset where the offset could read an element outside the bounds of the array.
     */
    @ParameterizedTest
    @ValueSource(ints = {4, 3, 2, 1, 0})
    void testQuaternionfConstructorByFloatArrayLessThenFourElementAndOffset(int arraySize) {
        final float[] array = new float[arraySize];
        final int value = 5;
        Arrays.fill(array, value);
        assertDoesNotThrow(() -> new Quaternionf(array, 0));
    }

    @Test
    void testQuaternionfConstructorByQuaternionfInstance() {
        final Quaternionf testQuat = new Quaternionf(0, 1, 2, 3);
        assertEquals(new Quaternionf(testQuat), testQuat);
    }

    /**
     * Testing out whether the program is prepared to handle the case where
     * a null instance of {@link Quaternionf} is provided as a param to the constructor
     * of the {@link Quaternionf}.
     */
    @Test
    void testQuaternionfConstructorByNullQuaternionfInstance() {
        final Quaternionf testQuat = null;
        assertDoesNotThrow(() -> new Quaternionf(testQuat));
    }

    /**
     * Testing out whether the constructor could handle the case when it is constructed
     * with an array that contains less than 4 elements.
     *
     * @param arraySize The size of the array that will be used to initialize the
     * an {@link Quaternionf} instance.
     */
    @ParameterizedTest
    @ValueSource(ints = { 4, 3, 2, 1, 0 })
    void testQuaternionfConstructorByFloatBuffer(int arraySize) {
        final float[] array = new float[arraySize];
        Arrays.fill(array, arraySize);
        assertDoesNotThrow(() -> new Quaternionf(FloatBuffer.wrap(array)));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, 1, 2, 3 })
    void testQuaternionfConstructorByFloatBufferAndOffset(int offset) {
        final float[] array = new float[4];
        Arrays.fill(array, 5);
        assertDoesNotThrow(() -> new Quaternionf(offset, FloatBuffer.wrap(array)));
    }

    @ParameterizedTest
    @ValueSource(ints = { 32, 24, 16, 8, 0 })
    void testQuaternionfConstructorByByteBuffer(int arraySize) {
        assertDoesNotThrow(() -> new Quaternionf(ByteBuffer.wrap(new byte[arraySize])));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -8, 8, 16, 24 })
    void testQuaternionfConstructorByByteBufferAndOffset(int offset) {
        assertDoesNotThrow(() -> new Quaternionf(offset, ByteBuffer.wrap(new byte[32])));
    }

    @Test
    void testQuaternionfAddCoordinatesMethod() {
        final Quaternionf testQuaternionf = new Quaternionf(0, 1, 2, 3);
        final Quaternionf finalQuaternionf = testQuaternionf.add(3, 3, 3, 3);
        assertEquals(3, finalQuaternionf.x());
        assertEquals(4, finalQuaternionf.y());
        assertEquals(5, finalQuaternionf.z());
        assertEquals(6, finalQuaternionf.w());
    }

    @Test
    void testQuaternionfAddQuaternionfMethod() {
        final Quaternionf testQuaternionf = new Quaternionf(0, 1, 2, 3);
        final Quaternionf finalQuaternionf = testQuaternionf.add(testQuaternionf);
        assertEquals(0, finalQuaternionf.x());
        assertEquals(2, finalQuaternionf.y());
        assertEquals(4, finalQuaternionf.z());
        assertEquals(6, finalQuaternionf.w());
    }

    @Test
    void testQuaternionfSubCoordinatesMethod() {
        final Quaternionf testQuaternionf = new Quaternionf(0, 1, 2, 3);
        final Quaternionf finalQuaternionf = testQuaternionf.sub(3, 3, 3, 3);
        assertEquals(-3, finalQuaternionf.x());
        assertEquals(-2, finalQuaternionf.y());
        assertEquals(-1, finalQuaternionf.z());
        assertEquals(0, finalQuaternionf.w());
    }

    @Test
    void testQuaternionfSubQuaternionfMethod() {
        final Quaternionf testQuaternionf = new Quaternionf(0, 1, 2, 3);
        final Quaternionf finalQuaternionf = testQuaternionf.sub(testQuaternionf);
        assertEquals(0, finalQuaternionf.x());
        assertEquals(0, finalQuaternionf.y());
        assertEquals(0, finalQuaternionf.z());
        assertEquals(0, finalQuaternionf.w());
    }

    @Test
    void testQuanternionDEqualsByQuaternionfInstanceAndDelta() throws CloneNotSupportedException {
        final Quaternionf testQuaternionf1 = new Quaternionf(0, 1, 2, 3);
        final Quaternionf testQuaternionf2 = new Quaternionf(0, 1, 2, 3);
        assertTrue(testQuaternionf1.equals(testQuaternionf2, DELTA));
        assertFalse(testQuaternionf1.equals(null, DELTA));
        assertTrue(testQuaternionf1.equals((Quaternionf) testQuaternionf1.clone(), DELTA));
        assertTrue(testQuaternionf1.equals(testQuaternionf1, DELTA));
        assertFalse(testQuaternionf1.equals(new Quaternionf(Integer.MAX_VALUE, 1, 2, 3), DELTA));
        assertFalse(testQuaternionf1.equals(new Quaternionf(0, Integer.MAX_VALUE, 2, 3), DELTA));
        assertFalse(testQuaternionf1.equals(new Quaternionf(0, 1, Integer.MAX_VALUE, 3), DELTA));
        assertFalse(testQuaternionf1.equals(new Quaternionf(0, 1, 2, Integer.MAX_VALUE), DELTA));
    }

    @Test
    void testQuanternionDEqualsByQuaternionfInstance() {
        final Quaternionf testQuaternionf1 = new Quaternionf(0, 1, 2, 3);
        final Quaternionf testQuaternionf2 = new Quaternionf(0, 1, 2, 3);
        assertEquals(testQuaternionf1, testQuaternionf2);
        assertFalse(testQuaternionf1.equals(null));
        assertNotEquals(testQuaternionf1, new Vector3d());
        assertEquals(testQuaternionf1, testQuaternionf1);
        assertNotEquals(new Quaternionf(Integer.MAX_VALUE, 1, 2, 3), testQuaternionf1);
        assertNotEquals(new Quaternionf(0, Integer.MAX_VALUE, 2, 3), testQuaternionf1);
        assertNotEquals(new Quaternionf(0, 1, Integer.MAX_VALUE, 3), testQuaternionf1);
        assertNotEquals(new Quaternionf(0, 1, 2, Integer.MAX_VALUE), testQuaternionf1);
    }

    @Test
    void testQuanternionDEqualsByCoordinates() {
        final Quaternionf testQuaternionf1 = new Quaternionf(0, 1, 2, 3);
        assertTrue(testQuaternionf1.equals(0, 1, 2, 3));
        assertFalse(testQuaternionf1.equals(Integer.MAX_VALUE, 1, 2, 3));
        assertFalse(testQuaternionf1.equals(0, Integer.MAX_VALUE, 2, 3));
        assertFalse(testQuaternionf1.equals(0, 1, Integer.MAX_VALUE, 3));
        assertFalse(testQuaternionf1.equals(0, 1, 2, Integer.MAX_VALUE));
    }

    @Test
    void testQuaternionfGetAxisAngle4f() {
        final AxisAngle4f axisAngle = new AxisAngle4f(0, 0, 0, 1);
        final Quaternionf Quaternionf1 = new Quaternionf(0, 1, 2, 3);
        Quaternionf1.get(axisAngle);
        assertEquals(axisAngle.x, Quaternionf1.x(), 0);

        final Quaternionf Quaternionf2 = new Quaternionf(0, 1, 2, -1);
        Quaternionf2.get(axisAngle);
        assertEquals(axisAngle.x, Quaternionf2.x(), 0);
    }

    @Test
    void testQuaternionfGetAxisAngle4d() {
        final AxisAngle4d axisAngle = new AxisAngle4d(0, 0, 0, 1);
        final Quaternionf Quaternionf1 = new Quaternionf(0, 1, 2, 3);
        Quaternionf1.get(axisAngle);
        assertEquals(axisAngle.x, Quaternionf1.x(), 0);

        final Quaternionf Quaternionf2 = new Quaternionf(0, 1, 2, -1);
        Quaternionf2.get(axisAngle);
        assertEquals(axisAngle.x, Quaternionf2.x(), 0);
    }

    @Test
    void testQuaternionfNormalize() {
        final Quaternionf Quaternionf = new Quaternionf(0, 1, 2, 3);
        assertDoesNotThrow(() -> Quaternionf.normalize());
    }

    @Test
    void testQuaternionfNormalizeWithNullInstance() {
        final Quaternionf Quaternionf = null;
        assertDoesNotThrow(() -> Quaternionf.normalize());
    }

    @Test
    void testQuaternionfNormalizeWithQuaternionfParam() {
        final Quaternionf Quaternionf = new Quaternionf(0, 1, 2, 3);
        final Quaternionf QuaternionfDestination = new Quaternionf(4, 5, 6, 3);
        assertDoesNotThrow(() -> Quaternionf.normalize(QuaternionfDestination));
    }

    @Test
    void testQuaternionfNormalizeWithQuaternionfParamWithNullInstance() {
        final Quaternionf Quaternionf = null;
        final Quaternionf QuaternionfDestination = new Quaternionf(4, 5, 6, 3);
        assertDoesNotThrow(() -> Quaternionf.normalize(QuaternionfDestination));
    }

    @Test
    void testQuaternionfGetDoubleArray() {
        final Quaternionf q = new Quaternionf(0, 1, 2, 3);
        final float[] array = q.get(new float[4]);
        assertEquals(0, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 2, 1, 0})
    void testQuaternionfGetDoubleArrayWithLessThanFourElements(int arraySize) {
        final Quaternionf Quaternionf = new Quaternionf(0, 1, 2, 3);
        assertDoesNotThrow(() -> Quaternionf.get(new float[arraySize]));
    }

    @Test
    void testQuaternionfGetFloatArray() {
        final Quaternionf Quaternionf = new Quaternionf(0, 1, 2, 3);
        final float[] array = Quaternionf.get(new float[4]);
        assertEquals(0, array[0]);
        assertEquals(1, array[1]);
        assertEquals(2, array[2]);
        assertEquals(3, array[3]);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 2, 1, 0})
    void testQuaternionfGetFloatArrayWithLessThanFourElements(int arraySize) {
        final Quaternionf Quaternionf = new Quaternionf(0, 1, 2, 3);
        assertDoesNotThrow(() -> Quaternionf.get(new float[arraySize]));
    }

    @Test
    void testIntegrate() {
        Quaternionf q = new Quaternionf();
        Quaternionf dest = new Quaternionf();
        Quaternionf res = q.integrate(1, 1, 1, 1, dest);
        assertEquals(res, dest);

        res = q.integrate(0, 0, 0, 1, dest);
        assertEquals(res, dest);
    }

    @Test
    void testSetFromNormalizedWithMatrix3f() {
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3f(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1})).equals(new Quaternionf(), DELTA));
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3f(new float[]{1, 1, 1, 1, -4, 1, 1, 1, 2})).equals(new Quaternionf(0.408f, 0.408f, 1.224f, 0.0f), DELTA));
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3f(new float[]{-1, 1, 1, 1, 1, 1, 1, 1, -1})).equals(new Quaternionf(0.5f, 1.0f, 0.5f, 0.0f), DELTA));
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3f(new float[]{1, 1, 1, 1, -1, 1, 1, 1, -1})).equals(new Quaternionf(1.0f, 0.5f, 0.5f, 0.0f), DELTA));
    }

    @Test
    void testSetFromNormalizedWithMatrix3d() {
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3d(new float[]{1, 1, 1, 1, 1, 1, 1, 1, 1})).equals(new Quaternionf(), DELTA));
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3d(new float[]{1, 1, 1, 1, -4, 1, 1, 1, 2})).equals(new Quaternionf(0.408f, 0.408f, 1.224f, 0.0f), DELTA));
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3d(new float[]{-1, 1, 1, 1, 1, 1, 1, 1, -1})).equals(new Quaternionf(0.5f, 1.0f, 0.5f, 0.0f), DELTA));
        assertTrue(new Quaternionf().setFromNormalized(new Matrix3d(new float[]{1, 1, 1, 1, -1, 1, 1, 1, -1})).equals(new Quaternionf(1.0f, 0.5f, 0.5f, 0.0f), DELTA));
    }

    @Test
    void testRotationToWithDoubleParam() {
        Quaternionf rotation = new Quaternionf();
        Quaternionf result = rotation.rotationTo(0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        assertEquals(rotation, result);

        rotation = new Quaternionf();
        result = rotation.rotationTo(1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f);
        assertEquals(rotation, result);

        rotation = new Quaternionf();
        result = rotation.rotationTo(0.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f);
        assertEquals(rotation, result);
    }
    
    @Test
    void testMulQuaternionQuaternionQuaternion() {
        // Multiplication with the identity quaternion should change nothing
        Quaternionf testQuat = new Quaternionf(1f, 23.3f, -7.57f, 2.1f);
        Quaternionf identityQuat = new Quaternionf().identity();
        Quaternionf resultQuat = new Quaternionf();
        
        testQuat.mul(identityQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_FLOAT));
        
        identityQuat.mul(testQuat, resultQuat);
        assertTrue(quatEqual(testQuat, resultQuat, STANDARD_AROUND_ZERO_PRECISION_FLOAT));

        // Multiplication with conjugate should give (0, 0, 0, dot(this, this))
        Quaternionf conjugate = new Quaternionf();
        testQuat.conjugate(conjugate);
        testQuat.mul(conjugate, resultQuat);
        
        Quaternionf wantedResultQuat = new Quaternionf(0, 0, 0, testQuat.dot(testQuat));
        assertTrue(quatEqual(resultQuat, wantedResultQuat, MANY_OPS_AROUND_ZERO_PRECISION_FLOAT));
    }

    @Test
    void testSlerp() {
        Quaternionf q1 = new Quaternionf().rotateY(0.0f);
        Quaternionf q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.slerp(q2, 0.5f, q);
        q.transform(v);
        assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    @Test
    void testNlerp() {
        Quaternionf q1 = new Quaternionf().rotateY(0.0f);
        Quaternionf q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.nlerp(q2, 0.5f, q);
        q.transform(v);
        assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    @Test
    void testNlerpRecursive() {
        Quaternionf q1 = new Quaternionf().rotateY(0.0f);
        Quaternionf q2 = new Quaternionf().rotateY((float) java.lang.Math.PI * 0.5f);
        Quaternionf q = new Quaternionf();
        Vector3f v = new Vector3f(1.0f, 0.0f, 0.0f);
        q1.nlerpIterative(q2, 0.5f, 1E-5f, q);
        q.transform(v);
        assertVector3fEquals(new Vector3f(1.0f, 0.0f, -1.0f).normalize(), v, 1E-5f);
    }

    @Test
    void testRotationXYZ() {
        Quaternionf v = new Quaternionf().rotationXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationZYX() {
        Quaternionf v = new Quaternionf().rotationZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotationYXZ() {
        Quaternionf v = new Quaternionf().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotationYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateXYZ() {
        Quaternionf v = new Quaternionf().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateXYZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateZYX() {
        Quaternionf v = new Quaternionf().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateZYX(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateYXZ() {
        Quaternionf v = new Quaternionf().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f m = new Matrix4f().rotateYXZ(0.12f, 0.521f, 0.951f);
        Matrix4f n = new Matrix4f().set(v);
        assertMatrix4fEquals(m, n, 1E-6f);
    }

    @Test
    void testRotateToUnit() {
        Vector3f v1 = new Vector3f(1, 2, 3).normalize();
        Vector3f v2 = new Vector3f(5, -2, -1).normalize();
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToNonUnit() {
        Vector3f v1 = new Vector3f(1, 2, 3).normalize().mul(3);
        Vector3f v2 = new Vector3f(5, -2, -1).normalize().mul(3);
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToUnitOpposite() {
        Vector3f v1 = new Vector3f(1, 2, 3).normalize();
        Vector3f v2 = new Vector3f(-1, -2, -3).normalize();
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToNonUnitOpposite() {
        Vector3f v1 = new Vector3f(1, 2, 3);
        Vector3f v2 = new Vector3f(-1, -2, -3);
        Quaternionf rotation = new Quaternionf().rotateTo(v1, v2);
        rotation.transform(v1);
        assertVector3fEquals(v1, v2, 1E-6f);
    }

    @Test
    void testRotateToReturnsDestination() {
        Quaternionf rotation = new Quaternionf();
        Quaternionf destination = new Quaternionf();
        Quaternionf result = rotation.rotateTo(0, 1, 0, 0, 1, 0, destination);
        assertSame(destination, result);

        result = rotation.rotateTo(1, 1, 0, -1, -1, 0, destination);
        assertSame(destination, result);

        result = rotation.rotateTo(0, 0, 1, 0, 0, -1, destination);
        assertSame(destination, result);
    }

    @Test
    void testConjugateBy() {
        Quaternionf p = new Quaternionf().rotateXYZ(0.234f, -0.62f, 0.11f);
        Quaternionf q = new Quaternionf().rotateXYZ(0.834f, 0.42f, -1.471f);
        Quaternionf r = p.mul(q.mul(p.invert(new Quaternionf()), new Quaternionf()), new Quaternionf());
        Quaternionf r2 = q.conjugateBy(p, new Quaternionf());
        assertQuaternionfEquals(r, r2, 1E-6f);
    }

    @Test
    void testGetEulerAnglesXYZ() {
        Random rnd = new Random(1L);
        int failure = 0;
        int N = 30000;
        for (int i = 0; i < N; i++) {
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateXYZ(x, y, z);
            Vector3f a = p.getEulerAnglesXYZ(new Vector3f());
            Quaternionf q = new Quaternionf().rotateX(a.x).rotateY(a.y).rotateZ(a.z);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
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
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateZYX(z, y, x);
            Vector3f a = p.getEulerAnglesZYX(new Vector3f());
            Quaternionf q = new Quaternionf().rotateZ(a.z).rotateY(a.y).rotateX(a.x);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
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
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateZ(z).rotateX(x).rotateY(y);
            Vector3f a = p.getEulerAnglesZXY(new Vector3f());
            Quaternionf q = new Quaternionf().rotateZ(a.z).rotateX(a.x).rotateY(a.y);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
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
            float x = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float y = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            float z = (rnd.nextFloat() * 2f - 1f) * (float) Math.PI;
            Quaternionf p = new Quaternionf().rotateY(y).rotateX(x).rotateZ(z);
            Vector3f a = p.getEulerAnglesYXZ(new Vector3f());
            Quaternionf q = new Quaternionf().rotateY(a.y).rotateX(a.x).rotateZ(a.z);
            Vector3f v = new Vector3f(rnd.nextFloat()*2-1, rnd.nextFloat()*2-1, rnd.nextFloat()*2-1);
            Vector3f t1 = p.transform(v, new Vector3f());
            Vector3f t2 = q.transform(v, new Vector3f());
            if (!t1.equals(t2, 1E-3f))
                failure++;
        }
        if ((float)failure / N > 0.0001f) // <- allow for a failure rate of 0.01%
            throw new AssertionError();
    }

    @Test
    void testLookAlong() {
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(-1, 1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(-1, 1, 1), new Vector3f(0, 1, 0))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(1, 1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(1, 1, 1), new Vector3f(0, 1, 0))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(1, -1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(1, -1, 1), new Vector3f(0, 1, 0))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(1, 1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(1, 1, 1), new Vector3f(0, 1, 0))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(1, 1, -1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(1, 1, -1), new Vector3f(0, 1, 0))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(1, 1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(1, 1, 1), new Vector3f(0, 1, 0))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(1, 1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(1, 1, 1), new Vector3f(1, -1, 1))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, 1), new Vector3f(1, 1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(1, 1, 1), new Vector3f(-1, 1, 1))), 1E-6f);
        assertVector3fEquals(new Vector3f(0, 0, -1), new Vector3f(1, 1, 1).normalize().rotate(new Quaternionf().lookAlong(new Vector3f(-1, -1, -1), new Vector3f(1, -1, 1))), 1E-6f);
    }
}
