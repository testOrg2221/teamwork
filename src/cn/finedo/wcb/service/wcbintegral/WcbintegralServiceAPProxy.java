package cn.finedo.wcb.service.wcbintegral;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.TypeReference;

import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.protocol.ServiceCaller;
import cn.finedo.fsdp.server.util.SessionUtil;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.wcb.pojo.Wcbpublicize;
import cn.finedo.wcb.service.wcbpublicize.domain.WcbpublicizeQueryDomain;

public class WcbintegralServiceAPProxy {
	private WcbintegralServiceAPProxy() {
	}
			
	public static ReturnValueDomain<PageDomain<Wcbpublicize>> query(WcbpublicizeQueryDomain arg) {
		String apuri="service/finedo/wcbintegral/query";
		
		RequestAttributes qa = RequestContextHolder.getRequestAttributes();
		String token="";
		if(NonUtil.isNotNon(qa)) {
	        HttpServletRequest request = ((ServletRequestAttributes)qa).getRequest();
	        token=getToken(request);
		}
		
		// 跨域调用，网络调用
		ReturnValueDomain<PageDomain<Wcbpublicize>> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<PageDomain<Wcbpublicize>>>() {});
		return ret;
	}

	public static ReturnValueDomain<SysEntityfile> exportexcel(WcbpublicizeQueryDomain arg) {
		String apuri="service/finedo/wcbintegral/exportexcel";
		
		RequestAttributes qa = RequestContextHolder.getRequestAttributes();
		String token="";
		if(NonUtil.isNotNon(qa)) {
	        HttpServletRequest request = ((ServletRequestAttributes)qa).getRequest();
	        token=getToken(request);
		}
		
		// 跨域调用，网络调用
		ReturnValueDomain<SysEntityfile> ret = ServiceCaller.call(apuri + "?token=" + token, arg, new TypeReference<ReturnValueDomain<SysEntityfile>>() {});
		return ret;
	}
	
	private static String getToken(HttpServletRequest request) {
		String token;
		LoginDomain logindomain = SessionUtil.getLoginDomain(request);
		if(NonUtil.isNotNon(logindomain)) {
			token=logindomain.getToken();
		}else {
			token="";
		}
		
		return token;
	}

}
