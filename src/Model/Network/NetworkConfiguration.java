package Model.Network;

public class NetworkConfiguration {

    // constants relacionades amb la comunicacio
    private String serverIp;
    private int serverPort;
    private String dbAddress;
    private String dbUser;
    private String dbPass;

    public NetworkConfiguration() {

    }

    public String getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getDbAddress() {
        return dbAddress;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }
}
