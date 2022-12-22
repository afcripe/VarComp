package net.dahliasolutions.varcomp;

import net.dahliasolutions.varcomp.models.User;

public abstract class ViewController {

    protected User user = new User();

    public void init() {}

    public void setUser(User user) {
        this.user = user;
    }
}