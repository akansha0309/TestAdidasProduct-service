package com.adidas.tests;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class BaseTest {
	public Logger LOG = Logger.getLogger(BaseTest.class);
	public static final ObjectMapper MAPPER = new ObjectMapper();
	
}
