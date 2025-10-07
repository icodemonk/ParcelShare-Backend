package com.parcelshare.parcelshare.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.parcelshare.parcelshare.Model.ENUM.Traveler_Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Traveler {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private double originLat;
    private double originLng;


    private double destinationLat;
    private double destinationLng;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date originDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date destinationDate;
    private int weight;

    @Enumerated(EnumType.STRING)
    private Traveler_Status status = Traveler_Status.ACTIVE;

    private int travelercreatorid;


}
