/**
 * 工单表管理服务接口
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbtask;

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
import cn.finedo.common.pojo.SysUser;
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
import cn.finedo.wcb.pojo.Wcbtask;
import cn.finedo.wcb.service.wcbtask.domain.WcbtaskListDomain;
import cn.finedo.wcb.service.wcbtask.domain.WcbtaskQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/wcbtask")
public class WcbtaskServiceAP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WcbtaskService wcbtaskservice;
	
	/**
	 * 工单表查询
	 * @param WcbtaskQueryDomain
	 * @return ReturnValueDomain<Wcbtask>对象
	 */
	@Proxy(method="query", inarg="WcbtaskQueryDomain", outarg="ReturnValueDomain<PageDomain<Wcbtask>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<Wcbtask>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<Wcbtask>> ret = new ReturnValueDomain<PageDomain<Wcbtask>>();
		WcbtaskQueryDomain wcbtaskquerydomain = null;
		 
		try {
			wcbtaskquerydomain = JsonUtil.request2Domain(request, WcbtaskQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbtaskservice.query(wcbtaskquerydomain);
	}
	
	/**
	 *  根据用户得到积分排名和总积分数
	 * @param WcbtaskQueryDomain
	 * @return ReturnValueDomain<Wcbtask>对象
	 */
	@Proxy(method="getCodenumOfUSer", inarg="SysUser", outarg="ReturnValueDomain<List<Wcbpublicize>>")
	@ResponseBody
	@RequestMapping("/getCodenumOfUSer")
	public ReturnValueDomain<List<Wcbpublicize>> getCodenumOfUSer(HttpServletRequest request) {
		ReturnValueDomain<List<Wcbpublicize>> ret = new ReturnValueDomain<List<Wcbpublicize>>();
		SysUser sysUser = null;
		 
		try {
			sysUser = JsonUtil.request2Domain(request, SysUser.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbtaskservice.getCodenumOfUSer(sysUser);
	}
	
	
	@Proxy(method="getPublicizeOfApp", inarg="WcbtaskQueryDomain", outarg="ReturnValueDomain<PageDomain<Wcbpublicize>>")
	@ResponseBody
	@RequestMapping("/getPublicizeOfApp")
	public ReturnValueDomain<PageDomain<Wcbpublicize>> getPublicizeOfApp(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<Wcbpublicize>> ret = new ReturnValueDomain<PageDomain<Wcbpublicize>>();
		WcbtaskQueryDomain wcbtaskquerydomain = null;
		 
		try {
			wcbtaskquerydomain = JsonUtil.request2Domain(request, WcbtaskQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbtaskservice.getPublicizeOfApp(wcbtaskquerydomain);
	}
	
	
	 
	/**
	 * 工单表新增
	 * 
	 * @param WcbtaskListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="add", inarg="WcbtaskListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbtaskListDomain wcbtasklistdomain = null;
		 
		try {
			wcbtasklistdomain = JsonUtil.request2Domain(request, WcbtaskListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}

		return wcbtaskservice.add(wcbtasklistdomain);
	 }

	/**
	 * 根据taskid查询海报素材
	 * @param request
	 * @return
	 */
	@Proxy(method="getPublicizeOftaskid", inarg="Wcbtask", outarg="ReturnValueDomain<List<Wcbpublicize>>")
	@ResponseBody
	@RequestMapping("/getPublicizeOftaskid")
	public ReturnValueDomain<List<Wcbpublicize>> getPublicizeOftaskid(HttpServletRequest request){
		ReturnValueDomain<List<Wcbpublicize>> ret=new ReturnValueDomain<List<Wcbpublicize>>();
		Wcbtask wcbtask=null;
		try {
			wcbtask=JsonUtil.request2Domain(request, Wcbtask.class);
		} catch (Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbtaskservice.getPublicizeOftaskid(wcbtask);
	}
	
	
	/**
	 * 海报置顶
	 * @param request
	 * @return
	 */
	@Proxy(method="stickOfPub", inarg="Wcbpublicize", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/stickOfPub")
	public ReturnValueDomain<String> stickOfPub(HttpServletRequest request){
		ReturnValueDomain<String> ret=new ReturnValueDomain<String>();
		Wcbpublicize wcbpublicize=null;
		try {
			wcbpublicize=JsonUtil.request2Domain(request, Wcbpublicize.class);
		} catch (Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbtaskservice.stickOfPub(wcbpublicize);
	}
	
	
	
	/**
	 * 工单表修改
	 * @param WcbtaskListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method="update", inarg="WcbtaskListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbtaskListDomain wcbtasklistdomain = null;
		  
		try {
			wcbtasklistdomain = JsonUtil.request2Domain(request, WcbtaskListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}

		List<Wcbtask> wcbtasklist= wcbtasklistdomain.getWcbtasklist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("taskid", "任务ID", false, DataType.STRING));
		items.add(new ValidateItem("taskname", "任务名称", true, DataType.STRING));
		items.add(new ValidateItem("taskdesc", "工单描述", true, DataType.STRING));
		items.add(new ValidateItem("optime", "操作时间", true, DataType.DATE));
		items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbtasklist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return wcbtaskservice.update(wcbtasklistdomain);
	}
	
	/**
	 * 工单表删除
	 * 
	 * @param WcbtaskListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="delete", inarg="WcbtaskListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbtaskListDomain wcbtasklistdomain = null;
		
		try {
			wcbtasklistdomain = JsonUtil.request2Domain(request, WcbtaskListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		List<Wcbtask> wcbtasklist= wcbtasklistdomain.getWcbtasklist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("taskid", "任务ID", false, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbtasklist, items);
		if (validret.hasFail()) {
			return validret;
		}
		
		return wcbtaskservice.delete(wcbtasklistdomain);
	}
	
	/**
	 * 批量导入工单表
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
		List<Wcbtask> datalist;
		try {
			List<HeaderDomain> headerlist=new ArrayList<HeaderDomain>();
			
			headerlist.add(new HeaderDomain("0", "taskname", "任务名称"));
			headerlist.add(new HeaderDomain("1", "taskdesc", "工单描述"));
			headerlist.add(new HeaderDomain("2", "optime", "操作时间"));
			headerlist.add(new HeaderDomain("3", "opuser", "操作人"));
			
			datalist=ExcelUtil.readExcel(filename, headerlist, Wcbtask.class);
			rowcount=datalist.size();
			
			// 合法性校验
			List<ValidateItem> items = new ArrayList<ValidateItem>();
			items.add(new ValidateItem("taskname", "任务名称", true, DataType.STRING));
			items.add(new ValidateItem("taskdesc", "工单描述", true, DataType.STRING));
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
		
		WcbtaskListDomain wcbtasklistdomain=new WcbtaskListDomain();
		wcbtasklistdomain.setWcbtasklist(datalist);
		ReturnValueDomain<String> oneret= wcbtaskservice.add(wcbtasklistdomain);
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
	  * @param WcbtaskQueryDomain
	  * @return ReturnValueDomain<SysEntityfile>对象
	  */
	@Proxy(method="exportexcel", inarg="WcbtaskQueryDomain", outarg="ReturnValueDomain<SysEntityfile>")
	@ResponseBody
	@RequestMapping("/exportexcel")
	public ReturnValueDomain<SysEntityfile> exportexcel(HttpServletRequest request) {
		ReturnValueDomain<SysEntityfile> ret=new ReturnValueDomain<SysEntityfile>();
		
		WcbtaskQueryDomain wcbtaskquerydomain = null;
		try {
			wcbtaskquerydomain = JsonUtil.request2Domain(request, WcbtaskQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		PageParamDomain pageparam=new PageParamDomain();
		pageparam.setRownumperpage(ServerFeature.EXPORT_MAXSIZE);
		pageparam.setPageindex(0);
		wcbtaskquerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<Wcbtask>> queryret = wcbtaskservice.query(wcbtaskquerydomain);
		
		List<Wcbtask> Wcbtasklist = queryret.getObject().getDatalist();
		
		List<HeaderDomain> headerlist = new ArrayList<HeaderDomain>();
		headerlist.add(new HeaderDomain("0", "taskname", "任务名称"));
		headerlist.add(new HeaderDomain("1", "taskdesc", "工单描述"));
		headerlist.add(new HeaderDomain("2", "optime", "操作时间"));
		headerlist.add(new HeaderDomain("3", "opuser", "操作人"));
		
		String filepath=ServerFeature.WEBAPP_HOME + File.separator + "download" + File.separator + DateUtil.getNowTime("yyyyMMdd");
		String filename=UUID.randomUUID().toString() + ".xlsx";
				
		try {
			ExcelUtil.writeExcel(filepath + File.separator + filename, headerlist, Wcbtasklist);
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
