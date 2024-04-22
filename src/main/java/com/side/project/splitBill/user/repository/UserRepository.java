package com.side.project.splitBill.user.repository;

import com.side.project.splitBill.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}