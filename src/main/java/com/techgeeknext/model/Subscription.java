package com.techgeeknext.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "the payment date is required")
    private Date payment_date;
    @NotNull(message = "the experation date is required")
    private Date experation_date;
    @NotNull(message = "the price is required")
    @DecimalMin(value = "50")
    private float price;

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private Client c;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public Date getExperation_date() {
        return experation_date;
    }

    public void setExperation_date(Date experation_date) {
        this.experation_date = experation_date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Client getC() {
        return c;
    }

    public void setC(Client c) {
        this.c = c;
    }
}
