package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

import org.joml.Options;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class OptionsTest {

	@Test
	void testDefaultOptions() {
		assertEquals(false, Options.DEBUG);
		assertEquals(false, Options.NO_UNSAFE);
		assertEquals(true, Options.INTERNAL_UNSAFE);
		assertEquals(false, Options.FORCE_UNSAFE);
		assertEquals(false, Options.FASTMATH);
		assertEquals(false, Options.SIN_LOOKUP);
		assertEquals(14, Options.SIN_LOOKUP_BITS);
		assertEquals(true, Options.useNumberFormat);
		assertEquals(false, Options.USE_MATH_FMA);
		assertEquals(3, Options.numberFormatDecimals);
		assertEquals(" 0.000E0;-0.000E0", ((DecimalFormat) Options.NUMBER_FORMAT).toPattern());
	}
	
	
	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "false", "something" })
	void testHasOptionFalse(String option) throws Exception {
		// arrange
		Method hasOptionMethod = Options.class.getDeclaredMethod("hasOption", String.class);
		hasOptionMethod.setAccessible(true);
		
		// act
		boolean result = (boolean) hasOptionMethod.invoke(null, option);
		
		// assert
		assertEquals(false, result);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "", "  ", "true", "tRuE" })
	void testHasOptionsTrue(String option) throws Exception {
		// arrange
		Method hasOptionMethod = Options.class.getDeclaredMethod("hasOption", String.class);
		hasOptionMethod.setAccessible(true);
		
		// act
		boolean result = (boolean) hasOptionMethod.invoke(null, option);
		
		// assert
		assertEquals(true, result);
	}
	
}
