package me.volt.main.shrinemc.gamemode;

public interface GameMode {
    int getGameModeId();

    String getGameModeCode();
    String getGameModeName();

    void startGame();
    void endGame();
}
