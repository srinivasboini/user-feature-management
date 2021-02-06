package com.moneylion.feature.model;

import com.moneylion.feature.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * This class holds the json request from UI.
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFeatureRequest {


    @NotEmpty
    private String featureName ;
    @NotEmpty
    @Email
    private String email ;
    @NotNull
    private Boolean enabled ;

    public void sanitize(){
        setEmail(AppUtil.sanitize(getEmail()));
        setFeatureName(AppUtil.sanitize(getFeatureName()));
    }



}
