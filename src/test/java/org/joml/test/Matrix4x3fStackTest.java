package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.joml.Matrix4x3f;
import org.joml.Matrix4x3fStack;
import org.junit.jupiter.api.Test;

class Matrix4x3fStackTest {

	@Test
	void testEqualsNullOrDifferentType() {
		// arrange
		Matrix4x3fStack s = new Matrix4x3fStack(2);

		// act & assert
		assertFalse(s.equals(null));
		assertFalse(s.equals(new Object()));
	}
	
	@Test
	void testEqualsSelf() {
		// arrange
		Matrix4x3fStack s = new Matrix4x3fStack(1);
		
		// act & assert
		assertTrue(s.equals(s));
	}

	@Test
	void testEqualsElementsEqual() {
		// arrange
		Matrix4x3fStack s = new Matrix4x3fStack(2);
		s.translate(1.0f, 2.0f, 3.0f);
		
		Matrix4x3f m = new Matrix4x3f();
		m.translate(1.0f, 2.0f, 3.0f);

		// act & assert
		assertTrue(s.equals(m));
	}

	@Test
	void testEqualsElementsDiffer() {
		// arrange
		Matrix4x3fStack s = new Matrix4x3fStack(2);
		s.translate(1.0f, 2.0f, 3.0f);
		Matrix4x3f m = new Matrix4x3f();

		// act & assert
		assertFalse(s.equals(m));
	}

	@Test
	void testEqualsAllEqual() {
		// arrange
		Matrix4x3fStack s1 = new Matrix4x3fStack(3);
		s1.translate(1.0f, 2.0f, 3.0f);
		s1.pushMatrix();
		Matrix4x3fStack s2 = new Matrix4x3fStack(3);
		s2.translate(1.0f, 2.0f, 3.0f);
		s2.pushMatrix();

		// act & assert
		assertTrue(s1.equals(s2));
	}

	@Test
	void testEqualsPointerDiffers() {
		// arrange
		Matrix4x3fStack s1 = new Matrix4x3fStack(3);
		s1.pushMatrix();
		Matrix4x3fStack s2 = new Matrix4x3fStack(3);

		// act & assert
		assertFalse(s1.equals(s2));
	}

	@Test
	void testEqualsArraysDiffer() {
		// arrange
		Matrix4x3fStack s1 = new Matrix4x3fStack(3);
		s1.translate(1.0f, 2.0f, 3.0f); 
		s1.pushMatrix();             
		s1.identity();               
		Matrix4x3fStack s2 = new Matrix4x3fStack(3);
		s2.translate(4.0f, 5.0f, 6.0f); 
		s2.pushMatrix();             
		s2.identity();               

		// act & assert
		assertFalse(s1.equals(s2));
	}
	
}
