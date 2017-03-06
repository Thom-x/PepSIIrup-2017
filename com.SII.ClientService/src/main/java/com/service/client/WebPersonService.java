package com.service.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Hide the access to the microservice inside this local service.
 */
@Service
public class WebPersonService {
	
	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;


	public WebPersonService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}
	
	public Person greeting() {
		return restTemplate.getForObject(serviceUrl + "/greeting",
				Person.class);
	}

}
