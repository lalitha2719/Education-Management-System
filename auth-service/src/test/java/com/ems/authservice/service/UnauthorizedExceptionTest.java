package com.ems.authservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class UnauthorizedExceptionTest {
	
	
	
	@Test
	 void constructortest()
	{
		UnauthorizedException unauthorizedException =new UnauthorizedException("unauthorized");
		//System.out.println(unauthorizedException.getMessage());
		assertEquals("unauthorized", unauthorizedException.getMessage());
	}

}
