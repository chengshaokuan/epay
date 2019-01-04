package com.csk.epay.service;

import com.csk.epay.domain.OperationLog;
import com.csk.epay.vo.LogCondition;
import com.csk.epay.vo.PaginationVO;

public interface OperationLogService {

	void writeLog(OperationLog ol);

	void delete(Integer[] ids);

	PaginationVO<OperationLog> selectLog(LogCondition logCondition);


}
