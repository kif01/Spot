package com.home.khalil.bottomsheet;

/**
 * Created by khalil on 1/31/17.
 */

public class Category {

    private String title;
    //private int image;
    private String level;
    /*private int textSize;
    private String statusBarColor;
    private String textColor;
    private boolean isHot;*/

    //public Category(String title, int image,int textSize, String statusBarColor, String textColor, boolean isHot){
    public Category(String title,String level){
        this.title=title;
        //this.image=image;
        this.level=level;
        /*this.textSize=textSize;
        this.statusBarColor=statusBarColor;
        this.textColor=textColor;
        this.isHot=isHot;*/
    }

    public String getTitle(){
        return title;
    }
    public String getLevel(){
        return level;
    }

   /* public int getImage(){
        return image;
    }*/

   /* public int getTextSize(){
        return textSize;
    }
    public String getStatusBarColor(){
        return statusBarColor;
    }
    public String getTextColor(){
        return textColor;
    }
    public boolean isHot(){
        return isHot;
    }*/
}

