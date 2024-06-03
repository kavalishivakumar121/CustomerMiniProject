package com.mini;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiniProjectBootApplicationTests {

	@Test
	void contextLoads() {
	assertThat("Hi").isEqualTo("Hello");
	}

}
