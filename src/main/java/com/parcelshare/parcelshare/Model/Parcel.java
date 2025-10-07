package com.parcelshare.parcelshare.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.parcelshare.parcelshare.Model.ENUM.Status_PARCEL;
import com.parcelshare.parcelshare.Service.UserServices;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Entity
@Data

public class Parcel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    private double pickupLat;
    private double pickupLng;
    private double destinationLat;
    private double destinationLng;
    private long weight;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date pickupDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date destinationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deadline;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status_PARCEL status = Status_PARCEL.PENDING;

    private int parcelcreaterid ;






}
