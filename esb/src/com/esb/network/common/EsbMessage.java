/**
 *
 */
package com.esb.network.common;

/**
 * @author GongYining
 */
public class EsbMessage {

    private int Type;
    private int length;
    private byte[] body;

    public EsbMessage(int type, int length, byte[] body) {
        Type = type;
        this.length = length;
        this.body = body;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "EsbMessage{" +
                "Type=" + Type +
                ", length=" + length +
                ", body=" + new String(body) +
                '}';
    }
}
