import java.util.*;

/**
 * Write a description of class Level here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level {
    private int lvl;
    private ArrayList<Asteroid> asts;
    DIFFICULTY diff;
    
    private int smalls, mediums, larges;
    
    public enum DIFFICULTY {
        EASY, MEDIUM,
        HARD, INSANE,
    }
    
    public int getLevel() { return lvl; }
    public ArrayList<Asteroid> getAsteroids() { return asts; }
    public DIFFICULTY getDifficulty() { return diff; }
    
    /**
     * Instantiate a new level
     * @deprecated
     * Use getLevel instead to fetch a Level object pertaining to a level 
     */
    @Deprecated(forRemoval = false)
    private Level() { }
    
    /**
     * Constructor for levels as private use
     */
    @Deprecated()
    private Level(int smalls, int mediums, int larges) {
        this.smalls = smalls; this.mediums = mediums; this.larges = larges;
        
    }
    
    /**
       * Get information for a level to create it
       * @param lvl The level to generate
       * @return null if lvl is greater than 
    */
    public static Level getLevel(int lvl) {
        return null;
    }
    
    public String toString() {
        return "Level " + lvl + " ()";
    }

}