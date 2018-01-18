package com.example.ldjg.pigknowclient.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by ldjg on 2017/12/25.
 */

public class Admin extends BmobObject {
    private String invitationCode;

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    private String adminAccount;
    private String adminName;
    private String password;
    private String instalId;

    public String getInstalId() {
        return instalId;
    }

    public void setInstalId(String instalId) {
        this.instalId = instalId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

}
