package cn.finedo.wcb.service.wcborderupload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.TypeReference;

import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.wcb.pojo.Orderupload;
import cn.finedo.wcb.service.wcborderupload.domain.OrderuploadQueryDomain;
import cn.finedo.wcb.service.wcbqrcode.domain.OrderuploadListDomain;

public class WcborderuploadServiceAPProxy {
	private WcborderuploadServiceAPProxy() {
	}

	public static ReturnValueDomain<PageDomain<Orderupload>> query(OrderuploadQueryDomain arg) {
		String apuri = "service/finedo/wcborderupload/query";

		RequestAttributes qa = RequestContextHolder.getRequestAttributes();
		String token = "";
		if (NonUtil.isNotNon(qa)) {
			HttpServletRequest request = ((ServletRequestAttributes) qa).getRequest();
			token = getToken(request);
		}

		// 跨域调用，网络调用
		ReturnValueDomain<PageDomain<Orderupload>> ret = ServiceCaller.call(apuri + "?token=" + token, arg,
				new TypeReference<ReturnValueDomain<PageDomain<Orderupload>>>() {
				});
		return ret;
	}

	public static ReturnValueDomain<String> delete(OrderuploadListDomain arg) {
		String apuri="service/finedo/wcborderupload/delete";
		
		RequestAttributes qa = RequestContextHolder.getRequestAttributes();
		String token="";
		if(NonUtil.isNotNon(qa)) {
	        HttpServletRequest request = ((ServletRequestAttributes)qa).getRequest();
	        token=getToken(request);
		}
		
		// 跨域调用，网络调用
		ReturnValueDomain<String> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<String>>() {});
		return ret;
	}
	
	
	
	private static String getToken(HttpServletRequest request) {
		String token;
		LoginDomain logindomain = SessionUtil.getLoginDomain(request);
		if (NonUtil.isNotNon(logindomain)) {
			token = logindomain.getToken();
		} else {
			token = "";
		}

		return token;
	}

}
