import greenfoot.*;
import java.util.concurrent.locks.*;

public class Session {
    public static final int ASTEROID_LAUNCHER_TIMES = 3;
    public static final int startAsteroids = 2;
    
    public static final int LIVES_INDEX = 1;//wont use for now
    public static final int LVL_INDEX = 2;// wont use for now
    public static final int MUSIC_INDEX = 3;
    
    private int guestHighestScore = 0;
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
        
        if (curLevel == null) curLevel = Level.getLevel(Level.FIRST_LEVEL);
        else curLevel = Level.getLevel(curLevel.getLevel());
        s.addAsteroids(curLevel.getSmalls(), 
            Asteroid.DEFAULT_LARGE_SIZE / Asteroid.DEFAULT_NEXT_SIZE_FACTOR 
            / Asteroid.DEFAULT_NEXT_SIZE_FACTOR
        );
        s.addAsteroids(curLevel.getMediums(), 
            Asteroid.DEFAULT_LARGE_SIZE / Asteroid.DEFAULT_NEXT_SIZE_FACTOR
        );
        
        GreenfootImage newBg = curLevel.getImg();
        if (newBg != null) s.getBackground().drawImage(newBg,0,0);
        else s.getBackground().drawImage(s.getDefaultImage(), 0, 0);
        
        s.addAsteroids(curLevel.getLarges());
        s.setPaintOrder(Labels.class);
    }
    
    public void nextLevel() {
        cleanUpGame();
        if (curLevel.getLevel() + 1 <= Level.LAST_LEVEL) {
            curLevel = Level.getLevel(curLevel.getLevel() + 1);
            s.setLevelCounter(curLevel.getLevel());
        }
        else { gameOver(); return;}
        createGame();
        gameStat = StatusOptions.PAUSED;
    }
    
    public void cleanUpGame() {
        s.removeObjects(s.getObjects(Asteroid.class));
        s.removeObjects(s.getObjects(Rocket.class));
    }
    
    /**
     * the clean up and setup for losing only, which is why there is an unlock  
     */
    public void cleanUpAndCreateGame(Lock l) {
        cleanUpGame();
        s.setScore(s.getScore() - curLevel.getScore());
        createGame();
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
        curLevel = null;
        setDeaths(curDeaths);
        cleanUpAndCreateGame();
        setGameStat(StatusOptions.ACTIVE);
    }
    
    // get music stat from user info, true to play, false to not
    public boolean musicStat() {
        if (!UserInfo.isStorageAvailable()) return false;
        return UserInfo.getMyInfo().getInt(MUSIC_INDEX) == 1;
    }
    
    public void setMusicStat(boolean stat) {
        if (!UserInfo.isStorageAvailable()) return;
        UserInfo.getMyInfo().setInt(MUSIC_INDEX, stat ? 1 : 0);
        UserInfo.getMyInfo().store();
    }
    
    public void updateUserInfoScore(int score) {
        curLevel.setScore(score);
        if (!UserInfo.isStorageAvailable()) { 
            if (guestHighestScore > score) {
                guestHighestScore = score;
            }
            return; 
        }
        if (UserInfo.getMyInfo().getScore() > score) {
            UserInfo.getMyInfo().setScore(score);
            UserInfo.getMyInfo().store();
        }
    }
    
    public int getHighestScore() {
        if (!UserInfo.isStorageAvailable()) return guestHighestScore;
        return UserInfo.getMyInfo().getScore();
    }
}
