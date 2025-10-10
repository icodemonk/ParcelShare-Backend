package com.parcelshare.parcelshare.Repository;

import com.parcelshare.parcelshare.Model.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelerRepo extends JpaRepository<Traveler,Integer> {
    Traveler findById(int id);
    List<Traveler> findByTravelercreatorid(int travelercreatorid);

}
