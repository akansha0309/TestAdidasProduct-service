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


public class TestUpdateProduct extends BaseTest{

	private static final String BASE_URL = ConfigManager.getInstance().getString("api.base_url");
	Product product1 , product2, product3;

	@BeforeClass
	public void setup() {
		product1 = new Product("4", "Shoe1", "New Shoes1");
		product2 = new Product("4","Shoe1 updated","New Shoes1 updated");
		ProductHelper.createProduct(product1);

	}


	@Test
	public void testValidUpdateProduct() {
		LOG.info("Test PUT - update product");
		Response response = given()
				.pathParam("id", "4")
				.header("accept", "application/json")
				.header("content-type", "application/json")
				.body(product2.toString())
				.put(BASE_URL+EndPoints.UPDATE_PRODUCT)
				.andReturn();
		assertTrue(response.getStatusCode()==200);
		Product updatedProduct = response.getBody().as(Product.class);
		assertTrue(updatedProduct.equals(product2));


	}


	@Test(dataProvider = "updateInvalidProducts")
	public void testInValidUpdateProduct(Product product) {
		LOG.info("Test PUT - update product with invalid data");
		given()
		.pathParam("id", "4")
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(product.toString())
		.put(BASE_URL+EndPoints.UPDATE_PRODUCT)
		.then()
		.assertThat()
		/* This test will fail as API currently allows updating product with empty fields.
		 * There is no validation on mandatory fields which should be fixed.
		 * Once such a entry is created, it breaks the system as GET /Products call starts failing */
		.statusCode(400)   
		.and()
		.body("$.",  hasEntry("error", "product fields cannot be empty"));

	}

	@DataProvider
	public Iterator<Product> updateInvalidProducts() {
		List<Product> list = new ArrayList<Product>();
		list.add(new Product("", "", ""));
		list.add(new Product("", "shoe1", "shoe1_desc"));
		list.add(new Product("4", "", "shoe1_desc"));
		list.add(new Product("4", "shoe2", ""));
		return list.iterator();
	}



	@Test
	public void testValidUpdateProduct_emptyID() {
		LOG.info("Test PUT - update product");
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(product2.toString())
		.put(BASE_URL+EndPoints.UPDATE_PRODUCT_EMPTYID)
		.then()
		.assertThat()
		.statusCode(400);
		

	}


	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
		ProductHelper.deleteProduct(product1);
	}


}
