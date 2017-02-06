package testTool;

import java.io.File;

import org.junit.Test;

import yyj.tools.FileUtil;

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
		System.out.println(FileUtil.getFileContentString(FileUtil.getFileOppositeClassPath("test.txt")));
	}
	
	
	public static void main(String[] args){
		TestFileUtil test=new TestFileUtil();
		test.getFileOppositeClassPath();
	}
}
