package com.example.week3daily3.database;

public class Animal {
    private int id;
    private String type;
    private String name;
    private String sound;
    private String imageUrl;

    public Animal() {
    }

    public Animal(int id, String type, String name, String sound, String imageUrl) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.sound = sound;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
