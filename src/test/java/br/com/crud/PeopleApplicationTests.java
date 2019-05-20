package br.com.crud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void initApplication() {
		PeopleApplication.main(new String[] {});
	}

}
