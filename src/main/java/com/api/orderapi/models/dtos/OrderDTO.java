package com.api.orderapi.models.dtos;

import com.api.orderapi.models.OrderStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderDTO {

    @NotBlank
    private String descriptionItem;
    @NotNull
    private int quantityItem;
    @NotNull
    private Double valueItem;

    public String getDescriptionItem() {
        return descriptionItem;
    }

    public void setDescriptionItem(String descriptionItem) {
        this.descriptionItem = descriptionItem;
    }

    public int getQuantityItem() {
        return quantityItem;
    }

    public void setQuantityItem(int quantityItem) {
        this.quantityItem = quantityItem;
    }

    public Double getValueItem() {
        return valueItem;
    }

    public void setValueItem(Double valueItem) {
        this.valueItem = valueItem;
    }
}
