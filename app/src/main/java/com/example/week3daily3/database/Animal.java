package com.example.week3daily3.database;

import android.os.Parcel;
import android.os.Parcelable;

public class Animal implements Parcelable {
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

    public Animal(String type, String name, String sound, String imageUrl) {
        this.type = type;
        this.name = name;
        this.sound = sound;
        this.imageUrl = imageUrl;
    }

    protected Animal(Parcel in) {
        id = in.readInt();
        type = in.readString();
        name = in.readString();
        sound = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(sound);
        dest.writeString(imageUrl);
    }
}
