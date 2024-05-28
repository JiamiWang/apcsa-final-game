import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OptionCounters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OptionCounters extends Actor
{
    private static final Color transparent = new Color(0,0,0,0);
    private String prefix;
    private GreenfootImage background;
    
    public OptionCounters() { this(new String()); }
    public OptionCounters(String prefix) { 
        background = getImage();
        this.prefix = prefix;
        updateImage();
    }
    
    
    public String getPrefix() { return prefix; }
    public void setPrefix(String newPrefix) { 
        prefix = newPrefix; updateImage();
    } 
    
    /**
     * Act - do whatever the OptionCounters wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    
        private void updateImage()
    {
        GreenfootImage image = new GreenfootImage(background);
        GreenfootImage text = new GreenfootImage(prefix, 22, Color.BLACK, transparent);
        
        if (text.getWidth() > image.getWidth() - 20)
        {
            image.scale(text.getWidth() + 20, image.getHeight());
        }
        
        image.drawImage(text, (image.getWidth()-text.getWidth())/2, 
                        (image.getHeight()-text.getHeight())/2);
        setImage(image);
    }
    
}
