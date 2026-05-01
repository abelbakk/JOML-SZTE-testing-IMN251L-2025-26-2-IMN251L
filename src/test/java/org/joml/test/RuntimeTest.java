package org.joml.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.NumberFormat;
import java.util.Locale;

import org.joml.Runtime;

class RuntimeTest {

	@Test
	void testFormatNumbers() {
		// act & assert
		assertEquals("1.0E+2", Runtime.formatNumbers("1.0E 2"));
		assertEquals("1.0E+2", Runtime.formatNumbers("1.0E2"));
		assertEquals("123", Runtime.formatNumbers("123"));
	}

	@Test
	void testFormat() {
		// arrange
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		
		// act & assert
		assertTrue(Runtime.format(Double.NaN, nf).endsWith("NaN"));
		assertTrue(Runtime.format(Double.POSITIVE_INFINITY, nf).endsWith("+Inf"));
		assertTrue(Runtime.format(Double.NEGATIVE_INFINITY, nf).endsWith("-Inf"));
		assertEquals("1.5", Runtime.format(1.5, nf));
	}

	@Test
	void testEqualsFloat() {
		// act & assert
		assertTrue(Runtime.equals(1.0f, 1.0f, 0.0f));
		assertTrue(Runtime.equals(1.0f, 1.1f, 0.2f));
		assertFalse(Runtime.equals(1.0f, 1.5f, 0.2f));
	}

	@Test
	void testEqualsDouble() {
		// act & assert
		assertTrue(Runtime.equals(1.0, 1.0, 0.0));
		assertTrue(Runtime.equals(1.0, 1.1, 0.2));
		assertFalse(Runtime.equals(1.0, 1.5, 0.2));
	}
	
}
