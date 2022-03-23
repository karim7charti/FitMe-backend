package com.techgeeknext.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank(message = "the email is required")
    @Email
    private String email;
    @Column
   
    @NotBlank(message = "the password is required")
    @Size(min=8,message = "the password must be more than 8 caracters")
    private String password;
    
    @NotBlank(message = "the user name is required")
    @Size(min=5,max = 30)
    private String username;

    public void setId(long id) {
        this.id = id;
    }

    @NotBlank(message = "the phone number is required")
    @Size(min=10,max = 20)
    private String num;
    @Column
    private Timestamp created_at;

    public long getId() {
        return id;
    }

    public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

