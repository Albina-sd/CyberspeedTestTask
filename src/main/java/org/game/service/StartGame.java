package org.game.service;

import java.io.IOException;

public class StartGame {
    public static void startGame(int betAmount, String file) throws IOException {
        String[][] matrix = new GenerateMatrix().generateRandomMatrix(file);

        String result = new CountReward().countReward(betAmount, matrix, file);

        System.out.println(result);
    }
}
