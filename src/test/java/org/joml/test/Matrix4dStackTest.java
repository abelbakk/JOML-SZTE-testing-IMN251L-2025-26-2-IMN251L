package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.joml.Matrix4d;
import org.joml.Matrix4dStack;
import org.junit.jupiter.api.Test;

class Matrix4dStackTest {

	@Test
	void testEqualsNullOrDifferentType() {
		// arrange
		Matrix4dStack s = new Matrix4dStack(2);

		// act & assert
		assertFalse(s.equals(null));
		assertFalse(s.equals(new Object()));
	}
	
	@Test
	void testEqualsSelf() {
		// arrange
		Matrix4dStack s = new Matrix4dStack(1);
		
		// act & assert
		assertTrue(s.equals(s));
	}

	@Test
	void testEqualsElementsEqual() {
		// arrange
		Matrix4dStack s = new Matrix4dStack(2);
		s.translate(1.0, 2.0, 3.0);
		
		Matrix4d m = new Matrix4d();
		m.translate(1.0, 2.0, 3.0);

		// act & assert
		assertTrue(s.equals(m));
	}

	@Test
	void testEqualsElementsDiffer() {
		// arrange
		Matrix4dStack s = new Matrix4dStack(2);
		s.translate(1.0, 2.0, 3.0);
		Matrix4d m = new Matrix4d();

		// act & assert
		assertFalse(s.equals(m));
	}

	@Test
	void testEqualsAllEqual() {
		// arrange
		Matrix4dStack s1 = new Matrix4dStack(3);
		s1.translate(1.0, 2.0, 3.0);
		s1.pushMatrix();
		Matrix4dStack s2 = new Matrix4dStack(3);
		s2.translate(1.0, 2.0, 3.0);
		s2.pushMatrix();

		// act & assert
		assertTrue(s1.equals(s2));
	}

	@Test
	void testEqualsPointerDiffers() {
		// arrange
		Matrix4dStack s1 = new Matrix4dStack(3);
		s1.pushMatrix();
		Matrix4dStack s2 = new Matrix4dStack(3);

		// act & assert
		assertFalse(s1.equals(s2));
	}

	@Test
	void testEqualsArraysDiffer() {
		// arrange
		Matrix4dStack s1 = new Matrix4dStack(3);
		s1.translate(1.0, 2.0, 3.0); 
		s1.pushMatrix();             
		s1.identity();               
		Matrix4dStack s2 = new Matrix4dStack(3);
		s2.translate(4.0, 5.0, 6.0); 
		s2.pushMatrix();             
		s2.identity();               

		// act & assert
		assertFalse(s1.equals(s2));
	}
	
}
