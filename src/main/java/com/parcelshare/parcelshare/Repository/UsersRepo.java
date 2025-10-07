package com.parcelshare.parcelshare.Repository;

import com.parcelshare.parcelshare.Model.Userss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Userss,Integer> {

    Userss findByUsername(String username);


}
