package com.parcelshare.parcelshare.Dto;

import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.Traveler;
import lombok.Data;

import java.util.List;

@Data
public class TravelerResponseDTO {
    int id;
    Parcel parcel;
    public TravelerResponseDTO(int id, Parcel parcel) {
        this.id = id;
        this.parcel = parcel;
    }
}
