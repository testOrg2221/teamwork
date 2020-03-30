/**
 * 工单表管理服务
 * 
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbtask;

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
import cn.finedo.fsdp.service.login.domain.LoginDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysUser;
import cn.finedo.wcb.pojo.Wcbpublicize;
import cn.finedo.wcb.pojo.Wcbpubqrcode;
import cn.finedo.wcb.pojo.Wcbtask;
import cn.finedo.wcb.service.wcbtask.domain.WcbtaskListDomain;
import cn.finedo.wcb.service.wcbtask.domain.WcbtaskQueryDomain;

@Service
@Transactional
@Scope("singleton")
public class WcbtaskService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
		
	/**
	 * 工单表查询
	 * @param WcbtaskQueryDomain
	 * @return ReturnValueDomain<PageDomain<Wcbtask>>
	 */
	public ReturnValueDomain<PageDomain<Wcbtask>> query(WcbtaskQueryDomain wcbtaskquerydomain) {
		ReturnValueDomain<PageDomain<Wcbtask>> ret = new ReturnValueDomain<PageDomain<Wcbtask>>();
		
		StringBuilder sql=new StringBuilder("SELECT tk.taskid,tk.taskname,tk.taskdesc,tk.optime,tk.opuser FROM tb_wcb_task tk WHERE 1=1");
			
		Wcbtask wcbtask=null;
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(wcbtaskquerydomain)) {
			pageparam=wcbtaskquerydomain.getPageparam();
			wcbtask=wcbtaskquerydomain.getWcbtask();
			
			if(NonUtil.isNotNon(wcbtask)) {
				if(NonUtil.isNotNon(wcbtask.getTaskname())) {
					sql.append(" AND instr(tk.taskname,:taskname)>0");
				}
				if(NonUtil.isNotNon(wcbtask.getTaskdesc())) {
					sql.append(" AND instr(tk.taskdesc,:taskdesc)>0");
				}
				if(NonUtil.isNotNon(wcbtask.getOptimebegin())) {
					sql.append(" AND optime >= " + ConvertUtil.stringToDateByMysql(":optimebegin"));
				}
				if(NonUtil.isNotNon(wcbtask.getOptimeend())) {
					sql.append(" AND optime <= " + ConvertUtil.stringToDateByMysql(":optimeend"));
				}
				
			}
			sql.append(" order by tk.optime desc");
		}
				
		PageDomain<Wcbtask> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sql.toString(), wcbtask, Wcbtask.class, pageparam);
		}catch (Exception e) {
			logger.error("工单表查询异常", e);
			return ret.setFail("工单表查询异常");
		}
		
		return ret.setSuccess("查询工单表成功", retpage);
	}
	
	
	/**
	 * 得到app所有素材列表
	 * @param wcbtaskquerydomain
	 * @return          
	 */
	public ReturnValueDomain<PageDomain<Wcbpublicize>> getPublicizeOfApp(WcbtaskQueryDomain wcbtaskquerydomain){
		ReturnValueDomain<PageDomain<Wcbpublicize>> ret = new ReturnValueDomain<PageDomain<Wcbpublicize>>();
		Wcbpublicize wcbpublicize=wcbtaskquerydomain.getWcbtask().getWcbpublicizeList().get(0);
		StringBuffer sqlall=new StringBuffer();
		StringBuffer sql =new StringBuffer();
		
		sql.append("select tt.taskname,tt.taskdesc,tp.publicizeid,tp.taskid,tp.publicizetype,tp.publicizedesc,tp.publicizepic,tp.addtime,tp.adduser,tp.optime,tp.opuser,");
		sql.append("te.fileid,te.filepath,te.filetype,te.filename ");
		sql.append("from tb_wcb_publicize tp,tb_sys_entityfile te,tb_wcb_task tt where tp.publicizepic=te.fileid and tp.taskid=tt.taskid ");
		
		/*sqlall.append("select t3.*,COUNT(t4.propatask)*t3.transactintegral totletransactintegralnum from (");
		sqlall.append("SELECT t1.*, sum(t2.codenum) * t1.scanintegral totlescanintegralnum FROM (");
		sqlall.append(sql);
		sqlall.append(") t1 LEFT JOIN tb_wcb_codeinfo t2 ON t1.publicizeid = t2.publicizeid ");
		sqlall.append(" AND t2.userid =:userid GROUP BY t1.publicizeid");
		sqlall.append(") t3 LEFT JOIN tb_wcb_orderupload t4 on t3.publicizetype=t4.propatask and t4.phonenum=:phonenum GROUP BY t3.publicizeid");
		sqlall.append(" order by t3.optime desc");*/
		sqlall.append("select a.*,b.userid,case when b.scancode is null then 0 when b.scancode is not null then b.scancode end as totlescanintegralnum,case when c.scancode is null then 0 when c.scancode is not null then c.scancode end as totletransactintegralnum from (");
		sqlall.append(sql);
		sqlall.append(") a LEFT JOIN (");
		sqlall.append("select publicizeid,userid,sum(codenum) as 'scancode' from tb_wcb_integrate  where type='1' and ");
		sqlall.append("userid=:userid GROUP BY publicizeid");
		sqlall.append(")b on a.publicizeid=b.publicizeid");
		sqlall.append(" LEFT JOIN (");
		sqlall.append("	select publicizeid,userid,sum(codenum) as 'scancode' from tb_wcb_integrate  where type='2' and ");
		sqlall.append("userid=:userid GROUP BY publicizeid");
		sqlall.append(")c on a.publicizeid=c.publicizeid");
		
		PageParamDomain pageparam=null;
		if(NonUtil.isNotNon(wcbtaskquerydomain)) {
			pageparam=wcbtaskquerydomain.getPageparam();
		}
		PageDomain<Wcbpublicize> retpage=null;
		try {
			retpage =  jdbcTemplate.queryForPage(sqlall.toString(), wcbpublicize,Wcbpublicize.class, pageparam);
		}catch (Exception e) {
			logger.error("工单表查询异常", e);
			return ret.setFail("工单表查询异常");
		}
		
		return ret.setSuccess("查询工单表成功", retpage);
	}
	
	
	/**
	 * 根据用户得到积分排名和总积分数
	 * @param loginDomain
	 * @return
	 */
	public ReturnValueDomain<List<Wcbpublicize>> getCodenumOfUSer(SysUser sysUser){
		ReturnValueDomain<List<Wcbpublicize>> ret=new ReturnValueDomain<List<Wcbpublicize>>();
		List<Wcbpublicize> list=new ArrayList<Wcbpublicize>();
		StringBuffer sql=new StringBuffer();
		sql.append("select * from (");
		sql.append(" select (@rowNO := @rowNo+1) AS rowno,a.* from ( ");
		sql.append(" select userid,SUM(codenum) as codenum from tb_wcb_integrate GROUP BY userid ORDER BY codenum DESC ");
		sql.append(" )a ,(select @rowNo:=0) as it order by codenum desc ");
		sql.append(" )b where userid=:usercode ");
		try {
			list=jdbcTemplate.query(sql.toString(), sysUser, Wcbpublicize.class);
		} catch (Exception e) {
			logger.info("加载排名出错");
			ret.setFail("加载数据出错");
			e.printStackTrace();
		}
		ret.setSuccess("加载成功");
		ret.setObject(list);
		return ret;
	}
	
	/**
	 * 工单表新增
	 * @param WcbtaskListDomain
	 * @return ReturnValueDomain<Wcbtask>
	 */
	public ReturnValueDomain<String> add(WcbtaskListDomain wcbtasklistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		List<Wcbtask> wcbtaskList=wcbtasklistdomain.getWcbtasklist();
		if(wcbtaskList.size()<1||wcbtaskList==null){
			ret.setFail("工单新增失败,无添加对象");
			return ret;
		}
		Wcbtask wcbtask=wcbtaskList.get(0);
		String taskid=idutil.getOptsn();//工单id
		wcbtask.setTaskid(taskid);
		List<Wcbpublicize> wcbpublicizelist=wcbtask.getWcbpublicizeList();
		for(Wcbpublicize wcbpublicize:wcbpublicizelist){
			String publicizeid=idutil.getOptsn();
			wcbpublicize.setPublicizeid(publicizeid);
			wcbpublicize.setTaskid(taskid);
		}
		String tasksql="insert into tb_wcb_task(taskid,taskname,taskdesc,optime,opuser) values(:taskid,:taskname,:taskdesc,sysdate(),:opuser)";
		String publicizesql="insert into tb_wcb_publicize(publicizeid,taskid,publicizetype,publicizedesc,publicizepic,addtime,adduser,optime,opuser,scanintegral,transactintegral) values(:publicizeid,:taskid,:publicizetype,:publicizedesc,:publicizepic,sysdate(),:adduser,sysdate(),:opuser,:scanintegral,:transactintegral)";
		try {
			jdbcTemplate.update(tasksql, wcbtask);
			logger.info("tb_wcb_task表添加成功");
			jdbcTemplate.batchUpdate(publicizesql, wcbpublicizelist);
			logger.info("tb_wcb_publicize表添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("微传播工单添加失败");
			throw new TransactionException(e);
		}
		return ret.setSuccess("工单表添加成功");
	}
	
	/**
	 * 根据主键查询工单表
	 * @param wcbtask
	 * @return
	 */
	public ReturnValueDomain<List<Wcbpublicize>> getPublicizeOftaskid(Wcbtask wcbtask){
		ReturnValueDomain<List<Wcbpublicize>> ret=new ReturnValueDomain<List<Wcbpublicize>>();
		List<Wcbpublicize> list=new ArrayList<Wcbpublicize>();
		StringBuffer sql =new StringBuffer();
		sql.append("select tp.transactintegral,tp.scanintegral,tp.publicizeid,tp.taskid,tp.publicizetype,tp.publicizedesc,tp.publicizepic,tp.addtime,tp.adduser,tp.optime,tp.opuser,");
		sql.append("te.fileid,te.filepath,te.filetype,te.filename ");
		sql.append("from tb_wcb_publicize tp,tb_sys_entityfile te where tp.publicizepic=te.fileid and taskid=:taskid");
		try {
		 list=jdbcTemplate.query(sql.toString(), wcbtask, Wcbpublicize.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("根据主键查询工单表失败");
			throw new TransactionException(e);
			
		}
		ret.setObject(list);
		ret.setSuccess("查询成功");
		return ret;
	}
	
	
	/**
	 * 工单表修改
	 * @param WcbtaskListDomain
	 * @return ReturnValueDomain<String>
	 */
	public ReturnValueDomain<String> update(WcbtaskListDomain wcbtasklistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbtasklistdomain)) {
			return ret.setFail("无工单表");
		}
		
		List<Wcbtask> wcbtasklist=wcbtasklistdomain.getWcbtasklist();
		
		if (NonUtil.isNon(wcbtasklist)) {
			return ret.setFail("无工单表");
		}
		
				
		String sql="UPDATE tb_wcb_task SET taskid=:taskid, taskname=:taskname, taskdesc=:taskdesc, optime=DATE_FORMAT(:optime, '%Y-%m-%d'), opuser=:opuser " +
  		           "WHERE taskid=:taskid";
		
		try {
			jdbcTemplate.batchUpdate(sql, wcbtasklist);
		} catch (Exception e) {
			logger.error("工单表修改异常", e);
			throw new TransactionException(e);
		}
		
		return ret.setSuccess("工单表修改成功");
	}
	
	/**
	 * 工单表删除
	 * @param WcbtaskListDomain
	 * @return ReturnValueDomain<SysUser>
	 */
	public ReturnValueDomain<String> delete(WcbtaskListDomain wcbtasklistdomain) {
		ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
		
		if (NonUtil.isNon(wcbtasklistdomain)) {
			return ret.setFail("无工单表");
		}
		
		List<Wcbtask> wcbtasklist = wcbtasklistdomain.getWcbtasklist();
		
		if (NonUtil.isNon(wcbtasklist)) {
			return ret.setFail("无工单表");
		}
		String querysql="select publicizeid from tb_wcb_publicize where taskid=:taskid";
		String sql="DELETE FROM tb_wcb_task WHERE taskid=:taskid";
		String csql="DELETE FROM tb_wcb_pubqrcode WHERE publicizeid=:publicizeid";
		String psql="DELETE FROM tb_wcb_publicize WHERE taskid=:taskid";
		List<Wcbpubqrcode> list=new ArrayList<Wcbpubqrcode>();
		try {
			list=jdbcTemplate.query(querysql, wcbtasklist.get(0), Wcbpubqrcode.class);
			jdbcTemplate.batchUpdate(sql, wcbtasklist);
			jdbcTemplate.batchUpdate(csql, list);
			jdbcTemplate.batchUpdate(psql, wcbtasklist);
		} catch (Exception e) {
			logger.error("工单表删除异常", e);
			throw new TransactionException(e);
		}
				
		return ret.setSuccess("工单表删除成功");
	}
	
	
	/**
	 * 海报置顶
	 * @param wcbpublicize
	 * @return
	 */
	public ReturnValueDomain<String> stickOfPub(Wcbpublicize wcbpublicize){
		ReturnValueDomain<String> ret=new ReturnValueDomain<String>();
		String sql="update tb_wcb_publicize set optime=sysdate() where publicizeid=:publicizeid";
		try {
			jdbcTemplate.update(sql, wcbpublicize);
			ret.setSuccess("置顶成功");
		} catch (Exception e) {
			logger.info("海报素材置顶失败");
			ret.setFail("海报素材置顶失败,请联系管理员");
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	
}
