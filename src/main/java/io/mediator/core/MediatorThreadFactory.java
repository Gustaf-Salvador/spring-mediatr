package io.mediator.core;

import java.util.concurrent.ThreadFactory;

public class MediatorThreadFactory implements ThreadFactory  {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "Mediator-$counter");
    }
}
