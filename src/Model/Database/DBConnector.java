package Model.Database;

import Model.Network.NetworkConfiguration;

import java.sql.*;


public class DBConnector {

    private static String userName;
    private static String password;
    private static String db;
    private static int port;
    private static String url = null;
    private static Connection conn;
    private static Statement s;
    private static DBConnector instance = null;
    private static String dbName = "lsstock";

    /**
     *Constructor de la clase
     * @param usr usuario de la BBDD
     * @param pass contraseña de la BBDD
     */
    private DBConnector(String usr, String pass, String url) {
        this.userName = usr;
        this.password = pass;
        this.instance = null;
        this.url = url;

    }

    /**
     *Método estático que genera una intancia de la clase para implementar JavaSingleton.
     * @return instancia de la clase.
     */
    public static DBConnector getInstance(){
        return  instance;
    }

    /**
     *Método que inicializa la clase
     * @param usr usuario de la BBDD
     * @param psw contraseña de la BBDD.
     * @return instancia de la clase
     */
    public static DBConnector init(String usr,String psw, String url){
        instance = new DBConnector(usr,psw,url);
        instance.connect();
        return instance;
    }

    public static DBConnector end(){
        //instance = new DBConnector(usr,psw);
        instance.disconnect();
        return instance;
    }



    /**
     *Método que permite la conexión con la BBDD.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");

            String url ="jdbc:mysql://lsstock-database.mysql.database.azure.com:3306/lsstock?useSSL=true&requireSSL=false";
            conn = DriverManager.getConnection(url, "ls_stock@lsstock-database", NetworkConfiguration.DB_PASS);

            if (conn != null) {
                System.out.println("Connexió a base de dades "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            System.out.println("Problema al connectanos a la BBDD --> "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    /**
     *Método que permite insertar una query en la BBDD.
     * @param query query
     */
    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.execute(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Problema al inserir --> " + ex.getSQLState());
        }
    }

    /**
     *Método que permite hacer un update en la BBDD.
     * @param query query
     */
    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al modificar --> " + ex.getSQLState());
        }
    }

    /**
     *Método que permite hacer un delete en la BBDD.
     * @param query query
     */
    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al eliminar --> " + ex.getSQLState());
        }

    }

    /**
     *Método que permite hacer un select en la BBDD.
     * @param query query
     * @return resultado
     */
    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al recuperar los datos --> " + ex.getSQLState());
        }
        return rs;
    }


    public void disconnect(){
        try {
            conn.close();
            System.out.printf("Desconexión de la base de datos " + url);
        } catch (SQLException e) {
            System.out.println("Problema al cerrar la connexión --> " + e.getSQLState());
        }
    }

}
