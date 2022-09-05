package com.wipro.velocity.repository;

import java.util.List;

import com.wipro.velocity.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

	//Custom method to find products based on madein field
	//we have pre-defined method for findbyId (in which Id is primary key)
	List<Product> findByMadein(String madein);
	
}
