package io.mediator.core;

import java.util.concurrent.ThreadFactory;

public class MediatorThreadFactory implements ThreadFactory  {

    private int counter = 0;

    @Override
    public Thread newThread(Runnable r) {
        counter++;

        return new Thread(r, "Mediator-$counter");
    }
}
