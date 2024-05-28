import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.JOptionPane; // JOptionPane
import java.util.concurrent.locks.*; // threadding control

/**
 * Write a description of class MusicOption here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MusicOption extends OptionCounters
{
    public static final String DEFAULT_PREFIX = "[M] Music ";
    public static final String DEFAULT_MUSIC_ON = "ON";
    public static final String DEFAULT_MUSIC_OFF = "OFF";
    private static final int DEBOUNCE_TIME = 100;
    
    private Lock lock;
    private long prevMillis; private boolean userState = false;
    private GreenfootSound track;
    
    private void InstantiationHelper() {
        lock = new ReentrantLock();
    }
    
    public MusicOption() {
        super(DEFAULT_PREFIX + DEFAULT_MUSIC_OFF);
        InstantiationHelper();
    }
    
    public MusicOption(GreenfootSound track) { 
        super(DEFAULT_PREFIX + DEFAULT_MUSIC_OFF);
        InstantiationHelper();
        this.track = track;
    }
    
    /**
     * Act - do whatever the MusicOption wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (System.currentTimeMillis() - prevMillis < DEBOUNCE_TIME) return;
        
        if (!lock.tryLock()) return;
        if (Greenfoot.isKeyDown("m")) 
        {
            prevMillis = System.currentTimeMillis();
            if (track == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Cannot play music - track set to NULL", 
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            if (!userState) {
                this.setPrefix(DEFAULT_PREFIX + DEFAULT_MUSIC_ON);
                track.playLoop();
            } else {
                this.setPrefix(DEFAULT_PREFIX + DEFAULT_MUSIC_OFF);
                track.pause();
            }
            
            userState = !userState;
        }
        lock.unlock();
    }
}
