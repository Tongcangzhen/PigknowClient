package com.example.ldjg.pigknowclient.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by ldjg on 2017/12/25.
 */

public class Farms extends BmobObject{
    private  String farmsName;
    private String adress;
    private Admin admin;

    public String getFarmsName() {
        return farmsName;
    }

    public void setFarmsName(String farmsName) {
        this.farmsName = farmsName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
