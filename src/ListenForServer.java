import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.DefaultMessage;
import message.NewClientMessage;

/**
 * This class is listening for incoming messages from the server
 * @author Simon Berntsspn
 *
 */
public class ListenForServer implements Runnable {

    private Socket socket;
    private Client client;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public ListenForServer(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }
    
    private void initStreams() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        initStreams();
        System.out.println("Client started listen on server!");
        Object input = null;
        while(true) {
            try {
                input = in.readObject();
                if(input == null)
                    break;
                handleMessage(input);
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Client is closing");
                break;
            }
        }
    }
    
    private void handleMessage(Object input) {
        if(input instanceof DefaultMessage) {
            DefaultMessage message = (DefaultMessage)input;
            System.out.println("Client received DefaultMessage: " + message.getMessage());
        } else if(input instanceof NewClientMessage) {
            NewClientMessage message = (NewClientMessage)input;
            client.setId(message.getClientId());
            System.out.println("Client received NewClientMessage: " + message.getClientId());
        }
    }
}
