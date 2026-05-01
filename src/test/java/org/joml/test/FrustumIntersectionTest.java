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

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Math;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link FrustumIntersection} class.
 * 
 * @author Kai Burjack
 */
class FrustumIntersectionTest {
    @Test
    void testIsSphereInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        FrustumIntersection c = new FrustumIntersection(m);
        assertTrue(c.testSphere(1, 0, 0, 0.1f));
        assertFalse(c.testSphere(1.2f, 0, 0, 0.1f));
    }

    @Test
    void testIsSphereInFrustumPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertTrue(c.testSphere(1, 0, -2, 0.1f));
        assertFalse(c.testSphere(4f, 0, -2, 1.0f));
    }

    @Test
    void testIsAabInFrustumOrtho() {
        Matrix4f m = new Matrix4f().ortho(-1, 1, -1, 1, -1, 1);
        FrustumIntersection c = new FrustumIntersection(m);
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(-20, -2, 0, 20, 2, 0));
        assertEquals(FrustumIntersection.INSIDE, c.intersectAab(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
        assertEquals(Matrix4fc.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        c.set(new Matrix4f().ortho(-1, 1, -1, 1, -1, 1));
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(0, 0, 0, 2, 2, 2));
        assertEquals(Matrix4fc.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        c.set(new Matrix4f());
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(0.5f, 0.5f, 0.5f, 2, 2, 2));
        assertEquals(Matrix4fc.PLANE_PX, c.intersectAab(1.5f, 0.5f, 0.5f, 2, 2, 2));
        assertEquals(Matrix4fc.PLANE_NX, c.intersectAab(-2.5f, 0.5f, 0.5f, -1.5f, 2, 2));
        assertEquals(Matrix4fc.PLANE_NY, c.intersectAab(-0.5f, -2.5f, 0.5f, 1.5f, -2, 2));
    }

    @Test
    void testIsAabInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertEquals(FrustumIntersection.INSIDE, c.intersectAab(0, 0, -7, 1, 1, -5));
        assertEquals(FrustumIntersection.PLANE_PX, c.intersectAab(1.1f, 0, 0, 2, 2, 2));
        assertEquals(FrustumIntersection.PLANE_PX, c.intersectAab(4, 4, -3, 5, 5, -5));
        assertEquals(FrustumIntersection.PLANE_NY, c.intersectAab(-6, -6, -2, -1, -4, -4));
    }

    @Test
    void testIsPointInPerspective() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertTrue(c.testPoint(0, 0, -5));
        assertFalse(c.testPoint(0, 6, -5));
    }

    @Test
    void testIsAabInPerspectiveMask() {
        Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
        FrustumIntersection c = new FrustumIntersection(m);
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(5.1f, 0, -3, 8, 2, -2, ~0 ^ FrustumIntersection.PLANE_MASK_PX));
        assertEquals(FrustumIntersection.INTERSECT, c.intersectAab(-6.1f, 0, -3, -5, 2, -2, ~0 ^ FrustumIntersection.PLANE_MASK_NX));
        assertEquals(Matrix4fc.PLANE_NX, c.intersectAab(-6.1f, 0, -3, -5, 2, -2, FrustumIntersection.PLANE_MASK_NX));
        assertEquals(Matrix4fc.PLANE_NX, c.intersectAab(-6.1f, 0, -3, -5, 2, -2, ~0, Matrix4fc.PLANE_NX));
    }
    
    // camera frustum
    // PARAMETRIC_SURFACE: [-u, u*v, -u]; u=[0,10], v=[-1,1]
    // PARAMETRIC_SURFACE: [u, u*v, -u]; u=[0,10], v=[-1,1]
    // PARAMETRIC_SURFACE: [u*v, u, -u]; u=[0,10], v=[-1,1]
    // PARAMETRIC_SURFACE: [u*v, -u, -u]; u=[0,10], v=[-1,1]
    // https://www.math3d.org/gT7vjuI2fo
    @ParameterizedTest
	@CsvSource({
		// LINE: [[-1.0, 0.0, -5.0],[1.0, 0.0, -5.0]]
		// https://www.math3d.org/JY1iqYPqRe
		"-1.0, 0.0, -5.0, 1.0, 0.0, -5.0, true",
		// LINE: [[-10.0, 0.0, -5.0],[-6.0, 0.0, -5.0]]
		// https://www.math3d.org/yZDPJ8qMvS
		"-10.0, 0.0, -5.0, -6.0, 0.0, -5.0, false",
		// LINE: [[6.0, 0.0, -5.0],[10.0, 0.0, -5.0]]
		// https://www.math3d.org/HRAzRfeIze
		"6.0, 0.0, -5.0, 10.0, 0.0, -5.0, false",
		// LINE: [[0.0, -10.0, -5.0],[0.0, -6.0, -5.0]]
		// https://www.math3d.org/e8HnXaoSl6
		"0.0, -10.0, -5.0, 0.0, -6.0, -5.0, false",
		// LINE: [[0.0, 6.0, -5.0],[0.0, 10.0, -5.0]]
		// https://www.math3d.org/AULYzdjLG9
		"0.0, 6.0, -5.0, 0.0, 10.0, -5.0, false",
		// LINE: [[0.0, 0.0, 0.0],[0.0, 0.0, 0.5]]
		// https://www.math3d.org/ztSST3Sn9M
		"0.0, 0.0, 0.0, 0.0, 0.0, 0.5, false",
		// LINE: [[0.0, 0.0, -110.0],[0.0, 0.0, -150.0]]
		// https://www.math3d.org/qVjD8F9a87
		"0.0, 0.0, -110.0, 0.0, 0.0, -150.0, false",
		// LINE: [[-10.0, 0.0, -5.0],[0.0, 0.0, -5.0]]
		// https://www.math3d.org/Iyz6t1AjAB
		"-10.0, 0.0, -5.0, 0.0, 0.0, -5.0, true",
		// LINE: [[0.0, 0.0, -5.0],[-10.0, 0.0, -5.0]]
		// https://www.math3d.org/8FT8rbrDSw
		"0.0, 0.0, -5.0, -10.0, 0.0, -5.0, true",
		// LINE: [[10.0, 0.0, -5.0],[0.0, 0.0, -5.0]]
		// https://www.math3d.org/xCzujd23Mo
		"10.0, 0.0, -5.0, 0.0, 0.0, -5.0, true",
		// LINE: [[0.0, -10.0, -5.0],[0.0, 0.0, -5.0]]
		// https://www.math3d.org/1CV5LDPDza
		"0.0, -10.0, -5.0, 0.0, 0.0, -5.0, true",
		// LINE: [[0.0, 0.0, -5.0],[0.0, 10.0, -5.0]]
		// https://www.math3d.org/bhGE6cEvab
		"0.0, 0.0, -5.0, 0.0, 10.0, -5.0, true",
		// LINE: [[-10.0, 6.0, -5.0],[10.0, 6.0, -5.0]]
		// https://www.math3d.org/7DBnokoNPB
		"-10.0, 6.0, -5.0, 10.0, 6.0, -5.0, false",
		// LINE: [[-10.0, -10.0, -5.0],[10.0, 10.0, -5.0]]
		// https://www.math3d.org/K7q8ulCCHH
		"-10.0, -10.0, -5.0, 10.0, 10.0, -5.0, true",
		// LINE: [[0.0, 0.0, -5.0],[10.0, 0.0, -5.0]]
		// https://www.math3d.org/K7q8ulCCHH
		"0.0, 0.0, -5.0, 10.0, 0.0, -5.0, true",
		// LINE: [[0.0, 0.0, -5.0],[0.0, -10.0, -5.0]]
		// https://www.math3d.org/8Re4hlKZc9
		"0.0, 0.0, -5.0, 0.0, -10.0, -5.0, true",
		// LINE: [[0.0, 10.0, -5.0],[0.0, 0.0, -5.0]]
		// https://www.math3d.org/V8B7xXFGfH
		"0.0, 10.0, -5.0, 0.0, 0.0, -5.0, true",
		// LINE: [[0.0, 0.0, 2.0],[0.0, 0.0, -5.0]]
		// https://www.math3d.org/NrIgr4obyT
		"0.0, 0.0, 2.0, 0.0, 0.0, -5.0, true",
		// LINE: [[0.0, 0.0, -5.0],[0.0, 0.0, 2.0]]
		// https://www.math3d.org/n6m8tfjLYC
		"0.0, 0.0, -5.0, 0.0, 0.0, 2.0, true",
		// LINE: [[0.0, 0.0, -5.0],[0.0, 0.0, -150.0]]
		// https://www.math3d.org/TSG6aJmiGW
		"0.0, 0.0, -5.0, 0.0, 0.0, -150.0, true",
		// LINE: [[0.0, 0.0, -150.0],[0.0, 0.0, -5.0]]
		// https://www.math3d.org/pcjUKsVIDN
		"0.0, 0.0, -150.0, 0.0, 0.0, -5.0, true"
	})
	void testTestLineSegment(float aX, float aY, float aZ, float bX, float bY, float bZ, boolean expected) {
		// arrange
		Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
		FrustumIntersection c = new FrustumIntersection(m);
		
		// act
		boolean result = c.testLineSegment(aX, aY, aZ, bX, bY, bZ);
		
		// assert
		assertEquals(expected, result);
	}
    
    @ParameterizedTest
	@CsvSource({
		"-1.0, 0.0, -5.0, 1.0, 0.0, -5.0, true",
		"-10.0, 0.0, -5.0, -6.0, 0.0, -5.0, false",
		"6.0, 0.0, -5.0, 10.0, 0.0, -5.0, false",
		"0.0, -10.0, -5.0, 0.0, -6.0, -5.0, false",
		"0.0, 6.0, -5.0, 0.0, 10.0, -5.0, false",
		"0.0, 0.0, 0.0, 0.0, 0.0, 0.5, false",
		"0.0, 0.0, -110.0, 0.0, 0.0, -150.0, false",
		"-10.0, 0.0, -5.0, 0.0, 0.0, -5.0, true",
		"0.0, 0.0, -5.0, -10.0, 0.0, -5.0, true",
		"10.0, 0.0, -5.0, 0.0, 0.0, -5.0, true",
		"0.0, -10.0, -5.0, 0.0, 0.0, -5.0, true",
		"0.0, 0.0, -5.0, 0.0, 10.0, -5.0, true",
		"-10.0, 6.0, -5.0, 10.0, 6.0, -5.0, false",
		"-10.0, -10.0, -5.0, 10.0, 10.0, -5.0, true",
		"0.0, 0.0, -5.0, 10.0, 0.0, -5.0, true",
		"0.0, 0.0, -5.0, 0.0, -10.0, -5.0, true",
		"0.0, 10.0, -5.0, 0.0, 0.0, -5.0, true",
		"0.0, 0.0, 2.0, 0.0, 0.0, -5.0, true",
		"0.0, 0.0, -5.0, 0.0, 0.0, 2.0, true",
		"0.0, 0.0, -5.0, 0.0, 0.0, -150.0, true",
		"0.0, 0.0, -150.0, 0.0, 0.0, -5.0, true"
	})
	void testTestLineSegmentVectors(float aX, float aY, float aZ, float bX, float bY, float bZ, boolean expected) {
		// arrange
		Matrix4f m = new Matrix4f().perspective((float) Math.PI / 2.0f, 1.0f, 0.1f, 100.0f);
		FrustumIntersection c = new FrustumIntersection(m);
		Vector3f a = new Vector3f(aX, aY, aZ);
		Vector3f b = new Vector3f(bX, bY, bZ);
		
		// act
		boolean result = c.testLineSegment(a, b);
		
		// assert
		assertEquals(expected, result);
	}
    
}
