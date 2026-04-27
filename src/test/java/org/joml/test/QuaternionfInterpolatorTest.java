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

import org.joml.Quaternionf;
import org.joml.QuaternionfInterpolator;
import org.joml.Vector3f;
import org.joml.Math;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.joml.test.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link QuaternionfInterpolator}.
 * 
 * @author Kai Burjack
 */
class QuaternionfInterpolatorTest {
	
	private static final double DELTA = 1E-3;
	
    @Test
    void testOneThird() {
        Quaternionf q0 = new Quaternionf().rotateX(Math.toRadians(90));
        Quaternionf q1 = new Quaternionf().rotateY(Math.toRadians(90));
        Quaternionf q2 = new Quaternionf().rotateZ(Math.toRadians(90));
        Quaternionf dest = new Quaternionf();
        QuaternionfInterpolator inter = new QuaternionfInterpolator();
        inter.computeWeightedAverage(new Quaternionf[] { q0, q1, q2 }, new float[] { 1.0f/3.0f, 1.0f/3.0f, 1.0f/3.0f }, 30, dest);
        Vector3f v = new Vector3f(0, 0, 1);
        dest.transform(v);
        assertEquals(1.0f, v.length(), 1E-6f);
        assertVector3fEquals(new Vector3f(2.0f/3.0f, -1.0f/3.0f, 2.0f/3.0f), v, 1E-6f);
    }
    
	@Test
	void testComputeWeightedAverageHandleIndenticalQuaternions() {
		// arrange
		Quaternionf q = new Quaternionf(1.0f, 1.0f, 1.0f, 1.0f).normalize();
		Quaternionf result = new Quaternionf();
		
		// act
		new QuaternionfInterpolator().computeWeightedAverage(new Quaternionf[] { q, q }, new float[] { 0.5f, 0.5f }, 15, result);
		
		// assert
		// normalized
		assertEquals(1.0f, result.lengthSquared(), DELTA);
		// average
		assertEquals(q.x, result.x, DELTA);
		assertEquals(q.y, result.y, DELTA);
		assertEquals(q.z, result.z, DELTA);
		assertEquals(q.w, result.w, DELTA);
	}
	
	@Test
	void testComputeWeightedAverage() {
		// arrange
		Quaternionf q1 = new Quaternionf(1.0f, 1.0f, 1.0f, 1.0f).normalize();
		Quaternionf q2 = new Quaternionf(123.0d, 123.0d, 123.0d, 123.0d).normalize();
		Quaternionf result = new Quaternionf();
		
		// act
		new QuaternionfInterpolator().computeWeightedAverage(new Quaternionf[] { q1, q2 }, new float[] { 0.0f, 1.0f }, 15, result);
		
		// assert
		// normalized
		assertEquals(1.0f, result.lengthSquared(), DELTA);
		// average
		assertEquals(q2.x, result.x, DELTA);
		assertEquals(q2.y, result.y, DELTA);
		assertEquals(q2.z, result.z, DELTA);
		assertEquals(q2.w, result.w, DELTA);
	}
	
	@Test
	void testComputeWeightedAverageInvalidWeightsLength() {
		// arrange
		Quaternionf q1 = new Quaternionf();
		Quaternionf q2 = new Quaternionf();
		float[] weights = new float[] { 1.0f };
		Quaternionf result = new Quaternionf();
		
		// act & assert
		assertThrows(IllegalArgumentException.class, () -> new QuaternionfInterpolator().computeWeightedAverage(new Quaternionf[] { q1, q2 }, weights, 15, result));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void testComputeWeightedAverageInvalidQuaternions(Quaternionf[] quaternions) {
		assertThrows(IllegalArgumentException.class, () -> new QuaternionfInterpolator().computeWeightedAverage(quaternions, new float[] { 1.0f }, 15, new Quaternionf()));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void testComputeWeightedAverageInvalidWeights(float[] weights) {
		assertThrows(IllegalArgumentException.class, () -> new QuaternionfInterpolator().computeWeightedAverage(new Quaternionf[] { new Quaternionf(), new Quaternionf() }, weights, 15, new Quaternionf()));
	}
	
	@Test
	void testComputeWeightedAverageNegativeIterationCount() {
		// arrange
		int iterations = -15;
		
		// act & assert
		assertThrows(IllegalArgumentException.class, () -> new QuaternionfInterpolator().computeWeightedAverage(new Quaternionf[] { new Quaternionf(), new Quaternionf() }, new float[] { 0.8f, 0.2f }, iterations, new Quaternionf()));
	}
}
