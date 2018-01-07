package com.example.ldjg.pigknowclient.DB;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ldjg on 2018/1/7.
 */

public class Record extends BmobObject {
    public BmobFile getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(BmobFile videoFile) {
        this.videoFile = videoFile;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFarmsRemarks() {
        return farmsRemarks;
    }

    public void setFarmsRemarks(String farmsRemarks) {
        this.farmsRemarks = farmsRemarks;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAdminRemarks() {
        return adminRemarks;
    }

    public void setAdminRemarks(String adminRemarks) {
        this.adminRemarks = adminRemarks;
    }

    public Farms getFarms() {
        return farms;
    }

    public void setFarms(Farms farms) {
        this.farms = farms;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private BmobFile videoFile;
    private int num;
    private String farmsRemarks;
    private int audit;
    private String adminRemarks;
    private Farms farms;
    private User user;
    private String upLoadDate;
    private String auditDate;

    public String getUpLoadDate() {
        return upLoadDate;
    }

    public void setUpLoadDate(String upLoadDate) {
        this.upLoadDate = upLoadDate;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }
}
