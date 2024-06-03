import java.util.*;
import java.util.concurrent.locks.*;
import greenfoot.*;

/**
 * the information for each level, i guess...?
 * 
 * @author jiamiwang
 * @version revision 1
 */
public class Level {
    
    
    private int lvl, score; // score is used to track so we deduce if they die
    private ArrayList<Asteroid> asts;
    
    public static final int FIRST_LEVEL = 1;
    public static final int LAST_LEVEL = 5;
    
    private int smalls, mediums, larges;
    private GreenfootImage img;
    
    public int getLevel() { return lvl; }
    
    public int getScore() { return score; }
    public void setScore(int newScore) { score = newScore; }
    public void addScore(int val) { score += val; }
    
    public ArrayList<Asteroid> getAsteroids() { return asts; }
    
    /**
     * Instantiate a new level
     * @deprecated
     * Use getLevel instead to fetch a Level object pertaining to a level 
     */
    //@Deprecated(forRemoval = false)
    private Level() { }
    
    /**
     * Constructor for levels as private use
     */
    private Level(int lvl, int smalls, int mediums, int larges) {
        this(lvl, smalls, mediums, larges, null);        
    }
    
    private Level(int lvl, int smalls, int mediums, int larges, String imgPath) {
        this.lvl = lvl; this.smalls = smalls; this.mediums = mediums; this.larges = larges;
        if (imgPath != null) {
            try {
                img = new GreenfootImage(imgPath);
            } catch(Exception e) {}
        }
    }
    
    /**
       * Get information for a level to create it
       * @param lvl The level to generate
       * @return null if lvl is greater than 
    */
    public static Level getLevel(int lvl) {
        switch (lvl) {
            case 1:
                return new Level(1, 0, 0, 2, "3.jpg");
            case 2:
                return new Level(2, 2, 1, 3, "2.jpg");
            case 3:
                return new Level(3, 1, 3, 2);
            case 4:
                return new Level(4, 4, 2, 1, "4.jpg");
            case 5:
                return new Level(5, 4, 0, 4, "5.jpg");
            default:
                return null;
        }
    }
    
    public GreenfootImage getImg() {
        return img;
    }
    
    public String toString() {
        return "Level " + lvl;
    }
    
    public int getSmalls() { return smalls; }
    public int getMediums() { return mediums; }
    public int getLarges() { return larges; }
}
