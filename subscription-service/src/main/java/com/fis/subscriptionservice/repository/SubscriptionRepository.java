package com.fis.subscriptionservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fis.subscriptionservice.entity.Subscription;

public interface SubscriptionRepository  extends CrudRepository<Subscription, String> {
	
	
	
	
	
	List<Subscription> findByBookID(String bookID);
	
	List<Subscription> findBySubscriberName(String subscriberName);
	
	

}
