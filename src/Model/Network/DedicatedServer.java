package Model.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class DedicatedServer extends Thread {

    private boolean isOn;
    private Socket sClient;
    private ObjectOutputStream objectOut;
    private Server server;

    public DedicatedServer(Socket sClient, Server server) {
        this.isOn = false;
        this.sClient = sClient;
        this.objectOut = objectOut;
        this.server = server;
    }

    public void startDedicatedServer() {
        // iniciem el servidor dedicat
        isOn = true;
        this.start();
    }

    public void stopDedicatedServer() {
        // aturem el servidor dedicat
        this.isOn = false;
        this.interrupt();
        // eliminem el servidor dedicat del conjunt de servidors dedicats
        server.remove(this);
    }
}
