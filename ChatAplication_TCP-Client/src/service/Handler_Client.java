/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import event.PublicEvent;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import model.Model_Input;
import model.Model_Login;
import model.Model_Message;
import model.Model_Out;
import model.Model_Receive_Message;
import model.Model_Register;
import model.Model_User_Account;

/**
 *
 * @author HOANG
 */
public class Handler_Client implements Runnable {

    private Socket mySocket;
    private InputStream input;

    public Handler_Client(Socket mySocket) {
        this.mySocket = mySocket;
        try {
            this.input = mySocket.getInputStream();
        } catch (Exception e) {
            System.out.println("service.Handler_Client");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(this.input);) {
            while (true) {
                Model_Out data = (Model_Out) input.readObject();
                select(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void select(Model_Out data) {
        if (data.getKey().equals("login")) {
            Model_Message rs = (Model_Message) data.getData();
            PublicEvent.getInstance().getEventLoginFeedback().callMessage(rs);
        } else if (data.getKey().equals("register")) {
            Model_Message rs = (Model_Message) data.getData();
            PublicEvent.getInstance().getEventRegisterFeedback().callMessage(rs);
        } else if (data.getKey().equals("list_user")) {
            List<Model_User_Account> users = (List<Model_User_Account>)data.getData();
            PublicEvent.getInstance().getEventMenuLeft().newUser(users);
        } else if (data.getKey().equals("send_to_user")) {
            Model_Receive_Message message = (Model_Receive_Message) data.getData();
            PublicEvent.getInstance().getEventChat().receiveMessage(message);
        }
    }
}
