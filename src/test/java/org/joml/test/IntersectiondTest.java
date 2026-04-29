package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.joml.Intersectiond;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IntersectiondTest {
	
	private static final double DELTA = 1E-6;

	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: 0.0x+0.0y+1.0z+0.0 = 0.0; IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-0.0)^2 = 1.0^2
		// https://www.math3d.org/yGVxofCCLj
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, true",
		// IMPLICIT: 0.0x+0.0y+1.0z+0.0 = 0.0; IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-2.0)^2 = 1.0^2
		// https://www.math3d.org/YfAAzPBdhe
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 2.0, 1.0, false",
		// IMPLICIT: 0.0x+0.0y+1.0z+0.0 = 0.0; IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z+2.0)^2 = 1.0^2
		// https://www.math3d.org/jAsDR2P8bg
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, -2.0, 1.0, false"
	})
	void testPlaneSphere(double a, double b, double c, double d, double centerX, double centerY, double centerZ, double radius, boolean expected) {
		// act
		boolean result = Intersectiond.testPlaneSphere(a, b, c, d, centerX, centerY, centerZ, radius);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, true, 0.0, 0.0, 0.0, 1.0",
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 2.0, 1.0, false, 0.0, 0.0, 0.0, 0.0",
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, -2.0, 1.0, false, 0.0, 0.0, 0.0, 0.0"
	})
	void testIntersectPlaneSphere(
			double a, double b, double c, double d, double centerX, double centerY, double centerZ, double radius, boolean expectedResult, double expX, double expY, double expZ, double expW) {
		// arrange
		Vector4d v = new Vector4d();
		
		// act
		boolean result = Intersectiond.intersectPlaneSphere(a, b, c, d, centerX, centerY, centerZ, radius, v);
		
		// assert
		assertEquals(expectedResult, result);
		if (result) {
			assertEquals(expX, v.x, DELTA);
			assertEquals(expY, v.y, DELTA);
			assertEquals(expZ, v.z, DELTA);
			assertEquals(expW, v.w, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 0.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(0.5+1.0*t))^2 = 1.0^2
		// https://www.math3d.org/pWgaoM5beA
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.5, 1.0, 0.0, 0.0, 1.0, true, 0.0, 0.0, 0.5, 0.0",
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 0.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(2.0+1.0*t))^2 = 1.0^2
		// https://www.math3d.org/qU6ZC2UKdn
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 2.0, 1.0, 0.0, 0.0, 1.0, false, 0.0, 0.0, 0.0, 0.0",
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 2.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(5.0-2.0*t))^2 = 1.0^2
		// https://www.math3d.org/CKnL1W3nPA
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 5.0, 1.0, 0.0, 0.0, -2.0, true, 0.0, 0.0, 0.0, 2.0",
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 2.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(-5.0+2.0*t))^2 = 1.0^2
		// https://www.math3d.org/sHTmlkMopn
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, -5.0, 1.0, 0.0, 0.0, 2.0, true, 0.0, 0.0, 0.0, 2.0"
	})
	void testIntersectPlaneSweptSphere(double a, double b, double c, double d, double cX, double cY, double cZ, double radius, double vX, double vY, double vZ, boolean expectedResult, double expX, double expY, double expZ, double expW) {
		// arrange
		Vector4d v = new Vector4d();
		
		// act
		boolean result = Intersectiond.intersectPlaneSweptSphere(a, b, c, d, cX, cY, cZ, radius, vX, vY, vZ, v);
		
		// assert
		assertEquals(expectedResult, result);
		if (result) {
			assertEquals(expX, v.x, DELTA);
			assertEquals(expY, v.y, DELTA);
			assertEquals(expZ, v.z, DELTA);
			assertEquals(expW, v.w, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 0.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(1.0+4.0*t))^2 = 1.0^2
		// https://www.math3d.org/33uPeEn9Z2
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 5.0, true",
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 0.5; (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(5.0-10.0*t))^2 = 1.0^2
		// https://www.math3d.org/Cltk2KEWqX
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 5.0, 1.0, 0.0, 0.0, -5.0, true",
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 1.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(5.0-4.0*t))^2 = 1.0^2
		// https://www.math3d.org/yrFna2qSIa
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 5.0, 1.0, 0.0, 0.0, 1.0, true",
		// IMPLICIT: 0.0*x+0.0*y+1.0*z+0.0 = 0; VARSLIDER: t = 1.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(5.0-3.0*t))^2 = 1.0^2
		// https://www.math3d.org/8WQtW2Q3mG
		"0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 5.0, 1.0, 0.0, 0.0, 2.0, false"
	})
	void testTestPlaneSweptSphere(double a, double b, double c, double d, double t0X, double t0Y, double t0Z, double r, double t1X, double t1Y, double t1Z, boolean expected) {
		// act
		boolean result = Intersectiond.testPlaneSweptSphere(a, b, c, d, t0X, t0Y, t0Z, r, t1X, t1Y, t1Z);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; IMPLICIT: 1.0*x+1.0*y+1.0*z+0.0 = 0
		// https://www.math3d.org/NpD8Hox6VF
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, true",
		// POINT: [-2.0, -2.0, -2.0]; POINT: [-1.0, -1.0, -1.0]; IMPLICIT: -1.0*x-1.0*y-1.0*z-2.0 = 0
		// https://www.math3d.org/BN96TIDPqj
		"-2.0, -2.0, -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0, false",
		// POINT: [1.0, 1.0, 1.0]; POINT: [2.0, 2.0, 2.0]; IMPLICIT: 1.0*x-1.0*y+1.0*z-4.0 = 0
		// https://www.math3d.org/gJH39iYXbW
		"1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, -1.0, 1.0, -4.0, false"
	})
	void testTestAabPlane(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double a, double b, double c, double d, boolean expected) {
		// act
		boolean result = Intersectiond.testAabPlane(minX, minY, minZ, maxX, maxY, maxZ, a, b, c, d);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, true",
		"-2.0, -2.0, -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0, false",
		"1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, -1.0, 1.0, -4.0, false"
	})
	void testTestAabPlaneVectors(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double a, double b, double c, double d, boolean expected) {
		// arrange
		Vector3d min = new Vector3d(minX, minY, minZ);
		Vector3d max = new Vector3d(maxX, maxY, maxZ);
		
		// act
		boolean result = Intersectiond.testAabPlane(min, max, a, b, c, d);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 2.0, 2.0]
		// https://www.math3d.org/2cRcFzhqAa
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 2.0, 2.0, 2.0, true",
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; POINT: [2.0, 0.0, 0.0]; POINT: [3.0, 2.0, 2.0]
		// https://www.math3d.org/RYquJYY6QE
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 2.0, 0.0, 0.0, 3.0, 2.0, 2.0, false",
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; POINT: [0.0, 2.0, 0.0]; POINT: [2.0, 3.0, 2.0]
		// https://www.math3d.org/xw9HydcvfR
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 0.0, 2.0, 0.0, 2.0, 3.0, 2.0, false",
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; POINT: [0.0, 0.0, 2.0]; POINT: [2.0, 2.0, 3.0]
		// https://www.math3d.org/73LQmPbLxT
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 2.0, 2.0, 2.0, 3.0, false",
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; POINT: [-3.0, -1.0, -1.0]; POINT: [-2.0, 1.0, 1.0]
		// https://www.math3d.org/mEPdK6cGTD
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -3.0, -1.0, -1.0, -2.0, 1.0, 1.0, false",
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; POINT: [-1.0, -3.0, -1.0]; POINT: [1.0, -2.0, 1.0]
		// https://www.math3d.org/cWE1ThglFj
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -1.0, -3.0, -1.0, 1.0, -2.0, 1.0, false",
		// POINT: [-1.0, -1.0, -1.0]; POINT: [1.0, 1.0, 1.0]; POINT: [-1.0, -1.0, -3.0]; POINT: [1.0, 1.0, -2.0]
		// https://www.math3d.org/AMm3Dt3zJF
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -1.0, -1.0, -3.0, 1.0, 1.0, -2.0, false"
	})
	void testTestAabAab(double minXA, double minYA, double minZA, double maxXA, double maxYA, double maxZA, double minXB, double minYB, double minZB, double maxXB, double maxYB, double maxZB, boolean expected) {
		// act
		boolean result = Intersectiond.testAabAab(minXA, minYA, minZA, maxXA, maxYA, maxZA, minXB, minYB, minZB, maxXB, maxYB, maxZB);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 2.0, 2.0, 2.0, true",
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 2.0, 0.0, 0.0, 3.0, 2.0, 2.0, false",
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 0.0, 2.0, 0.0, 2.0, 3.0, 2.0, false",
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 2.0, 2.0, 2.0, 3.0, false",
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -3.0, -1.0, -1.0, -2.0, 1.0, 1.0, false",
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -1.0, -3.0, -1.0, 1.0, -2.0, 1.0, false",
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -1.0, -1.0, -3.0, 1.0, 1.0, -2.0, false"
	})
	void testTestAabAabVectors(double minXA, double minYA, double minZA, double maxXA, double maxYA, double maxZA, double minXB, double minYB, double minZB, double maxXB, double maxYB, double maxZB, boolean expected) {
		// arrange
		Vector3d minA = new Vector3d(minXA, minYA, minZA);
		Vector3d maxA = new Vector3d(maxXA, maxYA, maxZA);
		Vector3d minB = new Vector3d(minXB, minYB, minZB);
		Vector3d maxB = new Vector3d(maxXB, maxYB, maxZB);
		
		// act
		boolean result = Intersectiond.testAabAab(minA, maxA, minB, maxB);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: max(max(abs(x-0.0),abs(y-0.0)),abs(z-0.0))=1.0; IMPLICIT: max(max(abs(x-0.0),abs(y-0.0)),abs(z-0.0))=1.0
		// https://www.math3d.org/DUiQxQBFjW
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, true",
		// IMPLICIT: max(max(abs(x-0.0),abs(y-0.0)),abs(z-0.0))=1.0; IMPLICIT: max(max(abs(x-3.0),abs(y-0.0)),abs(z-0.0))=1.0
		// https://www.math3d.org/28RwAq6vPR
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 3.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, false",
		// IMPLICIT: max(max(abs(x-0.0),abs(y-0.0)),abs(z-0.0))=1.0; IMPLICIT: max(max(abs(x-0.0),abs(y-3.0)),abs(z-0.0))=1.0
		// https://www.math3d.org/PS41ce8LTe
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 3.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, false",
		// IMPLICIT: max(max(abs(x-0.0),abs(y-0.0)),abs(z-0.0))=1.0; IMPLICIT: max(max(abs(x-0.0),abs(y-0.0)),abs(z-3.0))=1.0
		// https://www.math3d.org/igo47jb1Kh
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 3.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, false"
	})
	void testTestObOb(double b0cX, double b0cY, double b0cZ, double b0uXx, double b0uXy, double b0uXz, double b0uYx, double b0uYy, double b0uYz, double b0uZx, double b0uZy, double b0uZz, double b0hsX, double b0hsY, double b0hsZ, double b1cX, double b1cY, double b1cZ, double b1uXx, double b1uXy, double b1uXz, double b1uYx, double b1uYy, double b1uYz, double b1uZx, double b1uZy, double b1uZz, double b1hsX, double b1hsY, double b1hsZ, boolean expected) {
		// act
		boolean result = Intersectiond.testObOb(b0cX, b0cY, b0cZ, b0uXx, b0uXy, b0uXz, b0uYx, b0uYy, b0uYz, b0uZx, b0uZy, b0uZz, b0hsX, b0hsY, b0hsZ, b1cX, b1cY, b1cZ, b1uXx, b1uXy, b1uXz, b1uYx, b1uYy, b1uYz, b1uZx, b1uZy, b1uZz, b1hsX, b1hsY, b1hsZ);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, true",
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 3.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, false",
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 3.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, false",
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 3.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, false"
	})
	void testTestObObVectors(double b0cX, double b0cY, double b0cZ, double b0uXx, double b0uXy, double b0uXz, double b0uYx, double b0uYy, double b0uYz, double b0uZx, double b0uZy, double b0uZz, double b0hsX, double b0hsY, double b0hsZ, double b1cX, double b1cY, double b1cZ, double b1uXx, double b1uXy, double b1uXz, double b1uYx, double b1uYy, double b1uYz, double b1uZx, double b1uZy, double b1uZz, double b1hsX, double b1hsY, double b1hsZ, boolean expected) {
		// arrange
		Vector3d b0c = new Vector3d(b0cX, b0cY, b0cZ);
		Vector3d b0uX = new Vector3d(b0uXx, b0uXy, b0uXz);
		Vector3d b0uY = new Vector3d(b0uYx, b0uYy, b0uYz);
		Vector3d b0uZ = new Vector3d(b0uZx, b0uZy, b0uZz);
		Vector3d b0hs = new Vector3d(b0hsX, b0hsY, b0hsZ);
		Vector3d b1c = new Vector3d(b1cX, b1cY, b1cZ);
		Vector3d b1uX = new Vector3d(b1uXx, b1uXy, b1uXz);
		Vector3d b1uY = new Vector3d(b1uYx, b1uYy, b1uYz);
		Vector3d b1uZ = new Vector3d(b1uZx, b1uZy, b1uZz);
		Vector3d b1hs = new Vector3d(b1hsX, b1hsY, b1hsZ);

		// act
		boolean result = Intersectiond.testObOb(b0c, b0uX, b0uY, b0uZ, b0hs, b1c, b1uX, b1uY, b1uZ, b1hs);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-0.0)^2 = 25.0; IMPLICIT: (x-8.0)^2+(y-0.0)^2+(z-0.0)^2 = 25.0
		// https://www.math3d.org/eNBfcKkayK
		"0.0, 0.0, 0.0, 25.0, 8.0, 0.0, 0.0, 25.0, true, 4.0, 0.0, 0.0, 3.0",
		// IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-0.0)^2 = 1.0; IMPLICIT: (x-5.0)^2+(y-0.0)^2+(z-0.0)^2 = 1.0
		// https://www.math3d.org/eNBfcKkayK
		"0.0, 0.0, 0.0, 1.0, 5.0, 0.0, 0.0, 1.0, false, 0.0, 0.0, 0.0, 0.0"
	})
	void testIntersectSphereSphere(double aX, double aY, double aZ, double radiusSquaredA, double bX, double bY, double bZ, double radiusSquaredB, boolean expectedResult, double expX, double expY, double expZ, double expW) {
		// arrange
		Vector4d v = new Vector4d();
		
		// act
		boolean result = Intersectiond.intersectSphereSphere(aX, aY, aZ, radiusSquaredA, bX, bY, bZ, radiusSquaredB, v);
		
		// assert
		assertEquals(expectedResult, result);
		if (result) {
			assertEquals(expX, v.x, DELTA);
			assertEquals(expY, v.y, DELTA);
			assertEquals(expZ, v.z, DELTA);
			assertEquals(expW, v.w, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 0.0, 25.0, 8.0, 0.0, 0.0, 25.0, true, 4.0, 0.0, 0.0, 3.0",
		"0.0, 0.0, 0.0, 1.0, 5.0, 0.0, 0.0, 1.0, false, 0.0, 0.0, 0.0, 0.0"
	})
	void testIntersectSphereSphereVectors(double aX, double aY, double aZ, double radiusSquaredA, double bX, double bY, double bZ, double radiusSquaredB, boolean expectedResult, double expX, double expY, double expZ, double expW) {
		// arrange
		Vector3d centerA = new Vector3d(aX, aY, aZ);
		Vector3d centerB = new Vector3d(bX, bY, bZ);
		Vector4d v = new Vector4d();
		
		// act
		boolean result = Intersectiond.intersectSphereSphere(centerA, radiusSquaredA, centerB, radiusSquaredB, v);
		
		// assert
		assertEquals(expectedResult, result);
		if (result) {
			assertEquals(expX, v.x, DELTA);
			assertEquals(expY, v.y, DELTA);
			assertEquals(expZ, v.z, DELTA);
			assertEquals(expW, v.w, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; IMPLICIT: (x-0.5)^2+(y-0.5)^2+(z-0.0)^2 = 1.0^2
		// https://www.math3d.org/9Y8UktJvv2
		"0.5, 0.5, 0.0, 1.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 7, 0.5, 0.5, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; IMPLICIT: (x-0.5)^2+(y-0.5)^2+(z-2.0)^2 = 1.0^2
		// https://www.math3d.org/Y78aPAqGIP
		"0.5, 0.5, 2.0, 1.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0, 0.5, 0.5, 0.0"
	})
	void testIntersectSphereTriangle(double sX, double sY, double sZ, double sR, double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z, int expectedResult, double expX, double expY, double expZ) {
		// arrange
		Vector3d result = new Vector3d();
		
		// act
		int hit = Intersectiond.intersectSphereTriangle(sX, sY, sZ, sR, v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z, result);
		
		// assert
		assertEquals(expectedResult, hit);
		assertEquals(expX, result.x, DELTA);
		assertEquals(expY, result.y, DELTA);
		assertEquals(expZ, result.z, DELTA);
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-0.0)^2 = 1.0; IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-0.0)^2 = 1.0
		// https://www.math3d.org/uisADjvZgh
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, true",
		// IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-0.0)^2 = 25.0; IMPLICIT: (x-8.0)^2+(y-0.0)^2+(z-0.0)^2 = 25.0
		// https://www.math3d.org/FT7dzxMZkL
		"0.0, 0.0, 0.0, 25.0, 8.0, 0.0, 0.0, 25.0, true",
		// IMPLICIT: (x-0.0)^2+(y-0.0)^2+(z-0.0)^2 = 1.0; IMPLICIT: (x-5.0)^2+(y-0.0)^2+(z-0.0)^2 = 1.0
		// https://www.math3d.org/h1gKRgi6Cm
		"0.0, 0.0, 0.0, 1.0, 5.0, 0.0, 0.0, 1.0, false"
	})
	void testTestSphereSphere(double aX, double aY, double aZ, double radiusSquaredA, double bX, double bY, double bZ, double radiusSquaredB, boolean expected) {
		// act
		boolean result = Intersectiond.testSphereSphere(aX, aY, aZ, radiusSquaredA, bX, bY, bZ, radiusSquaredB);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, true",
		"0.0, 0.0, 0.0, 25.0, 8.0, 0.0, 0.0, 25.0, true",
		"0.0, 0.0, 0.0, 1.0, 5.0, 0.0, 0.0, 1.0, false"
	})
	void testTestSphereSphereVectors(double aX, double aY, double aZ, double radiusSquaredA, double bX, double bY, double bZ, double radiusSquaredB, boolean expected) {
		// arrange
		Vector3d centerA = new Vector3d(aX, aY, aZ);
		Vector3d centerB = new Vector3d(bX, bY, bZ);
		
		// act
		boolean result = Intersectiond.testSphereSphere(centerA, radiusSquaredA, centerB, radiusSquaredB);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		// POINT: [0.0, 0.0, 0.0]; IMPLICIT: 1.0*x+0.0*y+0.0*z+0.0 = 0
		// https://www.math3d.org/3KyLTe2pGl
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0",
		// POINT: [5.0, 0.0, 0.0]; IMPLICIT: 1.0*x+0.0*y+0.0*z+0.0 = 0
		// https://www.math3d.org/FfiiF88HE8
		"5.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 5.0",
		// POINT: [-3.0, 0.0, 0.0]; IMPLICIT: 1.0*x+0.0*y+0.0*z+0.0 = 0
		// https://www.math3d.org/EI6UwQ9lAe
		"-3.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, -3.0"
	})
	void testDistancePointPlaneEquation(double pointX, double pointY, double pointZ, double a, double b, double c, double d, double expected) {
		// act
		double result = Intersectiond.distancePointPlane(pointX, pointY, pointZ, a, b, c, d);
		
		// assert
		assertEquals(expected, result, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		// POINT: [0.0, 0.0, 0.0]; POINT: [0.0, 0.0, 0.0]; POINT: [1.0, 0.0, 0.0]; POINT: [0.0, 1.0, 0.0]
		// https://www.math3d.org/MA1b4UVuGN
		"0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0",
		// POINT: [0.0, 0.0, 5.0]; POINT: [0.0, 0.0, 0.0]; POINT: [1.0, 0.0, 0.0]; POINT: [0.0, 1.0, 0.0]
		// https://www.math3d.org/qCB96FDr8u
		"0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 5.0",
		// POINT: [0.0, 0.0, -3.0]; POINT: [0.0, 0.0, 0.0]; POINT: [1.0, 0.0, 0.0]; POINT: [0.0, 1.0, 0.0]
		// https://www.math3d.org/aCzuRFUH5G
		"0.0, 0.0, -3.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, -3.0"
	})
	void testDistancePointPlaneTriangle(double pointX, double pointY, double pointZ, double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z, double expected) {
		// act
		double result = Intersectiond.distancePointPlane(pointX, pointY, pointZ, v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z);
		
		// assert
		assertEquals(expected, result, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		// POINT: [0.0, 0.0, 5.0]; VECTOR: [0.0, 0.0, -1.0]; POINT: [0.0, 0.0, 0.0]; VECTOR: [0.0, 0.0, 1.0]
		// https://www.math3d.org/YPwVfeTuq5
		"0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, 5.0",
		// POINT: [0.0, 0.0, 5.0]; VECTOR: [0.0, 0.0, 1.0]; POINT: [0.0, 0.0, 0.0]; VECTOR: [0.0, 0.0, 1.0]
		// https://www.math3d.org/5UeAWMRTfE
		"0.0, 0.0, 5.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, -1.0",
		// POINT: [0.0, 0.0, 5.0]; VECTOR: [1.0, 0.0, 0.0]; POINT: [0.0, 0.0, 0.0]; VECTOR: [0.0, 0.0, 1.0]
		// https://www.math3d.org/WqzBwAK4fu
		"0.0, 0.0, 5.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, -1.0"
	})
	void testIntersectRayPlanePointNormal(double originX, double originY, double originZ, double dirX, double dirY, double dirZ, double pointX, double pointY, double pointZ, double normalX, double normalY, double normalZ, double epsilon, double expected) {
		double result = Intersectiond.intersectRayPlane(originX, originY, originZ, dirX, dirY, dirZ, pointX, pointY, pointZ, normalX, normalY, normalZ, epsilon);
		assertEquals(expected, result, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, 5.0",
		"0.0, 0.0, 5.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, -1.0",
		"0.0, 0.0, 5.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, -1.0"
	})
	void testIntersectRayPlanePointNormalVectors(double originX, double originY, double originZ, double dirX, double dirY, double dirZ, double pointX, double pointY, double pointZ, double normalX, double normalY, double normalZ, double epsilon, double expected) {
		// arrange
		Vector3d origin = new Vector3d(originX, originY, originZ);
		Vector3d dir = new Vector3d(dirX, dirY, dirZ);
		Vector3d point = new Vector3d(pointX, pointY, pointZ);
		Vector3d normal = new Vector3d(normalX, normalY, normalZ);
		
		// act
		double result = Intersectiond.intersectRayPlane(origin, dir, point, normal, epsilon);
		
		// assert
		assertEquals(expected, result, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 0.0, 1.0, 0.0, 0.000001, 5.0",
		"0.0, 0.0, 5.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.000001, -1.0",
		"0.0, 0.0, 5.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.000001, -1.0"
	})
	void testIntersectRayPlaneEquation(double originX, double originY, double originZ, double dirX, double dirY, double dirZ, double a, double b, double c, double d, double epsilon, double expected) {
		// act
		double result = Intersectiond.intersectRayPlane(originX, originY, originZ, dirX, dirY, dirZ, a, b, c, d, epsilon);
		
		// assert
		assertEquals(expected, result, DELTA);
	}
	
	@ParameterizedTest
	@CsvSource({
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; POINT: [-1.0, -1.0, 0.0]
		// https://www.math3d.org/BH18pHDPPG
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0, -1.0, 0.0, 1, 0.0, 0.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; POINT: [3.0, -1.0, 0.0]
		// https://www.math3d.org/YXp54SUnl1
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 3.0, -1.0, 0.0, 2, 2.0, 0.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; POINT: [-1.0, 3.0, 0.0]
		// https://www.math3d.org/lrdyhIPs69
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0, 3.0, 0.0, 3, 0.0, 2.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; POINT: [1.0, -1.0, 0.0]
		// https://www.math3d.org/PhKuoDmJEp
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 1.0, -1.0, 0.0, 4, 1.0, 0.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; POINT: [2.0, 2.0, 0.0]
		// https://www.math3d.org/oDNNMQ4MaH
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 2.0, 2.0, 0.0, 5, 1.0, 1.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; POINT: [-1.0, 1.0, 0.0]
		// https://www.math3d.org/tq7Tr3hpnT
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0, 1.0, 0.0, 6, 0.0, 1.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; POINT: [0.5, 0.5, 1.0]
		// https://www.math3d.org/cUmILqERKk
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.5, 0.5, 1.0, 7, 0.5, 0.5, 0.0"
	})
	void testFindClosestPointOnTriangle(double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z, double pX, double pY, double pZ, int expectedType, double expX, double expY, double expZ) {
		// arrange
		Vector3d result = new Vector3d();
		
		// act
		int type = Intersectiond.findClosestPointOnTriangle(v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z, pX, pY, pZ, result);
		
		// assert
		assertEquals(expectedType, type);
		assertEquals(expX, result.x, DELTA);
		assertEquals(expY, result.y, DELTA);
		assertEquals(expZ, result.z, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0, -1.0, 0.0, 1, 0.0, 0.0, 0.0",
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 3.0, -1.0, 0.0, 2, 2.0, 0.0, 0.0",
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0, 3.0, 0.0, 3, 0.0, 2.0, 0.0",
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 1.0, -1.0, 0.0, 4, 1.0, 0.0, 0.0",
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 2.0, 2.0, 0.0, 5, 1.0, 1.0, 0.0",
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, -1.0, 1.0, 0.0, 6, 0.0, 1.0, 0.0",
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.5, 0.5, 1.0, 7, 0.5, 0.5, 0.0"
	})
	void testFindClosestPointOnTriangleVectors(double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z, double pX, double pY, double pZ, int expectedType, double expX, double expY, double expZ) {
		// arrange
		Vector3d v0 = new Vector3d(v0X, v0Y, v0Z);
		Vector3d v1 = new Vector3d(v1X, v1Y, v1Z);
		Vector3d v2 = new Vector3d(v2X, v2Y, v2Z);
		Vector3d p = new Vector3d(pX, pY, pZ);
		Vector3d result = new Vector3d();
		
		// act
		int type = Intersectiond.findClosestPointOnTriangle(v0, v1, v2, p, result);
		
		// assert
		assertEquals(expectedType, type);
		assertEquals(expX, result.x, DELTA);
		assertEquals(expY, result.y, DELTA);
		assertEquals(expZ, result.z, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, -1.0, -1.0, 1, 0.0, 0.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 3.0, -1.0, 2, 2.0, 0.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, -1.0, 3.0, 3, 0.0, 2.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 1.0, -1.0, 4, 1.0, 0.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 2.0, 2.0, 5, 1.0, 1.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, -1.0, 1.0, 6, 0.0, 1.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 0.5, 0.5, 7, 0.5, 0.5"
	})
	void testFindClosestPointOnTriangle2D(double v0X, double v0Y, double v1X, double v1Y, double v2X, double v2Y, double pX, double pY, int expectedType, double expX, double expY) {
		// arrange
		Vector2d result = new Vector2d();
		
		// act
		int type = Intersectiond.findClosestPointOnTriangle(v0X, v0Y, v1X, v1Y, v2X, v2Y, pX, pY, result);
		
		// assert
		assertEquals(expectedType, type);
		assertEquals(expX, result.x, DELTA);
		assertEquals(expY, result.y, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, -1.0, -1.0, 1, 0.0, 0.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 3.0, -1.0, 2, 2.0, 0.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, -1.0, 3.0, 3, 0.0, 2.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 1.0, -1.0, 4, 1.0, 0.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 2.0, 2.0, 5, 1.0, 1.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, -1.0, 1.0, 6, 0.0, 1.0",
		"0.0, 0.0, 2.0, 0.0, 0.0, 2.0, 0.5, 0.5, 7, 0.5, 0.5"
	})
	void testFindClosestPointOnTriangle2DVectors(double v0X, double v0Y, double v1X, double v1Y, double v2X, double v2Y, double pX, double pY, int expectedType, double expX, double expY) {
		// arrange
		Vector2d v0 = new Vector2d(v0X, v0Y);
		Vector2d v1 = new Vector2d(v1X, v1Y);
		Vector2d v2 = new Vector2d(v2X, v2Y);
		Vector2d p = new Vector2d(pX, pY);
		Vector2d result = new Vector2d();
		
		// act
		int type = Intersectiond.findClosestPointOnTriangle(v0, v1, v2, p, result);
		
		// assert
		assertEquals(expectedType, type);
		assertEquals(expX, result.x, DELTA);
		assertEquals(expY, result.y, DELTA);
	}
	
	@ParameterizedTest
	@CsvSource({
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 1.0; IMPLICIT: (x-(0.0+1.0*t))^2+(y-(0.0+0.0*t))^2+(z-(5.0+0.0*t))^2 = 1.0^2
		// https://www.math3d.org/cUmILqERKk
		"0.0, 0.0, 5.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 0, 0.0, 0.0, 0.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 1.0; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(5.0-1.0*t))^2 = 1.0^2
		// https://www.math3d.org/onbsm4cwon
		"0.0, 0.0, 5.0, 1.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 0, 0.0, 0.0, 0.0, 0.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 0.5; IMPLICIT: (x-(0.5+0.0*t))^2+(y-(0.5+0.0*t))^2+(z-(2.0-2.0*t))^2 = 1.0^2
		// https://www.math3d.org/onbsm4cwon
		"0.5, 0.5, 2.0, 1.0, 0.0, 0.0, -2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 7, 0.5, 0.5, 0.0, 0.5",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 0.5; IMPLICIT: (x-(-0.8+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(1.2-1.2*t))^2 = 1.0^2
		// https://www.math3d.org/4EShcpLXun
		"-0.8, 0.0, 1.2, 1.0, 0.0, 0.0, -1.2, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 1, 0.0, 0.0, 0.0, 0.5",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 0.5; IMPLICIT: (x-(2.8+0.0*t))^2+(y-(0.0+0.0*t))^2+(z-(1.2-1.2*t))^2 = 1.0^2
		// https://www.math3d.org/sS3XCGnlQZ
		"2.8, 0.0, 1.2, 1.0, 0.0, 0.0, -1.2, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 2, 2.0, 0.0, 0.0, 0.5",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 0.5; IMPLICIT: (x-(0.0+0.0*t))^2+(y-(2.8+0.0*t))^2+(z-(1.2-1.2*t))^2 = 1.0^2
		// https://www.math3d.org/GxxohDMaoH
		"0.0, 2.8, 1.2, 1.0, 0.0, 0.0, -1.2, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 3, 0.0, 2.0, 0.0, 0.5",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 0.5; IMPLICIT: (x-(1.0+0.0*t))^2+(y-(-0.8+0.0*t))^2+(z-(1.2-1.2*t))^2 = 1.0^2
		// https://www.math3d.org/iNsHw4Nqw5
		"1.0, -0.8, 1.2, 1.0, 0.0, 0.0, -1.2, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 4, 1.0, 0.0, 0.0, 0.5",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 0.5; IMPLICIT: (x-(3.0+0.0*t))^2+(y-(3.0+0.0*t))^2+(z-(4.0-6.0*t))^2 = 3.0^2
		// https://www.math3d.org/jRYMtdkTwy
		"3.0, 3.0, 4.0, 3.0, 0.0, 0.0, -6.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 5, 1.0, 1.0, 0.0, 0.5",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 2.0, 0.0]; VARSLIDER: t = 0.5; IMPLICIT: (x-(-0.8+0.0*t))^2+(y-(1.0+0.0*t))^2+(z-(1.2-1.2*t))^2 = 1.0^2
		// https://www.math3d.org/phxklk6u1h
		"-0.8, 1.0, 1.2, 1.0, 0.0, 0.0, -1.2, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0001, 1.0, 6, 0.0, 1.0, 0.0, 0.5"
	})
	void testIntersectSweptSphereTriangle(double cX, double cY, double cZ, double radius, double velX, double velY, double velZ, double v0X, double v0Y, double v0Z, double v1X, double v1Y, double v1Z, double v2X, double v2Y, double v2Z, double epsilon, double maxT, int expectedType, double expX, double expY, double expZ, double expT) {
		// arrange
		Vector4d result = new Vector4d();
		
		// act
		int type = Intersectiond.intersectSweptSphereTriangle(cX, cY, cZ, radius, velX, velY, velZ, v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z, epsilon, maxT, result);
		
		// assert
		assertEquals(expectedType, type);
		if (type != 0) {
			assertEquals(expX, result.x, DELTA);
			assertEquals(expY, result.y, DELTA);
			assertEquals(expZ, result.z, DELTA);
			assertEquals(expT, result.w, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, 0.0, 5.0]; VECTOR: [0.0, 0.0, -1.0]
		// https://www.math3d.org/jRYMtdkTwy
		"0.0, 0.0, 5.0, 0.0, 0.0, -1.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, true, 4.0, 6.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, 0.0, -5.0]; VECTOR: [0.0, 0.0, 1.0]
		// https://www.math3d.org/w7UVtL6hdh
		"0.0, 0.0, -5.0, 0.0, 0.0, 1.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, true, 4.0, 6.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [5.0, 0.0, 0.0]; VECTOR: [-1.0, 0.0, 0.0]
		// https://www.math3d.org/a9AEqMfQEX
		"5.0, 0.0, 0.0, -1.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, true, 4.0, 6.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [-5.0, 0.0, 0.0]; VECTOR: [1.0, 0.0, 0.0]
		// https://www.math3d.org/qpNnom4reY
		"-5.0, 0.0, 0.0, 1.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, true, 4.0, 6.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, 5.0, 0.0]; VECTOR: [0.0, -1.0, 0.0]
		// https://www.math3d.org/E3YikK1yaq
		"0.0, 5.0, 0.0, 0.0, -1.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, true, 4.0, 6.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, -5.0, 0.0]; VECTOR: [0.0, 1.0, 0.0]
		// https://www.math3d.org/FqQiIbUBPN
		"0.0, -5.0, 0.0, 0.0, 1.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, true, 4.0, 6.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, 0.0, 0.0]; VECTOR: [1.0, 0.0, 0.0]
		// https://www.math3d.org/qlYjv6p6nR
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, true, -1.0, 1.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, 0.0, 5.0]; VECTOR: [1.0, 0.0, 0.0]
		// https://www.math3d.org/qlYjv6p6nR
		"0.0, 0.0, 5.0, 1.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, false, 0.0, 0.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [2.0, 2.0, 2.0]; VECTOR: [1.0, 1.0, 1.0]
		// https://www.math3d.org/kqgpRuwvqF
		"2.0, 2.0, 2.0, 1.0, 1.0, 1.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, false, 0.0, 0.0"
	})
	void testIntersectRayAab(double oX, double oY, double oZ, double dX, double dY, double dZ, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, boolean expected, double expNear, double expFar) {
		// arrange
		Vector2d result = new Vector2d();
		
		// act
		boolean hit = Intersectiond.intersectRayAab(oX, oY, oZ, dX, dY, dZ, minX, minY, minZ, maxX, maxY, maxZ, result);
		
		// assert
		assertEquals(expected, hit);
		if (hit) {
			assertEquals(expNear, result.x, DELTA);
			assertEquals(expFar, result.y, DELTA);
		}
	}

	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, 0.0, 0.0]; POINT: [0.5, 0.0, 0.0]
		// https://www.math3d.org/KW3Y77v7bN
		"0.0, 0.0, 0.0, 0.5, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 3, -2.0, 2.0",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 0.0, 0.0]
		// https://www.math3d.org/7bgcb2RTCG
		"2.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 1, 0.5, 0.5",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]
		// https://www.math3d.org/Cy6jdPSqTS
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 1, 0.5, 0.5",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [-2.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]
		// https://www.math3d.org/ztbh7WA2yE
		"-2.0, 0.0, 0.0, 2.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 2, 0.25, 0.75",
		// IMPLICIT: max(max(abs(x),abs(y)),abs(z))=1.0; POINT: [5.0, 0.0, 0.0]; POINT: [6.0, 0.0, 0.0]
		// https://www.math3d.org/Rc86Dxln6T
		"5.0, 0.0, 0.0, 6.0, 0.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 1.0, -1, 0.0, 0.0"
	})
	void testIntersectLineSegmentAab(double p0X, double p0Y, double p0Z, double p1X, double p1Y, double p1Z, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, int expectedType, double expNear, double expFar) {
		// arrange
		Vector2d result = new Vector2d();
		
		// act
		int type = Intersectiond.intersectLineSegmentAab(p0X, p0Y, p0Z, p1X, p1Y, p1Z, minX, minY, minZ, maxX, maxY, maxZ, result);
		
		// assert
		assertEquals(expectedType, type);
		if (type != Intersectiond.OUTSIDE) {
			assertEquals(expNear, result.x, DELTA);
			assertEquals(expFar, result.y, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [-5.0, 0.0, 0.0]; VECTOR: [1.0, 0.0, 0.0]
		// https://www.math3d.org/Rc86Dxln6T
		"-5.0, 0.0, 1.0, 0.0, -1.0, -1.0, 1.0, 1.0, 0, 4.0, 6.0",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [0.0, -5.0, 0.0]; VECTOR: [0.0, 1.0, 0.0]
		// https://www.math3d.org/hrTepvgHeV
		"0.0, -5.0, 0.0, 1.0, -1.0, -1.0, 1.0, 1.0, 1, 4.0, 6.0",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [5.0, 0.0, 0.0]; VECTOR: [-1.0, 0.0, 0.0]
		// https://www.math3d.org/uAm3sczzF7
		"5.0, 0.0, -1.0, 0.0, -1.0, -1.0, 1.0, 1.0, 2, 4.0, 6.0",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [0.0, 5.0, 0.0]; VECTOR: [0.0, -1.0, 0.0]
		// https://www.math3d.org/nyDQKu6icA
		"0.0, 5.0, 0.0, -1.0, -1.0, -1.0, 1.0, 1.0, 3, 4.0, 6.0",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [0.0, 0.0, 0.0]; VECTOR: [1.0, 0.0, 0.0]
		// https://www.math3d.org/aPzwAVvfWR
		"0.0, 0.0, 1.0, 0.0, -1.0, -1.0, 1.0, 1.0, 0, -1.0, 1.0",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [5.0, 5.0, 0.0]; VECTOR: [1.0, 1.0, 0.0]
		// https://www.math3d.org/Gzo59k6KQr
		"5.0, 5.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1, 0.0, 0.0"
	})
	void testIntersectRayAar(double oX, double oY, double dX, double dY, double minX, double minY, double maxX, double maxY, int expectedSide, double expNear, double expFar) {
		// arrange
		Vector2d result = new Vector2d();
		
		// act
		int side = Intersectiond.intersectRayAar(oX, oY, dX, dY, minX, minY, maxX, maxY, result);
		
		// assert
		assertEquals(expectedSide, side);
		if (side != -1) {
			assertEquals(expNear, result.x, DELTA);
			assertEquals(expFar, result.y, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [0.0, 0.0, 0.0]; POINT: [0.5, 0.0, 0.0]
		// https://www.math3d.org/Gzo59k6KQr
		"0.0, 0.0, 0.5, 0.0, -1.0, -1.0, 1.0, 1.0, 3, -2.0, 2.0",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [2.0, 0.0, 0.0]; POINT: [0.0, 0.0, 0.0]
		// https://www.math3d.org/SnoirjCI9N
		"2.0, 0.0, 0.0, 0.0, -1.0, -1.0, 1.0, 1.0, 1, 0.5, 0.5",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [-2.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]
		// https://www.math3d.org/az9U9nvflN
		"-2.0, 0.0, 2.0, 0.0, -1.0, -1.0, 1.0, 1.0, 2, 0.25, 0.75",
		// IMPLICIT: max(abs(x),abs(y))=1.0; POINT: [5.0, 0.0, 0.0]; POINT: [6.0, 0.0, 0.0]
		// https://www.math3d.org/7VqGQPoorr
		"5.0, 0.0, 6.0, 0.0, -1.0, -1.0, 1.0, 1.0, -1, 0.0, 0.0"
	})
	void testIntersectLineSegmentAar(double p0X, double p0Y, double p1X, double p1Y, double minX, double minY, double maxX, double maxY, int expectedType, double expNear, double expFar) {
		// arrange
		Vector2d result = new Vector2d();
		
		// act
		int type = Intersectiond.intersectLineSegmentAar(p0X, p0Y, p1X, p1Y, minX, minY, maxX, maxY, result);
		
		assertEquals(expectedType, type);
		if (type != Intersectiond.OUTSIDE) {
			assertEquals(expNear, result.x, DELTA);
			assertEquals(expFar, result.y, DELTA);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		// POINT: [-1.0, 0.0, -1.0]; POINT: [1.0, 0.0, 1.0]; POINT: [-1.0, 1.0, 1.0]; POINT: [1.0, 1.0, -1.0]
		// https://www.math3d.org/m6HE8yhlI1
		"-1.0, 0.0, -1.0, 1.0, 0.0, 1.0, -1.0, 1.0, 1.0, 1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [0.0, 0.0, 0.0]; POINT: [1.0, 0.0, 0.0]; POINT: [1.0, 0.0, 0.0]
		// https://www.math3d.org/AYZ5kvV4Dl
		"0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [0.0, 0.0, 0.0]; POINT: [-1.0, 1.0, 0.0]; POINT: [1.0, 1.0, 0.0]
		// https://www.math3d.org/MuxVIknyDp
		"0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0",
		// POINT: [-1.0, -1.0, 0.0]; POINT: [1.0, -1.0, 0.0]; POINT: [0.0, 0.0, 0.0]; POINT: [0.0, 0.0, 0.0]
		// https://www.math3d.org/XKP21KWjs4
		"-1.0, -1.0, 0.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0",
		// POINT: [-1.0, 0.0, 0.0]; POINT: [1.0, 0.0, 0.0]; POINT: [-1.0, 1.0, 0.0]; POINT: [1.0, 1.0, 0.0]
		// https://www.math3d.org/daiocmJgHd
		"-1.0, 0.0, 0.0, 1.0, 0.0, 0.0, -1.0, 1.0, 0.0, 1.0, 1.0, 0.0, -1.0, 0.0, 0.0, -1.0, 1.0, 0.0, 1.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [-2.0, 1.0, 0.0]; POINT: [-1.0, 1.0, 0.0]
		// https://www.math3d.org/n8NLf7Slqi
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, -2.0, 1.0, 0.0, -1.0, 1.0, 0.0, 0.0, 0.0, 0.0, -1.0, 1.0, 0.0, 2.0",
		// POINT: [0.0, 0.0, 0.0]; POINT: [2.0, 0.0, 0.0]; POINT: [3.0, 1.0, 0.0]; POINT: [4.0, 1.0, 0.0]
		// https://www.math3d.org/tq7GYhtZzF
		"0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 3.0, 1.0, 0.0, 4.0, 1.0, 0.0, 2.0, 0.0, 0.0, 3.0, 1.0, 0.0, 2.0"
	})
	void testFindClosestPointsLineSegments(double a0X, double a0Y, double a0Z, double a1X, double a1Y, double a1Z, double b0X, double b0Y, double b0Z, double b1X, double b1Y, double b1Z, double expAX, double expAY, double expAZ, double expBX, double expBY, double expBZ, double expDistSq) {
		// arrange
		Vector3d resultA = new Vector3d();
		Vector3d resultB = new Vector3d();
		
		// act
		double distSq = Intersectiond.findClosestPointsLineSegments(a0X, a0Y, a0Z, a1X, a1Y, a1Z, b0X, b0Y, b0Z, b1X, b1Y, b1Z, resultA, resultB);
		
		// assert
		assertEquals(expAX, resultA.x, DELTA);
		assertEquals(expAY, resultA.y, DELTA);
		assertEquals(expAZ, resultA.z, DELTA);
		assertEquals(expBX, resultB.x, DELTA);
		assertEquals(expBY, resultB.y, DELTA);
		assertEquals(expBZ, resultB.z, DELTA);
		assertEquals(expDistSq, distSq, DELTA);
	}
	
}
