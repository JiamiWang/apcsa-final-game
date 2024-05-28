import greenfoot.*;
import javafx.scene.shape.*;

/**
 * Space. Something for rockets to fly in.
 * 
 * @author Michael Kölling
 * @version 1.0
 */
public class Space extends World
{
    private Counter scoreCounter;
    private OptionCounters musicOption;
    private int startAsteroids = 3;

    public void addScore(int score) {
        scoreCounter.add(score);
    }
    
    public int getScore() { return scoreCounter.getValue(); }
    
    /**
     * Create the space and all objects within it.
     */
    public Space()
    {
        super(600, 500, 1);
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();
        drawStars(background, 2282, 1, 3);
        
        Rocket rocket = new Rocket();
        addObject(rocket, getWidth()/2 + 100, getHeight()/2);
        
        addAsteroids(startAsteroids);
        
        musicOption = new MusicOption(new GreenfootSound("soundtrack.mp3"));
        addObject(musicOption, 80, 450);
        
        scoreCounter = new Counter("Score: ");
        addObject(scoreCounter, 60, 480);

        Explosion.initializeImages();
        ProtonWave.initializeImages();
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
     * Add a given number of asteroids to our world. Asteroids are only added into
     * the left half of the world.
     */
    private void addAsteroids(int count) 
    {
        for(int i = 0; i < count; i++) 
        {
            int x = Greenfoot.getRandomNumber(getWidth()/2);
            int y = Greenfoot.getRandomNumber(getHeight()/2);
            addObject(new Asteroid(), x, y);
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