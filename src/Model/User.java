package Model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 12345L;
    private int user_id;
    private String nickName;
    private String email;
    private String password;
    private float money;

    public User(int user_id, String nickName, String email, String password, float money) {
        this.user_id = user_id;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.money = money;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return user_id +
                ", '" + nickName + '\'' +
                ", '" + email + '\'' +
                ", '" + password + '\'' +
                ", " + money;
    }
}