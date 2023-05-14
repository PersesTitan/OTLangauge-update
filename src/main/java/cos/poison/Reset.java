package cos.poison;

import bin.Repository;
import cos.poison.etc.PoisonToken;
import cos.poison.item.PoisonItem;
import cos.poison.mode.MethodMode;
import cos.poison.work.PoisonSetCookie;
import cos.poison.work.create.CreatePoison;
import cos.poison.work.method.PoisonMethod;
import work.ResetWork;

public class Reset implements ResetWork, PoisonToken {
    @Override
    public void reset() {
        Repository.createWorks.put(POISON, new CreatePoison());

        AddWork<PoisonItem> addWork = new AddWork<>(POISON);

        Repository.startWorks.put(POISON, SET_COOKIE, new PoisonSetCookie());
        addWork.addS(CREATE, PoisonItem::create);
        addWork.addS(START, PoisonItem::start);
        addWork.addS(STOP, PoisonItem::stop);
        addWork.addS(SET_HOST, s, PoisonItem::setHost);
        addWork.addS(SET_PORT, i, PoisonItem::setPort);
        addWork.addS(SET_TYPE, s, PoisonItem::setType);
        addWork.addS(SET_DATA, s, PoisonItem::setDataType);
        addWork.addS(REDIRECT, s, PoisonItem::redirect);
        addWork.addS(DELETE_COOKIE, s, s, PoisonItem::deleteCookie);

        addWork.addR(s, GET_COOKIE, s, PoisonItem::getCookie);
        addWork.addR(b, IS_EMPTY_COOKIE, s, PoisonItem::isCookie);

        Repository.loopWorks.put(POISON, POST, new PoisonMethod(MethodMode.POST));
        Repository.loopWorks.put(POISON, GET, new PoisonMethod(MethodMode.GET));
        Repository.loopWorks.put(POISON, PATCH, new PoisonMethod(MethodMode.PATCH));
        Repository.loopWorks.put(POISON, PUT, new PoisonMethod(MethodMode.PUT));
        Repository.loopWorks.put(POISON, DELETE, new PoisonMethod(MethodMode.DELETE));
    }
}
