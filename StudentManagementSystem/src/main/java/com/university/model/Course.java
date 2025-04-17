package com.university.model;

public class Course {
    private int id;
    private String code;  // Add this property
    private String name;
    private String description;
    private int credits;

    // Default constructor
    public Course() {}

    // Constructor with parameters
    public Course(int id, String code, String name, String description, int credits) {
        this.id = id;
        this.code = code; // Initialize code
        this.name = name;
        this.description = description;
        this.credits = credits;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() { // Add the getter
        return code;
    }

    public void setCode(String code) { // Add the setter
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
