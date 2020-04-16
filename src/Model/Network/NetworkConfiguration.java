package Model.Network;

public class NetworkConfiguration {

    // constants relacionades amb la comunicacio
    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 34567;
    public static final String DB_ADDRESS = "lsstock-database.mysql.database.azure.com";
    public static final String DB_USER = "ls_stock@lsstock-database";
    public static final String DB_PASS = "Hola123$";

    public static String getDbAddress() {
        return DB_ADDRESS;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPass() {
        return DB_PASS;
    }
}
