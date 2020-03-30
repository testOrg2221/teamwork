/**
 * 宣传海报表管理服务
 * 
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpublicize;

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
import cn.finedo.wcb.pojo.Wcbpublicize;
import cn.finedo.wcb.service.wcbpublicize.domain.WcbpublicizeListDomain;
import cn.finedo.wcb.service.wcbpublicize.domain.WcbpublicizeQueryDomain;

@Service
@Transactional
@Scope("singleton")
public class WcbpublicizeService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
		
	/**
	 * 宣传海报表查询
	 * @param WcbpublicizeQueryDomain
	 * @return ReturnValueDomain<PageDomain<Wcbpublicize>>
	 */
	public ReturnValueDomain<PageDomain<Wcbpublicize>> query(WcbpublicizeQueryDomain wcbpublicizequerydomain) {
		ReturnValueDomain<PageDomain<Wcbpublicize>> ret = new ReturnValueDomain<PageDomain<Wcbpublicize>>();
		
		StringBuilder sql=new StringBuilder("SELECT a.*,b.taskname,c.filetype,c.filepath,case when d.publicizeid is null then 0 when d.publicizeid is not null then count(1) end addtimeend FROM tb_wcb_publicize a left join tb_wcb_task b on a.taskid=b.taskid ");
		sql.append("left join tb_sys_entityfile c on a.publicizepic=c.fileid ");
		sql.append("LEFT JOIN tb_wcb_pubqrcode d on a.publicizeid=d.publicizeid WHERE 1=1 ");
			
		Wcbpublicize wcbpublicize=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(wcbpublicizequerydomain)) {
			pageparam=wcbpublicizequerydomain.getPageparam();
			wcbpublicize=wcbpublicizequerydomain.getWcbpublicize();
			
			if(NonUtil.isNotNon(wcbpublicize)) {
				
				if(NonUtil.isNotNon(wcbpublicize.getPublicizeid())) {
					sql.append(" AND publicizeid=:publicizeid");
				}
				if(NonUtil.isNotNon(wcbpublicize.getTaskid())) {
					sql.append(" AND taskid=:taskid");
				}
				if(NonUtil.isNotNon(wcbpublicize.getPublicizetype())) {
					sql.append(" AND publicizetype=:publicizetype");
				}
				if(NonUtil.isNotNon(wcbpublicize.getPublicizedesc())) {
					sql.append(" AND publicizedesc=:publicizedesc");
				}
				if(NonUtil.isNotNon(wcbpublicize.getPublicizepic())) {
					sql.append(" AND publicizepic=:publicizepic");
				}
				if(NonUtil.isNotNon(wcbpublicize.getAddtimebegin())) {
					sql.append(" AND addtime >= " + ConvertUtil.stringToDateByMysql(":addtimebegin"));
				}
				if(NonUtil.isNotNon(wcbpublicize.getAddtimeend())) {
					sql.append(" AND addtime <= " + ConvertUtil.stringToDateByMysql(":addtimeend"));
				}
				if(NonUtil.isNotNon(wcbpublicize.getAdduser())) {
					sql.append(" AND adduser=:adduser");
				}
				if(NonUtil.isNotNon(wcbpublicize.getOptimebegin())) {
					sql.append(" AND optime >= " + ConvertUtil.stringToDateByMysql(":optimebegin"));
				}
				if(NonUtil.isNotNon(wcbpublicize.getOptimeend())) {
					sql.append(" AND optime <= " + ConvertUtil.stringToDateByMysql(":optimeend"));
				}
				if(NonUtil.isNotNon(wcbpublicize.getOpuser())) {
					sql.append(" AND opuser=:opuser");
				}
			}
		}
		
		sql .append(" GROUP BY a.publicizeid order by a.optime desc");
				
		PageDomain<Wcbpublicize> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), wcbpublicize, Wcbpublicize.class, pageparam);
		}catch (Exception e) {
			logger.error("宣传海报表查询异常", e);
			return ret.setFail("宣传海报表查询异常");
		}
		
		return ret.setSuccess("查询宣传海报表成功", retpage);
	}
	
	/**
	 * 宣传海报表新增
	 * @param WcbpublicizeListDomain
	 * @return ReturnValueDomain<Wcbpublicize>
	 */
	public ReturnValueDomain<String> add(WcbpublicizeListDomain wcbpublicizelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbpublicizelistdomain)) {
			return ret.setFail("无宣传海报表");
		}
		
		List<Wcbpublicize> wcbpublicizelist=wcbpublicizelistdomain.getWcbpublicizelist();
		
		if (NonUtil.isNon(wcbpublicizelist)) {
			return ret.setFail("无宣传海报表");
		}
		
		try{
			for(Wcbpublicize wcbpublicize : wcbpublicizelist) {
				
				String publicizeid=idutil.getID("wcbpublicize");
				wcbpublicize.setPublicizeid(publicizeid);
			}
		}catch(Exception e) {
		    logger.error("宣传海报表处理异常", e);
			return ret.setFail("宣传海报表处理异常");
		}
		
  		String sql="INSERT INTO tb_wcb_publicize (publicizeid, taskid, publicizetype, publicizedesc, publicizepic, addtime, adduser, optime, opuser) " +
  		           "VALUES (:publicizeid, :taskid, :publicizetype, :publicizedesc, :publicizepic, DATE_FORMAT(:addtime, '%Y-%m-%d'), :adduser, DATE_FORMAT(:optime, '%Y-%m-%d'), :opuser)";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbpublicizelist);
		} catch (Exception e) {
			logger.error("宣传海报表入库异常", e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("宣传海报表新增成功");
	}
	
	/**
	 * 宣传海报表修改
	 * @param WcbpublicizeListDomain
	 * @return ReturnValueDomain<String>
	 */
	public ReturnValueDomain<String> update(WcbpublicizeListDomain wcbpublicizelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbpublicizelistdomain)) {
			return ret.setFail("无宣传海报表");
		}
		
		List<Wcbpublicize> wcbpublicizelist=wcbpublicizelistdomain.getWcbpublicizelist();
		
		if (NonUtil.isNon(wcbpublicizelist)) {
			return ret.setFail("无宣传海报表");
		}
		
				
		String sql="UPDATE tb_wcb_publicize SET publicizeid=:publicizeid, taskid=:taskid, publicizetype=:publicizetype, publicizedesc=:publicizedesc, publicizepic=:publicizepic, addtime=DATE_FORMAT(:addtime, '%Y-%m-%d'), adduser=:adduser, optime=DATE_FORMAT(:optime, '%Y-%m-%d'), opuser=:opuser " +
  		           "WHERE publicizeid=:publicizeid";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbpublicizelist);
		} catch (Exception e) {
			logger.error("宣传海报表修改异常", e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("宣传海报表修改成功");
	}
	
	/**
	 * 宣传海报表删除
	 * @param WcbpublicizeListDomain
	 * @return ReturnValueDomain<SysUser>
	 */
	public ReturnValueDomain<String> delete(WcbpublicizeListDomain wcbpublicizelistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbpublicizelistdomain)) {
			return ret.setFail("无宣传海报表");
		}
		
		List<Wcbpublicize> wcbpublicizelist = wcbpublicizelistdomain.getWcbpublicizelist();
		
		if (NonUtil.isNon(wcbpublicizelist)) {
			return ret.setFail("无宣传海报表");
		}
		
		String sql="DELETE FROM tb_wcb_publicize WHERE publicizeid=:publicizeid";
		String csql="DELETE FROM tb_wcb_pubqrcode WHERE publicizeid=:publicizeid";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbpublicizelist);
			jdbcTemplate.batchUpdate(csql, wcbpublicizelist);
		} catch (Exception e) {
			logger.error("宣传海报表删除异常", e);
			throw new TransactionException(e);
		}
				
		return ret.setSuccess("宣传海报表删除成功");
	}
}
