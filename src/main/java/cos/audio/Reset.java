package cos.audio;

import bin.Repository;
import cos.audio.etc.AudioToken;
import cos.audio.item.AudioItem;
import cos.audio.work.CreateAudio;
import work.ResetWork;

public class Reset implements ResetWork, AudioToken {
    @Override
    public void reset() {
        this.checkModuleError("file");

        Repository.createWorks.put(AUDIO, new CreateAudio());

        AddWork<AudioItem> addWork = new AddWork<>(AUDIO);

        addWork.addR(GET_LOOP, i, AudioItem::getLoop);
        addWork.addR(GET_PITCH, f, AudioItem::getPitch);
        addWork.addR(GET_SPEED, f, AudioItem::getSpeed);
        addWork.addR(GET_VOLUME, f, AudioItem::getVolume);

        addWork.addS(SET_LOOP, i, AudioItem::setLoop);
        addWork.addS(SET_PITCH, f, AudioItem::setPitch);
        addWork.addS(SET_SPEED, f, AudioItem::setSpeed);
        addWork.addS(SET_VOLUME, f, AudioItem::setVolume);
        addWork.addS(START, AudioItem::start);
    }
}
