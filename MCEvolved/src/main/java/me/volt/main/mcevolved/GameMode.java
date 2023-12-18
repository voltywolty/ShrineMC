package me.volt.main.mcevolved;

public interface GameMode {
    int getGameModeId();

    String getGameModeCode();
    String getGameModeName();

    void startGame();
    void endGame();
}
