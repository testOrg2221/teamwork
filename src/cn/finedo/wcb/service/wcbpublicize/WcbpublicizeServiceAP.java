/**
 * 宣传海报表管理服务接口
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpublicize;

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
import cn.finedo.wcb.pojo.Wcbpublicize;
import cn.finedo.wcb.service.wcbpublicize.domain.WcbpublicizeListDomain;
import cn.finedo.wcb.service.wcbpublicize.domain.WcbpublicizeQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/wcbpublicize")
public class WcbpublicizeServiceAP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WcbpublicizeService wcbpublicizeservice;
	
	/**
	 * 宣传海报表查询
	 * @param WcbpublicizeQueryDomain
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
		
		return wcbpublicizeservice.query(wcbpublicizequerydomain);
	}
	 
	/**
	 * 宣传海报表新增
	 * 
	 * @param WcbpublicizeListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="add", inarg="WcbpublicizeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbpublicizeListDomain wcbpublicizelistdomain = null;
		 
		try {
			wcbpublicizelistdomain = JsonUtil.request2Domain(request, WcbpublicizeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
	
		List<Wcbpublicize> wcbpublicizelist= wcbpublicizelistdomain.getWcbpublicizelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("taskid", "工单ID", true, DataType.STRING));
		items.add(new ValidateItem("publicizetype", "宣传类型", true, DataType.STRING));
		items.add(new ValidateItem("publicizedesc", "宣传描述", true, DataType.STRING));
		items.add(new ValidateItem("publicizepic", "宣传图片", true, DataType.STRING));
		items.add(new ValidateItem("addtime", "新增时间", true, DataType.DATE));
		items.add(new ValidateItem("adduser", "新增人", true, DataType.STRING));
		items.add(new ValidateItem("optime", "操作时间", true, DataType.DATE));
		items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbpublicizelist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return wcbpublicizeservice.add(wcbpublicizelistdomain);
	 }

	/**
	 * 宣传海报表修改
	 * @param WcbpublicizeListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method="update", inarg="WcbpublicizeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbpublicizeListDomain wcbpublicizelistdomain = null;
		  
		try {
			wcbpublicizelistdomain = JsonUtil.request2Domain(request, WcbpublicizeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}

		List<Wcbpublicize> wcbpublicizelist= wcbpublicizelistdomain.getWcbpublicizelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("publicizeid", "宣传ID", false, DataType.STRING));
		items.add(new ValidateItem("taskid", "工单ID", true, DataType.STRING));
		items.add(new ValidateItem("publicizetype", "宣传类型", true, DataType.STRING));
		items.add(new ValidateItem("publicizedesc", "宣传描述", true, DataType.STRING));
		items.add(new ValidateItem("publicizepic", "宣传图片", true, DataType.STRING));
		items.add(new ValidateItem("addtime", "新增时间", true, DataType.DATE));
		items.add(new ValidateItem("adduser", "新增人", true, DataType.STRING));
		items.add(new ValidateItem("optime", "操作时间", true, DataType.DATE));
		items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbpublicizelist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return wcbpublicizeservice.update(wcbpublicizelistdomain);
	}
	
	/**
	 * 宣传海报表删除
	 * 
	 * @param WcbpublicizeListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="delete", inarg="WcbpublicizeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbpublicizeListDomain wcbpublicizelistdomain = null;
		
		try {
			wcbpublicizelistdomain = JsonUtil.request2Domain(request, WcbpublicizeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		List<Wcbpublicize> wcbpublicizelist= wcbpublicizelistdomain.getWcbpublicizelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("publicizeid", "宣传ID", false, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbpublicizelist, items);
		if (validret.hasFail()) {
			return validret;
		}
		
		return wcbpublicizeservice.delete(wcbpublicizelistdomain);
	}
	
	/**
	 * 批量导入宣传海报表
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
		List<Wcbpublicize> datalist;
		try {
			List<HeaderDomain> headerlist=new ArrayList<HeaderDomain>();
			
			headerlist.add(new HeaderDomain("0", "taskid", "工单ID"));
			headerlist.add(new HeaderDomain("1", "publicizetype", "宣传类型"));
			headerlist.add(new HeaderDomain("2", "publicizedesc", "宣传描述"));
			headerlist.add(new HeaderDomain("3", "publicizepic", "宣传图片"));
			headerlist.add(new HeaderDomain("4", "addtime", "新增时间"));
			headerlist.add(new HeaderDomain("5", "adduser", "新增人"));
			headerlist.add(new HeaderDomain("6", "optime", "操作时间"));
			headerlist.add(new HeaderDomain("7", "opuser", "操作人"));
			
			datalist=ExcelUtil.readExcel(filename, headerlist, Wcbpublicize.class);
			rowcount=datalist.size();
			
			// 合法性校验
			List<ValidateItem> items = new ArrayList<ValidateItem>();
			items.add(new ValidateItem("taskid", "工单ID", true, DataType.STRING));
			items.add(new ValidateItem("publicizetype", "宣传类型", true, DataType.STRING));
			items.add(new ValidateItem("publicizedesc", "宣传描述", true, DataType.STRING));
			items.add(new ValidateItem("publicizepic", "宣传图片", true, DataType.STRING));
			items.add(new ValidateItem("addtime", "新增时间", true, DataType.DATE));
			items.add(new ValidateItem("adduser", "新增人", true, DataType.STRING));
			items.add(new ValidateItem("optime", "操作时间", true, DataType.DATE));
			items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
			
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
		
		WcbpublicizeListDomain wcbpublicizelistdomain=new WcbpublicizeListDomain();
		wcbpublicizelistdomain.setWcbpublicizelist(datalist);
		ReturnValueDomain<String> oneret= wcbpublicizeservice.add(wcbpublicizelistdomain);
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
	  * @param WcbpublicizeQueryDomain
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
		
		ReturnValueDomain<PageDomain<Wcbpublicize>> queryret = wcbpublicizeservice.query(wcbpublicizequerydomain);
		
		List<Wcbpublicize> Wcbpublicizelist = queryret.getObject().getDatalist();
		
		List<HeaderDomain> headerlist = new ArrayList<HeaderDomain>();
		headerlist.add(new HeaderDomain("0", "taskid", "工单ID"));
		headerlist.add(new HeaderDomain("1", "publicizetype", "宣传类型"));
		headerlist.add(new HeaderDomain("2", "publicizedesc", "宣传描述"));
		headerlist.add(new HeaderDomain("3", "publicizepic", "宣传图片"));
		headerlist.add(new HeaderDomain("4", "addtime", "新增时间"));
		headerlist.add(new HeaderDomain("5", "adduser", "新增人"));
		headerlist.add(new HeaderDomain("6", "optime", "操作时间"));
		headerlist.add(new HeaderDomain("7", "opuser", "操作人"));
		
		String filepath=ServerFeature.WEBAPP_HOME + File.separator + "download" + File.separator + DateUtil.getNowTime("yyyyMMdd");
		String filename=UUID.randomUUID().toString() + ".xlsx";
				
		try {
			ExcelUtil.writeExcel(filepath + File.separator + filename, headerlist, Wcbpublicizelist);
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
