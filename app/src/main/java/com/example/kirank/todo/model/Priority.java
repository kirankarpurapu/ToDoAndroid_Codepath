package com.example.kirank.todo.model;

/**
 * Created by kirank on 8/6/17.
 */

public enum Priority {
    HIGH ("High"),
    MEDIUM ("Medium"),
    LOW ("Low"),
    DEFAULT ("None");

    private final String priority;

    Priority(final String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return this.priority;
    }
}
