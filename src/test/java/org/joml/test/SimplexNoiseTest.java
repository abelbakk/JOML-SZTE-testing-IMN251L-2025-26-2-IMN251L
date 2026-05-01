package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.joml.SimplexNoise;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SimplexNoiseTest {

	@ParameterizedTest
	@CsvSource({
		"0.3, 0.2, 0.1",
		"0.3, 0.1, 0.2",
		"0.2, 0.1, 0.3",
		"0.1, 0.2, 0.3",
		"0.1, 0.3, 0.2",
		"0.2, 0.3, 0.1",
		"0.5, 0.5, 0.5",
		"0.99, 0.99, 0.99",
		"-0.1, -0.2, -0.3",
		"1.5, 1.2, 1.1",
		"-0.5, 0.5, -0.5"
	})
	void testNoise3D(float x, float y, float z) {
		// act
		float result = SimplexNoise.noise(x, y, z);
		
		// assert
		assertTrue(result >= -1.0f);
		assertTrue(result <= 1.0f);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0.2, 0.1",
		"0.1, 0.2",
		"0.5, 0.5",
		"0.99, 0.99",
		"-0.1, -0.2",
		"1.5, 0.2",
		"-0.5, 0.5",
		"0.57, 0.57",
		"0.01, 0.01"
	})
	void testNoise2D(float x, float y) {
		// act
		float result = SimplexNoise.noise(x, y);
		
		// assert
		assertTrue(result >= -1.0f);
		assertTrue(result <= 1.0f);
	}
	
}
