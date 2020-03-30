/**
 * 员工宣传海报表管理服务
 * 
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpubqrcode;

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
import cn.finedo.wcb.pojo.Wcbpubqrcode;
import cn.finedo.wcb.service.wcbpubqrcode.domain.WcbpubqrcodeListDomain;
import cn.finedo.wcb.service.wcbpubqrcode.domain.WcbpubqrcodeQueryDomain;

@Service
@Transactional
@Scope("singleton")
public class WcbpubqrcodeService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
		
	/**
	 * 员工宣传海报表查询
	 * @param WcbpubqrcodeQueryDomain
	 * @return ReturnValueDomain<PageDomain<Wcbpubqrcode>>
	 */
	public ReturnValueDomain<PageDomain<Wcbpubqrcode>> query(WcbpubqrcodeQueryDomain wcbpubqrcodequerydomain) {
		ReturnValueDomain<PageDomain<Wcbpubqrcode>> ret = new ReturnValueDomain<PageDomain<Wcbpubqrcode>>();
		
		StringBuilder sql=new StringBuilder("SELECT t1.taskid,t1.userid,t1.picture,t1.opuser,t1.opdate,t2.publicizetype,t2.publicizedesc FROM tb_wcb_pubqrcode t1,tb_wcb_publicize t2 WHERE t1.publicizeid=t2.publicizeid");
			
		Wcbpubqrcode wcbpubqrcode=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(wcbpubqrcodequerydomain)) {
			pageparam=wcbpubqrcodequerydomain.getPageparam();
			wcbpubqrcode=wcbpubqrcodequerydomain.getWcbpubqrcode();
			
			if(NonUtil.isNotNon(wcbpubqrcode)) {
				if(NonUtil.isNotNon(wcbpubqrcode.getPublicizetype())){
					sql.append(" AND instr(t2.publicizetype,:publicizetype)>0 ");
				}
				
				if(NonUtil.isNotNon(wcbpubqrcode.getPublicizedesc())){
					sql.append(" and instr(t2.publicizedesc,:publicizedesc)>0 ");
				}
				
				if(NonUtil.isNotNon(wcbpubqrcode.getUserid())) {
					wcbpubqrcode.setUserid("%"+wcbpubqrcode.getUserid()+"%");
					sql.append(" AND t1.userid like :userid ");
				}
				
				if(NonUtil.isNotNon(wcbpubqrcode.getPublicizeid())) {
					wcbpubqrcode.setPublicizeid("%"+wcbpubqrcode.getPublicizeid()+"%");
					sql.append(" AND t1.publicizeid like :publicizeid ");
				}
				
				if(NonUtil.isNotNon(wcbpubqrcode.getOpdate())) {
					sql.append(" AND opdate >= " + ConvertUtil.stringToDateByMysql(":optimebegin"));
				}
				if(NonUtil.isNotNon(wcbpubqrcode.getOpdate())) {
					sql.append(" AND opdate <= " + ConvertUtil.stringToDateByMysql(":optimeend"));
				}
			}
			
			sql.append(" order by t1.opdate desc");
		}
				
		PageDomain<Wcbpubqrcode> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), wcbpubqrcode, Wcbpubqrcode.class, pageparam);
		}catch (Exception e) {
			logger.error("员工宣传海报表查询异常", e);
			return ret.setFail("员工宣传海报表查询异常");
		}
		
		return ret.setSuccess("查询员工宣传海报表成功", retpage);
	}
	
	/**
	 * 员工宣传海报表新增
	 * @param WcbpubqrcodeListDomain
	 * @return ReturnValueDomain<Wcbpubqrcode>
	 */
	public ReturnValueDomain<String> add(WcbpubqrcodeListDomain wcbpubqrcodeListDomain) {
		List<Wcbpubqrcode> WcbpubqrcodeList=wcbpubqrcodeListDomain.getWcbpubqrcodelist();
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		WcbpubqrcodeQueryDomain wcbpubqrcodequerydomain = new WcbpubqrcodeQueryDomain();
		Wcbpubqrcode wcbpubqrcode1 = new Wcbpubqrcode();
		wcbpubqrcode1.setUserid(WcbpubqrcodeList.get(0).getUserid());
		wcbpubqrcode1.setPublicizeid(WcbpubqrcodeList.get(0).getPublicizeid());
		wcbpubqrcodequerydomain.setWcbpubqrcode(wcbpubqrcode1);
		ReturnValueDomain<PageDomain<Wcbpubqrcode>> retquery = query(wcbpubqrcodequerydomain);
		
		if(retquery.getObject().getDatalist().size()>0){
			ret.setFail(wcbpubqrcodeListDomain.getWcbpubqrcodelist().get(0).getUserid()+" 已添加，请勿重复添加");
			return ret;
		}
		if(WcbpubqrcodeList.size()<1||WcbpubqrcodeList==null){
			ret.setFail("添加二维码与海报合并表失败,无添加对象");
			return ret;
		}
		Wcbpubqrcode wcbpubqrcode=WcbpubqrcodeList.get(0);
		String taskid=idutil.getOptsn();
		wcbpubqrcode.setTaskid(taskid);
		String sql="insert into tb_wcb_pubqrcode(publicizeid,qrcodeid,taskid,userid,picture,opuser,opdate) values(:publicizeid,:qrcodeid,:taskid,:userid,:picture,:opuser,sysdate())";
		try {
			jdbcTemplate.update(sql, wcbpubqrcode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("添加二维码与海报合并表失败");
			throw new TransactionException(e);
		}
		return ret.setSuccess("添加成功");
	}
	
	
	/**
	 * 根据海报id查看该用户的二维码海报
	 * @param wcbpubqrcode
	 * @return
	 */
	public ReturnValueDomain<List<Wcbpubqrcode>> getPathOfPicid(Wcbpubqrcode wcbpubqrcode){
		ReturnValueDomain<List<Wcbpubqrcode>> ret=new ReturnValueDomain<List<Wcbpubqrcode>>();
		String sql="select publicizeid,qrcodeid,taskid,userid,picture,opuser,opdate from tb_wcb_pubqrcode where publicizeid=:publicizeid and userid=:userid";
		List<Wcbpubqrcode> list=new ArrayList<Wcbpubqrcode>();
		try {
			list=jdbcTemplate.query(sql, wcbpubqrcode, Wcbpubqrcode.class);
		} catch (Exception e) {
			logger.info("添加二维码与海报合并表失败");
			e.printStackTrace();
			throw new TransactionException(e);
		}
		if(NonUtil.isNon(list)||list.size()==0){
			ret.setSuccess("false");
		}else{
			ret.setSuccess("true");
		}
		ret.setObject(list);
		return ret;
	}
	
	/**
	 * 员工宣传海报表修改
	 * @param WcbpubqrcodeListDomain
	 * @return ReturnValueDomain<String>
	 */
	public ReturnValueDomain<String> update(WcbpubqrcodeListDomain wcbpubqrcodelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbpubqrcodelistdomain)) {
			return ret.setFail("无员工宣传海报表");
		}
		
		List<Wcbpubqrcode> wcbpubqrcodelist=wcbpubqrcodelistdomain.getWcbpubqrcodelist();
		
		if (NonUtil.isNon(wcbpubqrcodelist)) {
			return ret.setFail("无员工宣传海报表");
		}
		
				
		String sql="UPDATE tb_wcb_pubqrcode SET publicizeid=:publicizeid, qrcodeid=:qrcodeid, taskid=:taskid, userid=:userid, picture=:picture, opuser=:opuser, opdate=DATE_FORMAT(:opdate, '%Y-%m-%d') " +
  		           "WHERE ";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbpubqrcodelist);
		} catch (Exception e) {
			logger.error("员工宣传海报表修改异常", e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("员工宣传海报表修改成功");
	}
	
	/**
	 * 员工宣传海报表删除
	 * @param WcbpubqrcodeListDomain
	 * @return ReturnValueDomain<SysUser>
	 */
	public ReturnValueDomain<String> delete(WcbpubqrcodeListDomain wcbpubqrcodelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbpubqrcodelistdomain)) {
			return ret.setFail("无员工宣传海报表");
		}
		
		List<Wcbpubqrcode> wcbpubqrcodelist = wcbpubqrcodelistdomain.getWcbpubqrcodelist();
		
		if (NonUtil.isNon(wcbpubqrcodelist)) {
			return ret.setFail("无员工宣传海报表");
		}
		
		String sql="DELETE FROM tb_wcb_pubqrcode WHERE taskid=:taskid";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbpubqrcodelist);
		} catch (Exception e) {
			logger.error("员工宣传海报表删除异常", e);
			throw new TransactionException(e);
		}
				
		return ret.setSuccess("员工宣传海报表删除成功");
	}
}
