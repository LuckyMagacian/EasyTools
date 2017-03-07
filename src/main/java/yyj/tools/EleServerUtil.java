package yyj.tools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import yyj.tools.EleServerUtil.BuyResMsg.Sku;

public class EleServerUtil {
	/**报文当日序号*/
	private static volatile Integer reportCount=0;
	/**定时器上一次获取的时间*/
//	private static String  before=TimeUtil.getTime();
//	private static Timer   timer=new Timer(1000, new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			String current=TimeUtil.getTime();
//			if(before.compareTo(current)>0)
//				initCount();
//			before=current;
//		}
//	});
	
//	static{
//		timer.start();
//	}
	
	public static void setReportCount(Integer count){
		reportCount=count;
	}
	
	public static Integer getReportCount(){
		return reportCount;
	}
	
	public static void initCount(){
		reportCount=0;
//		timer.start();
	}

	
	public static class     EleReport{
		/**消息头*/
		private Head head;
		/**请求信息*/
		private Msg  msg;
		public Head getHead() {
			return head;
		}
		public void setHead(Head head) {
			this.head = head;
		}
		public Msg getMsg() {
			return msg;
		}
		public void setMsg(Msg msg) {
			this.msg = msg;
		}
		public Document toDocument(String charSet){
			selfCheck();
			sign();
			Document document=DocumentHelper.createDocument();
			document.setXMLEncoding(charSet==null||charSet.isEmpty()?"utf-8":charSet);
			DOMElement root=new DOMElement("JFDH");
			if(head!=null)
				root.add(head.toElement());
			else
				root.addElement(Head.NAME);
			if(msg!=null)
				root.add(msg.toElement());
			else
				root.addElement(Msg.NAME);
			document.setRootElement(root);
			return document;
		}
		
		protected void selfCheck(){
			try{
				List<Method> list=BeanUtil.getGetters(this);
				boolean flag=true;
				for(Method each:list){
					flag&=(!(each.invoke(this)==null));
				}
				assert flag:this.getClass().getName()+"中的属性值不允许为null或空";
			}catch (Exception e) {
				throw new RuntimeException(this.getClass().getSimpleName()+"自我检查异常",e);
			}
		}
		
		public static EleReport fromDocument(Document document){
			EleReport baoWen=null;
			if(document!=null&&document.getRootElement().getName().equals("JFDH")){
				Element root=document.getRootElement();
				baoWen=new EleReport();
				if(root.selectSingleNode("//ResCode")==null){
					ReqHead head=(ReqHead) ReqHead.fromElement(root.element(Head.NAME));
					baoWen.setHead(head);
					switch (head.getMsgNo()) {
					case ReqHead.MSG_NO_BUY:
						baoWen.setMsg(BuyReqMsg.fromElement(root.element(Msg.NAME)));
						break;
					case ReqHead.MSG_NO_QUERY_DETAIL:
						baoWen.setMsg(QueryDetailReqMsg.fromElement(root.element(Msg.NAME)));					
						break;
					case ReqHead.MSG_NO_QUERY_HISTORY:
						baoWen.setMsg(QueryHistoryReqMsg.fromElement(root.element(Msg.NAME)));
						break;
					case ReqHead.MSG_NO_ACCOUNT_CHECK:
						baoWen.setMsg(AccountCheckReqMsg.fromElement(root.element(Msg.NAME)));
						break;
					case ReqHead.MSG_NO_TEST:
						baoWen.setMsg(TestReqMsg.fromElement(root.element(Msg.NAME)));
						break;
					}
				}else{
					ResHead head=(ResHead) ResHead.fromElement(root.element(Head.NAME));
					baoWen.setHead(head);
					if(ResHead.SUCCESS.equals(root.selectSingleNode("//ResCode").getText())){
						switch (head.getMsgNo()) {
						case ReqHead.MSG_NO_BUY:
							baoWen.setMsg(BuyResMsg.fromElement(root.element(Msg.NAME)));
							break;
						case ReqHead.MSG_NO_QUERY_DETAIL:
							baoWen.setMsg(QueryDetailResMsg.fromElement(root.element(Msg.NAME)));					
							break;
						case ReqHead.MSG_NO_QUERY_HISTORY:
							baoWen.setMsg(QueryHistoryResMsg.fromElement(root.element(Msg.NAME)));
							break;
						case ReqHead.MSG_NO_ACCOUNT_CHECK:
							baoWen.setMsg(AccountCheckReqMsg.fromElement(root.element(Msg.NAME)));
							break;
						case ReqHead.MSG_NO_TEST:
							baoWen.setMsg(TestResMsg.fromElement(root.element(Msg.NAME)));
							break;
						}
					}
				}
			}
			return baoWen;
		}
		public static EleReport fromDocumentStr(String documentStr){
			try {
				return EleReport.fromDocument(DocumentHelper.parseText(documentStr));
			} catch (Exception e) {
				throw new RuntimeException("报文转实体异常",e);
			}
		}
		public String sign(){
			selfCheck();
			Map<String, String> params=BeanUtil.getParamMap(head);
			params.putAll(BeanUtil.getParamMap(msg));
			params.remove("sign");
			params.remove("key");
			String tempSign=SignUtil.md5LowerCase(SignUtil.mapToValueString(params)+head.getKey(),"GBK");
			head.setSign(tempSign);
			return head.getSign();
		}
		
		public String getPostResult(String url,String charSet,String fileCharset){
			return HttpUtil.postXml(toDocument(fileCharset).asXML(), url, charSet==null||charSet.isEmpty()?"utf-8":charSet, 6000);
		}
		
		public String getPostResult(){
			String url="http://192.168.17.181:8084/jfdh/BankServlet";
			String charSet="GBK";
			String fileCharset="GBK";
			return getPostResult(url, charSet, fileCharset);
		}
		
		public EleReport getPostReusltObj() {
			return fromDocumentStr(getPostResult());
		} 
		
		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
		
	}
	
	public static abstract class Head{
		/**交易类型-购买电子礼品*/
		public static final String MSG_NO_BUY="1001";
		/**交易类型-交易明细查询*/
		public static final String MSG_NO_QUERY_DETAIL="2001";
		/**交易类型-交易历史查询*/
		public static final String MSG_NO_QUERY_HISTORY="2002";
		/**交易类型-对账*/
		public static final String MSG_NO_ACCOUNT_CHECK="3001";
		/**交易类型-测试*/
		public static final String MSG_NO_TEST="9001";
		/**xml节点名称*/
		public static final  String NAME="HEAD";
		
		private String key;
		
		public abstract DOMElement toElement();
//		public abstract Head   fromElement(Element element);
		protected void selfCheck(){
			try{
				List<Method> list=BeanUtil.getGetters(this);
				boolean flag=true;
				for(Method each:list){
					if("getRemark".equals(each.getName())||"getSign".equals(each.getName())||"getReserve".equals(each.getName()))
						continue;
					flag&=(!(each.invoke(this)==null));
				}
				assert flag:this.getClass().getName()+"中的属性值不允许为null或空";
			}catch (Exception e) {
				throw new RuntimeException(this.getClass().getSimpleName()+"自我检查异常",e);
			}
		}
		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
		@Override
		public Object clone(){
			Object temp = null;
			try {
				if(this.getClass().getConstructors()==null)
					throw new IllegalArgumentException(this.getClass().getName()+"没有默认的无参构造方法!");
				temp = this.getClass().newInstance();
				return BeanUtil.copy(this, temp);
			} catch (Exception e) {
				throw new RuntimeException("浅拷贝对象失败",e);
			} 
		}
		public abstract String 	getSign();
		public abstract void  	setSign(String sign);
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		
	}
	
	public static abstract class Msg{
		public static final   String NAME="MSG";
		public abstract DOMElement toElement();
//		public abstract Msg   fromElement(Element element);
		protected void selfCheck(){
			try{
				List<Method> list=BeanUtil.getGetters(this);
				boolean flag=true;
				for(Method each:list){
					if("getRemark".equals(each.getName())||"getSign".equals(each.getName())||"getReserve".equals(each.getName()))
						continue;
					flag&=(!(each.invoke(this)==null));
				}
				assert flag:this.getClass().getName()+"中的属性值不允许为null";
			}catch (Exception e) {
				throw new RuntimeException(this.getClass().getSimpleName()+"自我检查异常",e);
			}
		}
		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	
	public static class ReqHead extends Head{	
		/**消息版本 默认1.0*/
		private String 	ver;		
		/**消息编号-请求类型*/
		private String 	msgNo;		
		/**清空日期 一般为交易日期*/
		private String  chkDate;	
		/**交易日期 */
		private String  workDate;	
		/**交易时间*/
		private String  workTime;
		/**地址*/
		private String 	add;
		/**交易发起方 蓝喜1000000000000000*/
		private String 	src;
		/**交易接收放 默认蓝喜1000000000000000*/
		private String	des;
		/**应用平台 默认 蓝喜电子礼品营销平台*/
		private String  app;		
		/**消息id 8位*/
		private String	msgId;		
		/**备注*/
		private String  reserve;	
		/**签名 参数值拼接加上密钥 结果为小写*/
		private String  sign;
		
		
		ReqHead() {
			
		}
		
		public ReqHead(String reqType,String add,String src,String key) {
			init();
			this.msgNo=reqType;
			this.add=add;
			this.src=src;
			setKey(key);
		}
		
		public ReqHead(String reqType,String add,String src,String key,String chkDate){
			init();
			this.msgNo=reqType;
			this.add=add;
			this.src=src;
			this.chkDate=chkDate;
			setKey(key);
		}
		
		public String getVer() {
			return ver;
		}

		public void setVer(String ver) {
			this.ver = ver;
		}

		public String getMsgNo() {
			return msgNo;
		}

		public void setMsgNo(String msgNo) {
			this.msgNo = msgNo;
		}

		public String getChkDate() {
			return chkDate;
		}

		public void setChkDate(String chkDate) {
			this.chkDate = chkDate;
		}

		public String getWorkDate() {
			return workDate;
		}

		public void setWorkDate(String workDate) {
			this.workDate = workDate;
		}

		public String getWorkTime() {
			return workTime;
		}

		public void setWorkTime(String workTime) {
			this.workTime = workTime;
		}

		public String getAdd() {
			return add;
		}

		public void setAdd(String add) {
			this.add = add;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}

		public String getApp() {
			return app;
		}

		public void setApp(String app) {
			this.app = app;
		}

		public String getMsgId() {
			return msgId;
		}

		public void setMsgId(String msgId) {
			this.msgId = msgId;
		}

		public String getReserve() {
			return reserve;
		}

		public void setReserve(String reserve) {
			this.reserve = reserve;
		}
		@Override
		public String getSign() {
			return sign;
		}
		@Override
		public void setSign(String sign) {
			this.sign = sign;
		}


		public void init() {
			ver="1.0";
			chkDate=TimeUtil.getDate();
			workDate=TimeUtil.getDate();
			workTime=TimeUtil.getTime();
			add="3301";
			des="1000000000000000";
			app="蓝喜电子礼品营销平台";
			msgId=reportCount.toString();
			while(msgId.length()<4)
				msgId="0"+msgId;
			msgId=TimeUtil.getDate().substring(4)+msgId;
			try {
				synchronized (reportCount) {
					reportCount=reportCount==9999?0:reportCount+1;
				}
			} catch (Exception e) {
				throw new RuntimeException("同步异常",e);
			}
		}
		@Override
		public DOMElement toElement() {
			selfCheck();
			DOMElement element=new DOMElement(NAME);
			element.addElement("VER").setText(ver==null?"":ver);
			element.addElement("MsgNo").setText(msgNo==null?"":msgNo);
			element.addElement("CHKDate").setText(chkDate==null?"":chkDate);
			element.addElement("WorkDate").setText(workDate==null?"":workDate);
			element.addElement("WorkTime").setText(workTime==null?"":workTime);
			element.addElement("ADD").setText(add==null?"":add);
			element.addElement("SRC").setText(src==null?"":src);
			element.addElement("DES").setText(des==null?"":des);
			element.addElement("APP").setText(app==null?"":app);
			element.addElement("MsgID").setText(msgId==null?"":msgId);
			element.addElement("Reserve").setText(reserve==null?"":reserve);
			element.addElement("Sign").setText(sign==null?"":sign);
			return element;
		}


		public static Head fromElement(Element element) {
			Head head=null;
			if(element!=null&&element.getName().equals(NAME)){
				head=new ReqHead();
				ReqHead reqHead=(ReqHead)head;
				reqHead.setVer		(element.element("VER").getText());
				reqHead.setMsgNo	(element.element("MsgNo").getText());
				reqHead.setChkDate	(element.element("CHKDate").getText());
				reqHead.setWorkDate	(element.element("WorkDate").getText());
				reqHead.setWorkTime	(element.element("WorkTime").getText());
				reqHead.setAdd		(element.element("ADD").getText());
				reqHead.setSrc		(element.element("SRC").getText());
				reqHead.setDes		(element.element("DES").getText());
				reqHead.setApp		(element.element("APP").getText());
				reqHead.setMsgId	(element.element("MsgID").getText());
				reqHead.setReserve	(element.element("Reserve").getText());
				reqHead.setSign		(element.element("Sign").getText());
			}
			return head;
		}
		

	}
	
	public static class ResHead extends Head{
		/**交易成功*/
		public static final String SUCCESS	="0000";
		/**失败*/
		public static final String FAIL		="9001";
		/**交易超时*/
		public static final String OVERTIME ="9002";
		/**签名验证错误*/
		public static final String SIGNERROR="5001";
		/**其他错误*/
		public static final String OTHERERR ="9999";
		
		/**消息版本 默认1.0*/
		private String 	ver;		
		/**消息编号-请求类型*/
		private String 	msgNo;		
		/**清空日期 一般为交易日期*/
		private String  chkDate;	
		/**交易日期 */
		private String  workDate;	
		/**交易时间*/
		private String  workTime;	
		/**地址*/
		private String 	add;		
		/**交易发起方 蓝喜1000000000000000*/
		private String 	src;		
		/**交易接收放 默认蓝喜1000000000000000*/
		private String	des;		
		/**应用平台 默认 蓝喜电子礼品营销平台*/
		private String  app;		
		/**消息id*/
		private String	msgId;		
		/**备注*/
		private String  reserve;	
		/**签名 参数值拼接加上密钥 结果为小写*/
		private String  sign;		
		/**返回码 */
		private String 	resCode;	
		/**返回消息 */
		private String 	resMsg;
		
		public String getVer() {
			return ver;
		}
		public void setVer(String ver) {
			this.ver = ver;
		}
		public String getMsgNo() {
			return msgNo;
		}
		public void setMsgNo(String msgNo) {
			this.msgNo = msgNo;
		}
		public String getChkDate() {
			return chkDate;
		}
		public void setChkDate(String chkDate) {
			this.chkDate = chkDate;
		}
		public String getWorkDate() {
			return workDate;
		}
		public void setWorkDate(String workDate) {
			this.workDate = workDate;
		}
		public String getWorkTime() {
			return workTime;
		}
		public void setWorkTime(String workTime) {
			this.workTime = workTime;
		}
		public String getAdd() {
			return add;
		}
		public void setAdd(String add) {
			this.add = add;
		}
		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
		public String getDes() {
			return des;
		}
		public void setDes(String des) {
			this.des = des;
		}
		public String getApp() {
			return app;
		}
		public void setApp(String app) {
			this.app = app;
		}
		public String getMsgId() {
			return msgId;
		}
		public void setMsgId(String msgId) {
			this.msgId = msgId;
		}
		public String getReserve() {
			return reserve;
		}
		public void setReserve(String reserve) {
			this.reserve = reserve;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getResCode() {
			return resCode;
		}
		public void setResCode(String resCode) {
			this.resCode = resCode;
		}
		public String getResMsg() {
			return resMsg;
		}
		public void setResMsg(String resMsg) {
			this.resMsg = resMsg;
		}
		@Override
		public DOMElement toElement() {
			selfCheck();
			DOMElement element=new DOMElement(CheckReplaceUtil.nullAsSpace(NAME));
			element.addElement("VER").setText(CheckReplaceUtil.nullAsSpace(ver));
			element.addElement("MsgNo").setText(CheckReplaceUtil.nullAsSpace(msgNo));
			element.addElement("CHKDate").setText(CheckReplaceUtil.nullAsSpace(chkDate));
			element.addElement("WorkDate").setText(CheckReplaceUtil.nullAsSpace(workDate));
			element.addElement("WorkTime").setText(CheckReplaceUtil.nullAsSpace(workTime));
			element.addElement("ADD").setText(CheckReplaceUtil.nullAsSpace(add));
			element.addElement("SRC").setText(CheckReplaceUtil.nullAsSpace(src));
			element.addElement("DES").setText(CheckReplaceUtil.nullAsSpace(des));
			element.addElement("APP").setText(CheckReplaceUtil.nullAsSpace(app));
			element.addElement("MsgID").setText(CheckReplaceUtil.nullAsSpace(msgId));
			element.addElement("Reserve").setText(CheckReplaceUtil.nullAsSpace(reserve));
			element.addElement("Sign").setText(CheckReplaceUtil.nullAsSpace(sign));
			element.addElement("ResCode").setText(CheckReplaceUtil.nullAsSpace(resCode));
			element.addElement("ResMsg").setText(resMsg);
			return element;
		}
		
		public static Head fromElement(Element element) {
			Head head=null;
			if(element!=null&&element.getName().equals(NAME)){
				head=new ResHead();
				ResHead resHead=(ResHead)head;
				resHead.setVer		(element.element("VER").getText());
				resHead.setMsgNo	(element.element("MsgNo").getText());
				resHead.setChkDate	(element.element("CHKDate").getText());
				resHead.setWorkDate	(element.element("WorkDate").getText());
				resHead.setWorkTime	(element.element("WorkTime").getText());
				resHead.setAdd		(element.element("ADD").getText());
				resHead.setSrc		(element.element("SRC").getText());
				resHead.setDes		(element.element("DES").getText());
				resHead.setApp		(element.element("APP").getText());
				resHead.setMsgId	(element.element("MsgID").getText());
				resHead.setReserve	(element.element("Reserve").getText());
				resHead.setSign		(element.element("Sign").getText());
				resHead.setResCode	(element.element("ResCode").getText());
				Element res=element.element("ResMsg");
				Element des=element.element("ResDesc");
				resHead.setResMsg	(res!=null?res.getText():des!=null?des.getText():"");
			}
			return head;
		}		
	}
	
	public static class BuyReqMsg extends Msg{
		/**商品类型-话费*/
		public static final String GOOD_TYPE_PHONE_COST		="10";
		/**商品类型-流量*/
		public static final String GOOD_TYPE_TRIFFIC		="20";
		/**商品类型-电子券*/
		public static final String GOOD_TYPE_ELE_COUPON		="30";
		/**商品类型-电影票-仅限江苏银行*/
		public static final String GOOD_TYPE_MOVIE_TICKET	="40";
		/**需要下发短信*/
		public static final String NEED_SEND="0";
		/**不需要下发短信*/
		public static final String NOT_NEED ="1";
		/**购买手机号*/
		private String 	phone;
		/**商品类型*/
		private String  type;
		/**商品编号*/
		private String  skuCode;
		/**商品数量*/
		private	String  count;
		/**是否需要下发短信*/
		private String  needSend;
		/**备注信息*/
		private String  remark;
		
		BuyReqMsg() {
			
		}
		
		public BuyReqMsg(String phone,String type,String skuCode,String count,String needSend){
			try {
				Integer.parseInt(count);
			} catch (Exception e) {
				throw new IllegalArgumentException("count 必须为整数",e);
			}
			
			this.phone=phone;
			this.type=type;
			this.skuCode=skuCode;
			this.count=count;
			this.needSend=needSend;
		}
		
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSkuCode() {
			return skuCode;
		}

		public void setSkuCode(String skuCode) {
			this.skuCode = skuCode;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getNeedSend() {
			return needSend;
		}

		public void setNeedSend(String needSend) {
			this.needSend = needSend;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public void init(){
			count="1";
			needSend=NEED_SEND;
		}
		
		@Override
		public DOMElement toElement() {
			selfCheck();
			DOMElement element=new DOMElement(CheckReplaceUtil.nullAsSpace(NAME));
			element.addElement("Phone").setText(CheckReplaceUtil.nullAsSpace(phone));
			element.addElement("Type").setText(CheckReplaceUtil.nullAsSpace(type));
			element.addElement("SkuCode").setText(CheckReplaceUtil.nullAsSpace(skuCode));
			element.addElement("Count").setText(CheckReplaceUtil.nullAsSpace(count));
			element.addElement("NeedSend").setText(CheckReplaceUtil.nullAsSpace(needSend));
			element.addElement("Remark").setText(CheckReplaceUtil.nullAsSpace(remark));
			return element;
		}

		public static Msg fromElement(Element element) {
			Msg msg=null;
			if(element!=null&&element.getName().equals(NAME)){
				msg=new BuyReqMsg();
				BuyReqMsg reqMsg=(BuyReqMsg)msg;
				reqMsg.setPhone		(element.elementText("Phone"));
				reqMsg.setType		(element.elementText("Type"));
				reqMsg.setSkuCode	(element.elementText("SkuCode"));
				reqMsg.setCount		(element.elementText("Count"));
				reqMsg.setNeedSend	(element.elementText("NeedSend"));
				reqMsg.setRemark	(element.elementText("Remark"));
			}
			return msg;
		}
	}
	
	public static class BuyResMsg extends Msg{
		/**商品总价*/
		private String totalAmt;
		/**商品列表*/
		private List<Sku> skuList;
		
		public String getTotalAmt() {
			return totalAmt;
		}

		public void setTotalAmt(String totalAmt) {
			this.totalAmt = totalAmt;
		}

		public List<Sku> getSkuList() {
			return skuList;
		}

		public void setSkuList(List<Sku> skuList) {
			this.skuList = skuList;
		}

		public static class Sku{
			/**商品单价*/
			private String amt;
			/**电子券串码*/
			private String code;
			/**过期时间*/
			private String endTime;
			public String getAmt() {
				return amt;
			}
			public void setAmt(String amt) {
				this.amt = amt;
			}
			public String getCode() {
				return code;
			}
			public void setCode(String code) {
				this.code = code;
			}
			public String getEndTime() {
				return endTime;
			}
			public void setEndTime(String endTime) {
				this.endTime = endTime;
			}
			
		}
		@Override
		public DOMElement toElement() {
			selfCheck();
			DOMElement element=new DOMElement(NAME);
			element.addElement("TotalAmt").setText(CheckReplaceUtil.nullAsSpace(totalAmt));
			Element list=element.addElement("SkuList");
			for(Sku each:skuList){
				list.addElement("Amt").setText(CheckReplaceUtil.nullAsSpace(each.getAmt()));
				list.addElement("Code").setText(CheckReplaceUtil.nullAsSpace(each.getCode()));
				list.addElement("EndTime").setText(CheckReplaceUtil.nullAsSpace(each.getEndTime()));
			}
			return element;
		}

		public static Msg fromElement(Element element) {
			Msg msg=null;
			if(element!=null&&element.getName().equals(NAME)){
				msg=new BuyResMsg();
				BuyResMsg resMsg=(BuyResMsg)msg;
				resMsg.setTotalAmt(element.element("TotalAmt").getText());
				Element list=element.element("SkuList");
				for(Object one:list.elements()){
					Element each=(Element)one;
					Sku temp=new Sku();
					temp.setAmt(each.elementText("Amt"));
					temp.setCode(each.elementText("Code"));
					temp.setEndTime(each.elementText("EndTime"));
					resMsg.getSkuList().add(temp);
				}
			}
			return msg;
		}
	}
	
	public static class QueryDetailReqMsg extends Msg{
		/**原交易日期*/
		private String orgWorkDate;
		/**原交易序号*/
		private String orgMsgId;
		/**手机号码*/
		private String phone;
		/**备注*/
		private String remark;
		
		QueryDetailReqMsg() {
			
		}
		
		public QueryDetailReqMsg(String orgWorkDate,String orgMsgId,String phone) {
			this.orgWorkDate=orgWorkDate;
			this.orgMsgId=orgMsgId;
			this.phone=phone;
		}
		
		public String getOrgWorkDate() {
			return orgWorkDate;
		}

		public void setOrgWorkDate(String orgWorkDate) {
			this.orgWorkDate = orgWorkDate;
		}

		public String getOrgMsgId() {
			return orgMsgId;
		}

		public void setOrgMsgId(String orgMsgId) {
			this.orgMsgId = orgMsgId;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		@Override
		public DOMElement toElement() {
			selfCheck();
			DOMElement element=new DOMElement(NAME);
			element.addElement("OrgWorkDate").setText(orgWorkDate);
			element.addElement("OrgMsgID").setText(orgMsgId);
			element.addElement("Phone").setText(phone);
			element.addElement("Remark").setText(remark);
			return element;
		}

		public static Msg fromElement(Element element) {
			Msg msg=null;
			if(element!=null&&element.getName().equals(NAME)){
				msg=new QueryDetailReqMsg();
				QueryDetailReqMsg reqMsg=(QueryDetailReqMsg)msg;
				reqMsg.setOrgWorkDate(element.elementText("OrgWorkDate"));
				reqMsg.setOrgMsgId(element.elementText("OrgMsgID"));
				reqMsg.setPhone(element.elementText("Phone"));
				reqMsg.setRemark(element.elementText("Remark"));
			}
			return msg;
		}
	}
	
	public static class QueryDetailResMsg extends Msg{
		/**原交易日期*/
		private String orgWorkDate;
		/**原交易序号*/
		private String orgMsgId;
		/**手机号码*/
		private String phone;
		/**商品类型*/
		private String type;
		/**商品编号*/
		private String skuCode;
		/**商品数量*/
		private String count;
		/**商品总价*/
		private String totalAmt;
		/**交易结果*/
		private String status;
		/**商品列表*/
		private List<Sku> skuList;
		/**备注*/
		private String remark;
		
		public String getOrgWorkDate() {
			return orgWorkDate;
		}

		public void setOrgWorkDate(String orgWorkDate) {
			this.orgWorkDate = orgWorkDate;
		}

		public String getOrgMsgId() {
			return orgMsgId;
		}

		public void setOrgMsgId(String orgMsgId) {
			this.orgMsgId = orgMsgId;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSkuCode() {
			return skuCode;
		}

		public void setSkuCode(String skuCode) {
			this.skuCode = skuCode;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getTotalAmt() {
			return totalAmt;
		}

		public void setTotalAmt(String totalAmt) {
			this.totalAmt = totalAmt;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public List<Sku> getSkuList() {
			return skuList;
		}

		public void setSkuList(List<Sku> skuList) {
			this.skuList = skuList;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		@Override
		public DOMElement toElement() {
			selfCheck();
			DOMElement element=new DOMElement(NAME);
			element.addElement("OrgWorkDate").setText(orgWorkDate);
			element.addElement("OrgMsgID").setText(orgMsgId);
			element.addElement("Phone").setText(phone);
			element.addElement("Type").setText(type);
			element.addElement("SkuCode").setText(skuCode);
			element.addElement("Count").setText(count);
			element.addElement("TotalAmt").setText(totalAmt);
			element.addElement("Status").setText(status);
			Element list=element.addElement("SkuList");
			for(Sku each:skuList){
				list.addElement("Amt").setText(CheckReplaceUtil.nullAsSpace(each.getAmt()));
				list.addElement("Code").setText(CheckReplaceUtil.nullAsSpace(each.getCode()));
				list.addElement("EndTime").setText(CheckReplaceUtil.nullAsSpace(each.getEndTime()));
			}
			return element;
		}

		public static Msg fromElement(Element element) {
			Msg msg=null;
			if(element!=null&&element.getName().equals(NAME)){
				msg=new QueryDetailResMsg();
				QueryDetailResMsg resMsg=(QueryDetailResMsg)msg;
				resMsg.setOrgWorkDate(element.elementText("OrgWorkDate"));
				resMsg.setOrgMsgId(element.elementText("OrgWorkDate"));
				resMsg.setPhone(element.elementText("Phone"));
				resMsg.setType(element.elementText("Type"));
				resMsg.setSkuCode(element.elementText("SkuCode"));
				resMsg.setCount(element.elementText("Count"));
				resMsg.setTotalAmt(element.element("TotalAmt").getText());
				resMsg.setStatus(element.elementText("Status"));
				Element list=element.element("SkuList");
				for(Object one:list.elements()){
					Element each=(Element)one;
					Sku temp=new Sku();
					temp.setAmt(each.elementText("Amt"));
					temp.setCode(each.elementText("Code"));
					temp.setEndTime(each.elementText("EndTime"));
					resMsg.getSkuList().add(temp);
				}
			}
			return msg;
		}
	}
	
	public static class QueryHistoryReqMsg extends Msg{
		/**起始日期*/
		private String startDate;
		/**终止日期*/
		private String endDate;
		/**手机号码*/
		private String phone;
		/**备注*/
		private String remark;
		
		QueryHistoryReqMsg() {
			
		}
		
		public QueryHistoryReqMsg(String startDate,String endDate,String phone) {
			this.startDate=startDate;
			this.endDate=endDate;
			this.phone=phone;
		}
		
		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		@Override
		public DOMElement toElement() {
			DOMElement element=new DOMElement(NAME);
			element.addElement("StartDate").setText(startDate);
			element.addElement("EndDate").setText(endDate);
			element.addElement("Phone").setText(phone);
			element.addElement("Remark").setText(remark);
			return element;
		}

		public static Msg fromElement(Element element) {
			Msg msg=null;
			if(element!=null&&element.getName().equals(NAME)){
				msg=new QueryHistoryReqMsg();
				QueryHistoryReqMsg reqMsg=(QueryHistoryReqMsg)msg;
				reqMsg.setStartDate(element.elementText("StartDate"));
				reqMsg.setEndDate(element.elementText("EndDate"));
				reqMsg.setPhone(element.elementText("Phone"));
				reqMsg.setRemark(element.elementText("Remark"));
			}
			return msg;
		}
	}
	
	public static class QueryHistoryResMsg extends Msg{
		/**手机号码*/
		private String phone;
		/**交易笔数*/
		private String count;
		/**订单列表*/
		private List<Order> orderList;
		
		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public List<Order> getOrderList() {
			return orderList;
		}

		public void setOrderList(List<Order> orderList) {
			this.orderList = orderList;
		}

		public static class Order{
			/**原交易日期*/
			private String orgWorkDate;
			/**原交易序号*/
			private String orgMsgId;
			/**商品类型*/
			private String type;
			/**商品编号*/
			private String skuCode;
			public String getOrgWorkDate() {
				return orgWorkDate;
			}
			public void setOrgWorkDate(String orgWorkDate) {
				this.orgWorkDate = orgWorkDate;
			}
			public String getOrgMsgId() {
				return orgMsgId;
			}
			public void setOrgMsgId(String orgMsgId) {
				this.orgMsgId = orgMsgId;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			public String getSkuCode() {
				return skuCode;
			}
			public void setSkuCode(String skuCode) {
				this.skuCode = skuCode;
			}
			
		}

		@Override
		public DOMElement toElement() {
			DOMElement element=new DOMElement(NAME);
			element.addElement("Phone").setText(phone);
			element.addElement("Count").setText(count);
			Element list=element.addElement("OrderList");
			for(Order each:orderList){
				list.addElement("OrgWorkDate").setText(each.getOrgWorkDate());
				list.addElement("OrgMsgID").setText(each.getOrgMsgId());
				list.addElement("Type").setText(each.getType());
				list.addElement("SkuCode").setText(each.getSkuCode());
			}
			return element;
		}

		public static Msg fromElement(Element element) {
			Msg msg=null;
			if(element!=null&&element.getName().equals(NAME)){
				msg=new QueryHistoryResMsg();
				QueryHistoryResMsg resMsg=(QueryHistoryResMsg)msg;
				resMsg.setPhone(element.elementText(""));
				resMsg.setCount(element.elementText(""));
				Element list=element.element("OrderList");
				for(Object one:list.elements()){
					Element each=(Element)one;
					Order temp=new Order();
					temp.setOrgWorkDate(each.elementText("OrgWorkDate"));
					temp.setOrgMsgId(each.elementText("OrgMsgID"));
					temp.setType(each.elementText("Type"));
					temp.setSkuCode(each.elementText("SkuCode"));
					resMsg.getOrderList().add(temp);
				}
			}
			return msg;
		}
	}
	
	public static class AccountCheckReqMsg extends Msg{
		/**清算日期*/
		private String checkDate;
		/**交易笔数*/
		private String count;
		/**交易金额*/
		private String amt;
		/**对账文件名称*/
		private String fileName;
		/**对账文件大小*/
		private String fileSize;
		/**对账文件校验码*/
		private String fileChkCode;
		/**备注*/
		private String remark;
		
		AccountCheckReqMsg() {
			
		}
		
		public AccountCheckReqMsg(String count,String amt,String fileName,String fileSize,String filechkCode) {
			this.count=count;
			this.amt=amt;
			this.fileName=fileName;
			this.fileSize=fileSize;
			this.fileChkCode=filechkCode;
		}
		
		public String getCheckDate() {
			return checkDate;
		}
		public void setCheckDate(String checkDate) {
			this.checkDate = checkDate;
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getAmt() {
			return amt;
		}
		public void setAmt(String amt) {
			this.amt = amt;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFileSize() {
			return fileSize;
		}
		public void setFileSize(String fileSize) {
			this.fileSize = fileSize;
		}
		public String getFileChkCode() {
			return fileChkCode;
		}
		public void setFileChkCode(String fileChkCode) {
			this.fileChkCode = fileChkCode;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		@Override
		public DOMElement toElement() {
			DOMElement element=new DOMElement(NAME);
			element.addElement("CheckDate").setText(checkDate);
			element.addElement("Count").setText(count);
			element.addElement("Amt").setText(amt);
			element.addElement("FileName").setText(fileName);
			element.addElement("FileSize").setText(fileSize);
			element.addElement("FileChkCode").setText(fileChkCode);
			element.addElement("Remark").setText(remark);
			return element;
		}
		public static Msg fromElement(Element element) {
			Msg msg=null;
			if(element!=null&&element.getName().equals(NAME)){
				msg=new AccountCheckReqMsg();
				AccountCheckReqMsg reqMsg=(AccountCheckReqMsg)msg;
				reqMsg.setCheckDate(element.elementText("CheckDate"));
				reqMsg.setCount(element.elementText("Count"));
				reqMsg.setAmt(element.elementText("Amt"));
				reqMsg.setFileName(element.elementText("FileName"));
				reqMsg.setFileSize(element.elementText("FileSize"));
				reqMsg.setFileChkCode(element.elementText("FileChkCode"));
				reqMsg.setRemark(element.elementText("Remark"));
			}
			return msg;
		}
	}
	
	public static class AccountCheckResMsg extends Msg{

		@Override
		public DOMElement toElement() {
			DOMElement element=new DOMElement(NAME);
			return element;
		}

		public static Msg fromElement(Element element) {
			return new AccountCheckResMsg();
		}
		
	}
	
	public static class TestReqMsg extends Msg{

		@Override
		public DOMElement toElement() {
			DOMElement element=new DOMElement(NAME);
			return element;
		}

		public static Msg fromElement(Element element) {
			return new TestReqMsg();
		}
		
	}
	
	public static class TestResMsg extends Msg{

		@Override
		public DOMElement toElement() {
			DOMElement element=new DOMElement(NAME);
			return element;
		}

		public static Msg fromElement(Element element) {
			return new TestResMsg();
		}
		
	}
}
