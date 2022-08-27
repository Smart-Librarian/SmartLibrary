package com.example.SmartLibrarian.Repository;

import com.example.SmartLibrarian.Model.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Integer> {

    Optional<UserMaster> findUserByUsername(String username);

    UserMaster findByUsername(String username);
}
