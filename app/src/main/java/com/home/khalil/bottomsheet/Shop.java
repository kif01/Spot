package com.home.khalil.bottomsheet;

/**
 * Created by khalil on 3/12/17.
 */

public class Shop {

    private String name;
    private double lon;
    private double lat;
    private String image;
    private String category;
    private String id;
    private String phone;
    private String type;
    private double rate;
    private String area;
    private String time;

    public Shop(){

    }


    public Shop(String name, double lon, double lat,String image,String category,String id,String phone, String type, double rate,String area,String time){
        this.name=name;
        this.lon=lon;
        this.lat=lat;
        this.image=image;
        this.category=category;
        this.id=id;
        this.phone=phone;
        this.type=type;
        this.rate=rate;
        this.area=area;
        this.time=time;

    }

    public String getName(){
        return name;
    }

    public double getLon(){
        return lon;
    }

    public double getLat(){
        return lat;
    }

    public String getImage(){
        return image;
    }

    public String getCuisine(){
        return category;
    }

    public String getPhone(){
        return phone;
    }
    public String getType(){
        return type;
    }
    public double getRate(){
        return rate;
    }
    public String getArea(){
        return area;
    }
    public String getTime(){
        return time;
    }

    public String getId(){
        return id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setLon(double lon){
        this.lon=lon;
    }

    public void setLat(double lat){
        this.lat=lat;
    }
    public void setImage(String img){
        this.image=img;

    }

    public void setCuisine(String category){
        this.category=category;
    }
    public void setId(String id){
        this.id=id;
    }
public void setTime(String time){
    this.time=time;
}
    public void setPhone(String phone){
        this.phone=phone;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setRate(double rate){
        this.rate=rate;
    }
    public void setArea(String area){
        this.area=area;
    }
}
