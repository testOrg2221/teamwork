package cn.finedo.wcb.service.wcborderupload;

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
import cn.finedo.common.non.NonUtil;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.wcb.pojo.Orderupload;
import cn.finedo.wcb.service.wcborderupload.domain.OrderuploadQueryDomain;
import cn.finedo.wcb.service.wcbqrcode.domain.OrderuploadListDomain;

@Service
@Transactional
@Scope("singleton")
public class WcborderuploadService {

		private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
		@Resource(name="jdbcTemplate")
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		private IDUtil idutil;

		/**
		 * 员工二维码表查询
		 * @param OrderuploadQueryDomain
		 * @return ReturnValueDomain<PageDomain<Orderupload>>
		 */
		public ReturnValueDomain<PageDomain<Orderupload>> query(OrderuploadQueryDomain orderuploadqueryDomain) {
			ReturnValueDomain<PageDomain<Orderupload>> ret = new ReturnValueDomain<PageDomain<Orderupload>>();
			
			StringBuilder sql=new StringBuilder(" SELECT orderid,propatask,ordernum,orderstate,phonenum,cretime,staffnum  ");
				sql.append(" FROM tb_wcb_orderupload  WHERE 1=1 ");
			Orderupload orderupload=null;
			PageParamDomain pageparam=null;
			if(NonUtil.isNotNon(orderuploadqueryDomain)) {
				pageparam=orderuploadqueryDomain.getPageparam();
				orderupload=orderuploadqueryDomain.getOrderupload();
				
				if(NonUtil.isNotNon(orderupload)) {
					
					if(NonUtil.isNotNon(orderupload.getOrderid())) {
						sql.append(" AND orderid=:orderid");
					}
					if(NonUtil.isNotNon(orderupload.getPropatask())) {
						sql.append(" AND propatask=:propatask");
					}
					if(NonUtil.isNotNon(orderupload.getOrdernum())) {
						sql.append(" AND ordernum=:ordernum");
					}
					if(NonUtil.isNotNon(orderupload.getOrderstate())) {
						sql.append(" AND orderstate=:orderstate");
					}
					if(NonUtil.isNotNon(orderupload.getPhonenum())) {
						sql.append(" AND phonenum=:phonenum");
					}
					if(NonUtil.isNotNon(orderupload.getCretimebeg())) {
						sql.append(" AND cretime >= " + ConvertUtil.stringToDateByMysql(":cretimebeg"));
					}
					if(NonUtil.isNotNon(orderupload.getCretimeend())) {
						sql.append(" AND cretime <= " + ConvertUtil.stringToDateByMysql(":cretimeend"));
					}
					if(NonUtil.isNotNon(orderupload.getStaffnum())) {
						sql.append(" AND staffnum=:staffnum");
					}
				}
			}
					
			PageDomain<Orderupload> retpage=null;
			try {
				retpage =  jdbcTemplate.queryForPage(sql.toString(), orderupload, Orderupload.class, pageparam);
			}catch (Exception e) {
				logger.error("订单信息查询异常", e);
				return ret.setFail("订单信息查询异常");
			}
			
			return ret.setSuccess("查询订单信息成功", retpage);
		}
		
		
		/**
		 * 员工二维码表删除
		 * @param OrderuploadListDomain
		 * @return ReturnValueDomain<SysUser>
		 */
		public ReturnValueDomain<String> delete(OrderuploadListDomain orderuploadlistDomain) {
			ReturnValueDomain<String> ret = new ReturnValueDomain<String>();
			
			if (NonUtil.isNon(orderuploadlistDomain)) {
				return ret.setFail("无订单传递");
			}
			
			List<Orderupload> orderuploadlist = orderuploadlistDomain.getOrderuploadlist();
			
			if (NonUtil.isNon(orderuploadlist)) {
				return ret.setFail("无订单传递接受");
			}
			
			String sql=" DELETE FROM tb_wcb_orderupload WHERE orderid=:orderid ";
			
			try {
				jdbcTemplate.batchUpdate(sql, orderuploadlist);
			} catch (Exception e) {
				logger.error("订单删除异常", e);
				throw new TransactionException(e);
			}
					
			return ret.setSuccess("订单删除成功");
		}
	
		
}
