/**
 *
 */
package com.esb.task;

import com.esb.communication.EsbClient;
import com.esb.operation.fileOperation.FileOperator;

import java.io.File;

/**
 * @author GongYining
 */
public class DirScanTask implements Runnable {

    private final String dirName;
    private final EsbClient client;

    public DirScanTask(String dirName,EsbClient client) {
        this.dirName = dirName;
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            File file = new File(dirName);
            File[] files = file.listFiles();
            for (File child : files) {
                FileOperator operator = new FileOperator();
                byte[] fileBytes = operator.readFile(child);
                String fileName = child.getName();
                System.out.println("!!!!! file :" + fileName);
                client.fileTrans(fileName,fileBytes);
                child.delete();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
