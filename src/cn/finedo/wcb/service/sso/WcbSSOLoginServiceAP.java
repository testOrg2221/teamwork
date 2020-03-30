package cn.finedo.wcb.service.sso;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.TypeReference;
import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysUser;
import cn.finedo.common.protocol.HttpClientUtil;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.fsdp.server.util.RequestUtil;
import cn.finedo.fsdp.server.util.TokenUtil;
import cn.finedo.fsdp.service.login.domain.AccountDomain;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.fsdp.service.sso.SsoLoginService;
import cn.finedo.common.valid.ValidateUtil;
import cn.finedo.common.valid.ValidateItem;
import cn.finedo.common.valid.DataType;

@Controller
@Scope("prototype")
@RequestMapping("service/finedo/wcbsso")
public class WcbSSOLoginServiceAP {
	private static Logger logger = LogManager.getLogger();
	
	// 项目开发中，该URL请配置到系统参数中，使用ConfigureUtil.getParamByName("登录配置", 	"SSO登录地址")方法获取系统参数
	private static String SSOLOINGURL="http://localhost:8005/fsdp2_webapp/finedo/auth/ssoauth";
	
	@Autowired
	private SsoLoginService ssoLoginService;
	
	@Autowired
	private WcbSSOLoginService wcbSSOLoginService;
	
	/**
	 * 本地账号与密码登录认证服务
	 * 
	 * @param AccountDomain对象
	 * @return ReturnValueDomain<LoginDomain>对象
	 */
	@Proxy(method="auth", inarg="AccountDomain", outarg="ReturnValueDomain<LoginDomain>")
	@ResponseBody
	@RequestMapping("/auth")
	public ReturnValueDomain<LoginDomain> auth(HttpServletRequest request) {
		ReturnValueDomain<LoginDomain> ret = new ReturnValueDomain<LoginDomain>();
		AccountDomain accountdomain = null;

		try {
			// 获取输入领域对象
			accountdomain = JsonUtil.request2Domain(request, AccountDomain.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}

		// 获取IP地址
		if (NonUtil.isNon(accountdomain.getIpaddr())) {
			accountdomain.setIpaddr(RequestUtil.getRemoteIP(request));
		}

		// 获取来源
		if (NonUtil.isNon(accountdomain.getLoginsource())) {
			accountdomain.setLoginsource("WEB应用");
		}
		
	/*	System.out.println(accountdomain);
		// 调用SSO服务验证账号与密码
		ReturnValueDomain<LoginDomain> remoteret=HttpClientUtil.httpGetJson(SSOLOINGURL, accountdomain, new TypeReference<ReturnValueDomain<LoginDomain>>() {});
		if (remoteret.hasFail()) {
			return remoteret;
		}*/
		
		// SSO单点登录验证通过, 这里可以调用本地的服务来生成logindomain对象
		// 如生成权限数据、自定义的登录信息等
		ReturnValueDomain<LoginDomain> localret = ssoLoginService.genLoginDomain(accountdomain);
		if (localret.hasFail()) {
			return localret;
		}
		
		// 这里要把SSO返回的token缓存起来，以便后续使用token调用SSO服务获取用户信息
		//String remotetoken=remoteret.getObject().getToken();
		LoginDomain locallogindomain = localret.getObject();
		//locallogindomain.setToken(remotetoken);
		
		//System.out.println("remotetoken=" + remotetoken);
		System.out.println("locallogindomain=" + locallogindomain);
		TokenUtil.add(locallogindomain.getToken(), locallogindomain);
		
		return ret.setSuccess("登录成功", locallogindomain);
	}	
	
	
	/**
	 * 根据用户号码查询账号
	 * @param request
	 * @return
	 */
	@Proxy(method="getUserOfByphone", inarg="SysUser", outarg="ReturnValueDomain<AccountDomain>")
	@ResponseBody
	@RequestMapping("/getUserOfByphone")
	public ReturnValueDomain<AccountDomain> getUserOfByphone(HttpServletRequest request) {
		ReturnValueDomain<AccountDomain> ret = new ReturnValueDomain<AccountDomain>();
		SysUser sysUser=null;
		try {
			// 获取输入领域对象
			sysUser = JsonUtil.request2Domain(request, SysUser.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ret.setFail(e.getMessage());
		}
		ret=wcbSSOLoginService.getUserOfByphone(sysUser);
		return ret;
	}
}
