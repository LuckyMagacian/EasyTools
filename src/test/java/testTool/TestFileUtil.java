package testTool;

import java.io.File;

import org.junit.Test;

import yyj.tools.FileUtil;
import yyj.tools.HttpUtil;

public class TestFileUtil {

	@Test
	public void getFiles(){
		for(File each:FileUtil.getFiles("d:/", null))
			System.out.println(each.getName());
	}
	@Test
	public void getFileList(){
		for(File each:FileUtil.getFileList("d:/", null))
			System.out.println(each.getName());
	}
	@Test
	public void getFileOppositeClassPath(){
		System.out.println(FileUtil.getFileOppositeClassPath("test.txt"));
	}
	@Test
	public void getFileStr(){
		String xml=FileUtil.getFileContentString(FileUtil.getFileOppositeClassPath("test.txt"));
		xml.replaceAll("702166200009", "702166200110");
		System.out.println(HttpUtil.postXml(xml, "http://1.202.156.227:7002/InterFaceServlet", "utf-8", 60000));
	}
	
	
	public static void main(String[] args){
		TestFileUtil test=new TestFileUtil();
		test.getFileOppositeClassPath();
	}
}
