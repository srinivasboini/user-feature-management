package com.moneylion.feature.repository;


import com.moneylion.feature.entity.Feature;
import org.springframework.data.repository.CrudRepository;
/**
 * This class is responsible to execute queries on Feature table
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

import java.util.Optional;

public interface FeatureRepository extends CrudRepository<Feature,Long> {

    Optional<Feature> findByFeatureName(String featureName) ;
}
