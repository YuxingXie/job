package com.lingyun.demos.job.domain.activity.entity;

import java.util.Date;



public class Activity {

    private String id;      //编号

    private String title;       //活动主题

    private Date beginDate;     //开始时间

    private Date endDate;       //结束时间

    private Date createDate;       //创建时间

    private String video;       //视频地址


    private String type;//活动类型


    private ScreenPolicy screenPolicy;


    private String inviteCode;//邀请码，防止未受邀请的用户加入，如果不采用邀请码保护，小程序用户可远程参与活动


    private String checkCode;//是否检查邀请码，默认情况下需要检查


    private String activityDesc; //活动描述


    private String barrageStyle; //弹幕风格


    private String barrageSpeed; //弹幕速度




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }


    public ScreenPolicy getScreenPolicy() {
        return screenPolicy;
    }

    public void setScreenPolicy(ScreenPolicy screenPolicy) {
        this.screenPolicy = screenPolicy;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getBarrageStyle() {
        return barrageStyle;
    }

    public void setBarrageStyle(String barrageStyle) {
        this.barrageStyle = barrageStyle;
    }

    public String getBarrageSpeed() {
        return barrageSpeed;
    }

    public void setBarrageSpeed(String barrageSpeed) {
        this.barrageSpeed = barrageSpeed;
    }


}
