package model;

import java.io.Serializable;

public class Model_Login implements Serializable{

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Model_Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Model_Login() {
    }

    private static final long serialVersionUID = 1L; // Thay đổi giá trị nếu cần
    private String userName;
    private String password;
}
