import greenfoot.*;
import javax.swing.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class Helicopter extends Actor {
    private int speed;
    private PowerUp powerUp;
    private int powerUpActsRemaining;
    public int respawnTimer = 1000000000;
    private int actsTillFlicker;
    private int xloop = 0;
    private String direction;
    private boolean visibility;
    private int radius;
    private int healthlost = 0;
    private int ropelength;
    private Rope rope;
    private Rope rope2;
    private Rope rope3;
    private Rope rope4;
    private Rope rope5;
    private Rope rope6;
    private Ropeman ropeman;

    public Helicopter() {
        speed = 1;
        radius = 5;
        rope = new Rope();
        rope2 = new Rope();
        rope3 = new Rope();
        rope4 = new Rope();
        rope5 = new Rope();
        rope6 = new Rope();
        ropeman = new Ropeman();
    }

    @Override
    public void act() {
        if(time+delay[currentIndex]<=System.currentTimeMillis()) {
            nextFrame();
        } 
        
        //Check user input
        if (Greenfoot.isKeyDown("w")) {
            move(0, -speed);
        }
        
        if (Greenfoot.isKeyDown("s")) {
            move(0, +speed);
        }
        
        if (Greenfoot.isKeyDown("a")) {
            move(-speed, 0); 
            switchImageLeft();
        }
        
        if (Greenfoot.isKeyDown("d")) {
            move(+speed, 0); 
            switchImageRight();
        }
        
        if (Greenfoot.isKeyDown("down")) {
            HelicopterWorld world = (HelicopterWorld)getWorld();
            int x = getX();
            int y = getY();
            increaseRope(x, y);
        }
        
        if (Greenfoot.isKeyDown("up")) {
            HelicopterWorld world = (HelicopterWorld)getWorld();
            int x = getX();
            int y = getY();
            decreaseRope(x, y);
        }
        
        //Check of helictopter de rand van het scherm aanraakt.
        
        if (atWorldEdge() == true) {    
           resetLocation();
        }   
        
        //Zorg ervoor dat powerups langzaam uitwerken.
        
        if (--powerUpActsRemaining <= 0) {
            setPowerUp(null);
        }
        
        //Zorg voor een flikkerende helicopter tijdens het respawnen.
        
        if (--respawnTimer <= 0) {
            if (xloop < 7) {
                if (--actsTillFlicker <= 0) {
                     if (!visibility) {
                         direction = "right";
                         setImage("helikopter_rechts.gif");
                         visibility = true;
                     } else {
                         direction = "right";
                         setImage("niets.gif");
                         visibility = false;
                     }
                     actsTillFlicker = 30;
                     xloop++;
                }
            }
            else if (xloop == 7) {
                respawnTimer = 10000000;
                setImage("helikopter_rechts.gif");
                speed = 1;
                xloop = 0;
            }
        }
        
        //Check collision
        
        Actor menubar = getOneObjectAtOffset(0, 1, MenuBar.class);
        if (menubar != null) {
            move(0, -1);
            if (powerUpActsRemaining > 0) {
                move(0, -1);
            }
        }
        
        Actor houselinks = getOneObjectAtOffset(1, 0, House.class);
        if (houselinks != null && Greenfoot.isKeyDown("d")) {
            resetLocation();
        }
        
        Actor houserechts = getOneObjectAtOffset(-2, 0, House.class);
        if (houserechts != null && Greenfoot.isKeyDown("a")) {
            resetLocation();
        }
        
        Actor houseboven = getOneObjectAtOffset(0, 1, House.class);
        if (houseboven != null && Greenfoot.isKeyDown("s")) {
            resetLocation();
        }
        
        Actor wall = getOneObjectAtOffset(-3, 0, Wall.class);
        if (wall != null && Greenfoot.isKeyDown("a")) {
            resetLocationWall();
        }
        
        int waterOffset = 70 - ((HelicopterWorld)getWorld()).getWaterLevel() / 2 / 10;
        if (waterOffset <= getY()) {
            resetLocation();
        }
        
        //Zet het touw op de goede locatie.
        
        if (ropelength > 0) {
            int x = getX()+1;
            int y = getY()+2;
            if (direction == "left") {
                x -= 2;
            }
            rope.setLocation(x,y);
            ropeman.setLocation(x,y+3);
        }
        
        if (ropelength > 24) {
            int x = getX()+1;
            int y = getY()+2;
            
            if (direction == "left") {
                x -= 2;
            }
            
            rope2.setLocation(x,y+2);
            ropeman.setLocation(x,y+5);
        }
        
        if (ropelength > 49) {
            int x = getX()+1;
            int y = getY()+2;
            
            if (direction == "left") {
                x -= 2;
            }
            
            rope3.setLocation(x,y+4);
            ropeman.setLocation(x,y+7);
        }
        
        if (ropelength > 74) {
            int x = getX()+1;
            int y = getY()+2;
            
            if (direction == "left") {
                x -= 2;
            }
            
            rope4.setLocation(x,y+6);
            ropeman.setLocation(x,y+9);
        }
        
        if (ropelength > 99) {
            int x = getX()+1;
            int y = getY()+2;
            
            if (direction == "left") {
                x -= 2;
            }
            
            rope5.setLocation(x,y+8);
            ropeman.setLocation(x,y+11);
        }
        
        if (ropelength > 124) {
            int x = getX()+1;
            int y = getY()+2;
            
            if (direction == "left") {
                x -= 2;
            }
            
            rope6.setLocation(x,y+10);
            ropeman.setLocation(x,y+13);
        }
        
        consumePowerUp();
    }
    
    public void setRadius(int r) {
        if (r <= 0) r = 1;
        radius = r;
    }
    
    public int getRadius() {
        return radius;
    }
    
    //Proces om de helikopter te laten bewegen.
    private void move(int dx, int dy) {
        setLocation(getX() + dx, getY() + dy);
    }
    
    //Proces om het touw langer te maken.
    public void increaseRope(int x , int y) {
        if (ropelength < 125){
            ropelength ++;
        }
        x += 1;
        y += 2;
        switch (ropelength) {
            case 1: getWorld().addObject(rope, x, y); getWorld().addObject(ropeman, x, y+3); break;
            case 25: y+= 2; getWorld().addObject(rope2, x, y); ropeman.setLocation(x, y+3); break;
            case 50: y+= 4; getWorld().addObject(rope3, x, y); ropeman.setLocation(x, y+3); break;
            case 75: y+= 6; getWorld().addObject(rope4, x, y); ropeman.setLocation(x, y+3); break;
            case 100: y+= 8; getWorld().addObject(rope5, x, y); ropeman.setLocation(x, y+3); break;
            case 125: y+= 10; getWorld().addObject(rope6, x, y); ropeman.setLocation(x, y+3); break;
        }
    }
    
    //Proces om het touw korter te maken.
    public void decreaseRope(int x, int y) {
        if (ropelength > -1) {
            ropelength --;
        }
        x += 1;
        y -= 2;
        switch (ropelength) {
            case 124: getWorld().removeObject(rope6); ropeman.setLocation(x, y+15); break;
            case 99: getWorld().removeObject(rope5); ropeman.setLocation(x, y+13); break;
            case 74: getWorld().removeObject(rope4); ropeman.setLocation(x, y+11); break;
            case 49: getWorld().removeObject(rope3); ropeman.setLocation(x, y+9); break;
            case 24: getWorld().removeObject(rope2); ropeman.setLocation(x, y+7); break;
            case 0: getWorld().removeObject(rope); getWorld().removeObject(ropeman); break;
        }
    }

    //Verhoog snelheid van helikopter
    public void increaseSpeed() {
        speed = 2;
    }

    //Verlaag snelheid van helikopter
    public void decreaseSpeed() {
        speed = 1;
    }
    
    //Powerups
    private void consumePowerUp() {
        Actor newPowerUp = (Actor)getOneIntersectingObject(PowerUp.class);
        if (newPowerUp != null) {
            setPowerUp((PowerUp)newPowerUp);
            newPowerUp.getWorld().removeObject(newPowerUp);
        }
    }

    private void setPowerUp(PowerUp newPowerUp) {
        if (newPowerUp == powerUp) return;
        if (powerUp != null) powerUp.remove(this);
        if (newPowerUp != null) {
            HelicopterWorld world = (HelicopterWorld)getWorld();
            world.addScore(100);
            powerUp = newPowerUp;
            powerUp.apply(this);
        }
        powerUpActsRemaining = 1000;
    }
    
    //Reset helikopter
    private void resetLocation() {
        speed = 0;
        Kaboom kaboom = new Kaboom();
        int x = getX();
        int y = getY();
        getWorld().addObject(kaboom, x, y);
        setLocation(40, 10);
        direction = "right";
        respawnTimer = 10;
        HelicopterWorld world = (HelicopterWorld)getWorld();
        world.lostLife();
    }
    
    //Reset helikopter bij botsing muur
    private void resetLocationWall() {
        speed = 0;
        Kaboom kaboom = new Kaboom();
        int x = getX();
        int y = getY();
        getWorld().addObject(kaboom, x, y);
        setLocation(50, 10);
        respawnTimer = 0;
        HelicopterWorld world = (HelicopterWorld)getWorld();
        world.lostLife();
    }
    
    //Zorg voor de goede sprite, helikopter naar links of rechts gericht.
    protected void switchImageLeft() {
        if (direction != "left" && xloop < 1){
            setImage("helikopter_links.gif");
            direction = "left";
        }
    }

    protected void switchImageRight() {
        if (direction != "right" && xloop < 1){   
            setImage("helikopter_rechts.gif");
            direction = "right";
        }
    }
    
    public boolean atWorldEdge() {   
        if (getY() == 0) {  
            return true;
        } 
        else {  
            return false;  
        }  
    }  
    
    public void addedToWorld(World world)
    {
        setImage("helikopter_rechts.gif");
    }
    
    /** CODE VOOR GIF ANIMATIES */
    
    /** The images used in the animation. */
    private GreenfootImage[] images;
    /** The delay between each frame. */
    private int[] delay;
    /** The index of the current frame in the GIF file. */
    private int currentIndex;
    /** The time passed since the last frame in ms. */
    private long time;
    /** The GIF file that contains the animation. */
    private String file;
    
    /**
     * Set the image of the actor. If the image is a normal picture, it will be displayed as normal.
     * If it's an animated GIF file then it will be displayed as an animated actor.
     * @overrides setImage in Actor.
     */
    public void setImage(String file)
    {
        this.file = file;
        if(file.endsWith(".gif")) {
            currentIndex = 0;
            loadImages();
            setImage(images[0]);
        }
        else {
            super.setImage(file);
        }
    }
    
    /**
     * Get all the images used in the animation
     * @return a list of GreenfootImages, corresponding to each frame.
     */
    public List<GreenfootImage> getImages()
    {
        ArrayList<GreenfootImage> images = new ArrayList<GreenfootImage>(this.images.length);
        for(GreenfootImage image : this.images) {
            images.add(image);
        }
        return images;
    }
    
    /**
     * Move onto the next frame if the time for the current frame has expired.
     */
 
    
    /**
     * Advance to the next frame in the animation.
     */
    private void nextFrame()
    {
        time = System.currentTimeMillis();
        currentIndex = (currentIndex+1) % images.length;
        setImage(images[currentIndex]);
    }

    /**
     * Load the images
     */
    private void loadImages()
    {
        GifDecoder decode = new GifDecoder();
        decode.read(file);
        int numFrames = decode.getFrameCount();
        if(numFrames>0) {
            images = new GreenfootImage[numFrames];
            delay = new int[numFrames];
        }
        else {
            images = new GreenfootImage[1];
            images[0] = new GreenfootImage(1, 1);
        }
        
        for (int i=0 ; i<numFrames ; i++) {
            GreenfootImage image = new GreenfootImage(decode.getFrame(i).getWidth(), decode.getFrame(i).getHeight());
            BufferedImage frame = image.getAwtImage();
            Graphics2D g = (Graphics2D)frame.getGraphics();
            g.drawImage(decode.getFrame(i), null, 0, 0);
            delay[i] = decode.getDelay(i);
            images[i] = image;
        }
        time = System.currentTimeMillis();
    }

    
    /**
     * Class GifDecoder - Decodes a GIF file into one or more frames. <br><br>
     * 
     * <i>I (Michael) edited this slightly on 10/09/08 to bring class up to date with generics and therefore remove warnings.
     * Also edited so that resources are grabbed from the jar file and not externally, so no security exceptions.</i>
     * <br><br>
     * <pre>
     *  Example:
     *     GifDecoder d = new GifDecoder();
     *     d.read(&quot;sample.gif&quot;);
     *     int n = d.getFrameCount();
     *     for (int i = 0; i &lt; n; i++) {
     *        BufferedImage frame = d.getFrame(i);  // frame i
     *        int t = d.getDelay(i);  // display duration of frame in milliseconds
     *        // do something with frame
     *     }
     * </pre>
     * 
     * No copyright asserted on the source code of this class. May be used for any
     * purpose, however, refer to the Unisys LZW patent for any additional
     * restrictions. Please forward any corrections to kweiner@fmsware.com.
     * 
     * @author Kevin Weiner, FM Software; LZW decoder adapted from John Cristy's
     *         ImageMagick.
     * @version 1.03 November 2003
     * 
     */
    private class GifDecoder {
    
      /**
       * File read status: No errors.
       */
      public static final int STATUS_OK = 0;
    
      /**
       * File read status: Error decoding file (may be partially decoded)
       */
      public static final int STATUS_FORMAT_ERROR = 1;
    
      /**
       * File read status: Unable to open source.
       */
      public static final int STATUS_OPEN_ERROR = 2;
    
      private BufferedInputStream in;
    
      private int status;
    
      private int width; // full image width
    
      private int height; // full image height
    
      private boolean gctFlag; // global color table used
    
      private int gctSize; // size of global color table
    
      private int loopCount = 1; // iterations; 0 = repeat forever
    
      private int[] gct; // global color table
    
      private int[] lct; // local color table
    
      private int[] act; // active color table
    
      private int bgIndex; // background color index
    
      private int bgColor; // background color
    
      private int lastBgColor; // previous bg color
    
      private int pixelAspect; // pixel aspect ratio
    
      private boolean lctFlag; // local color table flag
    
      private boolean interlace; // interlace flag
    
      private int lctSize; // local color table size
    
      private int ix, iy, iw, ih; // current image rectangle
    
      private Rectangle lastRect; // last image rect
    
      private BufferedImage image; // current frame
    
      private BufferedImage lastImage; // previous frame
    
      private byte[] block = new byte[256]; // current data block
    
      private int blockSize = 0; // block size
    
      // last graphic control extension info
      private int dispose = 0;
    
      // 0=no action; 1=leave in place; 2=restore to bg; 3=restore to prev
      private int lastDispose = 0;
    
      private boolean transparency = false; // use transparent color
    
      private int delay = 0; // delay in milliseconds
    
      private int transIndex; // transparent color index
    
      private static final int MaxStackSize = 4096;
    
      // max decoder pixel stack size
    
      // LZW decoder working arrays
      private short[] prefix;
    
      private byte[] suffix;
    
      private byte[] pixelStack;
    
      private byte[] pixels;
    
      private ArrayList<GifFrame> frames; // frames read from current file
    
      private int frameCount;
      
      /**
       * A single frame
       */
      private class GifFrame {
        public GifFrame(BufferedImage im, int del) {
          image = im;
          delay = del;
        }
    
        private BufferedImage image;
    
        private int delay;
      }
    
      /**
       * Gets display duration for specified frame.
       * 
       * @param n
       *          int index of frame
       * @return delay in milliseconds
       */
      public int getDelay(int n) {
        //
        delay = -1;
        if ((n >= 0) && (n < frameCount)) {
          delay = (frames.get(n)).delay;
        }
        return delay;
      }
    
      /**
       * Gets the number of frames read from file.
       * 
       * @return frame count
       */
      public int getFrameCount() {
        return frameCount;
      }
    
      /**
       * Gets the first (or only) image read.
       * 
       * @return BufferedImage containing first frame, or null if none.
       */
      public BufferedImage getImage() {
        return getFrame(0);
      }
    
      /**
       * Gets the "Netscape" iteration count, if any. A count of 0 means repeat
       * indefinitiely.
       * 
       * @return iteration count if one was specified, else 1.
       */
      public int getLoopCount() {
        return loopCount;
      }
    
      /**
       * Creates new frame image from current data (and previous frames as specified
       * by their disposition codes).
       */
      protected void setPixels() {
        // expose destination image's pixels as int array
        int[] dest = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    
        // fill in starting image contents based on last image's dispose code
        if (lastDispose > 0) {
          if (lastDispose == 3) {
            // use image before last
            int n = frameCount - 2;
            if (n > 0) {
              lastImage = getFrame(n - 1);
            } else {
              lastImage = null;
            }
          }
    
          if (lastImage != null) {
            int[] prev = ((DataBufferInt) lastImage.getRaster().getDataBuffer()).getData();
            System.arraycopy(prev, 0, dest, 0, width * height);
            // copy pixels
    
            if (lastDispose == 2) {
              // fill last image rect area with background color
              Graphics2D g = image.createGraphics();
              Color c = null;
              if (transparency) {
                c = new Color(0, 0, 0, 0); // assume background is transparent
              } else {
                c = new Color(lastBgColor); // use given background color
              }
              g.setColor(c);
              g.setComposite(AlphaComposite.Src); // replace area
              g.fill(lastRect);
              g.dispose();
            }
          }
        }
    
        // copy each source line to the appropriate place in the destination
        int pass = 1;
        int inc = 8;
        int iline = 0;
        for (int i = 0; i < ih; i++) {
          int line = i;
          if (interlace) {
            if (iline >= ih) {
              pass++;
              switch (pass) {
              case 2:
                iline = 4;
                break;
              case 3:
                iline = 2;
                inc = 4;
                break;
              case 4:
                iline = 1;
                inc = 2;
              }
            }
            line = iline;
            iline += inc;
          }
          line += iy;
          if (line < height) {
            int k = line * width;
            int dx = k + ix; // start of line in dest
            int dlim = dx + iw; // end of dest line
            if ((k + width) < dlim) {
              dlim = k + width; // past dest edge
            }
            int sx = i * iw; // start of line in source
            while (dx < dlim) {
              // map color and insert in destination
              int index = ((int) pixels[sx++]) & 0xff;
              int c = act[index];
              if (c != 0) {
                dest[dx] = c;
              }
              dx++;
            }
          }
        }
      }
    
      /**
       * Gets the image contents of frame n.
       * 
       * @return BufferedImage representation of frame, or null if n is invalid.
       */
      public BufferedImage getFrame(int n) {
        BufferedImage im = null;
        if ((n >= 0) && (n < frameCount)) {
          im = ((GifFrame) frames.get(n)).image;
        }
        return im;
      }
    
      /**
       * Gets image size.
       * 
       * @return GIF image dimensions
       */
      public Dimension getFrameSize() {
        return new Dimension(width, height);
      }
    
      /**
       * Reads GIF image from stream
       * 
       * @param BufferedInputStream
       *          containing GIF file.
       * @return read status code (0 = no errors)
       */
      public int read(BufferedInputStream is) {
        init();
        if (is != null) {
          in = is;
          readHeader();
          if (!err()) {
            readContents();
            if (frameCount < 0) {
              status = STATUS_FORMAT_ERROR;
            }
          }
        } else {
          status = STATUS_OPEN_ERROR;
        }
        try {
          is.close();
        } catch (IOException e) {
        }
        return status;
      }
    
      /**
       * Reads GIF image from stream
       * 
       * @param InputStream
       *          containing GIF file.
       * @return read status code (0 = no errors)
       */
      public int read(InputStream is) {
        init();
        if (is != null) {
          if (!(is instanceof BufferedInputStream))
            is = new BufferedInputStream(is);
          in = (BufferedInputStream) is;
          readHeader();
          if (!err()) {
            readContents();
            if (frameCount < 0) {
              status = STATUS_FORMAT_ERROR;
            }
          }
        } else {
          status = STATUS_OPEN_ERROR;
        }
        try {
          is.close();
        } catch (IOException e) {
        }
        return status;
      }
    
      /**
       * Reads GIF file from specified file/URL source (URL assumed if name contains
       * ":/" or "file:")
       * 
       * @param name
       *          String containing source
       * @return read status code (0 = no errors)
       */
      public int read(String name) {
        status = STATUS_OK;
        try {
          name = name.trim().toLowerCase();
          URL url = this.getClass().getClassLoader().getResource(name);
          if(url==null) {
              name = "images/" + name;
              url = this.getClass().getClassLoader().getResource(name);
              if(url==null) {
                  throw new RuntimeException("The specified gif file doesn't exist.");
              }
          }
          in = new BufferedInputStream(url.openStream());
          status = read(in);
        } catch (IOException e) {
          status = STATUS_OPEN_ERROR;
        }
    
        return status;
      }
    
      /**
       * Decodes LZW image data into pixel array. Adapted from John Cristy's
       * ImageMagick.
       */
      protected void decodeImageData() {
        int NullCode = -1;
        int npix = iw * ih;
        int available, clear, code_mask, code_size, end_of_information, in_code, old_code, bits, code, count, i, datum, data_size, first, top, bi, pi;
    
        if ((pixels == null) || (pixels.length < npix)) {
          pixels = new byte[npix]; // allocate new pixel array
        }
        if (prefix == null)
          prefix = new short[MaxStackSize];
        if (suffix == null)
          suffix = new byte[MaxStackSize];
        if (pixelStack == null)
          pixelStack = new byte[MaxStackSize + 1];
    
        // Initialize GIF data stream decoder.
    
        data_size = read();
        clear = 1 << data_size;
        end_of_information = clear + 1;
        available = clear + 2;
        old_code = NullCode;
        code_size = data_size + 1;
        code_mask = (1 << code_size) - 1;
        for (code = 0; code < clear; code++) {
          prefix[code] = 0;
          suffix[code] = (byte) code;
        }
    
        // Decode GIF pixel stream.
    
        datum = bits = count = first = top = pi = bi = 0;
    
        for (i = 0; i < npix;) {
          if (top == 0) {
            if (bits < code_size) {
              // Load bytes until there are enough bits for a code.
              if (count == 0) {
                // Read a new data block.
                count = readBlock();
                if (count <= 0)
                  break;
                bi = 0;
              }
              datum += (((int) block[bi]) & 0xff) << bits;
              bits += 8;
              bi++;
              count--;
              continue;
            }
    
            // Get the next code.
    
            code = datum & code_mask;
            datum >>= code_size;
            bits -= code_size;
    
            // Interpret the code
    
            if ((code > available) || (code == end_of_information))
              break;
            if (code == clear) {
              // Reset decoder.
              code_size = data_size + 1;
              code_mask = (1 << code_size) - 1;
              available = clear + 2;
              old_code = NullCode;
              continue;
            }
            if (old_code == NullCode) {
              pixelStack[top++] = suffix[code];
              old_code = code;
              first = code;
              continue;
            }
            in_code = code;
            if (code == available) {
              pixelStack[top++] = (byte) first;
              code = old_code;
            }
            while (code > clear) {
              pixelStack[top++] = suffix[code];
              code = prefix[code];
            }
            first = ((int) suffix[code]) & 0xff;
    
            // Add a new string to the string table,
    
            if (available >= MaxStackSize)
              break;
            pixelStack[top++] = (byte) first;
            prefix[available] = (short) old_code;
            suffix[available] = (byte) first;
            available++;
            if (((available & code_mask) == 0) && (available < MaxStackSize)) {
              code_size++;
              code_mask += available;
            }
            old_code = in_code;
          }
    
          // Pop a pixel off the pixel stack.
    
          top--;
          pixels[pi++] = pixelStack[top];
          i++;
        }
    
        for (i = pi; i < npix; i++) {
          pixels[i] = 0; // clear missing pixels
        }
    
      }
    
      /**
       * Returns true if an error was encountered during reading/decoding
       */
      protected boolean err() {
        return status != STATUS_OK;
      }
    
      /**
       * Initializes or re-initializes reader
       */
      protected void init() {
        status = STATUS_OK;
        frameCount = 0;
        frames = new ArrayList<GifFrame>();
        gct = null;
        lct = null;
      }
    
      /**
       * Reads a single byte from the input stream.
       */
      protected int read() {
        int curByte = 0;
        try {
          curByte = in.read();
        } catch (IOException e) {
          status = STATUS_FORMAT_ERROR;
        }
        return curByte;
      }
    
      /**
       * Reads next variable length block from input.
       * 
       * @return number of bytes stored in "buffer"
       */
      protected int readBlock() {
        blockSize = read();
        int n = 0;
        if (blockSize > 0) {
          try {
            int count = 0;
            while (n < blockSize) {
              count = in.read(block, n, blockSize - n);
              if (count == -1)
                break;
              n += count;
            }
          } catch (IOException e) {
          }
    
          if (n < blockSize) {
            status = STATUS_FORMAT_ERROR;
          }
        }
        return n;
      }
    
      /**
       * Reads color table as 256 RGB integer values
       * 
       * @param ncolors
       *          int number of colors to read
       * @return int array containing 256 colors (packed ARGB with full alpha)
       */
      protected int[] readColorTable(int ncolors) {
        int nbytes = 3 * ncolors;
        int[] tab = null;
        byte[] c = new byte[nbytes];
        int n = 0;
        try {
          n = in.read(c);
        } catch (IOException e) {
        }
        if (n < nbytes) {
          status = STATUS_FORMAT_ERROR;
        } else {
          tab = new int[256]; // max size to avoid bounds checks
          int i = 0;
          int j = 0;
          while (i < ncolors) {
            int r = ((int) c[j++]) & 0xff;
            int g = ((int) c[j++]) & 0xff;
            int b = ((int) c[j++]) & 0xff;
            tab[i++] = 0xff000000 | (r << 16) | (g << 8) | b;
          }
        }
        return tab;
      }
    
      /**
       * Main file parser. Reads GIF content blocks.
       */
      protected void readContents() {
        // read GIF file content blocks
        boolean done = false;
        while (!(done || err())) {
          int code = read();
          switch (code) {
    
          case 0x2C: // image separator
            readImage();
            break;
    
          case 0x21: // extension
            code = read();
            switch (code) {
            case 0xf9: // graphics control extension
              readGraphicControlExt();
              break;
    
            case 0xff: // application extension
              readBlock();
              String app = "";
              for (int i = 0; i < 11; i++) {
                app += (char) block[i];
              }
              if (app.equals("NETSCAPE2.0")) {
                readNetscapeExt();
              } else
                skip(); // don't care
              break;
    
            default: // uninteresting extension
              skip();
            }
            break;
    
          case 0x3b: // terminator
            done = true;
            break;
    
          case 0x00: // bad byte, but keep going and see what happens
            break;
    
          default:
            status = STATUS_FORMAT_ERROR;
          }
        }
      }
    
      /**
       * Reads Graphics Control Extension values
       */
      protected void readGraphicControlExt() {
        read(); // block size
        int packed = read(); // packed fields
        dispose = (packed & 0x1c) >> 2; // disposal method
        if (dispose == 0) {
          dispose = 1; // elect to keep old image if discretionary
        }
        transparency = (packed & 1) != 0;
        delay = readShort() * 10; // delay in milliseconds
        transIndex = read(); // transparent color index
        read(); // block terminator
      }
    
      /**
       * Reads GIF file header information.
       */
      protected void readHeader() {
        String id = "";
        for (int i = 0; i < 6; i++) {
          id += (char) read();
        }
        if (!id.startsWith("GIF")) {
          status = STATUS_FORMAT_ERROR;
          return;
        }
    
        readLSD();
        if (gctFlag && !err()) {
          gct = readColorTable(gctSize);
          bgColor = gct[bgIndex];
        }
      }
    
      /**
       * Reads next frame image
       */
      protected void readImage() {
        ix = readShort(); // (sub)image position & size
        iy = readShort();
        iw = readShort();
        ih = readShort();
    
        int packed = read();
        lctFlag = (packed & 0x80) != 0; // 1 - local color table flag
        interlace = (packed & 0x40) != 0; // 2 - interlace flag
        // 3 - sort flag
        // 4-5 - reserved
        lctSize = 2 << (packed & 7); // 6-8 - local color table size
    
        if (lctFlag) {
          lct = readColorTable(lctSize); // read table
          act = lct; // make local table active
        } else {
          act = gct; // make global table active
          if (bgIndex == transIndex)
            bgColor = 0;
        }
        int save = 0;
        if (transparency) {
          save = act[transIndex];
          act[transIndex] = 0; // set transparent color if specified
        }
    
        if (act == null) {
          status = STATUS_FORMAT_ERROR; // no color table defined
        }
    
        if (err())
          return;
    
        decodeImageData(); // decode pixel data
        skip();
    
        if (err())
          return;
    
        frameCount++;
    
        // create new image to receive frame data
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
    
        setPixels(); // transfer pixel data to image
    
        frames.add(new GifFrame(image, delay)); // add image to frame list
    
        if (transparency) {
          act[transIndex] = save;
        }
        resetFrame();
    
      }
    
      /**
       * Reads Logical Screen Descriptor
       */
      protected void readLSD() {
    
        // logical screen size
        width = readShort();
        height = readShort();
    
        // packed fields
        int packed = read();
        gctFlag = (packed & 0x80) != 0; // 1 : global color table flag
        // 2-4 : color resolution
        // 5 : gct sort flag
        gctSize = 2 << (packed & 7); // 6-8 : gct size
    
        bgIndex = read(); // background color index
        pixelAspect = read(); // pixel aspect ratio
      }
    
      /**
       * Reads Netscape extenstion to obtain iteration count
       */
      protected void readNetscapeExt() {
        do {
          readBlock();
          if (block[0] == 1) {
            // loop count sub-block
            int b1 = ((int) block[1]) & 0xff;
            int b2 = ((int) block[2]) & 0xff;
            loopCount = (b2 << 8) | b1;
          }
        } while ((blockSize > 0) && !err());
      }
    
      /**
       * Reads next 16-bit value, LSB first
       */
      protected int readShort() {
        // read 16-bit value, LSB first
        return read() | (read() << 8);
      }
    
      /**
       * Resets frame state for reading next image.
       */
      protected void resetFrame() {
        lastDispose = dispose;
        lastRect = new Rectangle(ix, iy, iw, ih);
        lastImage = image;
        lastBgColor = bgColor;
        int dispose = 0;
        boolean transparency = false;
        int delay = 0;
        lct = null;
      }
    
      /**
       * Skips variable length blocks up to and including next zero length block.
       */
      protected void skip() {
        do {
          readBlock();
        } while ((blockSize > 0) && !err());
      }
    }
}
