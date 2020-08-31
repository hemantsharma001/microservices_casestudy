package com.fis.subscriptionservice.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.fis.subscriptionservice.vo.SubscriptionRequestVO;

@Component
public class SubscriptionDaoUtil {
	
		
		@Autowired
		private DataSource dataSource;
		
		@Autowired
		private NamedParameterJdbcTemplate jdbcTemplate;

		public Integer getSubscriptionCount(SubscriptionRequestVO requestVO) {
			String query = "select count(*) from subscription where book_id =:bookID and subscriber_name= :subscriberName and date_returned is null";
			Map<String, Object> parameterMap = new HashMap<String,Object>();
			parameterMap.put("bookID", requestVO.getBookID());
			parameterMap.put("subscriberName", requestVO.getSubscriberName());
			Integer count = jdbcTemplate.queryForObject(query,parameterMap, Integer.class);
			return count;
		}
		
		
		public Integer update(SubscriptionRequestVO requestVO) {
			String query = "update subscription set date_returned= :dateReturned where book_id = :bookID" + 
							" and subscriber_name= :subscriberName and date_returned is null ORDER BY id LIMIT 1";
			Map<String, Object> parameterMap = new HashMap<String,Object>();
			parameterMap.put("bookID", requestVO.getBookID());
			parameterMap.put("subscriberName", requestVO.getSubscriberName());
			parameterMap.put("dateReturned", requestVO.getDateReturned());
			 jdbcTemplate.update(query,parameterMap);
			return 1;
		}


}
