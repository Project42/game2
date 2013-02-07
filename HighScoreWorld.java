import greenfoot.*;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HighScoreWorld extends World {
    private Game game;
    
    public HighScoreWorld(Game game) {
        super(700, 500, 1);
        this.game = game;
        
        List<HighScore> highScores;
        try {
            highScores = HighScore.readHighScores(HighScore.defaultFilenameForGame(game));
        } catch (IOException e) {
            highScores = new ArrayList<HighScore>();
        }
        
        GreenfootImage textImage = new GreenfootImage("images/highScores.png");
        textImage.setColor(Color.WHITE);
        textImage.setFont(new Font("Arial", Font.PLAIN, 20));
        
        int i = 0;
        for (HighScore highScore : highScores) {
            ++i;
            textImage.drawString("#" + i, 25, 200 + i * 25);
            textImage.drawString(highScore.getName(), 210, 200 + i * 25);
            textImage.drawString("" + highScore.getScore(), 590, 200 + i * 25);
        }
        
        setBackground(textImage);
    }
}
