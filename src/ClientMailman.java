

import java.io.IOException;

import communication.IMailman;
import communication.ISender;
import identification.Identification.ID;
import identification.Receiver;
import identification.Sender;
import message.ISendableMessage;

public class ClientMailman implements IMailman {

    private int clientId;
    private ISender sender;
    
    public ClientMailman(ISender sender) {
        this.sender = sender;
    }
    
    public ClientMailman(ISender sender, int clientId) {
        this.sender = sender;
        this.clientId = clientId;
    }
    
    public void setClientId(int id) {
        this.clientId = id;
    }
    
    public void setSendType(ISender sender) {
        this.sender = sender;
    }
    
    public Object readIncoming() {
        try {
            return sender.getObjectInputStream().readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Client disconnected");
            return null;
        }
    }
    
    @Override
    public void sendToClient(ISendableMessage msg, int receiverClientId) {
        if(msg.getSender() == null)
            msg.setSender(new Sender(ID.CLIENT, clientId));
        
        if(msg.getReceiver() == null)
            msg.setReceiver(new Receiver(ID.CLIENT, receiverClientId));
        
        sender.send(msg);
    }

    @Override
    public void sendToServer(ISendableMessage msg) {
        if(msg.getSender() == null)
            msg.setSender(new Sender(ID.CLIENT, clientId));
        
        if(msg.getReceiver() == null)
            msg.setReceiver(new Receiver(ID.SERVER));
        
        sender.send(msg);
    }

    @Override
    public void setupCommunications() {
        sender.setupCommunications();
    }
}
