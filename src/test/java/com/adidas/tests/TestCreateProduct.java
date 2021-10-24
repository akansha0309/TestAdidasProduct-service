package com.adidas.tests;


import org.testng.annotations.*;

import com.adidas.constants.EndPoints;
import com.adidas.helper.ConfigManager;
import com.adidas.helper.ProductHelper;
import com.adidas.model.Product;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
		.statusCode(200)
		.and()
		.body("$.", aMapWithSize(4))
		.body("$.", hasKey("_id"))
		.body("$.", hasEntry("id", product.getId()))
		.body("$.", hasEntry("name", product.getName()))
		.body("$.", hasEntry("description", product.getDescription()));
		
	}
	
	
	
	@Test(dataProvider = "createInvalidProducts")
	public void testCreateEmptyProduct(String body) {
		LOG.info("Test POST - create product");
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(body)
		.post(BASE_URL+EndPoints.CREATE_PRODUCT)
		.then()
		.assertThat()
		/* This test will fail as API currently allows creating a product with empty fields.
		 * There is no validation on mandatory fields which should be fixed.
		 * Once such a entry is created, it breaks the system as GET /Products call starts failing */
		.statusCode(400)   
		.and()
		.body("$.",  hasEntry("error", "product fields cannot be empty"));
		
	}
	
	@DataProvider
	public Iterator<String> createInvalidProducts() {
		List<String> list = new ArrayList<String>();
		list.add(new Product("", "", "").toString());
		list.add(new Product("", "shoe1", "shoe1_desc").toString());
		list.add(new Product("33", "", "shoe1_desc").toString());
		list.add(new Product("32", "shoe2", "").toString());
		return list.iterator();
	}
	
	@AfterClass
	public void tearDown() {
		LOG.info("deleting products");
		ProductHelper.deleteProduct(product);
	}


}
