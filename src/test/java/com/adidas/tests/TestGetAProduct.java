package com.adidas.tests;


import org.testng.annotations.*;

import com.adidas.constants.EndPoints;
import com.adidas.helper.ConfigManager;
import com.adidas.helper.ProductHelper;
import com.adidas.model.Product;
import com.adidas.model.ProductComplete;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestGetAProduct extends BaseTest{

	private static final String BASE_URL = ConfigManager.getInstance().getString("api.base_url");
	 public List<Product> products = new ArrayList<Product>();
	 Product productToBeSearched;
	 
	@BeforeClass
	public void setup() {
		LOG.info("creating products");
		Product product1 = new Product("1", "Shoe1", "New Shoes1");
		Product product2 = new Product("2", "Shoe2", "New Shoes2");
		Product product3 = new Product("3", "Shoe3", "New Shoes3");
		productToBeSearched = new Product("4", "Shoe4", "New Shoes4");
		
		products.add(product1);
		products.add(product2);
		products.add(product3);
		products.add(productToBeSearched);
		
		ProductHelper.createProducts(products);
		
	}

	@Test
	public void testGetProduct_product_exists() {
		LOG.info("Test GET a Products");
		Response response = given()
		.pathParam("id", "4")
		.get(BASE_URL + EndPoints.GET_PRODUCT_BY_ID)
		.andReturn();
		
		ProductComplete productComplete = response.getBody().as(ProductComplete.class);
		assertTrue(productComplete.getId().equals(productToBeSearched.getId()));
		assertTrue(productComplete.getName().equals(productToBeSearched.getName()));
		assertTrue(productComplete.getDescription().equals(productToBeSearched.getDescription()));
		assertTrue(productComplete.getCurrency()!=null);
		assertTrue(productComplete.getPrice()>0);
		
		assertTrue(response.getStatusCode()==200);
		
		
		
		
	}
	
	@Test
	public void testGetProduct_product_doesnt_exists() {
		LOG.info("Test GET a Products");
		given()
		.pathParam("id", "5")
		.get(BASE_URL + EndPoints.GET_PRODUCT_BY_ID)
		.then()
		.assertThat()
		 /* This test will fail as API is currently returning 500 Internal server error with entire exception being returned.
		  * This exception should be handled by developers and appropriate error message should be returned
		  */
		.statusCode(204)   
		.and()
		.body("$.", hasEntry("msg", "product not found"));
		
	}
	
	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
		ProductHelper.deleteProducts(products);
	}


}
