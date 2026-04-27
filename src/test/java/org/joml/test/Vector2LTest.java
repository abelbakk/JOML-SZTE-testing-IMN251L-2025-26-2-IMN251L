package org.joml.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.text.NumberFormat;
import java.util.Locale;

import org.joml.Vector2L;
import org.joml.Vector2Lc;
import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class Vector2LTest {

	@Test
	void testVector2L() {
		// act
		Vector2L v = new Vector2L();
		
		// assert
		assertEquals(0l, v.x);
		assertEquals(0l, v.y);
	}
	
	@Test
	void testVector2LOneArg() {
		// arrange
		long value = 10l;
		
		// act
		Vector2L v = new Vector2L(value);
		
		// assert
		assertEquals(value, v.x);
		assertEquals(value, v.y);
	}
	
	@Test
	void testVector2LTwoArgs() {
		// arrange
		long v1 = 10l;
		long v2 = 20l;
		
		// act
		Vector2L v = new Vector2L(v1, v2);
		
		// assert
		assertEquals(v1, v.x);
		assertEquals(v2, v.y);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1.9f, -1.9f, 0, 1, -1",
		"1.1f, -1.9f, 1, 2, -1",
		"1.9f, -1.1f, 2, 1, -2",
		"1.5f, 2.5f, 3, 2, 2",
		"1.5f, 2.5f, 4, 1, 2",
		"1.5f, 2.5f, 5, 2, 3"
	})
	void testVector2LThreeArgsFloat(float v1, float v2, int mode, long expectedV1, long expectedV2) {
		// act
		Vector2L v = new Vector2L(v1, v2, mode);
		
		// assert
		assertEquals(expectedV1, v.x);
		assertEquals(expectedV2, v.y);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1.9, -1.9, 0, 1, -1",
		"1.1, -1.9, 1, 2, -1",
		"1.9, -1.1, 2, 1, -2",
		"1.5, 2.5, 3, 2, 2",
		"1.5, 2.5, 4, 1, 2",
		"1.5, 2.5, 5, 2, 3"
	})
	void testVector2LThreeArgsDouble(double v1, double v2, int mode, long expectedV1, long expectedV2) {
		// act
		Vector2L v = new Vector2L(v1, v2, mode);
		
		// assert
		assertEquals(expectedV1, v.x);
		assertEquals(expectedV2, v.y);
	}
	
	@Test
	void testVector2LFromVector2Lc() {
		// arrange
		Vector2L lc = new Vector2L(10L, 20L);
		
		// act
		Vector2L v = new Vector2L((Vector2Lc) lc);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
	}

	@Test
	void testVector2LFromVector2ic() {
		// arrange
		Vector2i ic = new Vector2i(10, 20);
		
		// act
		Vector2L v = new Vector2L((Vector2ic) ic);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1.9f, -1.9f, 0, 1, -1",
		"1.1f, -1.9f, 1, 2, -1",
		"1.9f, -1.1f, 2, 1, -2",
		"1.5f, 2.5f, 3, 2, 2",
		"1.5f, 2.5f, 4, 1, 2",
		"1.5f, 2.5f, 5, 2, 3"
	})
	void testVector2LFromVector2fc(float v1, float v2, int mode, long expectedV1, long expectedV2) {
		// arrange
		Vector2f fc = new Vector2f(v1, v2);
		
		// act
		Vector2L v = new Vector2L((Vector2fc) fc, mode);
		
		// assert
		assertEquals(expectedV1, v.x);
		assertEquals(expectedV2, v.y);
	}

	@ParameterizedTest
	@CsvSource({
		"1.9, -1.9, 0, 1, -1",
		"1.1, -1.9, 1, 2, -1",
		"1.9, -1.1, 2, 1, -2",
		"1.5, 2.5, 3, 2, 2",
		"1.5, 2.5, 4, 1, 2",
		"1.5, 2.5, 5, 2, 3"
	})
	void testVector2LFromVector2dc(double v1, double v2, int mode, long expectedV1, long expectedV2) {
		// arrange
		Vector2d dc = new Vector2d(v1, v2);
		
		// act
		Vector2L v = new Vector2L((Vector2dc) dc, mode);
		
		// assert
		assertEquals(expectedV1, v.x);
		assertEquals(expectedV2, v.y);
	}
	
	@Test
	void testVector2LFromArray() {
		// arrange
		long[] arr = { 10L, 20L, 30L };
		
		// act
		Vector2L v = new Vector2L(arr);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
	}
	
	@Test
	void testVector2LFromByteBuffer() {
		// arrange
		ByteBuffer buffer = ByteBuffer.allocate(3 * Long.BYTES);
		buffer.putLong(1L);
		int position = buffer.position();
		buffer.putLong(2L);
		buffer.putLong(3L);
		buffer.position(position);
		
		// act
		Vector2L v = new Vector2L(buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
		assertEquals(position, buffer.position());
	}
	
	@Test
	void testVector2LFromByteBufferIndex() {
		// arrange
		ByteBuffer buffer = ByteBuffer.allocate(3 * Long.BYTES);
		buffer.putLong(0, 1L);
		buffer.putLong(Long.BYTES, 2L);
		buffer.putLong(2 * Long.BYTES, 3L);
		
		// act
		Vector2L v = new Vector2L(8, buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
	}
	
	@Test
	void testVector2LFromLongBuffer() {
		// arrange
		LongBuffer buffer = LongBuffer.allocate(3);
		buffer.put(1L);
		int position = buffer.position();
		buffer.put(2L);
		buffer.put(3L);
		buffer.position(position);
		
		// act
		Vector2L v = new Vector2L(buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
		assertEquals(position, buffer.position());
	}

	@Test
	void testVector2LFromLongBufferIndex() {
		// arrange
		LongBuffer buffer = LongBuffer.allocate(3);
		buffer.put(0, 1L);
		buffer.put(1, 2L);
		buffer.put(2, 3L);
		
		// act
		Vector2L v = new Vector2L(1, buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
	}
	
	@Test
	void testSetOneArg() {
		// arrange
		long value = 10L;
		Vector2L v = new Vector2L();
		
		// act
		v.set(value);
		
		// assert
		assertEquals(value, v.x);
		assertEquals(value, v.y);
	}
	
	@Test
	void testSetTwoArgs() {
		// arrange
		long v1 = 10L;
		long v2 = 20L;
		Vector2L v = new Vector2L();
		
		// act
		v.set(v1, v2);
		
		// assert
		assertEquals(v1, v.x);
		assertEquals(v2, v.y);
	}

	@Test
	void testSetFromVector2Lc() {
		// arrange
		Vector2L lc = new Vector2L(10L, 20L);
		Vector2L v = new Vector2L();
		
		// act
		v.set((Vector2Lc) lc);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
	}

	@Test
	void testSetFromVector2ic() {
		// arrange
		Vector2i ic = new Vector2i(10, 20);
		Vector2L v = new Vector2L();
		
		// act
		v.set((Vector2ic) ic);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
	}

	@Test
	void testSetFromVector2dc() {
		// arrange
		Vector2d dc = new Vector2d(1.9, -1.9);
		Vector2L v = new Vector2L();
		
		// act
		v.set((Vector2dc) dc);
		
		// assert
		assertEquals(1L, v.x);
		assertEquals(-1L, v.y);
	}

	@ParameterizedTest
	@CsvSource({
		"1.9, -1.9, 0, 1, -1",
		"1.1, -1.9, 1, 2, -1",
		"1.9, -1.1, 2, 1, -2",
		"1.5, 2.5, 3, 2, 2",
		"1.5, 2.5, 4, 1, 2",
		"1.5, 2.5, 5, 2, 3"
	})
	void testSetFromVector2dcWithMode(double v1, double v2, int mode, long expectedV1, long expectedV2) {
		// arrange
		Vector2d dc = new Vector2d(v1, v2);
		Vector2L v = new Vector2L();
		
		// act
		v.set((Vector2dc) dc, mode);
		
		// assert
		assertEquals(expectedV1, v.x);
		assertEquals(expectedV2, v.y);
	}

	@ParameterizedTest
	@CsvSource({
		"1.9f, -1.9f, 0, 1, -1",
		"1.1f, -1.9f, 1, 2, -1",
		"1.9f, -1.1f, 2, 1, -2",
		"1.5f, 2.5f, 3, 2, 2",
		"1.5f, 2.5f, 4, 1, 2",
		"1.5f, 2.5f, 5, 2, 3"
	})
	void testSetFromVector2fcWithMode(float v1, float v2, int mode, long expectedV1, long expectedV2) {
		// arrange
		Vector2f fc = new Vector2f(v1, v2);
		Vector2L v = new Vector2L();
		
		// act
		v.set((Vector2fc) fc, mode);
		
		// assert
		assertEquals(expectedV1, v.x);
		assertEquals(expectedV2, v.y);
	}

	@Test
	void testSetFromArray() {
		// arrange
		long[] arr = { 10L, 20L, 30L };
		Vector2L v = new Vector2L();
		
		// act
		v.set(arr);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
	}

	@Test
	void testSetFromByteBuffer() {
		// arrange
		ByteBuffer buffer = ByteBuffer.allocate(3 * Long.BYTES);
		buffer.putLong(1L);
		int position = buffer.position();
		buffer.putLong(2L);
		buffer.putLong(3L);
		buffer.position(position);
		Vector2L v = new Vector2L();
		
		// act
		v.set(buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
		assertEquals(position, buffer.position());
	}

	@Test
	void testSetFromByteBufferIndex() {
		// arrange
		ByteBuffer buffer = ByteBuffer.allocate(3 * Long.BYTES);
		buffer.putLong(0, 1L);
		buffer.putLong(Long.BYTES, 2L);
		buffer.putLong(2 * Long.BYTES, 3L);
		Vector2L v = new Vector2L();
		
		// act
		v.set(8, buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
	}

	@Test
	void testSetFromLongBuffer() {
		// arrange
		LongBuffer buffer = LongBuffer.allocate(3);
		buffer.put(1L);
		int position = buffer.position();
		buffer.put(2L);
		buffer.put(3L);
		buffer.position(position);
		Vector2L v = new Vector2L();
		
		// act
		v.set(buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
		assertEquals(position, buffer.position());
	}

	@Test
	void testSetFromLongBufferIndex() {
		// arrange
		LongBuffer buffer = LongBuffer.allocate(3);
		buffer.put(0, 1L);
		buffer.put(1, 2L);
		buffer.put(2, 3L);
		Vector2L v = new Vector2L();
		
		// act
		v.set(1, buffer);
		
		// assert
		assertEquals(2L, v.x);
		assertEquals(3L, v.y);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 10",
		"1, 20"
	})
	void testGet(int component, long expected) {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		long result = v.get(component);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@ValueSource(ints = { -1, 2 })
	void testGetThrows(int component) {
		// arrange
		Vector2L v = new Vector2L();
		
		// act & assert
		assertThrows(IllegalArgumentException.class, () -> v.get(component));
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, 1, 1, 20",
		"1, 1, 10, 1"
	})
	void testSetComponent(int component, long value, long expectedV1, long expectedV2) {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.setComponent(component, value);
		
		// assert
		assertEquals(expectedV1, v.x);
		assertEquals(expectedV2, v.y);
	}

	@ParameterizedTest
	@ValueSource(ints = { -1, 2 })
	void testSetComponentThrows(int component) {
		// arrange
		Vector2L v = new Vector2L();
		
		// act & assert
		assertThrows(IllegalArgumentException.class, () -> v.setComponent(component, 1L));
	}
	
	@Test
	void testGetByteBuffer() {
		// arrange
		Vector2L v = new Vector2L(1L, 2L);
		ByteBuffer buffer = ByteBuffer.allocate(3 * Long.BYTES);
		buffer.position(Long.BYTES);
		
		// act
		v.get(buffer);
		
		// assert
		assertEquals(1L, buffer.getLong(Long.BYTES));
		assertEquals(2L, buffer.getLong(2 * Long.BYTES));
	}
	
	@Test
	void testGetByteBufferIndex() {
		// arrange
		Vector2L v = new Vector2L(1L, 2L);
		ByteBuffer buffer = ByteBuffer.allocate(3 * Long.BYTES);
		
		// act
		v.get(Long.BYTES, buffer);
		
		// assert
		assertEquals(1L, buffer.getLong(Long.BYTES));
		assertEquals(2L, buffer.getLong(2 * Long.BYTES));
	}

	@Test
	void testGetLongBuffer() {
		// arrange
		Vector2L v = new Vector2L(1L, 2L);
		LongBuffer buffer = LongBuffer.allocate(3);
		buffer.position(1);
		
		// act
		v.get(buffer);
		
		// assert
		assertEquals(1L, buffer.get(1));
		assertEquals(2L, buffer.get(2));
	}

	@Test
	void testGetLongBufferIndex() {
		// arrange
		Vector2L v = new Vector2L(1L, 2L);
		LongBuffer buffer = LongBuffer.allocate(3);
		
		// act
		v.get(1, buffer);
		
		// assert
		assertEquals(1L, buffer.get(1));
		assertEquals(2L, buffer.get(2));
	}
	
	@Test
	void testSubVector2Lc() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L subV = new Vector2L(3L, 5L);
		
		// act
		v.sub((Vector2Lc) subV);
		
		// assert
		assertEquals(7L, v.x);
		assertEquals(15L, v.y);
	}

	@Test
	void testSubVector2LcDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L subV = new Vector2L(3L, 5L);
		Vector2L dest = new Vector2L();
		
		// act
		v.sub((Vector2Lc) subV, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(7L, dest.x);
		assertEquals(15L, dest.y);
	}

	@Test
	void testSubVector2ic() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2i subV = new Vector2i(3, 5);
		
		// act
		v.sub((Vector2ic) subV);
		
		// assert
		assertEquals(7L, v.x);
		assertEquals(15L, v.y);
	}

	@Test
	void testSubVector2icDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2i subV = new Vector2i(3, 5);
		Vector2L dest = new Vector2L();
		
		// act
		v.sub((Vector2ic) subV, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(7L, dest.x);
		assertEquals(15L, dest.y);
	}

	@Test
	void testSubLongs() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.sub(3L, 5L);
		
		// assert
		assertEquals(7L, v.x);
		assertEquals(15L, v.y);
	}

	@Test
	void testSubLongsDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L dest = new Vector2L();
		
		// act
		v.sub(3L, 5L, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(7L, dest.x);
		assertEquals(15L, dest.y);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, 0, 0",
		"3, 4, 25",
		"-3, 4, 25",
		"-3, -4, 25"
	})
	void testLengthSquared(long v1, long v2, long expected) {
		// arrange
		Vector2L v = new Vector2L(v1, v2);
		
		// act
		long result = v.lengthSquared();
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 0",
		"3, 4, 25",
		"-3, 4, 25",
		"-3, -4, 25"
	})
	void testLengthSquaredStatic(long v1, long v2, long expected) {
		// act
		long result = Vector2L.lengthSquared(v1, v2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 0.0",
		"3, 4, 5.0",
		"-3, 4, 5.0",
		"-3, -4, 5.0"
	})
	void testLength(long v1, long v2, double expected) {
		// arrange
		Vector2L v = new Vector2L(v1, v2);
		
		// act
		double result = v.length();
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 0.0",
		"3, 4, 5.0",
		"-3, 4, 5.0",
		"-3, -4, 5.0"
	})
	void testLengthStatic(long v1, long v2, double expected) {
		// act
		double result = Vector2L.length(v1, v2);
		
		// assert
		assertEquals(expected, result);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 5.0",
		"-1, -1, 2, 3, 5.0"
	})
	void testDistanceVector2Lc(long x1, long y1, long x2, long y2, double expected) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		Vector2L v2 = new Vector2L(x2, y2);
		
		// act
		double result = v.distance((Vector2Lc) v2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 5.0",
		"-1, -1, 2, 3, 5.0"
	})
	void testDistanceLongs(long x1, long y1, long x2, long y2, double expected) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		
		// act
		double result = v.distance(x2, y2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 25",
		"-1, -1, 2, 3, 25"
	})
	void testDistanceSquaredVector2Lc(long x1, long y1, long x2, long y2, long expected) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		Vector2L v2 = new Vector2L(x2, y2);
		
		// act
		long result = v.distanceSquared((Vector2Lc) v2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 25",
		"-1, -1, 2, 3, 25"
	})
	void testDistanceSquaredLongs(long x1, long y1, long x2, long y2, long expected) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		
		// act
		long result = v.distanceSquared(x2, y2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 7",
		"-1, -1, 2, 3, 7",
		"5, 5, 2, 2, 6"
	})
	void testGridDistanceVector2Lc(long x1, long y1, long x2, long y2, long expected) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		Vector2L v2 = new Vector2L(x2, y2);
		
		// act
		long result = v.gridDistance((Vector2Lc) v2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 7",
		"-1, -1, 2, 3, 7",
		"5, 5, 2, 2, 6"
	})
	void testGridDistanceLongs(long x1, long y1, long x2, long y2, long expected) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		
		// act
		long result = v.gridDistance(x2, y2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 5.0",
		"-1, -1, 2, 3, 5.0"
	})
	void testDistanceStatic(long x1, long y1, long x2, long y2, double expected) {
		// act
		double result = Vector2L.distance(x1, y1, x2, y2);
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"0, 0, 3, 4, 25",
		"-1, -1, 2, 3, 25"
	})
	void testDistanceSquaredStatic(long x1, long y1, long x2, long y2, long expected) {
		// act
		long result = Vector2L.distanceSquared(x1, y1, x2, y2);
		
		// assert
		assertEquals(expected, result);
	}
	
	@Test
	void testAddVector2Lc() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L addV = new Vector2L(3L, 5L);
		
		// act
		v.add((Vector2Lc) addV);
		
		// assert
		assertEquals(13L, v.x);
		assertEquals(25L, v.y);
	}

	@Test
	void testAddVector2LcDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L addV = new Vector2L(3L, 5L);
		Vector2L dest = new Vector2L();
		
		// act
		v.add((Vector2Lc) addV, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(13L, dest.x);
		assertEquals(25L, dest.y);
	}

	@Test
	void testAddVector2ic() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2i addV = new Vector2i(3, 5);
		
		// act
		v.add((Vector2ic) addV);
		
		// assert
		assertEquals(13L, v.x);
		assertEquals(25L, v.y);
	}

	@Test
	void testAddVector2icDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2i addV = new Vector2i(3, 5);
		Vector2L dest = new Vector2L();
		
		// act
		v.add((Vector2ic) addV, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(13L, dest.x);
		assertEquals(25L, dest.y);
	}

	@Test
	void testAddLongs() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.add(3L, 5L);
		
		// assert
		assertEquals(13L, v.x);
		assertEquals(25L, v.y);
	}

	@Test
	void testAddLongsDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L dest = new Vector2L();
		
		// act
		v.add(3L, 5L, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(13L, dest.x);
		assertEquals(25L, dest.y);
	}
	
	@Test
	void testMulScalar() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.mul(2L);
		
		// assert
		assertEquals(20L, v.x);
		assertEquals(40L, v.y);
	}

	@Test
	void testMulScalarDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L dest = new Vector2L();
		
		// act
		v.mul(2L, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(20L, dest.x);
		assertEquals(40L, dest.y);
	}

	@Test
	void testMulVector2Lc() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L mulV = new Vector2L(3L, 4L);
		
		// act
		v.mul((Vector2Lc) mulV);
		
		// assert
		assertEquals(30L, v.x);
		assertEquals(80L, v.y);
	}

	@Test
	void testMulVector2LcDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L mulV = new Vector2L(3L, 4L);
		Vector2L dest = new Vector2L();
		
		// act
		v.mul((Vector2Lc) mulV, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(30L, dest.x);
		assertEquals(80L, dest.y);
	}

	@Test
	void testMulVector2ic() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2i mulV = new Vector2i(3, 4);
		
		// act
		v.mul((Vector2ic) mulV);
		
		// assert
		assertEquals(30L, v.x);
		assertEquals(80L, v.y);
	}

	@Test
	void testMulVector2icDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2i mulV = new Vector2i(3, 4);
		Vector2L dest = new Vector2L();
		
		// act
		v.mul((Vector2ic) mulV, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(30L, dest.x);
		assertEquals(80L, dest.y);
	}

	@Test
	void testMulLongs() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.mul(3L, 4L);
		
		// assert
		assertEquals(30L, v.x);
		assertEquals(80L, v.y);
	}

	@Test
	void testMulLongsDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L dest = new Vector2L();
		
		// act
		v.mul(3L, 4L, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(30L, dest.x);
		assertEquals(80L, dest.y);
	}
	
	@Test
	void testDivFloat() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.div(2.0f);
		
		// assert
		assertEquals(5L, v.x);
		assertEquals(10L, v.y);
	}

	@Test
	void testDivFloatDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L dest = new Vector2L();
		
		// act
		v.div(2.0f, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(5L, dest.x);
		assertEquals(10L, dest.y);
	}

	@Test
	void testDivLong() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.div(2L);
		
		// assert
		assertEquals(5L, v.x);
		assertEquals(10L, v.y);
	}

	@Test
	void testDivLongDest() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		Vector2L dest = new Vector2L();
		
		// act
		v.div(2L, dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		assertEquals(5L, dest.x);
		assertEquals(10L, dest.y);
	}
	
	@Test
	void testZero() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		v.zero();
		
		// assert
		assertEquals(0L, v.x);
		assertEquals(0L, v.y);
	}

	@Test
	void testWriteExternal() throws Exception {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		
		// act
		v.writeExternal(out);
		out.flush();
		
		// assert
		assertTrue(baos.toByteArray().length > 0);
		baos.close();
		out.close();
	}

	@Test
	void testReadExternal() throws Exception {
		// arrange
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		out.writeLong(10L);
		out.writeLong(20L);
		out.flush();
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream in = new ObjectInputStream(bais);
		Vector2L v = new Vector2L();
		
		// act
		v.readExternal(in);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
		bais.close();
		in.close();
	}

	@Test
	void testNegate() {
		// arrange
		Vector2L v = new Vector2L(10L, -20L);
		
		// act
		v.negate();
		
		// assert
		assertEquals(-10L, v.x);
		assertEquals(20L, v.y);
	}

	@Test
	void testNegateDest() {
		// arrange
		Vector2L v = new Vector2L(10L, -20L);
		Vector2L dest = new Vector2L();
		
		// act
		v.negate(dest);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(-20L, v.y);
		assertEquals(-10L, dest.x);
		assertEquals(20L, dest.y);
	}

	@Test
	void testMin() {
		// arrange
		Vector2L v = new Vector2L(10L, 5L);
		Vector2L other = new Vector2L(3L, 20L);
		
		// act
		v.min((Vector2Lc) other);
		
		// assert
		assertEquals(3L, v.x);
		assertEquals(5L, v.y);
	}

	@ParameterizedTest
	@CsvSource({
		"5, 5, 10, 10, 5, 5",
		"10, 10, 5, 5, 5, 5",
		"5, 10, 10, 5, 5, 5",
		"10, 5, 5, 10, 5, 5",
		"5, 5, 5, 5, 5, 5"
	})
	void testMinDest(long x1, long y1, long x2, long y2, long expectedX, long expectedY) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		Vector2L other = new Vector2L(x2, y2);
		Vector2L dest = new Vector2L();
				
		// act
		v.min((Vector2Lc) other, dest);
				
		// assert
		assertEquals(x1, v.x);
		assertEquals(y1, v.y);
		assertEquals(expectedX, dest.x);
		assertEquals(expectedY, dest.y);
	}

	@Test
	void testMax() {
		// arrange
		Vector2L v = new Vector2L(10L, 5L);
		Vector2L other = new Vector2L(3L, 20L);
		
		// act
		v.max((Vector2Lc) other);
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(20L, v.y);
	}

	@ParameterizedTest
	@CsvSource({
		"5, 5, 10, 10, 10, 10",
		"10, 10, 5, 5, 10, 10",
		"5, 10, 10, 5, 10, 10",
		"10, 5, 5, 10, 10, 10",
		"5, 5, 5, 5, 5, 5"
	})
	void testMaxDest(long x1, long y1, long x2, long y2, long expectedX, long expectedY) {
		// arrange
		Vector2L v = new Vector2L(x1, y1);
		Vector2L other = new Vector2L(x2, y2);
		Vector2L dest = new Vector2L();
				
		// act
		v.max((Vector2Lc) other, dest);
				
		// assert
		assertEquals(x1, v.x);
		assertEquals(y1, v.y);
		assertEquals(expectedX, dest.x);
		assertEquals(expectedY, dest.y);
	}

	@ParameterizedTest
	@CsvSource({
		"10, 5, 0",
		"5, 10, 1",
		"-10, 5, 0",
		"5, -10, 1",
		"10, 10, 0"
	})
	void testMaxComponent(long v1, long v2, long expected) {
		// arrange
		Vector2L v = new Vector2L(v1, v2);
		
		// act
		long result = v.maxComponent();
		
		// assert
		assertEquals(expected, result);
	}

	@ParameterizedTest
	@CsvSource({
		"10, 5, 1",
		"5, 10, 0",
		"-10, 5, 1",
		"5, -10, 0",
		"10, 10, 1"
	})
	void testMinComponent(long v1, long v2, long expected) {
		// arrange
		Vector2L v = new Vector2L(v1, v2);
		
		// act
		long result = v.minComponent();
		
		// assert
		assertEquals(expected, result);
	}
	
	@Test
	void testAbsolute() {
		// arrange
		Vector2L v = new Vector2L(-10L, 5L);
		
		// act
		v.absolute();
		
		// assert
		assertEquals(10L, v.x);
		assertEquals(5L, v.y);
	}

	@Test
	void testAbsoluteDest() {
		// arrange
		Vector2L v = new Vector2L(-10L, 5L);
		Vector2L dest = new Vector2L();
		
		// act
		v.absolute(dest);
		
		// assert
		assertEquals(-10L, v.x);
		assertEquals(5L, v.y);
		assertEquals(10L, dest.x);
		assertEquals(5L, dest.y);
	}

	@Test
	void testHashCode() {
		// arrange
		Vector2L v1 = new Vector2L(10L, 20L);
		Vector2L v2 = new Vector2L(10L, 20L);
		
		// act
		int hash1 = v1.hashCode();
		int hash2 = v2.hashCode();
		
		// assert
		assertEquals(hash1, hash2);
	}

	@Test
	void testEqualsObject() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act & assert
		assertTrue(v.equals(v));
		assertFalse(v.equals(null));
		assertFalse(v.equals(new Object()));
		assertFalse(v.equals(new Vector2L(9L, 20L)));
		assertFalse(v.equals(new Vector2L(10L, 19L)));
		assertTrue(v.equals(new Vector2L(10L, 20L)));
	}

	@Test
	void testEqualsLongs() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act & assert
		assertFalse(v.equals(9L, 20L));
		assertFalse(v.equals(10L, 19L));
		assertTrue(v.equals(10L, 20L));
	}

	@Test
	void testToString() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		String s = v.toString();
		
		// assert
		assertTrue(s.length() > 0);
	}

	@Test
	void testToStringFormat() {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		NumberFormat format = NumberFormat.getInstance(Locale.US);
		
		// act
		String s = v.toString(format);
		
		// assert
		assertEquals("(10 20)", s);
	}

	@Test
	void testClone() throws CloneNotSupportedException {
		// arrange
		Vector2L v = new Vector2L(10L, 20L);
		
		// act
		Vector2L clone = (Vector2L) v.clone();
		
		// assert
		assertEquals(10L, clone.x);
		assertEquals(20L, clone.y);
	}
	
}
