/**
 *
 */
package com.esb.operation.fileOperation;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author GongYining
 */
public class FileOperator {


    public byte[] readFile(File file) {
        byte[] files = null;
        try {
            files = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public void writeFile(byte[] message,String destPath) {
        File file = new File(destPath);
        try {
            FileUtils.writeByteArrayToFile(file,message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
