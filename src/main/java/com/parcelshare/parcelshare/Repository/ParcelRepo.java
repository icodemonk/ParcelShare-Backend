package com.parcelshare.parcelshare.Repository;

import com.parcelshare.parcelshare.Model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepo extends JpaRepository<Parcel,Integer> {

    Parcel findByid(int id);

    List<Parcel> findByParcelcreaterid(int parcelcreaterid);
}
