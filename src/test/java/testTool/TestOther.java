package testTool;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

import yyj.tools.AppMessage;
import yyj.tools.BeanUtil;

public class TestOther {

	@Test
	public void testFileSeparator() throws URISyntaxException{
		System.out.println(File.separator);
		System.out.println(File.pathSeparator);
		String path1="/a/b/c/d/e";
		String[] strs=path1.split("/");
		System.out.println(path1.startsWith(File.separator));
		String path2=TestOther.class.getClassLoader().getResource(File.separator).toURI().getPath();
		System.out.println(path2);
		String path3=TestOther.class.getClassLoader().getResource("").toURI().getPath();
		System.out.println(path3);
		System.out.println(Arrays.asList(strs));
	}
	@Test
	public void  testClazz(){
		System.out.println(BeanUtil.getSetters(AppMessage.class));
	}
}
