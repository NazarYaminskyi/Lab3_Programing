package com.example.gymapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "visitors")
public class Visitor {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Long trainerId;

    private static final int NAME_MAX_LENGTH = 100;
    private static final int PHONE_MAX_LENGTH = 15;
    private static final int PHONE_MIN_LENGTH = 7;
    private static final int EMAIL_MAX_LENGTH = 100;

    public Visitor() {}

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        validateName(firstName);
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        validateName(lastName);
        this.lastName = lastName.trim();
    }

    public void setPhone(String phone) {

        phone = phone.trim();

        if (phone.length() < PHONE_MIN_LENGTH) {
            throw new IllegalArgumentException("Phone too short");
        }

        if (phone.length() > PHONE_MAX_LENGTH) {
            throw new IllegalArgumentException("Phone too long");
        }

        if (!phone.matches("[0-9+]+")) {
            throw new IllegalArgumentException("Phone format is invalid");
        }

        this.phone = phone;
    }

    public void setEmail(String email) {
        email = email.trim();

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        if (email.length() > EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException("Email too long");
        }

        this.email = email;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    private void validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (value.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Name too long");
        }
    }
}