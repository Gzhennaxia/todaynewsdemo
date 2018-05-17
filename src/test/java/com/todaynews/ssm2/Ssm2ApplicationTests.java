package com.todaynews.ssm2;

import com.todaynews.ssm2.asyncevent.EntityType;
import org.junit.Test;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class Ssm2ApplicationTests {

	@Test
	public void contextLoads() {

		System.out.println(EntityType.NEWS.getValue());
	}

}
