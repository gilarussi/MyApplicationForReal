package com.example.myapplicationforreal;

public class Resume {
    private String name;
    private String email;
    private String phone;
    private String degree;
    private String major;

    public Resume() {
        // Default constructor required for Firebase
    }

    public Resume(String name, String email, String phone, String degree, String major) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.degree = degree;
        this.major = major;
    }

    // Getters and setters (optional)

    // ...
}

