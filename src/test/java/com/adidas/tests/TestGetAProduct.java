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

public class TestGetAProduct extends BaseTest{

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
		
		//ProductHelper.createProducts(products);
		
	}

	@Test
	public void testGetProduct_product_exists() {
		LOG.info("Test GET a Products");
		given()
		.pathParam("id", "1")
		.get(BASE_URL + EndPoints.GET_PRODUCT_BY_ID)
		.then()
		.assertThat()
		.statusCode(201)    // This test will fail as API is currently returning 200 status code. This is a bug
		.and()
		.body("$.", aMapWithSize(4))
		.body("$.", hasKey("_id"))
		.body("$.", hasEntry("id", product.getId()))
		
	}
	
	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
	//	ProductHelper.deleteProducts(products);
	}


}
