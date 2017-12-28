package com.example.ldjg.pigknowclient.DB;

import cn.bmob.v3.BmobUser;

/**
 * Created by ldjg on 2017/12/25.
 */

public class User extends BmobUser {
    private Farms farms;

    public Farms getFarms() {
        return farms;
    }

    public void setFarms(Farms farms) {
        this.farms = farms;
    }
}
