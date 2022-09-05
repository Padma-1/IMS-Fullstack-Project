package com.wipro.velocity.repository;

import java.util.Optional;

import com.wipro.velocity.model.Dealer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DealerRepository extends MongoRepository<Dealer, String> {

	//Custom method to fetch Dealer Object based on Non-id field
	public Optional<Dealer> findByEmail(String email);
}
