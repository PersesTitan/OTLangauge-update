package cos.audio.item;

import bin.exception.FileException;
import bin.exception.SystemException;
import bin.token.Token;
import cos.audio.etc.AudioException;
import cos.audio.etc.AudioToken;
import cos.audio.etc.Sonic;
import cos.file.item.FileItem;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Getter
@Setter
public class AudioItem {
    private final File file;
    private final Sonic sonic;
    private float speed = 1.0f;
    private float pitch = 1.0f;
    private float rate = 1.0f;
    private float volume = 1.0f;
    private int loop = 1;

    private int quality = 0;
    private boolean emulateChordPitch = false;

    public AudioItem(FileItem fileItem) {
        this.file = fileItem.getFile();
        this.sonic = new Sonic();
    }

    public void start() {
        this.sonic.start(this);
    }

    public AudioInputStream getStream() {
        try {
            return AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            throw FileException.DO_NOT_READ.getThrow(file.getPath());
        } catch (IOException e) {
            throw SystemException.CREATE_ERROR.getThrow(file.getPath());
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed < 0 ? 1.0f / (-speed) : speed;
    }

    public void setVolume(float volume) {
        this.volume = volume < 0 ? 1.0f / (-volume) : volume;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch < 0 ? 1.0f / (-pitch) : pitch;
    }

    public void setRate(float rate) {
        this.rate = rate < 0 ? 1.0f / (-rate) : rate;
    }

    public void setLoop(int loop) {
        if (loop < 1) throw AudioException.LOOP_VALUE_ERROR.getThrow(loop);
        else this.loop = loop;
    }

    @Override
    public String toString() {
        return AudioToken.AUDIO + Token.PARAM_S + new FileItem(this.file) + Token.PARAM_E;
    }
}
