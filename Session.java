import greenfoot.*;

/**
 * Provide important information regarding the 
 * current gaming session, such as economy
 * status, store powerups, etc
 * 
 * Everything is purposefully static. :p
 * 
 * @author JiamiWang
 * @version revision 1
 */
public class Session  
{
    public static final int ASTEROID_LAUNCHER_TIMES = 3;
    
    private static int curDeaths = 0;
    private static boolean gameStat = false;
    
    private static World sessionWorld;
    
    public static void defaultSessionVals() {
        gameStat = false;
        curDeaths = 0;
    }
    
    protected static void setWorld(World wrld) { sessionWorld = wrld; }
    
    public static void toggleGameStat() { gameStat = !gameStat; }
    public static boolean getGameStat() { return gameStat; }
    
    public static int getDeaths() { return curDeaths; }
    public static void addDeath() { 
        curDeaths++;
        ((Space)sessionWorld).setLivesCounter(ASTEROID_LAUNCHER_TIMES - curDeaths);
    }
    public static void setDeaths(int newDeath) { 
        curDeaths = newDeath;
        ((Space)sessionWorld).setLivesCounter(ASTEROID_LAUNCHER_TIMES - curDeaths);
    }
    
    private Session() {}
}
