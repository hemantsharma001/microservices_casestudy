package com.fis.subscriptionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fis.subscriptionservice.entity.Subscription;
import com.fis.subscriptionservice.proxy.BookProxy;
import com.fis.subscriptionservice.repository.SubscriptionDaoUtil;
import com.fis.subscriptionservice.repository.SubscriptionRepository;
import com.fis.subscriptionservice.vo.SubscriptionRequestVO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.lang.RuntimeException;
import java.util.List;

@Service
public class SubscriptionService {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private BookProxy proxy;
	
	@Autowired
	private SubscriptionDaoUtil subscriptionDaoUtil;
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	@HystrixCommand(fallbackMethod = "unsubscribeFallback")
	public ResponseEntity<String> unsubscribe(SubscriptionRequestVO requestVO) {
		Subscription subscription = new Subscription();
		subscription.setBookID(requestVO.getBookID());
		subscription.setDateReturned(requestVO.getDateReturned());
		subscription.setDateSubscribed(requestVO.getDateSubscribed());
		subscription.setSubscriberName(requestVO.getSubscriberName());
		Integer count = subscriptionDaoUtil.getSubscriptionCount(requestVO);
		if (count > 0) {
			subscriptionDaoUtil.update(requestVO);
			ResponseEntity<String> subscriptionResponse = proxy.updateForUnsubscription(requestVO.getBookID());
			if (subscriptionResponse.getStatusCode() != HttpStatus.OK) {
				throw new RuntimeException();
			}
		} else {
			return new ResponseEntity<String>("This book hasn't been subscribed by user "+requestVO.getSubscriberName(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<String>("Book has been unsubcribed successfully", HttpStatus.OK);
		
		
	}
	
	public ResponseEntity<String> unsubscribeFallback(SubscriptionRequestVO requestVO) {
		return new ResponseEntity<String>("Book can't be unsubcribed at the moment", HttpStatus.EXPECTATION_FAILED);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	@HystrixCommand(fallbackMethod = "subscribeFallback")
	public ResponseEntity<String> subscribe(SubscriptionRequestVO requestVO) {
		Subscription subscription = new Subscription();
		subscription.setBookID(requestVO.getBookID());
		subscription.setDateReturned(requestVO.getDateReturned());
		subscription.setDateSubscribed(requestVO.getDateSubscribed());
		subscription.setSubscriberName(requestVO.getSubscriberName());
		ResponseEntity<Long> availableCopiesResponse = proxy.getAvailableCopies(requestVO.getBookID());
		if (availableCopiesResponse.getBody() > 0) {
			subscriptionRepository.save(subscription);
			ResponseEntity<String> subscriptionResponse = proxy.updateForSubscription(requestVO.getBookID());
			if (subscriptionResponse.getStatusCode() != HttpStatus.OK) {
				throw new RuntimeException();
			}
		} else {
			return new ResponseEntity<String>("Book copies not available for subscription", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<String>("Book has been subcribed successfully", HttpStatus.CREATED);
		
		
	}
	
	public ResponseEntity<String> subscribeFallback(SubscriptionRequestVO requestVO) {
		return new ResponseEntity<String>("Book can't be subscribed at the moment.", HttpStatus.EXPECTATION_FAILED);
	}
	
	public List<Subscription> getSubscriptions(String subscriberName) {
		if (null != subscriberName) {
			return subscriptionRepository.findBySubscriberName(subscriberName);
		} 
		return (List<Subscription>) subscriptionRepository.findAll();
	}
 
	
	

}
