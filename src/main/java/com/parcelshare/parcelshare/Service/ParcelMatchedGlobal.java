package com.parcelshare.parcelshare.Service;

import com.parcelshare.parcelshare.Dto.ParcelResponseDTO;
import com.parcelshare.parcelshare.Dto.TravelerResponseDTO;
import com.parcelshare.parcelshare.Model.ENUM.ParcelMatchTraveler;
import com.parcelshare.parcelshare.Model.ENUM.ParcelMatchedCourier;
import com.parcelshare.parcelshare.Model.ENUM.Stat;
import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.ParcelMatch;
import com.parcelshare.parcelshare.Model.Traveler;
import com.parcelshare.parcelshare.Repository.ParcelMatchRepo;
import com.parcelshare.parcelshare.Repository.ParcelRepo;
import com.parcelshare.parcelshare.Repository.TravelerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParcelMatchedGlobal {

    @Autowired
    TravelerRepo travelerRepo;

    @Autowired
    private ParcelMatchRepo repository;
    @Autowired
    private UserServices userServices;

    @Autowired
    ParcelRepo parcelRepo;
    //create a matched

    public List<Traveler> allTraveler(int currentuser){
        return travelerRepo.findByTravelercreatorid(currentuser);
    }

    public String addMatch(int id , int creatorid){
        ParcelMatch parcelMatch = new ParcelMatch();
        parcelMatch.setTravelerid(creatorid);
        parcelMatch.setTraveler(ParcelMatchTraveler.ACCEPTED);
        parcelMatch.setParcelid(id);
        repository.save(parcelMatch);
        return "Added sucessfully";
    }


    //filter for traveler_Accepted - TRAVELER
    public List<Parcel> getAcceptedTravelers(int creatorid) {
        int id = creatorid;
        List<ParcelMatch> parcelMatches = repository.findByTravelerid(id);
        List<Parcel> parcel = new ArrayList<>();
        for (ParcelMatch parcelMatch : parcelMatches) {
            if (parcelMatch.getTraveler() == ParcelMatchTraveler.ACCEPTED) {
                parcel.add(parcelRepo.findByid(parcelMatch.getParcelid()));
            }
        }
        return parcel;

    }

    //filter for status_matched - TRAVELER
    public List<TravelerResponseDTO> matchedTraveler(int creatorid) {
        int id = creatorid;
        List<ParcelMatch> parcelMatchbyidall = repository.findByTravelerid(id);
        List<TravelerResponseDTO> parcels = new ArrayList<>();
        for (ParcelMatch pm : parcelMatchbyidall) {
            if (pm.getTraveler() == ParcelMatchTraveler.ACCEPTED && pm.getStatus()==Stat.MATCHED) {
                TravelerResponseDTO travelerResponseDTO= new TravelerResponseDTO(pm.getId(),parcelRepo.findByid(pm.getParcelid()));
                parcels.add(travelerResponseDTO);
            }
        }
        return parcels;
    }



//    filter request recived for parcel

    public List<ParcelResponseDTO> ParcelRequest(int creatorid) {
        int id = creatorid;
        List<ParcelMatch> TravelerMatchbyidall = repository.findByParcelid(id);
        List<ParcelResponseDTO> traveler = new ArrayList<>();
        for (ParcelMatch pm : TravelerMatchbyidall) {
            if (pm.getTraveler() == ParcelMatchTraveler.ACCEPTED && pm.getStatus()==Stat.PENDING) {
                ParcelResponseDTO parcelResponseDTO = new ParcelResponseDTO(pm.getId(),travelerRepo.findById(pm.getParcelid()));
               traveler.add(parcelResponseDTO);
            }
        }
        return traveler;
    }

    public List<ParcelResponseDTO> Parcelmatched(int creatorid) {
        int id = creatorid;
        List<ParcelMatch> TravelerMatchbyidall = repository.findByParcelid(id);
        List<ParcelResponseDTO> traveler = new ArrayList<>();
        for (ParcelMatch pm : TravelerMatchbyidall) {
            if (pm.getCourier() == ParcelMatchedCourier.ACCEPTED && pm.getStatus()==Stat.MATCHED) {
                ParcelResponseDTO parcelResponseDTO = new ParcelResponseDTO(pm.getId(),travelerRepo.findById(pm.getParcelid()));
                traveler.add(parcelResponseDTO);
            }
        }
        return traveler;
    }

//    --------------------------UPDATE STAT----------------------------------
   public void updateStattoPending(int id){
        ParcelMatch parcelMatch = repository.findById(id);
        parcelMatch.setStatus(Stat.PENDING);
        repository.save(parcelMatch);
   }
   public void updateStattoMatched(int id){
        ParcelMatch parcelMatch = repository.findById(id);
        parcelMatch.setStatus(Stat.MATCHED);
        repository.save(parcelMatch);
   }
   //----------------------------Update ParcelMatchedourier------------------------

    public String updateParcelToReject(int id){
        ParcelMatch parcelMatch = repository.findById(id);
        if (parcelMatch.getCourier() == ParcelMatchedCourier.ACCEPTED) {
            parcelMatch.setCourier(ParcelMatchedCourier.NODATA);
            parcelMatch.setStatus(Stat.PENDING);
            return "SUCCESSFULLY REJECTED";
        }
        else {
            return "FAILED TO REJECT - Cause : you have never accepted this request";
        }
    }
    public String updateParcelToAccept(int id){
        ParcelMatch parcelMatch = repository.findById(id);
        if(parcelMatch.getTraveler()==ParcelMatchTraveler.ACCEPTED) {
            if (parcelMatch.getCourier() == ParcelMatchedCourier.NODATA) {
                parcelMatch.setCourier(ParcelMatchedCourier.ACCEPTED);
             parcelMatch.setStatus(Stat.MATCHED);
                return "SUCCESSFULLY ACCEPTED";
            } else {
                return "FAILED TO ACCEPT : you have never accepted this request";
            }
        }else {
            return " Buddy Traveler have to accept first that you can .....chill  ";
        }
    }
    //---------------------------------Update Parcelmatchtraveler----------------------

    public String updateTravelerToReject(int id,int creatorid){

        List<ParcelMatch> parcelMatch = repository.findByParcelid(id);

        if(!parcelMatch.isEmpty()){
            for (ParcelMatch match : parcelMatch) {
                if (match.getParcelid() == id && match.getTravelerid() == creatorid) {
                    match.setTraveler(ParcelMatchTraveler.NOTACCEPTED);
                    repository.save(match);
                    return "SUCCESSFULLY Rejected-1";

                }
            }

        }

        return "Parcel is not in parcel match";

    }


    public String updateTravelerToAccept(int id , int creatorid) {
        List<ParcelMatch> parcelMatch = repository.findByParcelid(id);

        if(!parcelMatch.isEmpty()){
            for (ParcelMatch match : parcelMatch) {
                if (match.getParcelid() == id && match.getTravelerid() == creatorid) {
                    match.setTraveler(ParcelMatchTraveler.ACCEPTED);
                    repository.save(match);
                    return "SUCCESSFULLY ACCEPTED-1";
                }
            }

        }
        else {
            ParcelMatch parcelMatcha = new ParcelMatch();
            parcelMatcha.setTravelerid(creatorid);
            parcelMatcha.setTraveler(ParcelMatchTraveler.ACCEPTED);
            parcelMatcha.setParcelid(id);
            repository.save(parcelMatcha);
            return "SUCCESSFULLY ACCEPTED-2";
        }


         return "FAILED TO ACCEPT";

    }



    }
