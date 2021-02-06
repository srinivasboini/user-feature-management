package com.moneylion.feature.resource;

import com.moneylion.feature.model.UserFeatureResponse;
import com.moneylion.feature.model.UserFeatureRequest;
import com.moneylion.feature.service.UserFeatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
/**
 * This class is restful resource, responds to url patterns /feature
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

@Api(value="feature management end point", tags = {"Feature management end points"})
@RestController
@Validated
public class UserFeatureResource {

    private final UserFeatureService userFeatureService;

    @Autowired
    public UserFeatureResource(UserFeatureService userFeatureService) {
        this.userFeatureService = userFeatureService;
    }

    @ApiOperation(value = "Returns user access to provided feature name")
    @GetMapping("/feature")
    @ResponseBody
    public UserFeatureResponse getUserFeatureAccess(@RequestParam(name = "email") @NotEmpty  @Email  String email,
                                                    @RequestParam(name = "featureName") @NotEmpty  String featureName) {
        return userFeatureService.getFeatureAccess(email,featureName) ;
    }

    @ApiOperation("Returns HTTP 200 if the requested user feature enabled or disabled")
    @ApiResponses(value={
            @ApiResponse(code=200, message="Feature enabled or disabled successfully"),
            @ApiResponse(code=304, message="Failed to enable or disable a feature"),
            @ApiResponse(code=400, message="Provided JSON request is having issues")
    })
    @PostMapping(value="/feature", consumes = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public void saveUserFeature(@Valid @RequestBody UserFeatureRequest userFeatureRequest){
         userFeatureService.saveUserFeature(userFeatureRequest);
    }
}
