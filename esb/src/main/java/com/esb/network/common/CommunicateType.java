package com.esb.network.common;

/**
 * @author GongYining
 */
public enum CommunicateType {

    REGISTER(0),
    QUERY(1),
    HEARTBEAT(2),
    FILETRANS(3),
    SYNC(4);

    private int id;

    private CommunicateType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
