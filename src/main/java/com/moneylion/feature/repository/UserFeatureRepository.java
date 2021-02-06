package com.moneylion.feature.repository;

import com.moneylion.feature.entity.UserFeature;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/**
 * This class is responsible to execute queries on UserFeature table
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

import java.util.Optional;

public interface UserFeatureRepository extends CrudRepository<UserFeature,Long> {

    @Query("select u from UserFeature u where u.user.userEmail = ?1 and u.feature.featureName = ?2 ")
    Optional<UserFeature> getUserFeatureByEmailAndFeatureName(String email, String feature) ;
}
