package org.game.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateMatrixTest {

    @Test
    public void testGenerateRandomMatrixPrintsMatrixHeader() throws IOException {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); // Перенаправляем вывод в outContent

        try {
            GenerateMatrix generateMatrix = new GenerateMatrix();
            String config = "/test-config.json";
            generateMatrix.generateRandomMatrix(config);

            String output = outContent.toString();
            assertTrue(output.contains("matrix:"), "Output should contain 'matrix:'");
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testGenerateRandomMatrixReturnsMatrixOfCorrectSize() throws IOException {
        GenerateMatrix generateMatrix = new GenerateMatrix();
        String config = "/test-config.json";

        String[][] matrix = generateMatrix.generateRandomMatrix(config);

        assertEquals( 3, matrix.length);
        for (String[] strings : matrix) {
            assertEquals(3, strings.length);
            for (String string : strings) {
                assertNotNull(string);
            }
        }
    }
}
