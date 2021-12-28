package com.fishingbooker.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    CUSTOMER,
    COTTAGE_OWNER,
    BOAT_OWNER,
    INSTRUCTOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return null;
    }
}