package com.nulp.shymoniak.mastersproject.entity.enums;

public enum ApplicationUserRole {
    ADMIN,           // should be able to view and edit all entities
    PRIVILEGED_USER, // should be able to view all entities
    USER            // should be able to edit own entities
}
