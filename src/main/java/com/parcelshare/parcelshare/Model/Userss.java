package com.parcelshare.parcelshare.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.parcelshare.parcelshare.Model.ENUM.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Userss {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date = new Date();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdDate = date;

    @Enumerated(EnumType.STRING)
    private Role role;






}
