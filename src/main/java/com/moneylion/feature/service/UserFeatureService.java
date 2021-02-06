package com.moneylion.feature.service;

import com.moneylion.feature.entity.Feature;
import com.moneylion.feature.entity.User;
import com.moneylion.feature.entity.UserFeature;
import com.moneylion.feature.exception.InvalidUserFeatureException;
import com.moneylion.feature.model.UserFeatureResponse;
import com.moneylion.feature.model.UserFeatureRequest;
import com.moneylion.feature.repository.FeatureRepository;
import com.moneylion.feature.repository.UserFeatureRepository;
import com.moneylion.feature.repository.UserRepository;
import com.moneylion.feature.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/**
 * This class is a Service which will be used by UserFeatureResource to delegate the request repositories
 * and prepares response for a specific request.
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

@Service
@Slf4j
public class UserFeatureService {

    private final UserRepository userRepository;
    private final FeatureRepository featureRepository;
    private final UserFeatureRepository userFeatureRepository;

    @Autowired
    public UserFeatureService(FeatureRepository featureRepository,
                              UserRepository userRepository,
                              UserFeatureRepository userFeatureRepository) {
        this.featureRepository = featureRepository;
        this.userRepository = userRepository;
        this.userFeatureRepository = userFeatureRepository;
    }

    /**
     * Returns user's access to a specific feature.
     * Returns false if no user feature relation exists in db
     *
     * @param email
     * @param featureName
     * @return UserFeatureResponse
     */
    public UserFeatureResponse getFeatureAccess(String email,
                                                String featureName) {
            Optional<UserFeature> userFeatureOptional =
                    userFeatureRepository.getUserFeatureByEmailAndFeatureName(AppUtil.sanitize(email), AppUtil.sanitize(featureName));
            if(log.isDebugEnabled())
                log.debug(userFeatureOptional.isPresent() ? "User feature relation exist" : "User feature relation doesn't exist. Returning canAccess as false") ;

            return userFeatureOptional.isPresent() ?
                new UserFeatureResponse(userFeatureOptional.get().getEnabled()) :
                new UserFeatureResponse(Boolean.FALSE);

    }

    /**
     * enables or disables a user's access to given feature
     * if no user or feature exist it will create a new one.
     * Sanitizes user input by default.
     *
     * @param userFeatureRequest
     */
    @Transactional
    public void saveUserFeature(UserFeatureRequest userFeatureRequest) {


        try {
            //sanitize userFeatureRequest bean to prevent xss attacks
            userFeatureRequest.sanitize();

            //first try to see if any existing user feature relation exist in db or not
            Optional<UserFeature> userFeatureOptional =
                    userFeatureRepository.getUserFeatureByEmailAndFeatureName(userFeatureRequest.getEmail(),
                            userFeatureRequest.getFeatureName());

            if (userFeatureOptional.isPresent()) {
                if(log.isDebugEnabled())
                    log.debug(String.format("User feature relation exist for %s and %2s ",
                                            userFeatureRequest.getEmail(),
                                            userFeatureRequest.getFeatureName()));

                UserFeature userFeature = userFeatureOptional.get();
                userFeature.setEnabled(userFeatureRequest.getEnabled());
                userFeatureRepository.save(userFeature);
            } else {

                if(log.isDebugEnabled())
                    log.debug(String.format("User feature relation does not exist for %s and %2s. Creating a new relation ",
                            userFeatureRequest.getEmail(),
                            userFeatureRequest.getFeatureName()));

                //see if any existing user record exist or not. if not present create a new one.
                Optional<User> userOptional = userRepository.findByUserEmail(userFeatureRequest.getEmail()) ;
                User user ;
                if(userOptional.isPresent()){
                    if(log.isDebugEnabled())
                        log.debug("User exist with email "+userFeatureRequest.getEmail());
                    user = userOptional.get() ;
                }else{
                    if(log.isDebugEnabled())
                        log.debug("User doesn't exist. Creating a new user with email "+userFeatureRequest.getEmail()) ;
                    user = new User();
                    user.setUserEmail(userFeatureRequest.getEmail());
                    userRepository.save(user);
                }

                //see if any existing feature record exist or not. if not present create a new one.
                Feature feature ;
                Optional<Feature> featureOptional = featureRepository.findByFeatureName(userFeatureRequest.getFeatureName()) ;
                if(featureOptional.isPresent()){
                    if(log.isDebugEnabled())
                        log.debug("Feature exist with name "+userFeatureRequest.getFeatureName());
                    feature = featureOptional.get() ;
                }else{
                    if(log.isDebugEnabled())
                        log.debug("Feature doesn't exist. Creating new feature with name "+userFeatureRequest.getFeatureName());
                    feature = new Feature() ;
                    feature.setFeatureName(userFeatureRequest.getFeatureName());
                    featureRepository.save(feature);
                }
                //create a new user feature with enabled flag received from request.
                UserFeature userFeature = new UserFeature();
                userFeature.setUser(user);
                userFeature.setFeature(feature);
                userFeature.setEnabled(userFeatureRequest.getEnabled());
                userFeatureRepository.save(userFeature);
                log.info("User feature relation created sucessfully with id "+ userFeature.getId());

            }
        } catch (DataAccessException dac) {
            log.error("Exception while saving user feature relation ",dac) ;
            throw new InvalidUserFeatureException(dac.getLocalizedMessage());
        }catch(Exception e){
            log.error("Unknown Exception while saving user feature relation ",e) ;
            throw new InvalidUserFeatureException(e.getLocalizedMessage());
        }

    }


}
