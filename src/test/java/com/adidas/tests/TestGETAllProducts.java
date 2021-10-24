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

public class TestGETAllProducts extends BaseTest{

	private static final String BASE_URL = ConfigManager.getInstance().getString("api.base_url");
    public List<Product> products = new ArrayList<Product>();

	@BeforeClass
	public void setup() {
		LOG.info("creating products");
		Product product1 = new Product("1", "Shoe1", "New Shoes1");
		Product product2 = new Product("2", "Shoe2", "New Shoes2");
		Product product3 = new Product("3", "Shoe3", "New Shoes3");
		
		products.add(product1);
		products.add(product2);
		products.add(product3);
		
		ProductHelper.createProducts(products);
		
	}

	@Test
	public void testGetAllProducts() {
		LOG.info("Test GET all Products");
		Response response = given()
		.get(BASE_URL + EndPoints.GET_ALL_PRODUCTS)
		.andReturn();
		
		 assertEquals(response.statusCode(), 200);
		 assertTrue(response.getBody().jsonPath().getList("id").contains("1"));
		 assertTrue(response.getBody().jsonPath().getList("id").size() >= 3);
		 assertTrue(response.getBody().jsonPath().getList("name").size() >= 3);
		 assertTrue(response.getBody().jsonPath().getList("description").size() >= 3);
		 assertTrue(response.getBody().jsonPath().getList("price").size() >= 3);
		 assertTrue(response.getBody().jsonPath().getList("currency").size() >= 3);
		
	}
	
	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
		ProductHelper.deleteProducts(products);
	}


}
