package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.joml.Intersectionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IntersecionfTest {

	private static final float DELTA = 1E-6f;

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
	void testPlaneSphere(float a, float b, float c, float d, float centerX, float centerY, float centerZ, float radius, boolean expected) {
		// act
		boolean result = Intersectionf.testPlaneSphere(a, b, c, d, centerX, centerY, centerZ, radius);
		
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
			float a, float b, float c, float d, float centerX, float centerY, float centerZ, float radius, boolean expectedResult, float expX, float expY, float expZ, float expW) {
		// arrange
		Vector4f v = new Vector4f();
		
		// act
		boolean result = Intersectionf.intersectPlaneSphere(a, b, c, d, centerX, centerY, centerZ, radius, v);
		
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
	void testIntersectPlaneSweptSphere(float a, float b, float c, float d, float cX, float cY, float cZ, float radius, float vX, float vY, float vZ, boolean expectedResult, float expX, float expY, float expZ, float expW) {
		// arrange
		Vector4f v = new Vector4f();
		
		// act
		boolean result = Intersectionf.intersectPlaneSweptSphere(a, b, c, d, cX, cY, cZ, radius, vX, vY, vZ, v);
		
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
	void testTestPlaneSweptSphere(float a, float b, float c, float d, float t0X, float t0Y, float t0Z, float r, float t1X, float t1Y, float t1Z, boolean expected) {
		// act
		boolean result = Intersectionf.testPlaneSweptSphere(a, b, c, d, t0X, t0Y, t0Z, r, t1X, t1Y, t1Z);
		
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
	void testTestAabPlane(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float a, float b, float c, float d, boolean expected) {
		// act
		boolean result = Intersectionf.testAabPlane(minX, minY, minZ, maxX, maxY, maxZ, a, b, c, d);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		"-1.0, -1.0, -1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, true",
		"-2.0, -2.0, -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0, false",
		"1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, -1.0, 1.0, -4.0, false"
	})
	void testTestAabPlaneVectors(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float a, float b, float c, float d, boolean expected) {
		// arrange
		Vector3f min = new Vector3f(minX, minY, minZ);
		Vector3f max = new Vector3f(maxX, maxY, maxZ);
		
		// act
		boolean result = Intersectionf.testAabPlane(min, max, a, b, c, d);
		
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
	void testTestAabAab(float minXA, float minYA, float minZA, float maxXA, float maxYA, float maxZA, float minXB, float minYB, float minZB, float maxXB, float maxYB, float maxZB, boolean expected) {
		// act
		boolean result = Intersectionf.testAabAab(minXA, minYA, minZA, maxXA, maxYA, maxZA, minXB, minYB, minZB, maxXB, maxYB, maxZB);
		
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
	void testTestAabAabVectors(float minXA, float minYA, float minZA, float maxXA, float maxYA, float maxZA, float minXB, float minYB, float minZB, float maxXB, float maxYB, float maxZB, boolean expected) {
		// arrange
		Vector3f minA = new Vector3f(minXA, minYA, minZA);
		Vector3f maxA = new Vector3f(maxXA, maxYA, maxZA);
		Vector3f minB = new Vector3f(minXB, minYB, minZB);
		Vector3f maxB = new Vector3f(maxXB, maxYB, maxZB);
		
		// act
		boolean result = Intersectionf.testAabAab(minA, maxA, minB, maxB);
		
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
	void testTestObOb(float b0cX, float b0cY, float b0cZ, float b0uXx, float b0uXy, float b0uXz, float b0uYx, float b0uYy, float b0uYz, float b0uZx, float b0uZy, float b0uZz, float b0hsX, float b0hsY, float b0hsZ, float b1cX, float b1cY, float b1cZ, float b1uXx, float b1uXy, float b1uXz, float b1uYx, float b1uYy, float b1uYz, float b1uZx, float b1uZy, float b1uZz, float b1hsX, float b1hsY, float b1hsZ, boolean expected) {
		// act
		boolean result = Intersectionf.testObOb(b0cX, b0cY, b0cZ, b0uXx, b0uXy, b0uXz, b0uYx, b0uYy, b0uYz, b0uZx, b0uZy, b0uZz, b0hsX, b0hsY, b0hsZ, b1cX, b1cY, b1cZ, b1uXx, b1uXy, b1uXz, b1uYx, b1uYy, b1uYz, b1uZx, b1uZy, b1uZz, b1hsX, b1hsY, b1hsZ);
		
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
	void testTestObObVectors(float b0cX, float b0cY, float b0cZ, float b0uXx, float b0uXy, float b0uXz, float b0uYx, float b0uYy, float b0uYz, float b0uZx, float b0uZy, float b0uZz, float b0hsX, float b0hsY, float b0hsZ, float b1cX, float b1cY, float b1cZ, float b1uXx, float b1uXy, float b1uXz, float b1uYx, float b1uYy, float b1uYz, float b1uZx, float b1uZy, float b1uZz, float b1hsX, float b1hsY, float b1hsZ, boolean expected) {
		// arrange
		Vector3f b0c = new Vector3f(b0cX, b0cY, b0cZ);
		Vector3f b0uX = new Vector3f(b0uXx, b0uXy, b0uXz);
		Vector3f b0uY = new Vector3f(b0uYx, b0uYy, b0uYz);
		Vector3f b0uZ = new Vector3f(b0uZx, b0uZy, b0uZz);
		Vector3f b0hs = new Vector3f(b0hsX, b0hsY, b0hsZ);
		Vector3f b1c = new Vector3f(b1cX, b1cY, b1cZ);
		Vector3f b1uX = new Vector3f(b1uXx, b1uXy, b1uXz);
		Vector3f b1uY = new Vector3f(b1uYx, b1uYy, b1uYz);
		Vector3f b1uZ = new Vector3f(b1uZx, b1uZy, b1uZz);
		Vector3f b1hs = new Vector3f(b1hsX, b1hsY, b1hsZ);

		// act
		boolean result = Intersectionf.testObOb(b0c, b0uX, b0uY, b0uZ, b0hs, b1c, b1uX, b1uY, b1uZ, b1hs);
		
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
	void testIntersectSphereSphere(float aX, float aY, float aZ, float radiusSquaredA, float bX, float bY, float bZ, float radiusSquaredB, boolean expectedResult, float expX, float expY, float expZ, float expW) {
		// arrange
		Vector4f v = new Vector4f();
		
		// act
		boolean result = Intersectionf.intersectSphereSphere(aX, aY, aZ, radiusSquaredA, bX, bY, bZ, radiusSquaredB, v);
		
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
	void testIntersectSphereSphereVectors(float aX, float aY, float aZ, float radiusSquaredA, float bX, float bY, float bZ, float radiusSquaredB, boolean expectedResult, float expX, float expY, float expZ, float expW) {
		// arrange
		Vector3f centerA = new Vector3f(aX, aY, aZ);
		Vector3f centerB = new Vector3f(bX, bY, bZ);
		Vector4f v = new Vector4f();
		
		// act
		boolean result = Intersectionf.intersectSphereSphere(centerA, radiusSquaredA, centerB, radiusSquaredB, v);
		
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
	void testIntersectSphereTriangle(float sX, float sY, float sZ, float sR, float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z, int expectedResult, float expX, float expY, float expZ) {
		// arrange
		Vector3f result = new Vector3f();
		
		// act
		int hit = Intersectionf.intersectSphereTriangle(sX, sY, sZ, sR, v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z, result);
		
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
	void testTestSphereSphere(float aX, float aY, float aZ, float radiusSquaredA, float bX, float bY, float bZ, float radiusSquaredB, boolean expected) {
		// act
		boolean result = Intersectionf.testSphereSphere(aX, aY, aZ, radiusSquaredA, bX, bY, bZ, radiusSquaredB);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, true",
		"0.0, 0.0, 0.0, 25.0, 8.0, 0.0, 0.0, 25.0, true",
		"0.0, 0.0, 0.0, 1.0, 5.0, 0.0, 0.0, 1.0, false"
	})
	void testTestSphereSphereVectors(float aX, float aY, float aZ, float radiusSquaredA, float bX, float bY, float bZ, float radiusSquaredB, boolean expected) {
		// arrange
		Vector3f centerA = new Vector3f(aX, aY, aZ);
		Vector3f centerB = new Vector3f(bX, bY, bZ);
		
		// act
		boolean result = Intersectionf.testSphereSphere(centerA, radiusSquaredA, centerB, radiusSquaredB);
		
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
	void testDistancePointPlaneEquation(float pointX, float pointY, float pointZ, float a, float b, float c, float d, float expected) {
		// act
		float result = Intersectionf.distancePointPlane(pointX, pointY, pointZ, a, b, c, d);
		
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
	void testDistancePointPlaneTriangle(float pointX, float pointY, float pointZ, float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z, float expected) {
		// act
		float result = Intersectionf.distancePointPlane(pointX, pointY, pointZ, v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z);
		
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
	void testIntersectRayPlanePointNormal(float originX, float originY, float originZ, float dirX, float dirY, float dirZ, float pointX, float pointY, float pointZ, float normalX, float normalY, float normalZ, float epsilon, float expected) {
		float result = Intersectionf.intersectRayPlane(originX, originY, originZ, dirX, dirY, dirZ, pointX, pointY, pointZ, normalX, normalY, normalZ, epsilon);
		assertEquals(expected, result, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, 5.0",
		"0.0, 0.0, 5.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, -1.0",
		"0.0, 0.0, 5.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.000001, -1.0"
	})
	void testIntersectRayPlanePointNormalVectors(float originX, float originY, float originZ, float dirX, float dirY, float dirZ, float pointX, float pointY, float pointZ, float normalX, float normalY, float normalZ, float epsilon, float expected) {
		// arrange
		Vector3f origin = new Vector3f(originX, originY, originZ);
		Vector3f dir = new Vector3f(dirX, dirY, dirZ);
		Vector3f point = new Vector3f(pointX, pointY, pointZ);
		Vector3f normal = new Vector3f(normalX, normalY, normalZ);
		
		// act
		float result = Intersectionf.intersectRayPlane(origin, dir, point, normal, epsilon);
		
		// assert
		assertEquals(expected, result, DELTA);
	}

	@ParameterizedTest
	@CsvSource({
		"0.0, 0.0, 5.0, 0.0, 0.0, -1.0, 0.0, 0.0, 1.0, 0.0, 0.000001, 5.0",
		"0.0, 0.0, 5.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.000001, -1.0",
		"0.0, 0.0, 5.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.000001, -1.0"
	})
	void testIntersectRayPlaneEquation(float originX, float originY, float originZ, float dirX, float dirY, float dirZ, float a, float b, float c, float d, float epsilon, float expected) {
		// act
		float result = Intersectionf.intersectRayPlane(originX, originY, originZ, dirX, dirY, dirZ, a, b, c, d, epsilon);
		
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
	void testFindClosestPointOnTriangle(float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z, float pX, float pY, float pZ, int expectedType, float expX, float expY, float expZ) {
		// arrange
		Vector3f result = new Vector3f();
		
		// act
		int type = Intersectionf.findClosestPointOnTriangle(v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z, pX, pY, pZ, result);
		
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
	void testFindClosestPointOnTriangleVectors(float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z, float pX, float pY, float pZ, int expectedType, float expX, float expY, float expZ) {
		// arrange
		Vector3f v0 = new Vector3f(v0X, v0Y, v0Z);
		Vector3f v1 = new Vector3f(v1X, v1Y, v1Z);
		Vector3f v2 = new Vector3f(v2X, v2Y, v2Z);
		Vector3f p = new Vector3f(pX, pY, pZ);
		Vector3f result = new Vector3f();
		
		// act
		int type = Intersectionf.findClosestPointOnTriangle(v0, v1, v2, p, result);
		
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
	void testFindClosestPointOnTriangle2D(float v0X, float v0Y, float v1X, float v1Y, float v2X, float v2Y, float pX, float pY, int expectedType, float expX, float expY) {
		// arrange
		Vector2f result = new Vector2f();
		
		// act
		int type = Intersectionf.findClosestPointOnTriangle(v0X, v0Y, v1X, v1Y, v2X, v2Y, pX, pY, result);
		
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
	void testFindClosestPointOnTriangle2DVectors(float v0X, float v0Y, float v1X, float v1Y, float v2X, float v2Y, float pX, float pY, int expectedType, float expX, float expY) {
		// arrange
		Vector2f v0 = new Vector2f(v0X, v0Y);
		Vector2f v1 = new Vector2f(v1X, v1Y);
		Vector2f v2 = new Vector2f(v2X, v2Y);
		Vector2f p = new Vector2f(pX, pY);
		Vector2f result = new Vector2f();
		
		// act
		int type = Intersectionf.findClosestPointOnTriangle(v0, v1, v2, p, result);
		
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
	void testIntersectSweptSphereTriangle(float cX, float cY, float cZ, float radius, float velX, float velY, float velZ, float v0X, float v0Y, float v0Z, float v1X, float v1Y, float v1Z, float v2X, float v2Y, float v2Z, float epsilon, float maxT, int expectedType, float expX, float expY, float expZ, float expT) {
		// arrange
		Vector4f result = new Vector4f();
		
		// act
		int type = Intersectionf.intersectSweptSphereTriangle(cX, cY, cZ, radius, velX, velY, velZ, v0X, v0Y, v0Z, v1X, v1Y, v1Z, v2X, v2Y, v2Z, epsilon, maxT, result);
		
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
	void testIntersectRayAab(float oX, float oY, float oZ, float dX, float dY, float dZ, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, boolean expected, float expNear, float expFar) {
		// arrange
		Vector2f result = new Vector2f();
		
		// act
		boolean hit = Intersectionf.intersectRayAab(oX, oY, oZ, dX, dY, dZ, minX, minY, minZ, maxX, maxY, maxZ, result);
		
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
	void testIntersectLineSegmentAab(float p0X, float p0Y, float p0Z, float p1X, float p1Y, float p1Z, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int expectedType, float expNear, float expFar) {
		// arrange
		Vector2f result = new Vector2f();
		
		// act
		int type = Intersectionf.intersectLineSegmentAab(p0X, p0Y, p0Z, p1X, p1Y, p1Z, minX, minY, minZ, maxX, maxY, maxZ, result);
		
		// assert
		assertEquals(expectedType, type);
		if (type != Intersectionf.OUTSIDE) {
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
	void testIntersectRayAar(float oX, float oY, float dX, float dY, float minX, float minY, float maxX, float maxY, int expectedSide, float expNear, float expFar) {
		// arrange
		Vector2f result = new Vector2f();
		
		// act
		int side = Intersectionf.intersectRayAar(oX, oY, dX, dY, minX, minY, maxX, maxY, result);
		
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
	void testIntersectLineSegmentAar(float p0X, float p0Y, float p1X, float p1Y, float minX, float minY, float maxX, float maxY, int expectedType, float expNear, float expFar) {
		// arrange
		Vector2f result = new Vector2f();
		
		// act
		int type = Intersectionf.intersectLineSegmentAar(p0X, p0Y, p1X, p1Y, minX, minY, maxX, maxY, result);
		
		assertEquals(expectedType, type);
		if (type != Intersectionf.OUTSIDE) {
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
	void testFindClosestPointsLineSegments(float a0X, float a0Y, float a0Z, float a1X, float a1Y, float a1Z, float b0X, float b0Y, float b0Z, float b1X, float b1Y, float b1Z, float expAX, float expAY, float expAZ, float expBX, float expBY, float expBZ, float expDistSq) {
		// arrange
		Vector3f resultA = new Vector3f();
		Vector3f resultB = new Vector3f();
		
		// act
		float distSq = Intersectionf.findClosestPointsLineSegments(a0X, a0Y, a0Z, a1X, a1Y, a1Z, b0X, b0Y, b0Z, b1X, b1Y, b1Z, resultA, resultB);
		
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
