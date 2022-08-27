package com.example.SmartLibrarian.Repository;

import com.example.SmartLibrarian.Model.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttemptsRepository extends JpaRepository<Attempts, Integer> {

    Optional<Attempts> findAttemptsByUsername(String username);
}
