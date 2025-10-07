package com.parcelshare.parcelshare.Dto;


import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.Traveler;
import lombok.Data;

import java.util.List;

@Data
public class ParcelResponseDTO {

    public ParcelResponseDTO(Parcel parcel, Traveler traveler) {
        this.parcel = parcel;
        this.traveler = traveler;
    }

    Parcel parcel;
    Traveler traveler;

    public ParcelResponseDTO() {

    }
}
