package cn.finedo.wcb.service.sso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.finedo.common.domain.ReturnValueDomain;
import cn.finedo.common.non.NonUtil;
import cn.finedo.common.pojo.SysUser;
import cn.finedo.fsdp.service.common.exception.TransactionException;
import cn.finedo.fsdp.service.common.id.IDUtil;
import cn.finedo.fsdp.service.common.jdbc.JdbcTemplate;
import cn.finedo.fsdp.service.login.domain.AccountDomain;
import cn.finedo.fsdp.service.login.domain.LoginDomain;

@Service
@Transactional
@Scope("singleton")
public class WcbSSOLoginService {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IDUtil idutil;
	
	/**
	 * 根据用户号码查询账号
	 * @param sysUser
	 * @return
	 */
	public ReturnValueDomain<AccountDomain> getUserOfByphone(SysUser sysUser){
		ReturnValueDomain<AccountDomain> ret=new ReturnValueDomain<AccountDomain>();
		List<SysUser>  list=new ArrayList<SysUser>();
		String sql ="select tu.userid,tu.usercode from tb_sys_user tu,tb_sys_person tp where tu.personid=tp.personid and tp.phoneno=:phoneno";
		try {
			list=jdbcTemplate.query(sql, sysUser, SysUser.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("根据用户电话查询user信息错误");
			throw new TransactionException(e);
		}
		if(NonUtil.isNon(list)||list.size()<1){
			logger.info("未查询到该电话号码的用户");
			ret.setFail("未查询到该电话号码的用户");
		}else{
			AccountDomain accountDomain=new AccountDomain();
			accountDomain.setAccount(list.get(0).getUsercode());
			ret.setObject(accountDomain);
			ret.setSuccess("查询成功");
		}
		
		return ret;
	}
	
}
