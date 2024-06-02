import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ActiveActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ActiveActor extends Actor
{
    /**
     * Act - do whatever the ActiveActor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public void act()
    {
        if (!((Space)getWorld()).getSession().getGameStat()) return;
        action();
    }
    
    public void action() { }
}
