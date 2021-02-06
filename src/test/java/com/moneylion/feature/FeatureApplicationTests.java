package com.moneylion.feature;

import com.moneylion.feature.model.UserFeatureRequest;
import com.moneylion.feature.model.UserFeatureResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeatureApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testUserFeatureEnableDisable() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:"+randomServerPort+"/feature";
		URI postUri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders() ;
		headers.add("Content-Type","application/json");

		UserFeatureRequest request = new UserFeatureRequest("Feature1","User1@email.com",true) ;
		HttpEntity<UserFeatureRequest> httpEntity = new HttpEntity<>(request,headers) ;
		ResponseEntity<String> postResponse = restTemplate.postForEntity(postUri,httpEntity,String.class) ;

		Assertions.assertNotNull(postResponse);
		Assertions.assertEquals(postResponse.getStatusCode(), HttpStatus.OK);

		URI getUri = new URI(baseUrl+"?email="+request.getEmail()+"&featureName="+request.getFeatureName()) ;
		ResponseEntity<UserFeatureResponse> getResponse = restTemplate.getForEntity(getUri, UserFeatureResponse.class) ;

		Assertions.assertNotNull(getResponse);
		Assertions.assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(getResponse.getBody());
		Assertions.assertTrue(getResponse.getBody().getCanAccess());

		request = new UserFeatureRequest("Feature2","User1@email.com",true) ;
		httpEntity = new HttpEntity<>(request,headers) ;
		postResponse = restTemplate.postForEntity(postUri,httpEntity,String.class) ;

		Assertions.assertNotNull(postResponse);
		Assertions.assertEquals(postResponse.getStatusCode(), HttpStatus.OK);

		getUri = new URI(baseUrl+"?email="+request.getEmail()+"&featureName="+request.getFeatureName()) ;
		getResponse = restTemplate.getForEntity(getUri, UserFeatureResponse.class) ;

		Assertions.assertNotNull(getResponse);
		Assertions.assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(getResponse.getBody());
		Assertions.assertTrue(getResponse.getBody().getCanAccess());


		request = new UserFeatureRequest("Feature1","User1@email.com",false) ;
		httpEntity = new HttpEntity<>(request,headers) ;
		postResponse = restTemplate.postForEntity(postUri,httpEntity,String.class) ;

		Assertions.assertNotNull(postResponse);
		Assertions.assertEquals(postResponse.getStatusCode(), HttpStatus.OK);

		getUri = new URI(baseUrl+"?email="+request.getEmail()+"&featureName="+request.getFeatureName()) ;
		getResponse = restTemplate.getForEntity(getUri, UserFeatureResponse.class) ;

		Assertions.assertNotNull(getResponse);
		Assertions.assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(getResponse.getBody());
		Assertions.assertFalse(getResponse.getBody().getCanAccess());

		request = new UserFeatureRequest("Feature2","User1@email.com",false) ;
		httpEntity = new HttpEntity<>(request,headers) ;
		postResponse = restTemplate.postForEntity(postUri,httpEntity,String.class) ;

		Assertions.assertNotNull(postResponse);
		Assertions.assertEquals(postResponse.getStatusCode(), HttpStatus.OK);

		getUri = new URI(baseUrl+"?email="+request.getEmail()+"&featureName="+request.getFeatureName()) ;
		getResponse = restTemplate.getForEntity(getUri, UserFeatureResponse.class) ;

		Assertions.assertNotNull(getResponse);
		Assertions.assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(getResponse.getBody());
		Assertions.assertFalse(getResponse.getBody().getCanAccess());

	}

	@Test
	public void testUserFeatureInvalidData() throws URISyntaxException {

		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:"+randomServerPort+"/feature";
		URI postUri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders() ;
		headers.add("Content-Type","application/json");

		UserFeatureRequest request = new UserFeatureRequest("Feature1","User1",false) ;
		HttpEntity<UserFeatureRequest> httpEntity = new HttpEntity<>(request,headers) ;
		ResponseEntity<String> postResponse = restTemplate.postForEntity(postUri,httpEntity,String.class) ;

		Assertions.assertNotNull(postResponse);
		Assertions.assertEquals(postResponse.getStatusCode(), HttpStatus.NOT_MODIFIED);

		request = new UserFeatureRequest("Feature1","User1@email.com",null) ;
		httpEntity = new HttpEntity<>(request,headers) ;
		postResponse = restTemplate.postForEntity(postUri,httpEntity,String.class) ;

		Assertions.assertNotNull(postResponse);
		Assertions.assertEquals(postResponse.getStatusCode(), HttpStatus.NOT_MODIFIED);

		request = new UserFeatureRequest(null,"User1",false) ;
		httpEntity = new HttpEntity<>(request,headers) ;
		postResponse = restTemplate.postForEntity(postUri,httpEntity,String.class) ;

		Assertions.assertNotNull(postResponse);
		Assertions.assertEquals(postResponse.getStatusCode(), HttpStatus.NOT_MODIFIED);


	}


}
