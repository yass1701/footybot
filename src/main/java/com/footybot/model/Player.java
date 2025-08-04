package com.footybot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private int age;
    private String team;

    // Constructors
    public Player() {}

    public Player(String name, String position, int age, String team) {
        this.name = name;
        this.position = position;
        this.age = age;
        this.team = team;
    }

    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String getTeam() { return team; }

    public void setTeam(String team) { this.team = team; }
}
