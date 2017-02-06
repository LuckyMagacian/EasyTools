package yyj.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yyj.tools.FileUtil;

@Controller
public class TestController {
	private static Logger logger=Logger.getLogger(TestController.class);
	@RequestMapping("/test1")
	@ResponseBody
	public String testFileUtil(){
		new Test().test();
		return "123456";
	}
	class Test{
		public void test(){
			logger.info(FileUtil.getFileOppositeClassPath("/test.txt"));
		}
	}
}

