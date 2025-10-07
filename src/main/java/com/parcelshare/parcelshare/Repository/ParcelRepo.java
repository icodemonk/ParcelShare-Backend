package com.parcelshare.parcelshare.Repository;

import com.parcelshare.parcelshare.Model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepo extends JpaRepository<Parcel,Integer> {

    Parcel findByid(int id);

}
