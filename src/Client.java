import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private Socket socket;
    private String name;
    private int id;
    private int port;
    private String serverUrl;
    private ExecutorService threads;
    private ListenForServer serverListener;
    
    public Client() {
        this.threads = Executors.newSingleThreadExecutor();
    }
    
    public void connectToServer(String host, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(host, port);
        this.serverUrl = host;
        this.port = port;
        this.serverListener = new ListenForServer(socket, this);
        threads.execute(this.serverListener);
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
}
