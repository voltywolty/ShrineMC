package me.volt.main.shrinemc.gamemode;

public class GlobalGameMode implements GameMode {

    @Override
    public int getGameModeId() {
        return 1;
    }

    @Override
    public String getGameModeCode() {
        return "DvZ";
    }

    @Override
    public String getGameModeName() {
        return "Dwarves vs. Zombies";
    }

    @Override
    public void startGame() {

    }

    @Override
    public void endGame() {

    }
}
