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
    private OptionCounters musicOption;
    
    private final int startAsteroids = 2;
    
    public void addScore(int score) {
        scoreCounter.add(score);
    }
    
    public int getScore() { return scoreCounter.getValue(); }
    
    public void setLivesCounter(int newVal) { livesCounter.setValue(newVal); }
    
    /**
     * Create the space and all objects within it.
     */
    public Space()
    {
        super(400, 600, 1);
        
        Session.setWorld(this);
        Session.defaultSessionVals();
        
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();
        drawStars(background, 2282, 1, 3);
        
        musicOption = new MusicOption(new GreenfootSound("soundtrack.mp3"));
        addObject(musicOption, 70, 110);
        
        levelCounter = new Counter("✨ ");//
        addObject(levelCounter, 60, 50);
        
        scoreCounter = new Counter("🎯 ");//⭐
        addObject(scoreCounter, 60, 20);
        
        livesCounter = new Counter("❤ ");//⭐
        addObject(livesCounter, 60, 80);
        setLivesCounter(Session.ASTEROID_LAUNCHER_TIMES);
        
        Explosion.initializeImages();
        ProtonWave.initializeImages();
        
        createGame();
    }
    
    public void createGame() {
        Rocket rocket = new Rocket();
        addObject(rocket, getWidth()/2, getHeight() - 45);
        
        addAsteroids(startAsteroids);
        setPaintOrder(Labels.class);
    }
    
    public void cleanUpGame() {
        removeObjects(getObjects(Asteroid.class));
        removeObjects(getObjects(Rocket.class));
    }
    
    public void cleanUpAndCreateGame(Lock l) {
        cleanUpGame(); createGame();
        l.unlock();
    }
    
    public void cleanUpAndCreateGame() {
        cleanUpGame(); createGame();
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
     * Add a given number of asteroids to our world. 
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
    
    /**
     * This method is called when the game is over to display the final score.
     */
    public void gameOver() 
    {
        addObject(
            new ScoreBoard(getScore()),
            getWidth() / 2,
            getHeight() / 2
        );
    }

}