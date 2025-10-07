package com.parcelshare.parcelshare.Dto;


import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.Traveler;
import lombok.Data;

import java.util.List;

@Data
public class ParcelResponseDTO {
    public ParcelResponseDTO(int id, Traveler parcels) {
        this.id = id;
        Parcels = parcels;
    }
    public ParcelResponseDTO() {
    }

    int id;
    Traveler Parcels;

}
