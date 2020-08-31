package com.fis.subscriptionservice.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RibbonClient(name="book-service")
@FeignClient(name="api-gateway", fallback = BookProxyFallback.class)
public interface BookProxy {
	
	@GetMapping("/book-service/book-service/getAvailableCopies")
	public ResponseEntity<Long> getAvailableCopies(@RequestParam("bookID") String bookID);
	
	@PostMapping("/book-service/book-service/updateForSubscription")
	public ResponseEntity<String> updateForSubscription(@RequestParam("bookID") String bookID);
	
	@PostMapping("/book-service/book-service/updateForUnsubscription")
	public ResponseEntity<String> updateForUnsubscription(@RequestParam("bookID") String bookID);
	
	
	

}
