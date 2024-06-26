import greenfoot.*;


/**
 * The ScoreBoard is used to display results on the screen. It can display some
 * text and a score.
 * 
 * @author M Kölling
 * @version 1.0
 */
public class ScoreBoard extends Actor
{
    public static final float FONT_SIZE = 48.0f;
    public static final float REG_TEXT_FONT_SIZE = 12.0f;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    
    /**
     * Create a score board with dummy result for testing.
     */
    public ScoreBoard()
    {
        this(100);
    }

    /**
     * Create a score board for the final result.
     */
    public ScoreBoard(int score)
    {
        makeImage("Game Over", "Score: ", score);
    }
    
    public ScoreBoard(String title, String paragraph) {
        makeImage(title, paragraph, -1);
    }

    /**
     * Make the score board image.
     */
    private void makeImage(String title, String prefix, int score)
    {
        GreenfootImage image = new GreenfootImage(WIDTH, HEIGHT);

        image.setColor(new Color(255,255,255, 128));
        image.fillRect(0, 0, WIDTH, HEIGHT);
        image.setColor(new Color(0, 0, 0, 128));
        image.fillRect(5, 5, WIDTH-10, HEIGHT-10);
        Font font = image.getFont();
        font = font.deriveFont(FONT_SIZE);
        image.setFont(font);

        image.setColor(Color.WHITE);
        image.drawString(title, 60, 100);
        image.drawString(prefix + score, 60, 200);
        Font font_reg = image.getFont();
        font_reg = font_reg.deriveFont(REG_TEXT_FONT_SIZE);
        image.setFont(font_reg);
        image.drawString("Press 'P' to restart with a new session.", 60, 250);
        setImage(image);
    }
}