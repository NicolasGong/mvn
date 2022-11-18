/**
 *
 */
package com.esb.communication;

import com.esb.network.client.EsbNettyClient;
import com.esb.network.cluster.MessageCache;
import com.esb.network.common.CommunicateType;
import com.esb.network.common.EsbMessage;
import com.esb.network.common.EsbUtils;

/**
 * @author GongYining
 */
public class EsbClient {

    private MessageCache cache;
    private EsbNettyClient nettyClient;

    public EsbClient(String ip, String port) {
        this.nettyClient = new EsbNettyClient(ip, port);
        cache = MessageCache.getInstance();
    }

    public void connect() throws InterruptedException {
        nettyClient.createBootStrap();
    }

    public void close() {
        nettyClient.doClose();
    }

    public void register(byte[] regBody) {
        EsbMessage message = new EsbMessage(CommunicateType.REGISTER.getId(), regBody.length,
                regBody);
        cache.putMessage(message);
        System.out.println("registe message!!!");
    }

    public void fileTrans(String filename, byte[] fileTransBody) {
        if (filename == null || filename.trim().equals("") || fileTransBody == null ||
                fileTransBody.length == 0) {
            System.out.println("File transfer error!!!");
            return;
        }
        byte[] filenameBytes = filename.getBytes();
        short length = (short) filenameBytes.length;
        byte[] fileNameLength = EsbUtils.shortToByte(length);
        byte[] file = new byte[fileNameLength.length + length + fileTransBody.length];
        System.out.println("***file length :" + length);
        System.arraycopy(fileNameLength, 0, file, 0, fileNameLength.length);
        System.arraycopy(filenameBytes, 0, file, fileNameLength.length,
                filenameBytes.length);
        System.arraycopy(fileTransBody, 0, file, fileNameLength.length + length, fileTransBody.length);
        EsbMessage message = new EsbMessage(CommunicateType.FILETRANS.getId(),
                file.length,
                file);
        cache.putMessage(message);
        System.out.println("fileTrans message!!!");

    }

}
