package com.jbaxenom.laget.aao.core.actors;

import org.openqa.selenium.WebDriver;

/**
 * Created by jbaxenom on 2015-11-04.
 */
public abstract class WebAppActor extends WebActor {

    private String username;
    private String password;

    public WebAppActor() {
        super();
    }

    public WebAppActor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public WebAppActor(WebDriver driver) {
        super(driver);
    }

    public WebAppActor withUsername(String username) {
        this.username = username;
        return this;
    }

    public WebAppActor withPassword(String password) {
        this.password = password;
        return this;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

}
