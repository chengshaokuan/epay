package com.csk.epay.service;

import com.csk.epay.domain.OperationLog;

public interface OperationLogService {

	/**
	 * 
	 * @param ol
	 */
	void writeLog(OperationLog ol);


}
