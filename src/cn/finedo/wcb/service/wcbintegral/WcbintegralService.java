package cn.finedo.wcb.service.wcbintegral;

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
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.wcb.pojo.Wcbpublicize;
import cn.finedo.wcb.service.wcbpublicize.domain.WcbpublicizeQueryDomain;

@Service
@Transactional
@Scope("singleton")
public class WcbintegralService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
		
	/**
	 * 宣传海报表查询
	 * @param WcbqrcodeQueryDomain
	 * @return ReturnValueDomain<PageDomain<Wcbpublicize>>
	 */
	public ReturnValueDomain<PageDomain<Wcbpublicize>> query(WcbpublicizeQueryDomain wcbpublicizequerydomain) {
		ReturnValueDomain<PageDomain<Wcbpublicize>> ret = new ReturnValueDomain<PageDomain<Wcbpublicize>>();
		
		StringBuilder sql=new StringBuilder("");
		sql.append(" select  publicizetype,publicizedesc,opuser,phonenum, scanintegral, transactintegral,optime FROM( ");
		
		sql.append(" select t2.publicizetype publicizetype,t2.publicizedesc publicizedesc,t4.personname opuser,t4.phoneno phonenum, ");
		sql.append(" t1.scanintegral scanintegral,t1.transactintegral transactintegral,t1.optime optime from ( ");
		sql.append(" select optime, SUM(ss.a1) scanintegral,SUM(ss.a2) transactintegral,publicizeid ,userid from ( ");
		sql.append(" SELECT publicizeid,userid,sum(codenum) AS a1,'0' as a2,optime ");
		sql.append(" FROM tb_wcb_integrate WHERE type = '1' GROUP BY publicizeid,userid ");
		sql.append(" UNION ALL  ");
		sql.append(" SELECT publicizeid,userid,'0' as a1,sum(codenum) AS a2,optime  ");
		sql.append(" FROM tb_wcb_integrate WHERE type = '2' ");
		sql.append(" GROUP BY publicizeid,userid) ss ");
		sql.append(" GROUP BY publicizeid,userid) t1,tb_wcb_publicize t2,tb_sys_user t3,tb_sys_person t4  ");
		sql.append(" where t1.publicizeid=t2.publicizeid  ");
		sql.append(" and t1.userid=t3.usercode and t3.personid=t4.personid ");
		
		sql.append(" )sec ");
		sql.append(" where 1=1 ");
		Wcbpublicize wcbpublicize=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(wcbpublicizequerydomain)) {
			pageparam=wcbpublicizequerydomain.getPageparam();
			wcbpublicize=wcbpublicizequerydomain.getWcbpublicize();
			
			if(NonUtil.isNotNon(wcbpublicize)) {
				
				if(NonUtil.isNotNon(wcbpublicize.getPublicizetype())) {
					sql.append(" AND instr(publicizetype,:publicizetype)>0 ");
				}
				if(NonUtil.isNotNon(wcbpublicize.getPublicizedesc())) {
					sql.append(" AND instr(publicizedesc,:publicizedesc)>0 ");
				}
				if(NonUtil.isNotNon(wcbpublicize.getOpuser())) {
					sql.append(" AND instr(opuser,:opuser)>0 ");
				}
				if(NonUtil.isNotNon(wcbpublicize.getPhonenum())) {
					sql.append(" AND instr(phonenum,:phonenum)>0 ");
				}
				if(NonUtil.isNotNon(wcbpublicize.getOptimebegin())) {
					sql.append(" AND optime >= " + ConvertUtil.stringToDateByMysql(":optimebegin"));
				}
				if(NonUtil.isNotNon(wcbpublicize.getOptimeend())) {
					sql.append(" AND optime <= " + ConvertUtil.stringToDateByMysql(":optimeend"));
				}
			}
			
		}	
		PageDomain<Wcbpublicize> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), wcbpublicize, Wcbpublicize.class, pageparam);
		}catch (Exception e) {
			logger.error("宣传海报表查询异常", e);
			return ret.setFail("宣传海报表查询异常");
		}
		
		return ret.setSuccess("查询宣传海报表成功", retpage);
		
	}
	
}