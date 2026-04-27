/*
 * The MIT License
 *
 * Copyright (c) 2015-2026  Richard Greenlees
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link GeometryUtils} class.
 *
 * @author Jarosław Piotrowski
 */
class GeometryUtilsTest {
	
	private static final float DELTA = 1E-5f;
	
    @Test
    void testTangents() {
        Vector3f pos1 = new Vector3f(-1.0f,  1.0f, 0.0f);
        Vector3f pos2 = new Vector3f( 1.0f, -1.0f, 0.0f);
        Vector3f pos3 = new Vector3f( 1.0f,  1.0f, 0.0f);

        Vector2f uv1 = new Vector2f(0.0f, 1.0f);
        Vector2f uv2 = new Vector2f(1.0f, 0.0f);
        Vector2f uv3 = new Vector2f(1.0f, 1.0f);

        Vector3f t = new Vector3f(1, 0, 0);
        Vector3f b = new Vector3f(0, 1, 0);

        Vector3f tangent = new Vector3f();
        Vector3f bitangent = new Vector3f();

        GeometryUtils.tangent(pos1, uv1, pos2, uv2, pos3, uv3, tangent);
        assertEquals(t, tangent);

        GeometryUtils.bitangent(pos1, uv1, pos2, uv2, pos3, uv3, bitangent);
        assertEquals(b, bitangent);

        GeometryUtils.tangentBitangent(pos1, uv1, pos2, uv2, pos3, uv3, tangent, bitangent);
        assertEquals(t, tangent);
        assertEquals(b, bitangent);
    }
    
    // https://www.wolframalpha.com/input?i=plane+through+%28-1.2%2C+-3.4%2C+-9.4%29%2C+%286.7%2C-5.2%2C4.9%29%2C+%2823.1%2C4.2%2C0.5%29
    @Test
    void testNormal() {
    	// arrange
    	Vector3f pos1 = new Vector3f(-1.2f, -3.4f, -9.4f);
    	Vector3f pos2 = new Vector3f(6.7f, -5.2f, 4.9f);
    	Vector3f pos3 = new Vector3f(23.1f, 4.2f, 0.5f);
    	Vector3f dest1 = new Vector3f();
    	Vector3f dest2 = new Vector3f();
    	Vector3f expected = new Vector3f(-0.401467f, 0.854602f, 0.329362f);
    	
    	// act
    	GeometryUtils.normal(pos1, pos2, pos3, dest1);
    	GeometryUtils.normal(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z, pos3.x, pos3.y, pos3.z, dest2);
    	
    	// assert
    	TestUtil.assertVector3fEquals(expected, dest1, DELTA);
    	TestUtil.assertVector3fEquals(expected, dest2, DELTA);
    	TestUtil.assertVector3fEquals(dest1, dest2, DELTA);
    }
    
    @ParameterizedTest
    @CsvSource({
    	"0.0, 1.0, 1.0",
    	"1.0, 0.0, 1.0",
    	"1.0, 1.0, 0.0",
    	"1.0, 2.0, 0.0"
    })
    void testPerpendicular(float x, float y, float z) {
    	// arrange
    	Vector3f v = new Vector3f(x, y, z).normalize();
    	Vector3f dest11 = new Vector3f();
    	Vector3f dest12 = new Vector3f();
    	Vector3f dest21 = new Vector3f();
    	Vector3f dest22 = new Vector3f();
    	
    	// act
    	GeometryUtils.perpendicular(v, dest11, dest12);
    	GeometryUtils.perpendicular(v.x, v.y, v.z, dest21, dest22);
    	
    	// assert
    	// normalized outputs
    	assertEquals(1.0f, dest11.length(), DELTA);
    	assertEquals(1.0f, dest12.length(), DELTA);
    	assertEquals(1.0f, dest21.length(), DELTA);
    	assertEquals(1.0f, dest22.length(), DELTA);
    	// perpendicular outputs (to v and each other)
    	assertEquals(0.0f, v.dot(dest11), DELTA);
    	assertEquals(0.0f, v.dot(dest12), DELTA);
    	assertEquals(0.0f, dest11.dot(dest12));
    	assertEquals(0.0f, v.dot(dest21), DELTA);
    	assertEquals(0.0f, v.dot(dest22), DELTA);
    	assertEquals(0.0f, dest21.dot(dest22), DELTA);
    }
}
