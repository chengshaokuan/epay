package com.csk.epay.dao;

import com.csk.epay.domain.OperationLog;
import com.csk.epay.vo.LogCondition;
import com.csk.epay.vo.UserCondition;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface OperationLogDao {

//	@Insert("insert into tbl_operationlog (ip,operator,time,module,type,node) values ( #{ip},#{operator}, #{time}, #{module}, #{type},#{node}")
//	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType =int.class, before = false)
	void save(OperationLog ol);

	void deleteById(Integer id);

	List<OperationLog> selectLog(LogCondition logCondition);

	Long getTotal(LogCondition logCondition);

}
