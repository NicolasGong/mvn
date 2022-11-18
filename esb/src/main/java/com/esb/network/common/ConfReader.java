/**
 *
 */
package com.esb.network.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author GongYining
 */
public class ConfReader {

    public static Properties loadProperties(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(fileInputStream);
            return props;
        } catch (Exception e) {
            throw new RuntimeException("Load property config error!", e);
        }
    }

}
