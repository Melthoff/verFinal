package com.example.springsecurityapplication.dto;

import com.example.springsecurityapplication.enumm.Status;

public class StatusChangeDto {
    private Status newStatus;

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
        this.newStatus = newStatus;
    }
}
