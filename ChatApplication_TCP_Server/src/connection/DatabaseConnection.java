/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;

/**
 *
 * @author HOANG
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {
    }
    
    public void connectionToData() throws SQLException{
        String server = "localhost";
        String port = "3306";
        String database = "applicationchat";
        String username = "root";
        String passwork = "";
        connection = (Connection) java.sql.DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database , username, passwork);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
