import com.esb.communication.EsbClient;
import com.esb.network.common.ConfReader;
import com.esb.task.DirScanTask;

import java.io.File;
import java.util.Properties;

public class ClientDemo {
    public static void main(String[] args) throws InterruptedException {
        File confFile = new File("conf/filetrans.properties");
        Properties properties = ConfReader.loadProperties(confFile);
        String srcdir = properties.getProperty("srcpath");
        EsbClient client = new EsbClient("localhost","7856");
        client.connect();
        DirScanTask dirScanTask = new DirScanTask(srcdir,client);
        new Thread(dirScanTask).start();
        Thread.sleep(5000);
        client.close();
    }
}
