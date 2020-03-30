/**
 * 员工宣传海报表管理服务接口
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpubqrcode;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
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
import cn.finedo.common.domain.FileImportResultDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ResultDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.common.valid.DataType;
import cn.finedo.common.valid.ValidateItem;
import cn.finedo.common.valid.ValidateUtil;
import cn.finedo.fsdp.server.framework.ServerFeature;
import cn.finedo.fsdp.service.common.excel.ExcelUtil;
import cn.finedo.fsdp.service.common.excel.HeaderDomain;
import cn.finedo.fsdp.service.common.configure.ConfigureUtil;
import cn.finedo.fsdp.service.file.FileServiceAPProxy;
import cn.finedo.wcb.pojo.Wcbpubqrcode;
import cn.finedo.wcb.service.wcbpubqrcode.domain.WcbpubqrcodeListDomain;
import cn.finedo.wcb.service.wcbpubqrcode.domain.WcbpubqrcodeQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/wcbpubqrcode")
public class WcbpubqrcodeServiceAP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WcbpubqrcodeService wcbpubqrcodeservice;
	
	/**
	 * 员工宣传海报表查询
	 * @param WcbpubqrcodeQueryDomain
	 * @return ReturnValueDomain<Wcbpubqrcode>对象
	 */
	@Proxy(method="query", inarg="WcbpubqrcodeQueryDomain", outarg="ReturnValueDomain<PageDomain<Wcbpubqrcode>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<Wcbpubqrcode>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<Wcbpubqrcode>> ret = new ReturnValueDomain<PageDomain<Wcbpubqrcode>>();
		WcbpubqrcodeQueryDomain wcbpubqrcodequerydomain = null;
		 
		try {
			wcbpubqrcodequerydomain = JsonUtil.request2Domain(request, WcbpubqrcodeQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbpubqrcodeservice.query(wcbpubqrcodequerydomain);
	}
	 
	/**
	 * 员工宣传海报表新增
	 * 
	 * @param WcbpubqrcodeListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="add", inarg="WcbpubqrcodeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbpubqrcodeListDomain wcbpubqrcodelistdomain = null;
		 
		try {
			wcbpubqrcodelistdomain = JsonUtil.request2Domain(request, WcbpubqrcodeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		return wcbpubqrcodeservice.add(wcbpubqrcodelistdomain);
	 }

	/**
	 * 根据海报id查看该用户的二维码海报
	 * @param request
	 * @return
	 */
	@Proxy(method="getPathOfPicid", inarg="Wcbpubqrcode", outarg="ReturnValueDomain<List<Wcbpubqrcode>>")
	@ResponseBody
	@RequestMapping("/getPathOfPicid")
	public ReturnValueDomain<List<Wcbpubqrcode>> getPathOfPicid(HttpServletRequest request) {
		ReturnValueDomain<List<Wcbpubqrcode>> ret=new ReturnValueDomain<List<Wcbpubqrcode>>();
		Wcbpubqrcode wcbpubqrcode=null;
		try {
			wcbpubqrcode = JsonUtil.request2Domain(request, Wcbpubqrcode.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		return wcbpubqrcodeservice.getPathOfPicid(wcbpubqrcode);
	}
	
	
	/**
	 * 员工宣传海报表修改
	 * @param WcbpubqrcodeListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method="update", inarg="WcbpubqrcodeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbpubqrcodeListDomain wcbpubqrcodelistdomain = null;
		  
		try {
			wcbpubqrcodelistdomain = JsonUtil.request2Domain(request, WcbpubqrcodeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}

		List<Wcbpubqrcode> wcbpubqrcodelist= wcbpubqrcodelistdomain.getWcbpubqrcodelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("publicizeid", "宣传海报ID", false, DataType.STRING));
		items.add(new ValidateItem("qrcodeid", "二维码ID", false, DataType.STRING));
		items.add(new ValidateItem("taskid", "工单id", false, DataType.STRING));
		items.add(new ValidateItem("userid", "员工工号", false, DataType.STRING));
		items.add(new ValidateItem("picture", "图片", true, DataType.STRING));
		items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
		items.add(new ValidateItem("opdate", "操作时间", true, DataType.DATE));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbpubqrcodelist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return wcbpubqrcodeservice.update(wcbpubqrcodelistdomain);
	}
	
	/**
	 * 员工宣传海报表删除
	 * 
	 * @param WcbpubqrcodeListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="delete", inarg="WcbpubqrcodeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbpubqrcodeListDomain wcbpubqrcodelistdomain = null;
		
		try {
			wcbpubqrcodelistdomain = JsonUtil.request2Domain(request, WcbpubqrcodeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		List<Wcbpubqrcode> wcbpubqrcodelist= wcbpubqrcodelistdomain.getWcbpubqrcodelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbpubqrcodelist, items);
		if (validret.hasFail()) {
			return validret;
		}
		
		return wcbpubqrcodeservice.delete(wcbpubqrcodelistdomain);
	}
	
	/**
	 * 批量导入员工宣传海报表
	 * 
	 * @param SysEntityfile
	 * @return ReturnValueDomain<FileImportResultDomain>对象
	 */
	@Proxy(method="importexcel", inarg="SysEntityfile", outarg="ReturnValueDomain<FileImportResultDomain>")
	@ResponseBody
	@RequestMapping(value="/importexcel")
	public ReturnValueDomain<FileImportResultDomain> importexcel(HttpServletRequest request) {
		ReturnValueDomain<FileImportResultDomain> ret=new ReturnValueDomain<FileImportResultDomain>();
		
		SysEntityfile entityfile = null;
		try {
			entityfile = JsonUtil.request2Domain(request, SysEntityfile.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}

		ReturnValueDomain<SysEntityfile> queryfileret=FileServiceAPProxy.queryById(entityfile);
		entityfile=queryfileret.getObject();
		
		String uploadpath = ConfigureUtil.getParamByName("系统基本参数", "上传路径");
		String filename=uploadpath + File.separator + entityfile.getFilepath() + File.separator + entityfile.getFileid() + entityfile.getFiletype();
				
		// 总记录数
		int rowcount=0;
		// 成功记录数 
		int successcount=0;
		// 失败明细
		List<ResultDomain> faillist=new ArrayList<ResultDomain>();
		List<Wcbpubqrcode> datalist;
		try {
			List<HeaderDomain> headerlist=new ArrayList<HeaderDomain>();
			
			headerlist.add(new HeaderDomain("0", "publicizeid", "宣传海报ID"));
			headerlist.add(new HeaderDomain("1", "qrcodeid", "二维码ID"));
			headerlist.add(new HeaderDomain("2", "taskid", "工单id"));
			headerlist.add(new HeaderDomain("3", "userid", "员工工号"));
			headerlist.add(new HeaderDomain("4", "picture", "图片"));
			headerlist.add(new HeaderDomain("5", "opuser", "操作人"));
			headerlist.add(new HeaderDomain("6", "opdate", "操作时间"));
			
			datalist=ExcelUtil.readExcel(filename, headerlist, Wcbpubqrcode.class);
			rowcount=datalist.size();
			
			// 合法性校验
			List<ValidateItem> items = new ArrayList<ValidateItem>();
			items.add(new ValidateItem("publicizeid", "宣传海报ID", false, DataType.STRING));
			items.add(new ValidateItem("qrcodeid", "二维码ID", false, DataType.STRING));
			items.add(new ValidateItem("taskid", "工单id", false, DataType.STRING));
			items.add(new ValidateItem("userid", "员工工号", false, DataType.STRING));
			items.add(new ValidateItem("picture", "图片", true, DataType.STRING));
			items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
			items.add(new ValidateItem("opdate", "操作时间", true, DataType.DATE));
			
			ReturnValueDomain<String> validret = ValidateUtil.checkForList(datalist, items);
			int failindex=0;
			for(ResultDomain rd : validret.getResultlist()) {
				rd.setResultdesc("[行号:" + failindex + 2 + "]" + rd.getResultdesc());
				faillist.add(rd);
				
				failindex++;
			}
			successcount=rowcount - failindex;
		}catch(Exception e) {
			logger.error("导入失败", e);
			return ret.setFail("导入失败");
		}
		
		if(successcount != rowcount) {
			FileImportResultDomain importresult=new FileImportResultDomain();
			importresult.setRowcount(rowcount);
			importresult.setSuccesscount(successcount);
			importresult.setFailcount(rowcount-successcount);
			importresult.setFaillist(faillist);
						
			return ret.setFail("导入数据合法性校验不通过", importresult);
		}
		
		WcbpubqrcodeListDomain wcbpubqrcodelistdomain=new WcbpubqrcodeListDomain();
		wcbpubqrcodelistdomain.setWcbpubqrcodelist(datalist);
		ReturnValueDomain<String> oneret= wcbpubqrcodeservice.add(wcbpubqrcodelistdomain);
		if(oneret.hasFail()) {
			return ret.setFail("导入失败:" + oneret.getResultdesc());
		}
	
		FileImportResultDomain importresult=new FileImportResultDomain();
		importresult.setRowcount(rowcount);
		importresult.setSuccesscount(successcount);
		importresult.setFailcount(rowcount-successcount);
		importresult.setFaillist(faillist);
		
		return ret.setSuccess("导入成功", importresult);
	}
	
	 /**
	  * 批量导出用户信息
	  * 
	  * @param WcbpubqrcodeQueryDomain
	  * @return ReturnValueDomain<SysEntityfile>对象
	  */
	@Proxy(method="exportexcel", inarg="WcbpubqrcodeQueryDomain", outarg="ReturnValueDomain<SysEntityfile>")
	@ResponseBody
	@RequestMapping("/exportexcel")
	public ReturnValueDomain<SysEntityfile> exportexcel(HttpServletRequest request) {
		ReturnValueDomain<SysEntityfile> ret=new ReturnValueDomain<SysEntityfile>();
		
		WcbpubqrcodeQueryDomain wcbpubqrcodequerydomain = null;
		try {
			wcbpubqrcodequerydomain = JsonUtil.request2Domain(request, WcbpubqrcodeQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		PageParamDomain pageparam=new PageParamDomain();
		pageparam.setRownumperpage(ServerFeature.EXPORT_MAXSIZE);
		pageparam.setPageindex(0);
		wcbpubqrcodequerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<Wcbpubqrcode>> queryret = wcbpubqrcodeservice.query(wcbpubqrcodequerydomain);
		
		List<Wcbpubqrcode> Wcbpubqrcodelist = queryret.getObject().getDatalist();
		
		List<HeaderDomain> headerlist = new ArrayList<HeaderDomain>();
		headerlist.add(new HeaderDomain("0", "publicizeid", "宣传海报ID"));
		headerlist.add(new HeaderDomain("1", "qrcodeid", "二维码ID"));
		headerlist.add(new HeaderDomain("2", "taskid", "工单id"));
		headerlist.add(new HeaderDomain("3", "userid", "员工工号"));
		headerlist.add(new HeaderDomain("4", "picture", "图片"));
		headerlist.add(new HeaderDomain("5", "opuser", "操作人"));
		headerlist.add(new HeaderDomain("6", "opdate", "操作时间"));
		
		String filepath=ServerFeature.WEBAPP_HOME + File.separator + "download" + File.separator + DateUtil.getNowTime("yyyyMMdd");
		String filename=UUID.randomUUID().toString() + ".xlsx";
				
		try {
			ExcelUtil.writeExcel(filepath + File.separator + filename, headerlist, Wcbpubqrcodelist);
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
