package com.moneylion.feature.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response represents whether a specific user has access
 * to a specific feature or not
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */


@Data
@NoArgsConstructor
public class UserFeatureResponse {

    private Boolean canAccess = Boolean.FALSE ;

    public UserFeatureResponse(Boolean canAccess){
        this.canAccess = canAccess ;
    }
}
