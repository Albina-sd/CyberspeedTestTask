package org.game;

import org.game.service.StartGame;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        String configFile = null;
        int bettingAmount = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--config") && i + 1 < args.length) {
                configFile = args[i + 1];
            } else if (args[i].equals("--betting-amount") && i + 1 < args.length) {
                bettingAmount = Integer.parseInt(args[i + 1]);
            }
        }

        StartGame.startGame(bettingAmount,  configFile);
    }
}