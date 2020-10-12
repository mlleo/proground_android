package com.example.proground;

public class MemberInfo {
    private String unickname;
    private String ubirth;
    private String usex;
    private String uheight;
    private String uweight;

    public MemberInfo(String unickname, String ubirth, String usex, String uheight, String uweight){
        this.unickname = unickname;
        this.ubirth = ubirth;
        this.usex = usex;
        this.uheight = uheight;
        this.uweight = uweight;
    }

    public String getUnickname(){
        return this.unickname;
    }

    public void setUnickname(String unickname){
        this.unickname = unickname;
    }

    public String getUbirth(){
        return this.ubirth;
    }

    public void setUbirth(String ubirth){
        this.ubirth = ubirth;
    }
    public String getUsex(){
        return this.usex;
    }

    public void setUsex(String usex){
        this.usex = usex;
    }
    public String getUheight(){
        return this.uheight;
    }

    public void setUheight(String uheight){
        this.uheight = uheight;
    }
    public String getUweight(){
        return this.uweight;
    }

    public void setUweight(String uweight){
        this.uweight = uweight;
    }



}
