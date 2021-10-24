package com.adidas.helper;


import org.apache.log4j.Logger;

import com.adidas.constants.EndPoints;
import com.adidas.model.Product;

import static io.restassured.RestAssured.given;

import java.util.List;

public class ProductHelper {

	public static Logger LOG = Logger.getLogger(ProductHelper.class);
	private static final String BASE_URL = ConfigManager.getInstance().getString("api.base_url");

	
	
	public void createProduct(Product product) {
		LOG.info("creating product" + product.toString());
		given()
		.header("accept", "application/json")
		.header("content-type", "application/json")
		.body(product)
		.post(BASE_URL+EndPoints.CREATE_PRODUCT);
	}

	
	public static void createProducts(List<Product> products) {

		for(Product product: products) {
			LOG.info("creating product" + product.toString());

			given()
			.header("accept", "application/json")
			.header("content-type", "application/json")
			.body(product)
			.post(BASE_URL+EndPoints.CREATE_PRODUCT);
		}
	}

	
	public static void deleteProduct(Product product) {
		LOG.info("deleting product" + product.toString());
		given()
		.pathParam("id", product.getId())
		.delete(BASE_URL + EndPoints.DELETE_PRODUCT);
	}

	
	public static void deleteProducts(List<Product> products) {

		for(Product product: products) {
			LOG.info("deleting product" + product.toString());

			given()
			.pathParam("id", product.getId())
			.delete(BASE_URL + EndPoints.DELETE_PRODUCT);
		}
	}
	
	
	
	
	
	
	

}
