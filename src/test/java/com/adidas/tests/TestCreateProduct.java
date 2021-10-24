package com.adidas.tests;


import org.testng.annotations.*;

import com.adidas.constants.EndPoints;
import com.adidas.helper.ConfigManager;
import com.adidas.helper.ProductHelper;
import com.adidas.model.Product;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TestCreateProduct extends BaseTest{

	private static final String BASE_URL = ConfigManager.getInstance().getString("api.base_url");
	Product product;
	Product emptyProduct;
	@BeforeClass
	public void setup() {
		 product = new Product("1", "Shoe1", "New Shoes1");
		 emptyProduct = new Product();
		 
		
	}

	
	
	@Test
	public void testCreateProduct() {
		LOG.info("Test POST - create product");
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(product)
		.post(BASE_URL+EndPoints.CREATE_PRODUCT)
		.then()
		.assertThat()
		.statusCode(201)    // This test will fail as API is currently returning 200 status code. This is a bug
		.and()
		.body("$.", aMapWithSize(4))
		.body("$.", hasKey("_id"))
		.body("$.", hasEntry("id", product.getId()))
		.body("$.", hasEntry("name", product.getName()))
		.body("$.", hasEntry("description", product.getDescription()));
		
	}
	
	@Test
	public void testCreateEmptyProduct() {
		LOG.info("Test POST - create product");
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(emptyProduct)
		.post(BASE_URL+EndPoints.CREATE_PRODUCT)
		.then()
		.assertThat()
		// This test will fail as API currently allows creating a product with empty fields. Once such entry is created, it breaks the system as GET /Products call starts failing
		.statusCode(400)   
		.and()
		.body("$.",  hasEntry("error", "product cannot be empty"));
		
	}
	
	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
		ProductHelper.deleteProduct(product);
	}


}
