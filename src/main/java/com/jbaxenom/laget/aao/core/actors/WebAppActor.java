package com.jbaxenom.laget.aao.core.actors;

import org.openqa.selenium.WebDriver;

/**
 * Created by jbaxenom on 2014-08-19.
 */
public class WebAppActor extends WebActor {

    private String username;
    private String password;

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
