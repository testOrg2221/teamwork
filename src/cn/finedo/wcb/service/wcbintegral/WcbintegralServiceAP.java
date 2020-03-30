package cn.finedo.wcb.service.wcbintegral;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.finedo.common.annotation.Proxy;
import cn.finedo.common.date.DateUtil;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.fsdp.server.framework.ServerFeature;
import cn.finedo.fsdp.service.common.excel.ExcelUtil;
import cn.finedo.fsdp.service.common.excel.HeaderDomain;
import cn.finedo.wcb.pojo.Wcbpublicize;
import cn.finedo.wcb.service.wcbpublicize.domain.WcbpublicizeQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/wcbintegral")
public class WcbintegralServiceAP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WcbintegralService wcbintegralservice;
	
	/**
	 * 宣传海报表查询
	 * @param WcbqrcodeQueryDomain
	 * @return ReturnValueDomain<Wcbpublicize>对象
	 */
	@Proxy(method="query", inarg="WcbpublicizeQueryDomain", outarg="ReturnValueDomain<PageDomain<Wcbpublicize>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<Wcbpublicize>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<Wcbpublicize>> ret = new ReturnValueDomain<PageDomain<Wcbpublicize>>();
		WcbpublicizeQueryDomain wcbpublicizequerydomain = null;
		 
		try {
			wcbpublicizequerydomain = JsonUtil.request2Domain(request, WcbpublicizeQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbintegralservice.query(wcbpublicizequerydomain);
	}
	
	
	 /**
	  * 批量导出用户信息
	  * 
	  * @param WcbqrcodeQueryDomain
	  * @return ReturnValueDomain<SysEntityfile>对象
	  */
	@Proxy(method="exportexcel", inarg="WcbpublicizeQueryDomain", outarg="ReturnValueDomain<SysEntityfile>")
	@ResponseBody
	@RequestMapping("/exportexcel")
	public ReturnValueDomain<SysEntityfile> exportexcel(HttpServletRequest request) {
		ReturnValueDomain<SysEntityfile> ret=new ReturnValueDomain<SysEntityfile>();
		
		WcbpublicizeQueryDomain wcbpublicizequerydomain = null;
		try {
			wcbpublicizequerydomain = JsonUtil.request2Domain(request, WcbpublicizeQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		PageParamDomain pageparam=new PageParamDomain();
		pageparam.setRownumperpage(ServerFeature.EXPORT_MAXSIZE);
		pageparam.setPageindex(0);
		wcbpublicizequerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<Wcbpublicize>> queryret = wcbintegralservice.query(wcbpublicizequerydomain);
		
		List<Wcbpublicize> wcbpublicizelist = queryret.getObject().getDatalist();
		
		List<HeaderDomain> headerlist = new ArrayList<HeaderDomain>();
		headerlist.add(new HeaderDomain("0", "publicizetype", "海报类型"));
		headerlist.add(new HeaderDomain("1", "publicizedesc", "海报描述"));
		headerlist.add(new HeaderDomain("2", "opuser", "员工姓名"));
		headerlist.add(new HeaderDomain("3", "phonenum", "手机号码"));
		headerlist.add(new HeaderDomain("4", "optime", "时间"));
		headerlist.add(new HeaderDomain("5", "scanintegral", "扫描积分"));
		headerlist.add(new HeaderDomain("6", "transactintegral", "订单积分"));
		
		String filepath=ServerFeature.WEBAPP_HOME + File.separator + "download" + File.separator + DateUtil.getNowTime("yyyyMMdd");
		String filename=UUID.randomUUID().toString() + ".xlsx";
				
		try {
			ExcelUtil.writeExcel(filepath + File.separator + filename, headerlist, wcbpublicizelist);
		} catch (Exception e) {
			logger.error("生成excel文件失败", e);
			return ret.setFail("生成excel文件失败:" + e.getMessage());
		}
		
		SysEntityfile entityfile=new SysEntityfile();
		entityfile.setFilename(filename);
		entityfile.setFilepath(filepath);
		return ret.setSuccess("生成excel文件成功", entityfile);
	}
	
	
}
	