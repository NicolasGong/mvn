/**
 *
 */
package com.esb.network.cluster;

import com.esb.network.common.EsbCommons;
import com.esb.network.common.EsbMessage;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author GongYining
 */
public class MessageCache {

    private ArrayBlockingQueue<EsbMessage> cache = new ArrayBlockingQueue<EsbMessage>(EsbCommons.CACHE_SIZE);
    private volatile static MessageCache instance = null;

    private MessageCache() {
    }

    public static MessageCache getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (MessageCache.class) {
            if (instance == null) {
                instance = new MessageCache();
            }

            return instance;
        }
    }

    public void putMessage(EsbMessage message) {
        try {
            cache.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public EsbMessage getMessage() {
        try {
            return cache.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
