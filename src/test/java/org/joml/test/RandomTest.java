package org.joml.test;

import org.joml.Random;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the {@link Random} class.
 *
 * @author Attila Udvardi
 */
class RandomTest {
    /**
     * Repeate the same test multiple times, but with different random seeds
     * to see if the generated value is within the documented interval range.
     */
    @RepeatedTest(5)
    void testRandomFloatNumberGeneration() {
        final Random random = new Random();
        final float value = random.nextFloat();
        assertTrue(0 <= value && value < 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 25, 100, Integer.MAX_VALUE})
    void testRandomPositiveIntNumberGeneration(final int n) {
        final Random random = new Random();
        final int value = random.nextInt(n);
        assertTrue(0 <= value && value < n);
    }
}
