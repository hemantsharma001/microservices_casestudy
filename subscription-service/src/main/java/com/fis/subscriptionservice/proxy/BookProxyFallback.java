package com.fis.subscriptionservice.proxy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BookProxyFallback implements BookProxy{

	@Override
	public ResponseEntity<Long> getAvailableCopies(String bookID) {
		return new ResponseEntity<Long>(new Long(0), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> updateForSubscription(String bookID) {
		return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
	}

	@Override
	public ResponseEntity<String> updateForUnsubscription(String bookID) {
		return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
	}

}
