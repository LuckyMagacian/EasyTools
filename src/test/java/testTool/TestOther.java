package testTool;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import yyj.entity.CarOwner;
import yyj.tools.AppMessage;
import yyj.tools.BeanUtil;
import yyj.tools.EleServerUtil;
import yyj.tools.EleServerUtil.BuyReqMsg;
import yyj.tools.EleServerUtil.EleReport;
import yyj.tools.EleServerUtil.Head;
import yyj.tools.EleServerUtil.ReqHead;

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
	public void  testClazz() throws Exception{
		
	}
	@Test
	public void testqst() throws InterruptedException{
		EleReport report=new EleReport();
		ReqHead head=new ReqHead(Head.MSG_NO_BUY, "3301","100000000000","hzlx");
		head.init();
		head.setMsgNo(ReqHead.MSG_NO_BUY);
		head.setSrc(head.getDes());
		BuyReqMsg msg=new BuyReqMsg("15068610940",BuyReqMsg.GOOD_TYPE_PHONE_COST,"1001", "1","1");
		report.setHead(head);
		report.setMsg(msg);
		System.out.println(report.getPostResult());	
	}
	@Test
	public void testsql(){
		CarOwner owner=new CarOwner();
	}
}
