package org.game.service;

import java.io.IOException;

public class StartGame {
    public static void startGame(int betAmount, String configFile) throws IOException {
        String[][] matrix = new GenerateMatrix().generateRandomMatrix(configFile);

        String result = new CountReward().countReward(betAmount, matrix, configFile);

        System.out.println(result);
    }
}
