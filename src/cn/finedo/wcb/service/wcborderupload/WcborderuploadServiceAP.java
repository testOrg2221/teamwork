package cn.finedo.wcb.service.wcborderupload;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.common.valid.DataType;
import cn.finedo.common.valid.ValidateItem;
import cn.finedo.common.valid.ValidateUtil;
import cn.finedo.wcb.pojo.Orderupload;
import cn.finedo.wcb.service.wcborderupload.domain.OrderuploadQueryDomain;
import cn.finedo.wcb.service.wcbqrcode.domain.OrderuploadListDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/wcborderupload")
public class WcborderuploadServiceAP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WcborderuploadService wcborderuploadservice;
	
	/**
	 * 宣传海报表查询
	 * @param OrderuploadQueryDomain
	 * @return ReturnValueDomain<Orderupload>对象
	 */
	@Proxy(method="query", inarg="OrderuploadQueryDomain", outarg="ReturnValueDomain<PageDomain<Orderupload>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<Orderupload>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<Orderupload>> ret = new ReturnValueDomain<PageDomain<Orderupload>>();
		OrderuploadQueryDomain orderuploadqueryDomain = null;
		 
		try {
			orderuploadqueryDomain = JsonUtil.request2Domain(request, OrderuploadQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcborderuploadservice.query(orderuploadqueryDomain);
	}
	
	
	/**
	 * 员工二维码表删除
	 * 
	 * @param OrderuploadListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="delete", inarg="OrderuploadListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		OrderuploadListDomain orderuploadlistDomain = null;
		
		try {
			orderuploadlistDomain = JsonUtil.request2Domain(request, OrderuploadListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		List<Orderupload> orderuploadlist= orderuploadlistDomain.getOrderuploadlist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("orderid", "订单ID", false, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(orderuploadlist, items);
		if (validret.hasFail()) {
			return validret;
		}
		
		return wcborderuploadservice.delete(orderuploadlistDomain);
	}
	
	
	
}
	 