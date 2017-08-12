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

    Priority (final String priority) {
        this.priority = priority;
    }

    public String getPriorityAsString() {
        return this.priority;
    }

    public static Priority getPriority(final String priority) {
        if (priority == null)
            return null;

        if (priority.equals(Priority.HIGH.getPriorityAsString())) {
            return Priority.HIGH;
        }
        else if (priority.equals(Priority.MEDIUM.getPriorityAsString())) {
            return Priority.MEDIUM;
        }
        else if (priority.equals(Priority.LOW.getPriorityAsString())) {
            return Priority.LOW;
        }
        else if (priority.equals(Priority.DEFAULT.getPriorityAsString())) {
            return Priority.DEFAULT;
        }
        return null;
    }
}
