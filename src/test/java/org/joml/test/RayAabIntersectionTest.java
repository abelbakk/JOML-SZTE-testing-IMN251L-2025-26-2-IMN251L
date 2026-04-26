/*
 * The MIT License
 *
 * Copyright (c) 2016-2026 JOML.
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

import org.joml.RayAabIntersection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link RayAabIntersection}.
 * 
 * @author Kai Burjack
 */
class RayAabIntersectionTest {

    @Test
    void testPX() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0, 0, 1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testPY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, -1, 0, 0, 1, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testPZ() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 0, -1, 0, 0, 1);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testNX() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(1, 0, 0, -1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testNY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 1, 0, 0, -1, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testNZ() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(0, 0, 1, 0, 0, -1);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testPXPY() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -1, 0, 1, 1, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testPXEdge() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0.5f, 0, 1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testPXEdgeDelta() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, 0.500001f, 0, 1, 0, 0);
        assertFalse(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testNXEdge() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -0.5f, 0, 1, 0, 0);
        assertTrue(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }

    @Test
    void testNXEdgeDelta() {
        RayAabIntersection r = new RayAabIntersection();
        r.set(-1, -0.500001f, 0, 1, 0, 0);
        assertFalse(r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
    }
    
    @ParameterizedTest
    @CsvSource({
        "-1.0, -1.0, -1.0", // MMM
        "-1.0, -1.0, 0.0", // MMO
        "-1.0, -1.0, 1.0", // MMP
        "-1.0, 0.0, -1.0", // MOM
        "-1.0, 0.0, 0.0", // MOO
        "-1.0, 0.0, 1.0", // MOP
        "-1.0, 1.0, -1.0", // MPM
        "-1.0, 1.0, 0.0", // MPO
        "-1.0, 1.0, 1.0", // MPP
        " 0.0, -1.0, -1.0", // OMM
        " 0.0, -1.0, 0.0", // OMO
        " 0.0, -1.0, 1.0", // OMP
        " 0.0, 0.0, -1.0", // OOM
        " 0.0, 0.0, 1.0", // OOP
        " 0.0, 1.0, -1.0", // OPM
        " 0.0, 1.0, 0.0", // OPO
        " 0.0, 1.0, 1.0", // OPP
        " 1.0, -1.0, -1.0", // PMM
        " 1.0, -1.0, 0.0", // PMO
        " 1.0, -1.0, 1.0", // PMP
        " 1.0, 0.0, -1.0", // POM
        " 1.0, 0.0, 0.0", // POO
        " 1.0, 0.0, 1.0", // POP
        " 1.0, 1.0, -1.0", // PPM
        " 1.0, 1.0, 0.0", // PPO
        " 1.0, 1.0, 1.0" // PPP
    })
    void testBoxOverlapTestsTrue(float dirX, float dirY, float dirZ) {
    	// arrange
    	RayAabIntersection r = new RayAabIntersection(-dirX, -dirY, -dirZ, dirX, dirY, dirZ);
    	
    	// act
    	boolean result = r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    	
    	// assert
    	assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({
        "-1.0, -1.0, -1.0", // MMM
        "-1.0, -1.0, 0.0", // MMO
        "-1.0, -1.0, 1.0", // MMP
        "-1.0, 0.0, -1.0", // MOM
        "-1.0, 0.0, 0.0", // MOO
        "-1.0, 0.0, 1.0", // MOP
        "-1.0, 1.0, -1.0", // MPM
        "-1.0, 1.0, 0.0", // MPO
        "-1.0, 1.0, 1.0", // MPP
        " 0.0, -1.0, -1.0", // OMM
        " 0.0, -1.0, 0.0", // OMO
        " 0.0, -1.0, 1.0", // OMP
        " 0.0, 0.0, -1.0", // OOM
        " 0.0, 0.0, 1.0", // OOP
        " 0.0, 1.0, -1.0", // OPM
        " 0.0, 1.0, 0.0", // OPO
        " 0.0, 1.0, 1.0", // OPP
        " 1.0, -1.0, -1.0", // PMM
        " 1.0, -1.0, 0.0", // PMO
        " 1.0, -1.0, 1.0", // PMP
        " 1.0, 0.0, -1.0", // POM
        " 1.0, 0.0, 0.0", // POO
        " 1.0, 0.0, 1.0", // POP
        " 1.0, 1.0, -1.0", // PPM
        " 1.0, 1.0, 0.0", // PPO
        " 1.0, 1.0, 1.0" // PPP
    })
    void testBoxOverlapTestsFailWhenStartingPastTarget(float dirX, float dirY, float dirZ) {
    	// arrange
    	RayAabIntersection r = new RayAabIntersection(dirX * 1.5f, dirY * 1.5f, dirZ * 1.5f, dirX, dirY, dirZ);
    	
    	// act
    	boolean result = r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    	
    	// assert
    	assertFalse(result);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-1.0, -1.0, -1.0", // MMM
        "-1.0, -1.0, 0.0", // MMO
        "-1.0, -1.0, 1.0", // MMP
        "-1.0, 0.0, -1.0", // MOM
        "-1.0, 0.0, 1.0", // MOP
        "-1.0, 1.0, -1.0", // MPM
        "-1.0, 1.0, 0.0", // MPO
        "-1.0, 1.0, 1.0", // MPP
        " 0.0, -1.0, -1.0", // OMM
        " 0.0, -1.0, 0.0", // OMO
        " 0.0, -1.0, 1.0", // OMP
        " 0.0, 0.0, -1.0", // OOM
        " 0.0, 0.0, 1.0", // OOP
        " 0.0, 1.0, -1.0", // OPM
        " 0.0, 1.0, 0.0", // OPO
        " 0.0, 1.0, 1.0", // OPP
        " 1.0, -1.0, -1.0", // PMM
        " 1.0, -1.0, 0.0", // PMO
        " 1.0, -1.0, 1.0", // PMP
        " 1.0, 0.0, -1.0", // POM
        " 1.0, 0.0, 1.0", // POP
        " 1.0, 1.0, -1.0", // PPM
        " 1.0, 1.0, 0.0", // PPO
        " 1.0, 1.0, 1.0" // PPP
    })
    void testBoxOverlapTestsFailWhenXPastTarget(float dirX, float dirY, float dirZ) {
    	// arrange
    	RayAabIntersection r = new RayAabIntersection(-dirX + 1.1f, -dirY, -dirZ, dirX, dirY, dirZ);
    	
    	// act
    	boolean result = r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    	
    	// assert
    	assertFalse(result);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-1.0, -1.0, -1.0", // MMM
        "-1.0, -1.0, 0.0", // MMO
        "-1.0, -1.0, 1.0", // MMP
        "-1.0, 0.0, -1.0", // MOM
        "-1.0, 0.0, 0.0", // MOO
        "-1.0, 0.0, 1.0", // MOP
        "-1.0, 1.0, -1.0", // MPM
        "-1.0, 1.0, 0.0", // MPO
        "-1.0, 1.0, 1.0", // MPP
        " 0.0, -1.0, -1.0", // OMM
        " 0.0, -1.0, 1.0", // OMP
        " 0.0, 0.0, -1.0", // OOM
        " 0.0, 0.0, 1.0", // OOP
        " 0.0, 1.0, -1.0", // OPM
        " 0.0, 1.0, 1.0", // OPP
        " 1.0, -1.0, -1.0", // PMM
        " 1.0, -1.0, 0.0", // PMO
        " 1.0, -1.0, 1.0", // PMP
        " 1.0, 0.0, -1.0", // POM
        " 1.0, 0.0, 0.0", // POO
        " 1.0, 0.0, 1.0", // POP
        " 1.0, 1.0, -1.0", // PPM
        " 1.0, 1.0, 0.0", // PPO
        " 1.0, 1.0, 1.0" // PPP
    })
    void testBoxOverlapTestsFailWhenYPastTarget(float dirX, float dirY, float dirZ) {
    	// arrange
    	RayAabIntersection r = new RayAabIntersection(-dirX, -dirY + 1.1f, -dirZ, dirX, dirY, dirZ);
    	
    	// act
    	boolean result = r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    	
    	// assert
    	assertFalse(result);
    }
    
    @ParameterizedTest
    @CsvSource({
        "-1.0, -1.0, -1.0", // MMM
        "-1.0, -1.0, 0.0", // MMO
        "-1.0, -1.0, 1.0", // MMP
        "-1.0, 0.0, -1.0", // MOM
        "-1.0, 0.0, 0.0", // MOO
        "-1.0, 0.0, 1.0", // MOP
        "-1.0, 1.0, -1.0", // MPM
        "-1.0, 1.0, 0.0", // MPO
        "-1.0, 1.0, 1.0", // MPP
        " 0.0, -1.0, -1.0", // OMM
        " 0.0, -1.0, 0.0", // OMO
        " 0.0, -1.0, 1.0", // OMP
        " 0.0, 1.0, -1.0", // OPM
        " 0.0, 1.0, 0.0", // OPO
        " 0.0, 1.0, 1.0", // OPP
        " 1.0, -1.0, -1.0", // PMM
        " 1.0, -1.0, 0.0", // PMO
        " 1.0, -1.0, 1.0", // PMP
        " 1.0, 0.0, -1.0", // POM
        " 1.0, 0.0, 0.0", // POO
        " 1.0, 0.0, 1.0", // POP
        " 1.0, 1.0, -1.0", // PPM
        " 1.0, 1.0, 0.0", // PPO
        " 1.0, 1.0, 1.0" // PPP
    })
    void testBoxOverlapTestsFailWhenZPastTarget(float dirX, float dirY, float dirZ) {
    	// arrange
    	RayAabIntersection r = new RayAabIntersection(-dirX, -dirY, -dirZ + 1.1f, dirX, dirY, dirZ);
    	
    	// act
    	boolean result = r.test(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    	
    	// assert
    	assertFalse(result);
    }
    
}
