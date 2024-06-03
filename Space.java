import greenfoot.*;
import java.util.concurrent.locks.*;

/**
 * Space. Something for rockets to fly in.
 * 
 * @author Michael Kölling
 * @version 1.0
 */
public class Space extends World
{
    private Counter scoreCounter, levelCounter, livesCounter;
    private Pause pause;
    private OptionCounters musicOption;
    private Session sess;
    
    GreenfootImage background;
    
    /* Interaction with the Score Counter */
    public void addScore(int score) {
        sess.updateUserInfoScore(scoreCounter.add(score));
    }
    
    public void setScore(int score) {
        scoreCounter.setValue(score);
        sess.updateUserInfoScore(score);
    }
    
    public int getScore() { return scoreCounter.getValue(); }

    public void setLevelCounter(int newVal) { levelCounter.setValue(newVal); }
    
    public GreenfootImage getDefaultImage() { return background; }
    
    /* Interaction with the lives counter */
    public void setLivesCounter(int newVal) { livesCounter.setValue(newVal); }
    
    /* Game Session getter */
    public Session getSession() {
        return sess;
    }
    
    /**
     * Create the space and all objects within it.
     */
    public Space()
    {
        super(400, 600, 1);
        
        sess = new Session(this); 
        // above will handle all user and game related stuff
        
        background = new GreenfootImage(getBackground());
        background.setColor(Color.BLACK);
        background.fill();
        drawStars(background, 2282, 1, 3);
        getBackground().drawImage(background, 0, 0);
        
        musicOption = new MusicOption(new GreenfootSound("soundtrack.mp3"), sess);
        addObject(musicOption, 70, 110);
        
        levelCounter = new Counter("✨ ");//
        addObject(levelCounter, 60, 50);
        levelCounter.setValue(Level.FIRST_LEVEL);
        
        scoreCounter = new Counter("🎯 ");//⭐
        addObject(scoreCounter, 60, 20);
        
        livesCounter = new Counter("❤ ");//⭐
        addObject(livesCounter, 60, 80);
        setLivesCounter(Session.ASTEROID_LAUNCHER_TIMES);
        
        pause = new Pause();
        addObject(pause, getWidth() - 35, 35);
        
        Explosion.initializeImages();
        ProtonWave.initializeImages();
        
        sess.createGame();
    }
    
    private void drawStars(GreenfootImage img, 
        int times, int minRad, int maxRad) { 
            Color prevColor = img.getColor();
            for (int i = 0; times > i; i++) {
                int whiteVal = (int) (Math.random() * 56) + 200;
                img.setColor(new Color(whiteVal, whiteVal, whiteVal));
                int randX = (int)(Math.random() * (img.getWidth()));
                int randY = (int)(Math.random() * (img.getHeight()));
                int rad = (int)(Math.random() * (maxRad - minRad + 1)) + minRad;
                img.fillOval(
                    randX, randY, rad, rad
                );
                //img.fillOval();
            }
            img.setColor(prevColor);
    }
    
    /**
     * Add a given number of asteroids to our world. 
     */
    public void addAsteroids(int count) 
    {
        for(int i = 0; i < count; i++) 
        {
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight()/3);
            addObject(new Asteroid(), x, y);
        }
    }
    
    /**
     * Add a given number of asteroids with a specified size to our world. 
     */
    public void addAsteroids(int count, int size) 
    {
        for(int i = 0; i < count; i++) 
        {
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight()/3);
            addObject(new Asteroid(size), x, y);
        }
    }
}