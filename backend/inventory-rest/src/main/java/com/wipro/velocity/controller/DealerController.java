package com.wipro.velocity.controller;

import java.util.List;

import com.wipro.velocity.exception.ResourceNotFoundException;
import com.wipro.velocity.model.Dealer;
import com.wipro.velocity.repository.DealerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins= "http://localhost:4200")
@RequestMapping("/api")
public class DealerController {

	@Autowired
	private DealerRepository drepo;

	//POST - http://localhost:9095/ims/api/registerDealer

	//use body-raw -JSON
	/*
	  { "email" : "raj@ims.com", 
	   "fname" : "raj", 
	   "lname" : "gs", 
	   "password" : "Testing",
	   "dob" : "2000-12-01",
	   "phoneNo" : "7983929832",
	   "street" : "Down Town Stree", 
	   "city" : "Bangalore"
       "pincode" : 232783 }
	 */
	@PostMapping("/registerDealer")
	public String saveDealer(@RequestBody Dealer dealer) {
		drepo.save(dealer);
		return "Dealer Registered Successfully";
	}

	//GET - http://localhost:9095/ims/api/findAllDealers
	@GetMapping("/findAllDealers")
	public List<Dealer> getDealerList(){
		return drepo.findAll();
	}

	//POST - http://localhost:9095/ims/api/loginDealer
	//Body - raw - JSON
	/*
	 {
    "email" : "raj@ims.com",
    "password" : "Testing"
	 }
	 */
	@PostMapping("/loginDealer")
	public Boolean loginDealer(@Validated @RequestBody Dealer dealer) throws ResourceNotFoundException {
		Boolean isLogin=false;
		String email = dealer.getEmail();
		String password = dealer.getPassword();

		//findByEmail() is defined in MongoRepository
		//If u give unknown email, then below Exception will be thrown
		Dealer d = drepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Unknown Dealer")); 

		if(email.equals(d.getEmail()) && password.equals(d.getPassword())) {
			isLogin  =true;
		}

		return isLogin;
	}


}
