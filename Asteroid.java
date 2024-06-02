import greenfoot.*;
import java.util.concurrent.locks.*;

/**
 * A rock in space.
 * 
 * @author Poul Henriksen
 * @author Michael Kölling
 */
public class Asteroid extends SmoothMover
{
    public static final int DEFAULT_LARGE_SIZE = 50;
    public static final int DEFAULT_NEXT_SIZE_FACTOR = 2;
    
    private static Lock lock = new ReentrantLock();
    
    /** Size of this asteroid */
    private int size;

    /** When the stability reaches 0 the asteroid will explode */
    private int stability;

    
    /**
     * Create an asteroid with default size and random direction of movement.
     */
    public Asteroid()
    {
        this(DEFAULT_LARGE_SIZE);
    }
    
    
    /**
     * Create an asteroid with a given size and random direction of movement.
     */
    public Asteroid(int size)
    {
        super(new Vector(Greenfoot.getRandomNumber(360), 2), true);
        setSize(size);
    }
    
    /**
     * Create an asteroid with a given size and direction of movement.
     */
    public Asteroid(int size, Vector velocity)
    {
        super(velocity, true);
        setSize(size);
    }
    
    public void act()
    {         
        move();
        checkRocketHit();
        if (forDestroy()) {
            ((Space)getWorld()).addAsteroids(1, size);
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Check whether we have hit the rocket.
     */
    private void checkRocketHit()
    {
        if (!lock.tryLock()) return;
        Rocket rkt = (Rocket) getOneIntersectingObject(Rocket.class);
        if (rkt != null)
        {
            Session.addDeath();
            getWorld().addObject(new Explosion(), getX(), getY());

            if (Session.ASTEROID_LAUNCHER_TIMES - Session.getDeaths() == 0) {
                ((Space) getWorld()).gameOver();
                Greenfoot.playSound("overforyou.wav");
                getWorld().removeObject(rkt);
                lock.unlock();
            }
            else {
                ((Space)getWorld()).cleanUpAndCreateGame(lock);
            }
        }
    }

    /**
     * Set the size of this asteroid. Note that stability is directly
     * related to size. Smaller asteroids are less stable.
     */
    public void setSize(int size) 
    {
        stability = size;
        this.size = size;
        GreenfootImage image = getImage();
        image.scale(size, size);
    }

    /**
     * Return the current stability of this asteroid. (If it goes down to 
     * zero, it breaks up.)
     */
    public int getStability() 
    {
        return stability;
    }
    
    /**
     * Hit this asteroid dealing the given amount of damage.
     */
    public void hit(int damage) 
    {
        stability = stability - damage;
        if (stability <= 0) 
        {
            breakUp();
        }
    }
    
    /**
     * Break up this asteroid. If we are still big enough, this will create two
     * smaller asteroids. If we are small already, just disappear.
     */
    private void breakUp() 
    {
        Greenfoot.playSound("Explosion.wav");
        
        if (size <= 16) {
            ((Space)getWorld()).addScore(2);
            getWorld().removeObject(this);
        }
        else {
            int r = getVelocity().getDirection() + Greenfoot.getRandomNumber(45);
            double l = getVelocity().getLength();
            Vector speed1 = new Vector(r + 60, l * 1.2);
            Vector speed2 = new Vector(r - 60, l * 1.2);        
            Asteroid a1 = new Asteroid(size/DEFAULT_NEXT_SIZE_FACTOR, speed1);
            Asteroid a2 = new Asteroid(size/DEFAULT_NEXT_SIZE_FACTOR, speed2);
            getWorld().addObject(a1, getX(), getY());
            getWorld().addObject(a2, getX(), getY());        
            a1.move();
            a2.move();

            ((Space)getWorld()).addScore(1);
            getWorld().removeObject(this);
        }
    }
}
