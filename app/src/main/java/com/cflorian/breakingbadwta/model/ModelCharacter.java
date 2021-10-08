package com.cflorian.breakingbadwta.model;

import java.util.ArrayList;


public class ModelCharacter {
    private String char_id;
    private String name;
    private String birthday;
    private String img;
    private String status;
    private String nickname;
    private String portrayed;
    private String category;
    private String fav;

    public ModelCharacter(String char_id, String name, String birthday, String img, String status,
                          String nickname, String portrayed, String category, String fav) {
        this.char_id = char_id;
        this.name = name;
        this.birthday = birthday;
        this.img = img;
        this.status = status;
        this.nickname = nickname;
        this.portrayed = portrayed;
        this.category = category;
        this.fav = fav;
    }

    public String getChar_id() {
        return char_id;
    }

    public void setChar_id(String char_id) {
        this.char_id = char_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrayed() {
        return portrayed;
    }

    public void setPortrayed(String portrayed) {
        this.portrayed = portrayed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

}
