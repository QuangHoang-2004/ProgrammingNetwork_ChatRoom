/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.net.Socket;

/**
 *
 * @author HOANG
 */
public class Model_Client {

    Socket client;
    Model_User_Account user;

    public Model_Client() {
    }

    public Model_Client(Socket client, Model_User_Account user) {
        this.client = client;
        this.user = user;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }

}
