/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.ObjectOutputStream;
import java.net.Socket;
import model.Model_User_Account;

/**
 *
 * @author HOANG
 */
public class Service {

    private static Service instance;
    private Socket client;
    private ObjectOutputStream output;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
    private Model_User_Account user;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void startServer() {
        try {
            client = new Socket("localhost", PORT_NUMBER);
            Handler_Client handel = new Handler_Client(client);
            new Thread(handel).start(); 
            output = new ObjectOutputStream(client.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
//            client.on("list_user", new Emitter.Listener() {
//                @Override
//                public void call(Object... os) {
//                    //list user
//                    List<Model_User_Account> users = new ArrayList<>();
//                    for (Object o : os) {
//                        Model_User_Account u = new Model_User_Account(o);
//                        if (u.getUserID() != user.getUserID()) {
//                            users.add(u);
//                        }
//                    }
//                    PublicEvent.getInstance().getEventMenuLeft().newUser(users);
//                }
//            });
//            client.on("user_status", new Emitter.Listener() {
//                @Override
//                public void call(Object... os) {
//                    int userID = (Integer) os[0];
//                    boolean status = (Boolean) os[1];
//                    if (status) {
//                        //connect
//                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
//                    } else {
//                        //disconnect
//                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
//                    }
//                }
//            });
//            chuyen sang handler
//            client.on("receive_ms", new Emitter.Listener() {
//                        @Override
//                        public void call(Object... os) {
//                            Model_Receive_Message message = new Model_Receive_Message(os[0]);
//                            PublicEvent.getInstance().getEventChat().receiveMessage(message);
//                        }
//                    });
//            client.open();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            error(e);
//        }
    }

    private void error(Exception e) {
        System.err.println(e);
    }

    public Socket getClient() {
        return client;
    }

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }
}
