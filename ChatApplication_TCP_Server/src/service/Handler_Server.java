/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import app.MessageType;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model_Client;
import model.Model_Input;
import model.Model_Login;
import model.Model_Message;
import model.Model_Out;
import model.Model_Receive_Message;
import model.Model_Register;
import model.Model_Send_Message;
import model.Model_User_Account;

/**
 *
 * @author HOANG
 */
public class Handler_Server implements Runnable {

    private Service myService;
    private Socket mySocket;
    private InputStream input;
    private ObjectOutputStream output;
    private ServiceUser serviceUser;
    private int userID;
//    private String userIDSocket;
//
//    public String getUserIDSocket() {
//        return userIDSocket;
//    }

    public int getUserID() {
        return userID;
    }

    public Handler_Server(Socket mySocket, String ID, Service myService) {
        this.mySocket = mySocket;
        this.myService = myService;
//        this.userIDSocket = ID;
        serviceUser = new ServiceUser();
        try {
            this.input = mySocket.getInputStream();
            this.output = new ObjectOutputStream(mySocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(this.input);) {
            while (true) {
                Model_Input data = (Model_Input) input.readObject();
                Select(data);
            }
        } catch (Exception e) {
//            for (Model_Client object : myService.getListClient()) {
//                System.out.println(object.getUser().getUserID());
//            }
            myService.removeClient(mySocket);
            try {
//                myService.sendBroadcast(userIDSocket);
                myService.sendBroadcast(userID);
            } catch (SQLException ex) {
                Logger.getLogger(Handler_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
//            for (Model_Client object : myService.getListClient()) {
//                System.out.println(object.getUser().getUserID());
//            }
            e.printStackTrace();
        }

    }

    public void Send(Model_Out data) {
        try {
            output.writeObject(data);
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendListUser() throws SQLException {
        List<Model_User_Account> list = serviceUser.getUser(userID);
        Model_Out dataout2 = new Model_Out("list_user", list);
        Send(dataout2);
    }

    public void Select(Model_Input data) throws SQLException {
        if (data.getKey().equals("login")) {
            Model_Login rs = (Model_Login) data.getData();
            Model_User_Account login = serviceUser.login(rs);
            if (login != null) {
                myService.addClientStatus(mySocket, login);
                Model_Message message = new Model_Message();
                message.setAction(true);
                message.setMessager("oke");
                message.setData(login);
                Model_Out dataout = new Model_Out("login", message);
                Send(dataout);
                userID = login.getUserID();
                List<Model_User_Account> list = serviceUser.getUser(userID);
                Model_Out dataout2 = new Model_Out("list_user", list);
                Send(dataout2);
                //                myService.sendBroadcast(userIDSocket);
                myService.sendBroadcast(userID);

            } else {
                Model_Message message = new Model_Message();
                message.setAction(false);
                message.setMessager("no");
                Model_Out dataout = new Model_Out("login", message);
                Send(dataout);
            }
        } else if (data.getKey().equals("register")) {
            Model_Register rs = (Model_Register) data.getData();
            Model_Message message = serviceUser.register(rs);
            Model_Out dataout1 = new Model_Out("register", message);
            Send(dataout1);
            Model_Login user = new Model_Login(rs.getUserName(), rs.getPassWord());
            Model_User_Account login = serviceUser.login(user);
            myService.addClientStatus(mySocket, login);
            userID = login.getUserID();
            List<Model_User_Account> list = serviceUser.getUser(userID);
            Model_Out dataout2 = new Model_Out("list_user", list);
            Send(dataout2);
            //                myService.sendBroadcast(userIDSocket);
            myService.sendBroadcast(userID);
        } else if (data.getKey().equals("send_to_user")) {
            Model_Send_Message message = (Model_Send_Message) data.getData();
            Model_Receive_Message rm = new Model_Receive_Message(message.getMessageType(), message.getFromUserID(), message.getText());
            Model_Out dataout = new Model_Out("send_to_user", rm);
            System.out.println("ID nguoi gui: " + message.getFromUserID() + " ID nguoi can gui : " + message.getToUserID() + " : " + message.getText());
            myService.sendToUser(message.getToUserID(), dataout);
        }
    }

}
