package entity;

public class User {
    private String userName;

    private String token;

    public User(){
    }

    public User(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
