package com.bow.forest.common.mqlite;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2017/5/20.
 */
public abstract class AbstractServerThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServerThread.class);

    /**
     * acceptor or processor's selector
     */
    private Selector selector;

    /**
     * indicate running state
     */
    protected final AtomicBoolean alive = new AtomicBoolean(false);

    public Selector getSelector() {
        if (selector == null) {
            try {
                selector = Selector.open();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return selector;
    }

    protected boolean isRunning() {
        return alive.get();
    }


}
