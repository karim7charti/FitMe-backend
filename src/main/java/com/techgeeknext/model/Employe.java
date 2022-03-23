package com.techgeeknext.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank(message = "the first name is required")
    private String firstName;


    @NotBlank(message = "the last name is required")
    private String lastName;



    @NotNull(message = "the payment day is required")
    @Positive
    private int paymentDay;
    @NotBlank(message = "the National Id is required")
    private String NationalId;


    @NotNull(message = "the Monthly Payment Amount is required")
    @DecimalMin(value = "1500")
    @DecimalMax(value = "20000")
    private double Monthly_Amount;

    @ManyToOne
    @JoinColumn(name = "id_profession", referencedColumnName = "id")
    private Profession prof;


    @Column
    private Timestamp created_at;

    @Transient
    private long id_profe;

    public String getNationalId() {
        return NationalId;
    }

    public void setNationalId(String nationalId) {
        NationalId = nationalId;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public long getId_profe() {
        return id_profe;
    }

    public void setId_profe(long id_profe) {
        this.id_profe = id_profe;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public double getMonthly_Amount() {
        return Monthly_Amount;
    }

    public void setMonthly_Amount(double monthly_Amount) {
        Monthly_Amount = monthly_Amount;
    }

    public Profession getProf() {
        return prof;
    }

    public void setProf(Profession prof) {
        this.prof = prof;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
