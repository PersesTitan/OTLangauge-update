package cos.audio.work;

import cos.audio.etc.AudioToken;
import cos.audio.item.AudioItem;
import cos.file.etc.FileToken;
import cos.file.item.FileItem;
import work.CreateWork;

public class CreateAudio extends CreateWork<AudioItem> {
    public CreateAudio() {
        super(AudioItem.class, AudioToken.AUDIO, FileToken.FILE);
    }

    @Override
    protected Object createItem(Object[] params) {
        return new AudioItem((FileItem) params[0]);
    }
}
