package com.fishingbooker.dto;

public class ReserveActionDTO {
    private Long id;
    private String customerUsername;

    public ReserveActionDTO(Long id, String customerUsername) {
        this.id = id;
        this.customerUsername = customerUsername;
    }

    public ReserveActionDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }
}
