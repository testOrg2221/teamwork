/**
 * 员工二维码表管理服务接口
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbqrcode;

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
import cn.finedo.common.domain.FileImportResultDomain;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ResultDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysEntityfile;
import cn.finedo.common.protocol.JsonUtil;
import cn.finedo.common.valid.DataType;
import cn.finedo.common.valid.ValidateItem;
import cn.finedo.common.valid.ValidateUtil;
import cn.finedo.fsdp.server.framework.ServerFeature;
import cn.finedo.fsdp.service.common.configure.ConfigureUtil;
import cn.finedo.fsdp.service.common.excel.ExcelUtil;
import cn.finedo.fsdp.service.common.excel.HeaderDomain;
import cn.finedo.fsdp.service.file.FileServiceAPProxy;
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.wcb.pojo.Integrate;
import cn.finedo.wcb.pojo.Orderupload;
import cn.finedo.wcb.pojo.Wcbqrcode;
import cn.finedo.wcb.service.wcbqrcode.domain.OrderuploadListDomain;
import cn.finedo.wcb.service.wcbqrcode.domain.WcbqrcodeListDomain;
import cn.finedo.wcb.service.wcbqrcode.domain.WcbqrcodeQueryDomain;

@Controller
@Scope("singleton")
@RequestMapping("service/finedo/wcbqrcode")
public class WcbqrcodeServiceAP {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WcbqrcodeService wcbqrcodeservice;
	
	/**
	 * 员工二维码表查询
	 * @param WcbqrcodeQueryDomain
	 * @return ReturnValueDomain<Wcbqrcode>对象
	 */
	@Proxy(method="query", inarg="WcbqrcodeQueryDomain", outarg="ReturnValueDomain<PageDomain<Wcbqrcode>>")
	@ResponseBody
	@RequestMapping("/query")
	public ReturnValueDomain<PageDomain<Wcbqrcode>> query(HttpServletRequest request) {
		ReturnValueDomain<PageDomain<Wcbqrcode>> ret = new ReturnValueDomain<PageDomain<Wcbqrcode>>();
		WcbqrcodeQueryDomain wcbqrcodequerydomain = null;
		 
		try {
			wcbqrcodequerydomain = JsonUtil.request2Domain(request, WcbqrcodeQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		return wcbqrcodeservice.query(wcbqrcodequerydomain);
	}
	 
	/**
	 * 员工二维码表新增
	 * 
	 * @param WcbqrcodeListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="add", inarg="WcbqrcodeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/add")
	public ReturnValueDomain<String> add(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbqrcodeListDomain wcbqrcodelistdomain = null;
		 
		try {
			wcbqrcodelistdomain = JsonUtil.request2Domain(request, WcbqrcodeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
	
		List<Wcbqrcode> wcbqrcodelist= wcbqrcodelistdomain.getWcbqrcodelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("taskid", "工单id", true, DataType.STRING));
		items.add(new ValidateItem("publicizeid", "海报ID", true, DataType.STRING));
		items.add(new ValidateItem("qrcodepic", "二维码图片", true, DataType.STRING));
		items.add(new ValidateItem("userid", "员工编号", true, DataType.STRING));
		items.add(new ValidateItem("optime", "操作时间", true, DataType.DATE));
		items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbqrcodelist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return wcbqrcodeservice.add(wcbqrcodelistdomain);
	 }

	/**
	 * 员工二维码表修改
	 * @param WcbqrcodeListDomain
	 * @return ReturnValueDomain<String>对象
	 */
	@Proxy(method="update", inarg="WcbqrcodeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/update")
	public ReturnValueDomain<String> update(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbqrcodeListDomain wcbqrcodelistdomain = null;
		  
		try {
			wcbqrcodelistdomain = JsonUtil.request2Domain(request, WcbqrcodeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}

		List<Wcbqrcode> wcbqrcodelist= wcbqrcodelistdomain.getWcbqrcodelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("qrcodeid", "二维码ID", false, DataType.STRING));
		items.add(new ValidateItem("taskid", "工单id", true, DataType.STRING));
		items.add(new ValidateItem("publicizeid", "海报ID", true, DataType.STRING));
		items.add(new ValidateItem("qrcodepic", "二维码图片", true, DataType.STRING));
		items.add(new ValidateItem("userid", "员工编号", true, DataType.STRING));
		items.add(new ValidateItem("optime", "操作时间", true, DataType.DATE));
		items.add(new ValidateItem("opuser", "操作人", true, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbqrcodelist, items);
		if (validret.hasFail()) {
			return validret;
		}

		return wcbqrcodeservice.update(wcbqrcodelistdomain);
	}
	
	/**
	 * 员工二维码表删除
	 * 
	 * @param WcbqrcodeListDomain
	 * @return ReturnValueDomain<String>
	 */
	@Proxy(method="delete", inarg="WcbqrcodeListDomain", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/delete")
	public ReturnValueDomain<String> delete(HttpServletRequest request) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbqrcodeListDomain wcbqrcodelistdomain = null;
		
		try {
			wcbqrcodelistdomain = JsonUtil.request2Domain(request, WcbqrcodeListDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		List<Wcbqrcode> wcbqrcodelist= wcbqrcodelistdomain.getWcbqrcodelist();
		
		List<ValidateItem> items = new ArrayList<ValidateItem>();
		items.add(new ValidateItem("qrcodeid", "二维码ID", false, DataType.STRING));
		ReturnValueDomain<String> validret = ValidateUtil.checkForList(wcbqrcodelist, items);
		if (validret.hasFail()) {
			return validret;
		}
		
		return wcbqrcodeservice.delete(wcbqrcodelistdomain);
	}
	
	/**
	 * 批量导入员工二维码表
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
		List<Wcbqrcode> datalist;
		try {
			List<HeaderDomain> headerlist=new ArrayList<HeaderDomain>();
			
			headerlist.add(new HeaderDomain("0", "taskid", "工单id"));
			headerlist.add(new HeaderDomain("1", "publicizeid", "海报ID"));
			headerlist.add(new HeaderDomain("2", "qrcodepic", "二维码图片"));
			headerlist.add(new HeaderDomain("3", "userid", "员工编号"));
			headerlist.add(new HeaderDomain("4", "optime", "操作时间"));
			headerlist.add(new HeaderDomain("5", "opuser", "操作人"));
			
			datalist=ExcelUtil.readExcel(filename, headerlist, Wcbqrcode.class);
			rowcount=datalist.size();
			
			// 合法性校验
			List<ValidateItem> items = new ArrayList<ValidateItem>();
			items.add(new ValidateItem("taskid", "工单id", true, DataType.STRING));
			items.add(new ValidateItem("publicizeid", "海报ID", true, DataType.STRING));
			items.add(new ValidateItem("qrcodepic", "二维码图片", true, DataType.STRING));
			items.add(new ValidateItem("userid", "员工编号", true, DataType.STRING));
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
		
		WcbqrcodeListDomain wcbqrcodelistdomain=new WcbqrcodeListDomain();
		wcbqrcodelistdomain.setWcbqrcodelist(datalist);
		ReturnValueDomain<String> oneret= wcbqrcodeservice.add(wcbqrcodelistdomain);
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
	  * @param WcbqrcodeQueryDomain
	  * @return ReturnValueDomain<SysEntityfile>对象
	  */
	@Proxy(method="exportexcel", inarg="WcbqrcodeQueryDomain", outarg="ReturnValueDomain<SysEntityfile>")
	@ResponseBody
	@RequestMapping("/exportexcel")
	public ReturnValueDomain<SysEntityfile> exportexcel(HttpServletRequest request) {
		ReturnValueDomain<SysEntityfile> ret=new ReturnValueDomain<SysEntityfile>();
		
		WcbqrcodeQueryDomain wcbqrcodequerydomain = null;
		try {
			wcbqrcodequerydomain = JsonUtil.request2Domain(request, WcbqrcodeQueryDomain.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		
		PageParamDomain pageparam=new PageParamDomain();
		pageparam.setRownumperpage(ServerFeature.EXPORT_MAXSIZE);
		pageparam.setPageindex(0);
		wcbqrcodequerydomain.setPageparam(pageparam);
		
		ReturnValueDomain<PageDomain<Wcbqrcode>> queryret = wcbqrcodeservice.query(wcbqrcodequerydomain);
		
		List<Wcbqrcode> Wcbqrcodelist = queryret.getObject().getDatalist();
		
		List<HeaderDomain> headerlist = new ArrayList<HeaderDomain>();
		headerlist.add(new HeaderDomain("0", "taskid", "工单id"));
		headerlist.add(new HeaderDomain("1", "publicizeid", "海报ID"));
		headerlist.add(new HeaderDomain("2", "qrcodepic", "二维码图片"));
		headerlist.add(new HeaderDomain("3", "userid", "员工编号"));
		headerlist.add(new HeaderDomain("4", "optime", "操作时间"));
		headerlist.add(new HeaderDomain("5", "opuser", "操作人"));
		
		String filepath=ServerFeature.WEBAPP_HOME + File.separator + "download" + File.separator + DateUtil.getNowTime("yyyyMMdd");
		String filename=UUID.randomUUID().toString() + ".xlsx";
				
		try {
			ExcelUtil.writeExcel(filepath + File.separator + filename, headerlist, Wcbqrcodelist);
		} catch (Exception e) {
			logger.error("生成excel文件失败", e);
			return ret.setFail("生成excel文件失败:" + e.getMessage());
		}
		
		SysEntityfile entityfile=new SysEntityfile();
		entityfile.setFilename(filename);
		entityfile.setFilepath(filepath);
		return ret.setSuccess("生成excel文件成功", entityfile);
	}
	
	
	/**
	 * 将二维码信息入表
	 * @param wcbqrcode
	 * @return
	 */
	@Proxy(method="addcode", inarg="Wcbqrcode", outarg="ReturnValueDomain<String>")
	@ResponseBody
	@RequestMapping("/addcode")
	public ReturnValueDomain<String> addcode(HttpServletRequest request) {
		ReturnValueDomain<String> ret =new ReturnValueDomain<String>();
		Wcbqrcode wcbqrcode=new Wcbqrcode();
		try {
			wcbqrcode = JsonUtil.request2Domain(request, Wcbqrcode.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		ret=wcbqrcodeservice.addcode(wcbqrcode);
		return ret;
	}
	
	
	/**
	 * 修改二维码扫描数目
	 * @param request
	 * @return
	 */
	@Proxy(method="updatecode", inarg="Integrate", outarg="ReturnValueDomain<Wcbqrcode>")
	@ResponseBody
	@RequestMapping("/updatecode")
	public ReturnValueDomain<Wcbqrcode> updatecode(HttpServletRequest request) {
		ReturnValueDomain<Wcbqrcode> ret =new ReturnValueDomain<Wcbqrcode>();
		Integrate integrate=new Integrate();
		try {
			integrate = JsonUtil.request2Domain(request, Integrate.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		ret=wcbqrcodeservice.updatecode(integrate);
		return ret;
	}
	
	
	/**
	 * 订单批量上传模板
	 * 
	 * @param SysEntityfile
	 * @return ReturnValueDomain<FileImportResultDomain>对象
	 */
	@Proxy(method="orderimport", inarg="SysEntityfile", outarg="ReturnValueDomain<FileImportResultDomain>")
	@ResponseBody
	@RequestMapping(value="/orderimport")
	public ReturnValueDomain<FileImportResultDomain> orderimport(HttpServletRequest request) {
		ReturnValueDomain<FileImportResultDomain> ret=new ReturnValueDomain<FileImportResultDomain>();
		SysEntityfile entityfile = null;
		try {
			entityfile = JsonUtil.request2Domain(request, SysEntityfile.class);
		}catch(Exception e) {
			logger.error("请求参数解析异常", e);
			return ret.setFail("请求参数解析异常");
		}
		String usercode=entityfile.getFilename();
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
		List<Orderupload> datalist =new ArrayList<Orderupload>();
		List<Integrate> integratelist=new ArrayList<Integrate>();
		try {
			List<HeaderDomain> headerlist=new ArrayList<HeaderDomain>();
			
			headerlist.add(new HeaderDomain("0", "propatask", "宣传任务"));
			headerlist.add(new HeaderDomain("1", "ordernum", "订单编号"));
			headerlist.add(new HeaderDomain("2", "orderstate", "订单状态"));
			headerlist.add(new HeaderDomain("3", "phonenum", "推广人员手机号码"));
			headerlist.add(new HeaderDomain("4", "cretime", "订单生成时间"));
			headerlist.add(new HeaderDomain("5", "staffnum", "受理员工工号"));
			
			datalist=ExcelUtil.readExcel(filename, headerlist, Orderupload.class);
			for(int i=0;i<datalist.size();i++){
				Integrate integrate=new Integrate();
				integrate.setPublicizetype(datalist.get(i).getPropatask());
				integrate.setPhone(datalist.get(i).getPhonenum());
				integrate.setType("2");
				integrate.setOpuser(usercode);
				integratelist.add(integrate);
				if(NonUtil.isNon(datalist.get(i).getPropatask())){
					datalist.remove(datalist.get(i));
					integratelist.remove(integrate);
					i--;
				}
			}
			rowcount=datalist.size();
			
			// 合法性校验
			List<ValidateItem> items = new ArrayList<ValidateItem>();
			items.add(new ValidateItem("propatask", "宣传任务", true, DataType.STRING));
			items.add(new ValidateItem("ordernum", "订单编号", true, DataType.STRING));
			items.add(new ValidateItem("orderstate", "订单状态", true, DataType.STRING));
			items.add(new ValidateItem("phonenum", "推广人员手机号码", true, DataType.STRING));
			items.add(new ValidateItem("cretime", "订单生成时间", true, DataType.STRING));
			items.add(new ValidateItem("staffnum", "受理员工工号", true, DataType.STRING));
			
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
		
		OrderuploadListDomain orderuploadListDomain=new OrderuploadListDomain();
		orderuploadListDomain.setOrderuploadlist(datalist);
		ReturnValueDomain<String> oneret= wcbqrcodeservice.addorder(orderuploadListDomain);
		ReturnValueDomain<String> ret1= wcbqrcodeservice.addcode(integratelist);
		if(ret1.hasFail()){
			return ret.setFail("导入失败");
		}
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
	
	
	
}
