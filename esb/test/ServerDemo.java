import com.esb.communication.EsbServer;

public class ServerDemo {
    public static void main(String[] args) {
        EsbServer server = new EsbServer(7856);
        try {
            server.open();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("process end!!!");
    }
}
