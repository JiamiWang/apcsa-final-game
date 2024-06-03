import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

public class OptionsPanel extends Labels
{
    public static final float FONT_SIZE_REG = 10.0f;
    public static final float FONT_SIZE_TITLE = 16.0f;
    
    Session sess;
    
    private enum OptionsPage {
        SCORE_LEADERBOARD
    }
    
    public OptionsPanel(Session sess) {
        this.sess = sess; updateImage();
    }
    
    public void updateImage() {
        GreenfootImage image = new GreenfootImage(
            getImage().getWidth(), getImage().getHeight()
        );
        
        image.setColor(new Color(255,255,255, 128));
        image.fillRect(0, 0, getImage().getWidth(), getImage().getHeight());
        image.setColor(new Color(0, 0, 0, 128));
        image.fillRect(5, 5, getImage().getWidth()-10, getImage().getHeight()-10);
        Font font = image.getFont();
        font = font.deriveFont(FONT_SIZE_TITLE);
        image.setFont(font);

        image.setColor(Color.WHITE);
        image.drawString("Game Options", 10, 32);
        
        Font font_reg = image.getFont();
        font_reg = font_reg.deriveFont(FONT_SIZE_REG);
        image.setFont(font_reg);

        image.setColor(Color.WHITE);
        image.drawString("Press 'P' to start/resume the game", 10, 15);
        
        String usernameStr = getUsername();
        usernameStr = usernameStr == null ? 
            "Not logged in." : "Hiya, " + usernameStr;
        image.setColor(Color.GREEN);
        image.drawString(usernameStr,
        image.getWidth() - (usernameStr.length() * 5) - 10, 14);
        
        image.drawString("Leaderboard", 10, 46);
        image.setColor(Color.WHITE);
        image.drawString("1) " + getLeaderboardFormat(0), 10, 56);
        image.drawString("2) " + getLeaderboardFormat(1), 10, 66);
        image.drawString("3) " + getLeaderboardFormat(2), 10, 76);
        image.setColor(Color.GREEN);
        image.drawString("My opps (those to take care of)", 10, 96);
        image.setColor(Color.WHITE);
        image.drawString("/\\ " + "a", 10, 106);
        image.drawString("-> " , 10, 116);
        image.drawString("\\/ ", 10, 126);
        setImage(image);
    }
    
    public String getUsername() {
        if (!UserInfo.isStorageAvailable()) return null;
        return UserInfo.getMyInfo().getUserName();
    }
    
    public String getLeaderboardFormat(int rank) {
        List<UserInfo> list = UserInfo.getTop(3);
        String assemble = "";
        if (list == null) assemble = "Cannot fetch storage.";
        else assemble = list.size() >= rank + 1 ? 
            list.get(rank).getUserName() + " - " + list.get(rank).getScore() :
            "N/A";
        return assemble;
    }
    
    public String getNearbyOppositionsFormat(int rank) {
        if (!UserInfo.isStorageAvailable()) return "Storage is not accessible.";
        return null;
    }
    
    public void act()
    {
        // Add your action code here.
    }
}
