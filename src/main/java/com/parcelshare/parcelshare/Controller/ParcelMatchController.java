package com.parcelshare.parcelshare.Controller;

import com.parcelshare.parcelshare.Dto.ParcelResponseDTO;
import com.parcelshare.parcelshare.Dto.TravelerResponseDTO;
import com.parcelshare.parcelshare.Model.Parcel;
import com.parcelshare.parcelshare.Model.Traveler;
import com.parcelshare.parcelshare.Service.ParcelMatchedGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pmc")
public class ParcelMatchController {

    @Autowired
    ParcelMatchedGlobal service;

    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @PostMapping("/add/{id}/{creatorid}")
    public String addParcelMatch(@PathVariable int id , @PathVariable int creatorid){
        return service.addMatch(id , creatorid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @GetMapping("/trequest/{creatorid}")
    public List<Parcel> getallTravelerRequest(@PathVariable int creatorid){
        return service.getAcceptedTravelers(creatorid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @GetMapping("/tmatched/{creatorid}")
    public List<TravelerResponseDTO> getallTravelerMatched(@PathVariable int creatorid){
        return service.matchedTraveler(creatorid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PARCEL')")
    @GetMapping("prequest/{user}")
    public List<ParcelResponseDTO> getallParcelRequest(@PathVariable int user){
        return service.ParcelRequest(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PARCEL')")
    @GetMapping("pmatched/{creatorid}")
    public List<ParcelResponseDTO> getallParcelMatched(@PathVariable int creatorid){
        return service.Parcelmatched(creatorid);
    }
    @PreAuthorize("hasAnyRole('ADMIN','PARCEL')")
    @GetMapping("parequest/{user}")
    public List<ParcelResponseDTO> AcceptedParcelRequest(@PathVariable int user){
        return service.AcceptedRequest(user);
    }


    @PreAuthorize("hasAnyRole('ADMIN','PARCEL')")
    @PostMapping("/updatepreject")
    public String updateParcelToReject(int id){
       return service.updateParcelToReject(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PARCEL')")
    @PostMapping("/updatepaccept")
    public String updateParcelToAccept(int id){
        return service.updateParcelToAccept(id);
    }


    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @PostMapping("/updatetreject/{id}/{creatorid}")
    public String updateTravelerToReject(@PathVariable int id , @PathVariable int creatorid){
        return service.updateTravelerToReject(id , creatorid);
    }


    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @PostMapping("/updatetaccept/{id}/{creatorid}")
    public String updateTravelerToAccept(@PathVariable int id , @PathVariable int creatorid){
        return service.updateTravelerToAccept(id , creatorid);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAVELER')")
    @PostMapping("/alltraveler/{currentuser}")
    public List<Traveler> alltraveler(@PathVariable int currentuser){
        return service.allTraveler(currentuser);
    }


}
