/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author HOANG
 */
public class Model_Message implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private boolean action;
    private String messager;
    private Object data;

    public Model_Message() {
    }
//, Object data
    public Model_Message(boolean action, String messager) {
        this.action = action;
        this.messager = messager;
//        this.data = data;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public String getMessager() {
        return messager;
    }

    public void setMessager(String messager) {
        this.messager = messager;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
