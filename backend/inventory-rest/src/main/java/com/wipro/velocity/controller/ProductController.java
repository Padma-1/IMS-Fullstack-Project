package com.wipro.velocity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.velocity.exception.ResourceNotFoundException;
import com.wipro.velocity.model.Product;
import com.wipro.velocity.repository.ProductRepository;

/*
 * Cross-origin resource sharing (CORS) is a standard protocol that defines the interaction between
 * a browser and a server for safely handling cross-origin HTTP requests.
Simply put, a cross-origin HTTP request is a request to a specific resource,
which is located at a different origin, namely a domain, protocol and port, than
the one of the client performing the request.
 */

@RestController
@CrossOrigin(origins= "http://localhost:4200")

@RequestMapping(value="/api")
public class ProductController {
	
    @Autowired
	private ProductRepository prepo;
    
    // @RequestBody annotation automatically deserializes the JSON into a Java type
   
    //  POST - http://localhost:9095/ims/api/products
    @PostMapping("/products")
    public Product saveProduct(@Validated @RequestBody Product product)
    {
    	return prepo.save(product); //invokes save() method of MongoRepository
    }
    
    // GET - http://localhost:9095/ims/api/products
    @GetMapping("/products")
    public List<Product> getAllProducts()
    {
    	return prepo.findAll();
    }
    
    //ResponseEntity represents the whole HTTP response: status code, headers, and body. 
    //As a result,we can use it to fully configure the HTTP response.
   
    // PUT - http://localhost:9095/ims/api/products/101
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value="id") String pId,
    		@Validated @RequestBody Product p) throws ResourceNotFoundException
    {
    	Product product=prepo.findById(pId).
    			orElseThrow(() -> new ResourceNotFoundException
    			("Product Not Found for this Id: " +pId));
    			
    			product.setName(p.getName());
    			product.setBrand(p.getBrand());
    			product.setMadein(p.getMadein());
    			product.setPrice(p.getPrice());
    			
    			final Product updatedProduct=prepo.save(product);
    			
    			return ResponseEntity.ok(updatedProduct);
    }
    
    // DELETE - http://localhost:9095/ims/api/products/101
    
    @DeleteMapping("/products/{id}")
    public Map<String,Boolean> deleteProduct(@PathVariable(value="id") String pId)
       throws ResourceNotFoundException
    {
    	Product product =prepo.findById(pId).
    			orElseThrow(() -> new ResourceNotFoundException
    			("Product Not Found for this Id: "+pId));
    	
    	prepo.delete(product);
    	
    	Map<String,Boolean> response=new HashMap<>();
    	
    	response.put("Deleted the product",Boolean.TRUE);
    	return response;
    }
    
 // GET - http://localhost:9095/ims/api/products{id}
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value="id") String pId)
    		throws ResourceNotFoundException
    		{
    	             Product product =prepo.findById(pId).  //findById()  method is predefined in MongoRepository
    			         orElseThrow(() -> new ResourceNotFoundException
    			          ("Product Not Found for this Id: "+pId));
    	             
    	             return ResponseEntity.ok().body(product);     
    		}
    
    // GET - http://localhost:9095/ims/api/getproductsbycountry/India
    @GetMapping("/getproductsbycountry/{madein}")
    public List<Product> getAllProductsByMadein(@PathVariable(value="madein") String madein)
    {
    	return  (List<Product>) prepo.findByMadein(madein);
    }
}