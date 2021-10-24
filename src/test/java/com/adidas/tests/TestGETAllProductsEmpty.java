package com.adidas.tests;


import org.testng.annotations.*;

import com.adidas.constants.EndPoints;
import com.adidas.helper.ConfigManager;
import com.adidas.helper.ProductHelper;
import com.adidas.model.Product;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class TestGETAllProductsEmpty extends BaseTest{

	private static final String BASE_URL = ConfigManager.getInstance().getString("api.base_url");
    public List<Product> products = new ArrayList<Product>();

	@BeforeClass
	public void setup() {
		
		
	}

	
	
	
	@Test
	public void testGetAllProductsEmpty() {
		LOG.info("Test GET all Products");
		given()
		.get(BASE_URL + EndPoints.GET_ALL_PRODUCTS)
		.then()
		.assertThat()
		.statusCode(200)   
		.and()
		.body("$.", hasSize(0));
		 
		
	}
	
	@AfterClass
	public void tearDown() {
		
	}


}
