package testTool;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestService {
	private ApplicationContext ac;
	@Before
	public void Init(){
		ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void test1(){
		System.out.println(ac.getBean("carOwnerDao"));
	}
	
}
