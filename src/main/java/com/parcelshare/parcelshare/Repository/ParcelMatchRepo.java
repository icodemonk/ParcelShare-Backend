package com.parcelshare.parcelshare.Repository;

import com.parcelshare.parcelshare.Model.ParcelMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelMatchRepo extends JpaRepository<ParcelMatch, Integer> {

    ParcelMatch findById(int id);

    List<ParcelMatch> findByParcelid(int id);

    List<ParcelMatch> findByTravelerid(int id);

}
