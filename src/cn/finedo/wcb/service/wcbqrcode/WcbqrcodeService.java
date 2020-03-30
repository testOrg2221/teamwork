/**
 * 员工二维码表管理服务
 * 
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbqrcode;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.finedo.common.convert.ConvertUtil;
import cn.finedo.common.domain.PageDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.common.non.NonUtil;
import cn.finedo.wcb.pojo.Integrate;
import cn.finedo.wcb.pojo.Orderupload;
import cn.finedo.wcb.pojo.Wcbqrcode;
import cn.finedo.wcb.service.wcbqrcode.domain.OrderuploadListDomain;
import cn.finedo.wcb.service.wcbqrcode.domain.WcbqrcodeListDomain;
import cn.finedo.wcb.service.wcbqrcode.domain.WcbqrcodeQueryDomain;

@Service
@Transactional
@Scope("singleton")
public class WcbqrcodeService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
		
	/**
	 * 员工二维码表查询
	 * @param WcbqrcodeQueryDomain
	 * @return ReturnValueDomain<PageDomain<Wcbqrcode>>
	 */
	public ReturnValueDomain<PageDomain<Wcbqrcode>> query(WcbqrcodeQueryDomain wcbqrcodequerydomain) {
		ReturnValueDomain<PageDomain<Wcbqrcode>> ret = new ReturnValueDomain<PageDomain<Wcbqrcode>>();
		
		StringBuilder sql=new StringBuilder("SELECT * FROM tb_wcb_qrcode WHERE 1=1");
			
		Wcbqrcode wcbqrcode=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(wcbqrcodequerydomain)) {
			pageparam=wcbqrcodequerydomain.getPageparam();
			wcbqrcode=wcbqrcodequerydomain.getWcbqrcode();
			
			if(NonUtil.isNotNon(wcbqrcode)) {
				
				if(NonUtil.isNotNon(wcbqrcode.getQrcodeid())) {
					sql.append(" AND qrcodeid=:qrcodeid");
				}
				if(NonUtil.isNotNon(wcbqrcode.getTaskid())) {
					sql.append(" AND taskid=:taskid");
				}
				if(NonUtil.isNotNon(wcbqrcode.getPublicizeid())) {
					sql.append(" AND publicizeid=:publicizeid");
				}
				if(NonUtil.isNotNon(wcbqrcode.getQrcodepic())) {
					sql.append(" AND qrcodepic=:qrcodepic");
				}
				if(NonUtil.isNotNon(wcbqrcode.getUserid())) {
					sql.append(" AND userid=:userid");
				}
				if(NonUtil.isNotNon(wcbqrcode.getOptimebegin())) {
					sql.append(" AND optime >= " + ConvertUtil.stringToDateByMysql(":optimebegin"));
				}
				if(NonUtil.isNotNon(wcbqrcode.getOptimeend())) {
					sql.append(" AND optime <= " + ConvertUtil.stringToDateByMysql(":optimeend"));
				}
				if(NonUtil.isNotNon(wcbqrcode.getOpuser())) {
					sql.append(" AND opuser=:opuser");
				}
			}
		}
				
		PageDomain<Wcbqrcode> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), wcbqrcode, Wcbqrcode.class, pageparam);
		}catch (Exception e) {
			logger.error("员工二维码表查询异常", e);
			return ret.setFail("员工二维码表查询异常");
		}
		
		return ret.setSuccess("查询员工二维码表成功", retpage);
	}
	
	/**
	 * 员工二维码表新增
	 * @param WcbqrcodeListDomain
	 * @return ReturnValueDomain<Wcbqrcode>
	 */
	public ReturnValueDomain<String> add(WcbqrcodeListDomain wcbqrcodelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbqrcodelistdomain)) {
			return ret.setFail("无员工二维码表");
		}
		
		List<Wcbqrcode> wcbqrcodelist=wcbqrcodelistdomain.getWcbqrcodelist();
		
		if (NonUtil.isNon(wcbqrcodelist)) {
			return ret.setFail("无员工二维码表");
		}
		
		try{
			for(Wcbqrcode wcbqrcode : wcbqrcodelist) {
				
				String qrcodeid=idutil.getID("wcbqrcode");
				wcbqrcode.setQrcodeid(qrcodeid);
			}
		}catch(Exception e) {
		    logger.error("员工二维码表处理异常", e);
			return ret.setFail("员工二维码表处理异常");
		}
		
  		String sql="INSERT INTO tb_wcb_qrcode (qrcodeid, taskid, publicizeid, qrcodepic, userid, optime, opuser) " +
  		           "VALUES (:qrcodeid, :taskid, :publicizeid, :qrcodepic, :userid, DATE_FORMAT(:optime, '%Y-%m-%d'), :opuser)";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbqrcodelist);
		} catch (Exception e) {
			logger.error("员工二维码表入库异常", e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("员工二维码表新增成功");
	}
	
	/**
	 * 员工二维码表修改
	 * @param WcbqrcodeListDomain
	 * @return ReturnValueDomain<String>
	 */
	public ReturnValueDomain<String> update(WcbqrcodeListDomain wcbqrcodelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbqrcodelistdomain)) {
			return ret.setFail("无员工二维码表");
		}
		
		List<Wcbqrcode> wcbqrcodelist=wcbqrcodelistdomain.getWcbqrcodelist();
		
		if (NonUtil.isNon(wcbqrcodelist)) {
			return ret.setFail("无员工二维码表");
		}
		
				
		String sql="UPDATE tb_wcb_qrcode SET qrcodeid=:qrcodeid, taskid=:taskid, publicizeid=:publicizeid, qrcodepic=:qrcodepic, userid=:userid, optime=DATE_FORMAT(:optime, '%Y-%m-%d'), opuser=:opuser " +
  		           "WHERE qrcodeid=:qrcodeid";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbqrcodelist);
		} catch (Exception e) {
			logger.error("员工二维码表修改异常", e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("员工二维码表修改成功");
	}
	
	/**
	 * 员工二维码表删除
	 * @param WcbqrcodeListDomain
	 * @return ReturnValueDomain<SysUser>
	 */
	public ReturnValueDomain<String> delete(WcbqrcodeListDomain wcbqrcodelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbqrcodelistdomain)) {
			return ret.setFail("无员工二维码表");
		}
		
		List<Wcbqrcode> wcbqrcodelist = wcbqrcodelistdomain.getWcbqrcodelist();
		
		if (NonUtil.isNon(wcbqrcodelist)) {
			return ret.setFail("无员工二维码表");
		}
		
		String sql="DELETE FROM tb_wcb_qrcode WHERE qrcodeid=:qrcodeid";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbqrcodelist);
		} catch (Exception e) {
			logger.error("员工二维码表删除异常", e);
			throw new TransactionException(e);
		}
				
		return ret.setSuccess("员工二维码表删除成功");
	}
	
	
	/**
	 * 将二维码信息入表
	 * @param wcbqrcode
	 * @return
	 */
	public ReturnValueDomain<String> addcode(Wcbqrcode wcbqrcode){
		ReturnValueDomain<String> ret=new ReturnValueDomain<String>();
		String cid=idutil.getOptsn();
		wcbqrcode.setCid(cid);
		String sql="insert into tb_wcb_codeinfo(cid,publicizeid,urlpath,optime,userid,codenum) values(:cid,:publicizeid,:urlpath,sysdate(),:userid,0)";
		try {
			jdbcTemplate.update(sql,wcbqrcode);
			ret.setObject(cid);
			ret.setSuccess("入表成功");
		} catch (Exception e) {
			ret.setFail("入表失败");
			logger.info("二维码信息入表失败");
			e.printStackTrace();
		}
		return ret;
	}
	
	
	/**
	 * 添加扫描记录
	 * @param wcbqrcode
	 * @return
	 */
	public ReturnValueDomain<Wcbqrcode> updatecode(Integrate integrate){
		integrate.setIid(idutil.getOptsn());
		integrate.setType("1");
		ReturnValueDomain<Wcbqrcode> ret=new ReturnValueDomain<Wcbqrcode>();
		Wcbqrcode wcbqrcode2=new Wcbqrcode();
		//String sql="update tb_wcb_codeinfo set codenum=codenum+1 where cid=:cid";
		String sql="insert into tb_wcb_integrate(iid,cid,type,publicizeid,userid,opuser,optime,codenum) values(:iid,:cid,:type,:publicizeid,:userid,:opuser,sysdate(),:codenum)";
		String querysql="select t1.cid,t1.publicizeid,t1.urlpath,t1.optime,t1.userid,t2.scanintegral from tb_wcb_codeinfo t1,tb_wcb_publicize t2 where t1.publicizeid=t2.publicizeid and t1.cid=:cid";
		String querynum="select count(1) codenum from tb_wcb_integrate where publicizeid=:publicizeid and userid=:userid and type='1' and DATE_FORMAT(optime,'%Y-%m-%d')=DATE_FORMAT(sysdate(),'%Y-%m-%d')";
		try {
			wcbqrcode2=jdbcTemplate.queryForObject(querysql, integrate, Wcbqrcode.class);
			integrate.setPublicizeid(wcbqrcode2.getPublicizeid());
			integrate.setUserid(wcbqrcode2.getUserid());
			integrate.setCodenum(wcbqrcode2.getScanintegral());
			Integrate numIntegrate=jdbcTemplate.queryForObject(querynum, integrate,Integrate.class);
			if(Integer.parseInt(numIntegrate.getCodenum())<100){
				jdbcTemplate.update(sql,integrate);
			}
			ret.setSuccess("扫描数目添加成功");
		} catch (Exception e) {
			logger.info("扫描数目添加失败");
			ret.setFail("扫描数目添加失败");
			e.printStackTrace();
		}
		ret.setObject(wcbqrcode2);
		return ret;
	}
	
	
	/**
	 * 办理业务记录
	 * @return
	 */
	public ReturnValueDomain<String> addcode(List<Integrate> list){
		List<Integrate> list1=new ArrayList<Integrate>();
		ReturnValueDomain<String> ret=new ReturnValueDomain<String>();
		String sql = "select t1.publicizeid,t2.usercode,t1.transactintegral codenum from tb_wcb_publicize t1,tb_sys_user t2,tb_sys_person t3 where t1.publicizetype=:publicizetype and t2.personid=t3.personid and t3.phoneno=:phone";
		Integrate integrate2=new Integrate();
		for(Integrate integrate :list){
			try {
				integrate2=jdbcTemplate.queryForObject(sql, integrate, Integrate.class);
			} catch (Exception e) {
				ret.setFail("导入失败");
				logger.info("办理记录查询表失败");
				e.printStackTrace();
				return ret;
			}
			
			integrate.setIid(idutil.getOptsn());
			integrate.setPublicizeid(integrate2.getPublicizeid());
			integrate.setUsercode(integrate2.getUsercode());
			integrate.setCodenum(integrate2.getCodenum());
		}
		String addsql="insert into tb_wcb_integrate(iid,cid,type,publicizeid,userid,opuser,optime,codenum) values(:iid,:cid,:type,:publicizeid,:usercode,:opuser,sysdate(),:codenum)";
		try {
			jdbcTemplate.batchUpdate(addsql, list);
		} catch (Exception e) {
			ret.setFail("导入失败");
			logger.info("办理记录添加到tb_wcb_integrate表失败");
			e.printStackTrace();
			return ret;
		}
		ret.setSuccess("导入成功");
		return ret;
	}
	
	
	
	
	/**
	 * 办理业务新增
	 * @param WcbqrcodeListDomain
	 * @return ReturnValueDomain<String>
	 */
	public ReturnValueDomain<String> addorder(OrderuploadListDomain orderuploadListDomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(orderuploadListDomain)) {
			return ret.setFail("无订单");
		}
		
		List<Orderupload> orderuploadlist=orderuploadListDomain.getOrderuploadlist();
		
		if (NonUtil.isNon(orderuploadlist)) {
			return ret.setFail("无订单");
		}
		
		try{
			for(Orderupload orderupload : orderuploadlist) {
				
				String orderid=idutil.getOptsn();
				orderupload.setOrderid(orderid);
			}
		}catch(Exception e) {
		    logger.error("订单表处理异常", e);
			return ret.setFail("订单表处理异常");
		}
		
  		String sql="INSERT INTO tb_wcb_orderupload (orderid, propatask, ordernum, orderstate, phonenum, cretime, staffnum) " +
  		           "VALUES (:orderid, :propatask, :ordernum, :orderstate, :phonenum, :cretime, :staffnum)";
		
		try {
			jdbcTemplate.batchUpdate(sql, orderuploadlist);
		} catch (Exception e) {
			logger.error("订单表入库异常", e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("订单表新增成功");
	}
	
	
	
	
	
	
}
