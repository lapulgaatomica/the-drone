package com.example.the_drone.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "code")
    private String code;
    @Lob
    private byte[] image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id", referencedColumnName = "id")
    private Drone drone;

    public Medication() {
    }

    public Medication(String name, Integer weight, String code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

    public Medication(String name, Integer weight, String code, byte[] image, Drone drone) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.drone = drone;
    }

    public Medication(String id, String name, Integer weight, String code, byte[] image, Drone drone) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.drone = drone;
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

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(weight, that.weight) && Objects.equals(code, that.code) && Objects.deepEquals(image, that.image) && Objects.equals(drone, that.drone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, code, Arrays.hashCode(image), drone);
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", code='" + code + '\'' +
                ", image=" + Arrays.toString(image) +
                ", drone=" + drone +
                '}';
    }
}
