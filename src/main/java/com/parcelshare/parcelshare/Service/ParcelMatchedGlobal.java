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

    public List<ParcelResponseDTO> ParcelRequest(int user) {
        List<ParcelMatch> TravelerMatchbyidall = repository.findByParcelid(user);
        List<ParcelResponseDTO> filter = new ArrayList<>();
        for (ParcelMatch parcelMatch : TravelerMatchbyidall) {
            if(parcelMatch.getTraveler() == ParcelMatchTraveler.ACCEPTED && parcelMatch.getCourier()==ParcelMatchedCourier.NODATA) {
                Traveler traveler = new Traveler();
                traveler = travelerRepo.findById(parcelMatch.getTravelerid());
                Parcel parcel = new Parcel();
                parcel = parcelRepo.findByid(parcelMatch.getParcelid());
                ParcelResponseDTO dto = new ParcelResponseDTO(parcel, traveler ,parcelMatch.getId());
                filter.add(dto);
            }
        }
        return filter;
    }

    public List<ParcelResponseDTO> Parcelmatched(int user) {
        int id = user;
        List<ParcelMatch> TravelerMatchbyidall = repository.findByParcelid(id);
        List<ParcelResponseDTO> filter = new ArrayList<>();
        for (ParcelMatch parcelMatch : TravelerMatchbyidall) {
            if (parcelMatch.getCourier() == ParcelMatchedCourier.ACCEPTED && parcelMatch.getTraveler() == ParcelMatchTraveler.ACCEPTED) {
                Traveler traveler = new Traveler();
                traveler = travelerRepo.findById(parcelMatch.getTravelerid());
                Parcel parcel = new Parcel();
                parcel = parcelRepo.findByid(parcelMatch.getParcelid());
                ParcelResponseDTO dto = new ParcelResponseDTO(parcel, traveler,parcelMatch.getId());
                filter.add(dto);
            }
        }
        return filter;
    }
    public List<ParcelResponseDTO> AcceptedRequest(int user) {
        List<ParcelMatch> TravelerMatchbyidall = repository.findByParcelid(user);
        List<ParcelResponseDTO> filter = new ArrayList<>();
        for (ParcelMatch parcelMatch : TravelerMatchbyidall) {
            if(parcelMatch.getTraveler() == ParcelMatchTraveler.ACCEPTED && parcelMatch.getCourier()==ParcelMatchedCourier.ACCEPTED) {
                Traveler traveler = new Traveler();
                traveler = travelerRepo.findById(parcelMatch.getTravelerid());
                Parcel parcel = new Parcel();
                parcel = parcelRepo.findByid(parcelMatch.getParcelid());
                ParcelResponseDTO dto = new ParcelResponseDTO(parcel, traveler , parcelMatch.getId());
                filter.add(dto);
            }
        }
        return filter;
    }
   //----------------------------Update ParcelMatchedourier------------------------

    public String updateParcelToReject(int parcelid){
        ParcelMatch parcelMatch = repository.findById(parcelid);
        if (parcelMatch.getCourier() == ParcelMatchedCourier.ACCEPTED) {
            parcelMatch.setCourier(ParcelMatchedCourier.NODATA);
            parcelMatch.setStatus(Stat.PENDING);
            repository.save(parcelMatch);
            return "SUCCESSFULLY REJECTED";
        }
        else {
            return "FAILED TO REJECT - Cause : you have never accepted this request";
        }
    }

    public String updateParcelToAccept(int parcelid){
        ParcelMatch parcelMatch = repository.findById(parcelid);
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
