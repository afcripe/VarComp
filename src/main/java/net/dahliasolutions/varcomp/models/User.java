package net.dahliasolutions.varcomp.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import net.dahliasolutions.varcomp.connectors.UserConnector;

public class User {
    private final SimpleIntegerProperty user_id = new SimpleIntegerProperty(0);
    private final SimpleStringProperty user_name = new SimpleStringProperty("user");
    private final SimpleStringProperty user_password = new SimpleStringProperty("");
    private final SimpleStringProperty user_type = new SimpleStringProperty("user");

    public User() {
        setUser_id(0);
        setUser_name("");
        setUser_password("");
        setUser_type("user");
    }

    public User(Integer userID, String username, String password, String userType) {
        setUser_id(userID);
        setUser_name(username);
        setUser_password(password);
        setUser_type(userType);
    }

    public Integer getUser_id() {
        return user_id.get();
    }

    public void setUser_id(Integer userID) {
        user_id.set(userID);
    }

    public String getUser_name() {
        return user_name.get();
    }

    public void setUser_name(String userName) {
        user_name.set(userName);
    }

    public String getUser_password() {
        return user_password.get();
    }

    public void setUser_password(String userPassword) {
        user_password.set(userPassword);
    }

    public String getUser_type() {
        return user_type.get();
    }

    public void setUser_type(String userType) {
        user_type.set(userType);
    }

    public void setUser(User u) {
        setUser_id(u.getUser_id());
        setUser_name(u.getUser_name());
        setUser_password(u.getUser_password());
        setUser_type(u.getUser_type());
    }

    public User getUser() {
        return this;
    }

    public Integer insertUser() { return UserConnector.insertUser(this).getUser_id(); }

    public void updateUser() { UserConnector.updateUser(this); }

}
