package com.mgt2.backendproject;

import com.mgt2.backendproject.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = WebConfig.class)
class BackendProjectApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Passed");
	}

}
