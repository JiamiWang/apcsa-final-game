import greenfoot.*;

/**
 * A rocket that can be controlled by the arrowkeys: up, left, right.
 * The gun is fired by hitting the 'space' key. 'z' releases a proton wave.
 * 
 * @author Poul Henriksen
 * @author Michael Kölling
 * 
 * @version 1.1
 */
public class Rocket extends SmoothMover
{
    private static final int gunReloadTime = 5;         // The minimum delay between firing the gun.

    private int reloadDelayCount;               // How long ago we fired the gun the last time.    
    private long prevTime; private boolean beingHeld, continuousHeld; 
    private GreenfootImage rocket = new GreenfootImage("rocket.png");    
    private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");

    /**
     * Initialise this rocket.
     */
    public Rocket()
    {
        reloadDelayCount = 5;
        setRotation(270);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void act()
    {
        checkKeys();
        move();
        reloadDelayCount++;
    }
    
    /**
     * Check whether there are any key pressed and react to them.
     * @return true if moving left or right, false if up and down
     */
    private void checkKeys() 
    {
        boolean isMoving = false;
        boolean leftRight = false;
        if (Greenfoot.isKeyDown("space")) 
        {
            fire();
        }
        if (Greenfoot.isKeyDown("w")) {
            up(); isMoving = true;
        }
        if (Greenfoot.isKeyDown("s")) {
            down(); isMoving = true;
        }
        
        if (Greenfoot.isKeyDown("a")) {
            left(); isMoving = true;
        }
        
        if (Greenfoot.isKeyDown("d")) {
            right(); isMoving = true;
        }
        
        if (System.currentTimeMillis() - prevTime > 100) {
            prevTime = System.currentTimeMillis();
            if (isMoving) {
                getImage().clear();
                getImage().drawImage(new GreenfootImage("rocketWithThrust.png"), 0, 0);
            } else {
                getImage().clear();
                getImage().drawImage(new GreenfootImage("rocket.png"), 0, 0);
            }
            
            if (getWorld().getObjects(Asteroid.class).size() == 0 &&
                getWorld().getObjects(ScoreBoard.class).size() == 0)
                ((Space) getWorld()).gameOver();
        }        
    }
    
    /**
     * Helper method assist with moving (up or right)
     */
    private void up() {
        Vector v = new Vector(getRotation(), 0.1);
        addToVelocity(v);
    }
    
    /**
     * Helper method assist with moving (down or left)
     */
    private void down() {
        Vector v = new Vector(getRotation(), -0.1);
        addToVelocity(v);
    }

    /**
     * Helper method assist with moving (up or right)
     */
    private void left() {
        int x = getX() - 4;
        
        if (x >= getWorld().getWidth()) {
            x = 0;
        }
        if (x < 0) {
            x = getWorld().getWidth() - 1;
        }
        
        this.setLocation(x, getY());
    }
    
    /**
     * Helper method assist with moving (down or left)
     */
    private void right() {
        int x = getX() + 4;
        
        if (x >= getWorld().getWidth()) {
            x = 0;
        }
        if (x < 0) {
            x = getWorld().getWidth() - 1;
        }
        
        this.setLocation(x, getY());    }
    
    /** 
     * Fire a bullet if the gun is ready.
     */
    private void fire() {
        if (reloadDelayCount >= gunReloadTime) {
            Bullet bullet = new Bullet (getVelocity(), getRotation());
            getWorld().addObject(bullet, getX(), getY());
            bullet.move ();
            reloadDelayCount = 0;
        }
    }
    
}