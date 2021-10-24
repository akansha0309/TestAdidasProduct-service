package com.adidas.tests;


import org.testng.annotations.*;

import com.adidas.constants.EndPoints;
import com.adidas.helper.ConfigManager;
import com.adidas.helper.ProductHelper;
import com.adidas.model.Product;
import com.adidas.model.ProductComplete;

import groovy.lang.Newify;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TestDeleteProduct extends BaseTest{

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
	public void testValidDeleteProduct_productExists() {
		LOG.info("Test DELETE - delete product");
		given()
		.pathParam("id", "1")
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.delete(BASE_URL+EndPoints.DELETE_PRODUCT)
		.then()
		.assertThat()
		.statusCode(200)   
		.and()
		.body("$.",  hasEntry("ok", 1));

	}

	@Test
	public void testValidDeleteProduct_productDoesntExists() {
		LOG.info("Test DELETE - delete product");
		given()
		.pathParam("id", "4")
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.delete(BASE_URL+EndPoints.DELETE_PRODUCT)
		.then()
		.assertThat()
		.statusCode(200)   
		.and()
		.body("$.", aMapWithSize(1))
		.body("$.", hasEntry("ok", 0));


	}

	@Test
	public void testInValidDeleteProduct_emptyProductId() {
		LOG.info("Test DELETE - delete product");
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.delete(BASE_URL+EndPoints.DELETE_PRODUCT_EMPTYID)
		.then()
		.assertThat()
		.statusCode(400); // This test will fail as API is currently returning 404 but expected as per Swagger doc is 400

	}



	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
		ProductHelper.deleteProducts(products);
	}


}
