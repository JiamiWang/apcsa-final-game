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
     */
    private void checkKeys() 
    {
        if (Greenfoot.isKeyDown("space")) 
        {
            fire();
        }
        if (Greenfoot.isKeyDown("w")) {
            ignite();
            beingHeld = true;
        }
        else { beingHeld = false; }
        if (Greenfoot.isKeyDown("s")) {
            doBreak();
        }
        if (Greenfoot.isKeyDown("a")) {
            turn(-3);
        }
        if (Greenfoot.isKeyDown("d")) {
            turn(3);
        }
        
        if (System.currentTimeMillis() - prevTime > 100) {
            prevTime = System.currentTimeMillis();
            if (beingHeld) {
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
     * Helper method assist with moving (accelerate)
     */
    private void ignite() {
        Vector v = new Vector(getRotation(), 0.1);
        addToVelocity(v);
    }
    
    /**
     * Helper method assist with moving (accelerate)
     */
    private void doBreak() {
        Vector v = new Vector(getRotation(), -0.1);
        addToVelocity(v);
    }
    
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