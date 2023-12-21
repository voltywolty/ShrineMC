package me.volt.main.mcevolved.gamemode;

public interface GameMode {
    int getGameModeId();

    String getGameModeCode();
    String getGameModeName();

    void startGame();
    void endGame();
}
