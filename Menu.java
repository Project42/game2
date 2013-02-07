import greenfoot.*;

/**
 * Bevat alle menuitems
 * 
 */
public class Menu extends Actor
{
    /** Checks if object has been clicked or pressed */
    public void checkClicked(int character) 
    {
        ControlroomWorld world = (ControlroomWorld)getWorld();
        
        /** Checks if menu objects can be clicked or selected by keyboard */
        if (world.getPoliceUnits().getValue() == 0) {
                world.getShutOffMenu().setImage("shut_off_street_depleted.png");
                world.getEvacuateMenu().setImage("evac_vehicle_depleted.png");
                world.getCatchThiefMenu().setImage("arrest_depleted.png");
        } else {
               /** Checks whether object has been clicked
                 * If true, sets Character to the right variable
                 */
            if (Greenfoot.mouseClicked(this)) {
                if (character == 2) {
                     world.setSelectedCharacter(ControlroomWorld.Character.POLICE_SHUTOFF);
                } else if (character == 3) {
                     world.setSelectedCharacter(ControlroomWorld.Character.POLICE_EVACUATE);
                } else if (character == 4) {
                     world.setSelectedCharacter(ControlroomWorld.Character.POLICE_CATCHTHIEF);
                }
            }
            
            /** Checks if keyboard has been pressed, sets character if so */
            if (Greenfoot.isKeyDown("2")) {
                world.setSelectedCharacter(ControlroomWorld.Character.POLICE_SHUTOFF);
            }
            
            if (Greenfoot.isKeyDown("3")) {
                 world.setSelectedCharacter(ControlroomWorld.Character.POLICE_CATCHTHIEF);
            }
            
            /** Switches image if an object has been selected, else keeps the original image */
            if (Greenfoot.isKeyDown("4")) {
                 world.setSelectedCharacter(ControlroomWorld.Character.POLICE_EVACUATE);
            } else if (world.getSelectedCharacter() == ControlroomWorld.Character.POLICE_SHUTOFF) {
                world.getShutOffMenu().setImage("shut_off_street_glow.png");
                world.getExtinguishMenu().setImage("firefighter.png");
                world.getEvacuateMenu().setImage("evac_vehicle.png");
                world.getCatchThiefMenu().setImage("arrest.png");
            } else if (world.getSelectedCharacter() == ControlroomWorld.Character.POLICE_EVACUATE) {
                world.getEvacuateMenu().setImage("evac_vehicle_glow.png");
                world.getExtinguishMenu().setImage("firefighter.png");
                world.getShutOffMenu().setImage("shut_off_street.png");
                world.getCatchThiefMenu().setImage("arrest.png");
            } else if (world.getSelectedCharacter() == ControlroomWorld.Character.POLICE_CATCHTHIEF) {
                world.getCatchThiefMenu().setImage("arrest_glow.png");
                world.getExtinguishMenu().setImage("firefighter.png");
                world.getShutOffMenu().setImage("shut_off_street.png");
                world.getEvacuateMenu().setImage("evac_vehicle.png");
            } else {
                world.getExtinguishMenu().setImage("firefighter.png");
                world.getShutOffMenu().setImage("shut_off_street.png");
                world.getEvacuateMenu().setImage("evac_vehicle.png");
                world.getCatchThiefMenu().setImage("arrest.png");
            }
        }
        
        /** Checks if the firefighter can be clicked or selected by keyboard */
        if (world.getFirefighterUnits().getValue() == 0) {
             world.getExtinguishMenu().setImage("firefighter_depleted.png");
        } else {
           
          /** Checks whether the firefighter object has been clicked
            * If true, sets Character to FIREFIGHTER
            */
           
          if (Greenfoot.mouseClicked(this)) {
              if (character == 1) {
                  world.setSelectedCharacter(ControlroomWorld.Character.FIREFIGHTER);
                }
            }
                
          /** Checks if a key has been pressed, sets character to FIREFIGHTER if so */
          if (Greenfoot.isKeyDown("1")) {
                world.setSelectedCharacter(ControlroomWorld.Character.FIREFIGHTER);
            }
            
          /** Switches image if the object has been selected, else keeps the original image */
          if (world.getSelectedCharacter() == ControlroomWorld.Character.FIREFIGHTER) {
                world.getExtinguishMenu().setImage("firefighter_glow.png");
                world.getShutOffMenu().setImage("shut_off_street.png");
                world.getEvacuateMenu().setImage("evac_vehicle.png");
                world.getCatchThiefMenu().setImage("arrest.png");
            }        
        }
    }
}