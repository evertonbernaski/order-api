package com.api.orderapi.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ORDER")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderNumber;
    @Column
    private String descriptionItem;
    @Column
    private OrderStatus status;
    @Column
    private Double valueItem;
    @Column
    private int quantityItem;
    @Column
    private double amount;
    public Order() {}

    public Order(Long orderNumber, String descriptionItem, OrderStatus status, Double valueItem, int quantityItem, double amount) {
        this.orderNumber = orderNumber;
        this.descriptionItem = descriptionItem;
        this.status = status;
        this.valueItem = valueItem;
        this.quantityItem = quantityItem;
        this.amount = amount;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return quantityItem * valueItem;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

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
