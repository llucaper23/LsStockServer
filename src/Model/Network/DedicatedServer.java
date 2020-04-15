package Model.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DedicatedServer extends Thread {

    private boolean isOn;
    private Socket sClient;
    private ObjectOutputStream objectOut;
    private Server server;

    public DedicatedServer(boolean isOn, Socket sClient, ObjectOutputStream objectOut, Server server) {
        this.isOn = isOn;
        this.sClient = sClient;
        this.objectOut = objectOut;
        this.server = server;
    }
}
