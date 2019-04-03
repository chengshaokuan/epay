package com.csk.epay.dao.annotation;

import com.csk.epay.domain.OperationLog;
import com.csk.epay.vo.LogCondition;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

public interface OperationLogDao {

	@Insert("insert into tbl_operationlog (ip,operator,time,module,type,node) values ( #{ip},#{operator}, #{time}, #{module}, #{type},#{node}")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType =int.class, before = false)
	void save (OperationLog ol);

	@Delete("delete from tbl_operationlog where id= #{id}")
	void deleteById (Integer id);

	@Select("<script>" +
			"select  * from tbl_operationlog <where> " +
			"<if test=\"startTime!=null and startTime!=''\">" +
			"and expireTime =#{startTime}</if>" +
			"<if test=\"endTime !=null and endTime !=''\">" +
			"and expireTime =#{endTime}</if>" +
			"limit #{pageNo},#{pageSize}</where>" +
			"</script>")
	List<OperationLog> selectLog (LogCondition logCondition);


	@Select("<script>" +
			"select count(*) from tbl_operationlog where 1=1" +
			"<if test=\"startTime!=null and startTime!=''\">" +
			"and #{startTime}>time</if>" +
			"<if test=\"endTime !=null and endTime !=''\">" +
			"and time >#{endTime}" +
			"</if></script>")
	Long getTotal (LogCondition logCondition);

}
