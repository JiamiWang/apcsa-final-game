import greenfoot.*;
import java.util.concurrent.locks.*;

public class Session {
    public static final int ASTEROID_LAUNCHER_TIMES = 3;
    public static final int startAsteroids = 2;
    
    public static final int LIVES_INDEX = 1;
    public static final int LVL_INDEX = 2;
    public static final int MUSIC_INDEX = 3;
    
    private int curDeaths = 0; 
    private StatusOptions gameStat = StatusOptions.PAUSED; // true if running, false if paused 
    private Level curLevel;
    
    private Space s;
    
    public Session(Space wrld) {
        s = wrld;
    }
    
    public void setGameStat(StatusOptions option) {
        gameStat = option;
    }
    
    public StatusOptions getGameStat() {
        return gameStat;
    }
    
    public int getDeaths() {
        return curDeaths;
    }
    
    public void addDeath() { 
        curDeaths++;
        s.setLivesCounter(ASTEROID_LAUNCHER_TIMES - curDeaths);
    }
    
    public void setDeaths(int newDeath) { 
        curDeaths = newDeath;
        s.setLivesCounter(ASTEROID_LAUNCHER_TIMES - curDeaths);
    }
    
    public void createGame() {
        Rocket rocket = new Rocket();
        s.addObject(rocket, s.getWidth()/2, s.getHeight() - 45);
        
        s.addAsteroids(startAsteroids);
        s.setPaintOrder(Labels.class);
    }
    
    public void cleanUpGame() {
        s.removeObjects(s.getObjects(Asteroid.class));
        s.removeObjects(s.getObjects(Rocket.class));
    }
    
    public void cleanUpAndCreateGame(Lock l) {
        cleanUpGame(); createGame();
        l.unlock();
    }
    
    public void cleanUpAndCreateGame() {
        cleanUpGame(); createGame();
    }
        
    public void gameOver() {
        s.addObject(
            new ScoreBoard(s.getScore()),
            s.getWidth() / 2,
            s.getHeight() / 2
        );
        setGameStat(StatusOptions.GAMEOVER);
    }
    
    public void restartGame() {
        curDeaths = 0;
        s.setScore(0);
        setDeaths(curDeaths);
        cleanUpAndCreateGame();
        setGameStat(StatusOptions.ACTIVE);
    }
}
