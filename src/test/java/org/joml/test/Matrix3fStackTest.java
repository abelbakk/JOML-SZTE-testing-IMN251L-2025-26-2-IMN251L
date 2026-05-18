/*
 * The MIT License
 *
 * Copyright (c) 2016-2026  JOML.
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

import org.joml.Matrix3f;
import org.joml.Matrix3fStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Matrix3fStackTest {
    @Test
    void testPushPop() {
        Matrix3f identity = new Matrix3f();
        Matrix3fStack m = new Matrix3fStack(2);
        m.pushMatrix();
        m.scale(2);
        assertNotEquals(identity, m);
        m.popMatrix();
        assertEquals(identity, m);
    }

    @Test
    void testPushTooFar() {
        assertThrows(IllegalStateException.class, () -> {
            Matrix3fStack m = new Matrix3fStack(2);
            m.pushMatrix();
            m.pushMatrix();
        });
    }

    @Test
    void testPopTooFar() {
        assertThrows(IllegalStateException.class, () -> {
            Matrix3fStack m = new Matrix3fStack(2);
            m.pushMatrix();
            m.popMatrix();
            m.popMatrix();
        });
    }

    @Test
    void testEquals() {
        Matrix3fStack s1 = new Matrix3fStack(3);
        Matrix3fStack s2 = new Matrix3fStack(1);
        Matrix3fStack s3 = new Matrix3fStack(2);
        Matrix3fStack s4 = new Matrix3fStack(2);
        s4.scale(2);
        Matrix3fStack s5 = new Matrix3fStack(2);
        s5.pushMatrix();
        Matrix3fStack s6 = new Matrix3fStack(2);
        s6.pushMatrix();
        s6.scale(2);
        s6.popMatrix();
        Matrix3f m1 = new Matrix3f();
        Matrix3f m2 = new Matrix3f().scale(2);
        
        // Matrix3fStack.equals(Matrix3f) only compares the 9 matrix elements
        assertEquals(s1, m1);
        assertEquals(s2, m1);
        assertNotEquals(s1, m2);
        assertEquals(s4, m2);

        // Matrix3fStack.equals(Matrix3fStack) compares the 9 matrix elements
        // and all matrices from the bottom to the current/top of the stack.
        assertEquals(s1, s2);
        assertEquals(s1, s3);
        assertNotEquals(s1, s4);
        assertNotEquals(s3, s5);
        assertEquals(s3, s6);
    }

    @Test
    void testClear() {
        Matrix3fStack m = new Matrix3fStack(3);
        m.scale(5);
        m.pushMatrix();
        m.scale(2);
        m.clear();
        assertEquals(new Matrix3f(), m);
    }

    @Test
    void testStackSize() {
        assertThrows(IllegalArgumentException.class, () -> new Matrix3fStack(0));
        assertThrows(IllegalArgumentException.class, () -> new Matrix3fStack(-1));
        
        Matrix3fStack m1 = new Matrix3fStack(1);
        assertEquals(m1, new Matrix3f());
        
        Matrix3fStack m5 = new Matrix3fStack(5);
        assertEquals(m5, new Matrix3f());
    }
}
