package com.csk.epay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csk.epay.dao.OperationLogDao;
import com.csk.epay.domain.OperationLog;
import com.csk.epay.service.OperationLogService;


@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

	@Resource(name = "operationLogDao")
	private OperationLogDao operationLogDao;


	public void writeLog(OperationLog ol) {
		operationLogDao.save(ol);
	}

}
