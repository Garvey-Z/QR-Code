package com.modify_identity;

/**
 * Created by apple on 2017/5/25.
 */

public class User_infomation {
    private String account, nickname, identity;
    public User_infomation(){}
    public User_infomation(String account, String nickname, String identity){
        this.account = account;
        this.nickname = nickname;
        this.identity = identity;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
