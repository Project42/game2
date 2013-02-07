import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*; 
/**
 * Schrijft levens in de menubar
 **/
public class ControlroomLifes extends Actor
{
    public ControlroomLifes() {  
        GreenfootImage img = new GreenfootImage(200, 200);     
        img.setColor(new Color(255,255,255));    
        Font font = img.getFont();      
 
        img.setFont(font);      
        img.drawString("Levens: ", 5, 195);  
        setImage(img);      
    }   
}