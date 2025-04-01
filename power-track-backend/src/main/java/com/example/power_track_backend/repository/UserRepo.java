package com.example.power_track_backend.repository;

import com.example.power_track_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Query("DELETE FROM UserEntity u WHERE u.username = :username")
    void deleteByUsername(@Param("username") String username);
}
