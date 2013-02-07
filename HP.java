import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*; 
//"HEALTH: " TEKST
public class HP extends Actor
{
    public HP() {  
        GreenfootImage img = new GreenfootImage(200, 200);     
        img.setColor(new Color(255,255,255));    
        Font font = img.getFont();      
 
        img.setFont(font);      
        img.drawString("Health: ", 5, 195);  
        setImage(img);      
    }   
}
