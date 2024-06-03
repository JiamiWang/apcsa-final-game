import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.concurrent.locks.*; // threadding control

public class Pause extends Labels
{
    private GreenfootImage pausedState; 
    private GreenfootImage deadState;
    private GreenfootImage activeState;
    
    private static final float FONT_SIZE = 14.0f;
    private static final float FONT_SIZE_NORMAL = 10.0f;
    private static final int DEBOUNCE_TIME = 300;
    private long prevMillis;
    
    private Lock lock;
    
    public Pause() {
        pausedState = getImage();
        deadState = new GreenfootImage("death.png");
        activeState = drawActiveImage();
        
        lock = new ReentrantLock();
    }
    
    public void act()
    {
        if (!lock.tryLock()) return;
        checkKeys();
        validateOptionPanel();
        lock.unlock();
    }
    
    public void checkKeys() {
        if (System.currentTimeMillis() - prevMillis < DEBOUNCE_TIME) return;
        if (Greenfoot.isKeyDown("p")) 
        {
            Session sess = ((Space)getWorld()).getSession();
            if (sess.getGameStat() == StatusOptions.PAUSED) {
                sess.setGameStat(StatusOptions.ACTIVE);
                setImage(activeState);
            }
            else if (sess.getGameStat() == StatusOptions.ACTIVE) {
                sess.setGameStat(StatusOptions.PAUSED);
                setImage(pausedState);
            } 
            else if (sess.getGameStat() == StatusOptions.GAMEOVER) {
                sess.restartGame();
                sess.setGameStat(StatusOptions.ACTIVE);
                setImage(activeState);
            }
        }
        else if (((Space)getWorld()).getSession().getGameStat() == StatusOptions.GAMEOVER) {
            setImage(deadState);
        }
        prevMillis = System.currentTimeMillis();
    }
    
    public void validateOptionPanel() {
        boolean panelPresent = getWorld().getObjects(OptionsPanel.class).size() != 0;
        if (((Space)getWorld()).getSession().getGameStat() == StatusOptions.PAUSED) {
            if (!panelPresent)
                getWorld().addObject(
                    new OptionsPanel(((Space)getWorld()).getSession()),
                    getWorld().getWidth() / 2,
                    getWorld().getHeight() / 2
                );
            }
        else getWorld().removeObjects(getWorld().getObjects(OptionsPanel.class));
        
        if (((Space)getWorld()).getSession().getGameStat() == StatusOptions.ACTIVE &&
        getWorld().getObjects(ScoreBoard.class).size() != 0) 
            getWorld().removeObjects(getWorld().getObjects(ScoreBoard.class));
    }
    
    // other images lol
    public GreenfootImage drawActiveImage() {
        GreenfootImage image = new GreenfootImage(pausedState.getWidth(), pausedState.getHeight());
        image.setColor(new Color(255,255,255, 128));
        image.fillRect(0, 0, pausedState.getWidth(), pausedState.getHeight());
        Font font = image.getFont();
        font = font.deriveFont(FONT_SIZE);
        image.setFont(font);
        image.setColor(Color.WHITE);
        image.drawString("(P)", 4, 12);
        Font font_reg = image.getFont();
        font_reg = font_reg.deriveFont(FONT_SIZE_NORMAL);
        image.setFont(font_reg);
        image.drawString("Pause", 4, 22);
        return image;
    }
}
