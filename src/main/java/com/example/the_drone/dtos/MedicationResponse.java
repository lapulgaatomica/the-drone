package com.example.the_drone.dtos;



public class MedicationResponse {
    private String id;
    private String name;
    private Integer weight;
    private String code;
    private byte[] image;

    public MedicationResponse() {
    }

    public MedicationResponse(String id, String name, Integer weight, String code, byte[] image) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

