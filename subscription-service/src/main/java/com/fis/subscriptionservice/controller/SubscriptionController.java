package com.fis.subscriptionservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fis.subscriptionservice.entity.Subscription;
import com.fis.subscriptionservice.service.SubscriptionService;
import com.fis.subscriptionservice.vo.SubscriptionRequestVO;

@RestController
@RequestMapping("/subscription-service")
public class SubscriptionController {
	
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	
	
	
	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribe(SubscriptionRequestVO requestVO) {
		if (null != requestVO.getDateReturned()) {
			return subscriptionService.unsubscribe(requestVO);
		}
		return subscriptionService.subscribe(requestVO);
		
	}
	
	@GetMapping("/getSubscriptions")
	public ResponseEntity<List<Subscription>> getSubscriptions(@QueryParam("subscriberName") String subscriberName) {
			List<Subscription> list = subscriptionService.getSubscriptions(subscriberName);
			return new ResponseEntity<List<Subscription>>(list, HttpStatus.OK);
			
		
	}


}
