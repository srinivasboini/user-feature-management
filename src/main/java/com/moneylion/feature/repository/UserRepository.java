package com.moneylion.feature.repository;

import com.moneylion.feature.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * This class is responsible to execute queries on User table
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByUserEmail(String email) ;
}
