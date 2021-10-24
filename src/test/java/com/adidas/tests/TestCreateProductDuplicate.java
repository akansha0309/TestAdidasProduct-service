package com.adidas.tests;


import org.testng.annotations.*;

import com.adidas.constants.EndPoints;
import com.adidas.helper.ConfigManager;
import com.adidas.helper.ProductHelper;
import com.adidas.model.Product;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TestCreateProductDuplicate extends BaseTest{

	private static final String BASE_URL = ConfigManager.getInstance().getString("api.base_url");
	Product product;
	
	@BeforeClass
	public void setup() {
		 product = new Product("1", "Shoe1", "New Shoes1");
		
	}

	
	@Test
	public void testCreateProductDuplicate() {
		LOG.info("Test POST - create product");
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(product)
		.post(BASE_URL+EndPoints.CREATE_PRODUCT);
		
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(product)
		.post(BASE_URL+EndPoints.CREATE_PRODUCT)
		.then()
		.assertThat()
		.statusCode(400)    // This test will fail as API currently lets you create product with same ID
		.and()
		.body("$.",  hasEntry("error", "Duplicate product id"));
		
		
	}
	
	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
		ProductHelper.deleteProduct(product);
		ProductHelper.deleteProduct(product);
	}


}
