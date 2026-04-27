package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.QuaterniondInterpolator;
import org.joml.Vector3d;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class QuaterniondInterpolatorTest {
	
	private static final double DELTA = 1E-3;

	@Test
	void testComputeWeightedAverageHandleIndenticalQuaternions() {
		// arrange
		Quaterniond q = new Quaterniond(1.0d, 1.0d, 1.0d, 1.0d).normalize();
		Quaterniond result = new Quaterniond();
		
		// act
		new QuaterniondInterpolator().computeWeightedAverage(new Quaterniond[] { q, q }, new double[] { 0.5d, 0.5d }, 15, result);
		
		// assert
		// normalized
		assertEquals(1.0d, result.lengthSquared(), DELTA);
		// average
		assertEquals(q.x, result.x, DELTA);
		assertEquals(q.y, result.y, DELTA);
		assertEquals(q.z, result.z, DELTA);
		assertEquals(q.w, result.w, DELTA);
	}
	
	@Test
	void testComputeWeightedAverage() {
		// arrange
		Quaterniond q1 = new Quaterniond(1.0d, 1.0d, 1.0d, 1.0d).normalize();
		Quaterniond q2 = new Quaterniond(123.0d, 123.0d, 123.0d, 123.0d).normalize();
		Quaterniond result = new Quaterniond();
		
		// act
		new QuaterniondInterpolator().computeWeightedAverage(new Quaterniond[] { q1, q2 }, new double[] { 0.0d, 1.0d }, 15, result);
		
		// assert
		// normalized
		assertEquals(1.0d, result.lengthSquared(), DELTA);
		// average
		assertEquals(q2.x, result.x, DELTA);
		assertEquals(q2.y, result.y, DELTA);
		assertEquals(q2.z, result.z, DELTA);
		assertEquals(q2.w, result.w, DELTA);
	}
	
	@Test
	void testComputeWeightedAverageOneThird() {
		// arrange
        Quaterniond q0 = new Quaterniond().rotateX(Math.toRadians(90));
        Quaterniond q1 = new Quaterniond().rotateY(Math.toRadians(90));
        Quaterniond q2 = new Quaterniond().rotateZ(Math.toRadians(90));
        Quaterniond dest = new Quaterniond();
        QuaterniondInterpolator inter = new QuaterniondInterpolator();
        
        // act
        inter.computeWeightedAverage(new Quaterniond[] { q0, q1, q2 }, new double[] { 1.0d/3.0d, 1.0d/3.0d, 1.0d/3.0d }, 30, dest);
        Vector3d v = new Vector3d(0, 0, 1);
        dest.transform(v);
        assertEquals(1.0d, v.length(), 1E-6f);
        TestUtil.assertVector3dEquals(new Vector3d(2.0d/3.0d, -1.0d/3.0d, 2.0d/3.0d), v, 1E-6f);
	}
	
	@Test
	void testComputeWeightedAverageInvalidWeightsLength() {
		// arrange
		Quaterniond q1 = new Quaterniond();
		Quaterniond q2 = new Quaterniond();
		double[] weights = new double[] { 1.0d };
		Quaterniond result = new Quaterniond();
		
		// act & assert
		assertThrows(IllegalArgumentException.class, () -> new QuaterniondInterpolator().computeWeightedAverage(new Quaterniond[] { q1, q2 }, weights, 15, result));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void testComputeWeightedAverageInvalidQuaternions(Quaterniond[] quaternions) {
		assertThrows(IllegalArgumentException.class, () -> new QuaterniondInterpolator().computeWeightedAverage(quaternions, new double[] { 1.0d }, 15, new Quaterniond()));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	void testComputeWeightedAverageInvalidWeights(double[] weights) {
		assertThrows(IllegalArgumentException.class, () -> new QuaterniondInterpolator().computeWeightedAverage(new Quaterniond[] { new Quaterniond(), new Quaterniond() }, weights, 15, new Quaterniond()));
	}
	
	@Test
	void testComputeWeightedAverageNegativeIterationCount() {
		// arrange
		int iterations = -15;
		
		// act & assert
		assertThrows(IllegalArgumentException.class, () -> new QuaterniondInterpolator().computeWeightedAverage(new Quaterniond[] { new Quaterniond(), new Quaterniond() }, new double[] { 0.8d, 0.2d }, iterations, new Quaterniond()));
	}
	
}
