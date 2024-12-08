package com.epam.training.gen.ai.model;

public class JiraModel {

    String name;
    Status status;

    public JiraModel(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JiraModel{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
