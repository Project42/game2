import greenfoot.*;
import java.util.Collection;
import java.util.HashSet;

public class SandbagMuteButton extends Actor {
    private static GreenfootImage mutedImage;
    private static GreenfootImage unmutedImage;

    private boolean muted;
    private Collection<GreenfootSound> registeredSounds;

    public SandbagMuteButton() {
        registeredSounds = new HashSet<GreenfootSound>();
        unmute();
    }

    @Override
    public void act() {
        if (Greenfoot.mouseClicked(this)) toggleMute();
    }

    public void registerSound(GreenfootSound sound) {
        registeredSounds.add(sound);
    }

    public void unregisterSound(GreenfootSound sound) {
        registeredSounds.remove(sound);
    }

    public boolean isMuted() {
        return muted;
    }

    private void toggleMute() {
        if (muted) unmute(); else mute();
    }

    private void mute() {
        if (mutedImage == null) mutedImage = new GreenfootImage("images/volumeknop_uit.png");

        muted = true;
        for (GreenfootSound sound : registeredSounds) {
            sound.setVolume(0);
        }
        setImage(mutedImage);
    }

    private void unmute() {
        if (unmutedImage == null) unmutedImage = new GreenfootImage("images/volumeknop_aan.png");

        muted = false;
        for (GreenfootSound sound : registeredSounds) {
            sound.setVolume(100);
        }
        setImage(unmutedImage);
    }
}

