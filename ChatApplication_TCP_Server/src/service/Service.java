/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import model.Model_Client;
import model.Model_Login;
import model.Model_Message;
import model.Model_Out;
import model.Model_Register;
import model.Model_User_Account;

/**
 *
 * @author HOANG
 */
public class Service {

    private static Service instance;
    private ServerSocket server;
    private ServiceUser serviceUser;
    private List<Handler_Server> listClient;
    private List<Model_Client> listClientStatus;
    private JTextArea textArea;
    private final int PORT_NUMBER = 9999;

    public static Service getInstance(JTextArea textArea) {
        if (instance == null) {
            instance = new Service(textArea);
        }
        return instance;
    }

    private Service(JTextArea textArea) {
        this.textArea = textArea;
        serviceUser = new ServiceUser();
        listClient = new ArrayList<>();
        listClientStatus = new ArrayList<>();
    }

    public void startServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(PORT_NUMBER);
                    setTextArea("Server has start on port: 9999\n");
                    while (true) {
                        Socket client = server.accept();
                        setTextArea("connect one client\n");
                        Handler_Server handler = new Handler_Server(client, System.currentTimeMillis() + "", Service.this);
                        listClient.add(handler);
                        new Thread(handler).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
//        Configuration config = new Configuration();
//        config.setPort(PORT_NUMBER); // port 9999
//        server = new SocketIOServer(config);
//        server.addConnectListener(new ConnectListener() {
//            @Override
//            public void onConnect(SocketIOClient sioc) {
//                textArea.append("One client connected.");
//            }
//        });
//        server.addEventListener("register", Model_Register.class, new DataListener<Model_Register>() {
//            @Override
//            public void onData(SocketIOClient sioc, Model_Register t, AckRequest ar) throws Exception {
//                Model_Message message = serviceUser.register(t);
//                ar.sendAckData(message.isAction(), message.getMessager(), message.getData());;
//                if (message.isAction()) {
//                    textArea.append("User has Register :" + t.getUserName() + " Pass :" + t.getPassWord() + "\n");
//                    server.getBroadcastOperations().sendEvent("list_user", (Model_User_Account) message.getData());
//                    addClient(sioc, (Model_User_Account) message.getData());
//                }
//            }
//        });

//        server.addEventListener("login", Model_Login.class, new DataListener<Model_Login>() {
//            @Override
//            public void onData(SocketIOClient sioc, Model_Login t, AckRequest ar) throws Exception {
//                System.out.println(t.getUserName());
//                Model_User_Account login = serviceUser.login(t);
//                if (login != null) {
//                    ar.sendAckData(true, login);
//                    addClient(sioc, login);
//                    userConnect(login.getUserID());
//                } else {
//                    ar.sendAckData(false);
//                }
//            }
//        });
//        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
//            @Override
//            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) throws Exception {
//                try {
//                    List<Model_User_Account> list = serviceUser.getUser(userID);
//                    sioc.sendEvent("list_user", list.toArray());
//                } catch (SQLException e) {
//                    System.out.print(e);
//                }
//            }
//        });
//        server.addDisconnectListener(new DisconnectListener() {
//            @Override
//            public void onDisconnect(SocketIOClient sioc) {
//                int userID = removeClient(sioc);
//                if(userID != 0){
//                    userDisconnect(userID);
//                }
//            }
//        });
//        server.start();
    }

    public void setTextArea(String message) {
        textArea.append(message);
    }

//    public void userConnect(int userID){
//        server.getBroadcastOperations().sendEvent("user_status", userID, true);
//    }
//    
//    public void userDisconnect(int userID){
//        server.getBroadcastOperations().sendEvent("user_status", userID, false);
//    }
    public void addClientStatus(Socket client, Model_User_Account user) {
        listClientStatus.add(new Model_Client(client, user));
    }

    public int removeClient(Socket client) {
        for (Model_Client c : listClientStatus) {
            if (c.getClient() == client) {
                listClientStatus.remove(c);
                return c.getUser().getUserID();
            }
        }
        return 0;
    }

    public void sendBroadcast(int ID) throws SQLException {
        for (Handler_Server handler_Server : listClient) {
//            if(!handler_Server.getUserIDSocket().equals(ID)){
//                handler_Server.SendListUser();
//            }
            if (!(handler_Server.getUserID() == ID)) {
                handler_Server.SendListUser();
            }
        }
    }

    public void sendToUser(int ID, Model_Out data) throws SQLException {
        for (Handler_Server handler_Server : listClient) {
            if (handler_Server.getUserID() == ID) {
                handler_Server.Send(data);
            }
        }
    }
//

    public List<Model_Client> getListClient() {
        return listClientStatus;
    }

}
