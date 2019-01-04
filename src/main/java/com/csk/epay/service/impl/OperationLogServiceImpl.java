package com.csk.epay.service.impl;

import javax.annotation.Resource;

import com.csk.epay.vo.LogCondition;
import com.csk.epay.vo.PaginationVO;
import org.springframework.stereotype.Service;

import com.csk.epay.dao.OperationLogDao;
import com.csk.epay.domain.OperationLog;
import com.csk.epay.service.OperationLogService;

import java.util.List;


@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

	@Resource(name = "operationLogDao")
	private OperationLogDao operationLogDao;


	@Override
    public void writeLog(OperationLog ol) {
		operationLogDao.save(ol);
	}

	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			operationLogDao.deleteById(id);
		}
	}


	@Override
	public PaginationVO<OperationLog> selectLog(LogCondition logCondition) {
		PaginationVO<OperationLog> paginationVO = new PaginationVO<OperationLog>();
		logCondition.setPageNo((logCondition.getPageNo()-1)*logCondition.getPageSize());
		paginationVO.setTotal(operationLogDao.getTotal(logCondition));
		paginationVO.setDataList(operationLogDao.selectLog(logCondition));
		return paginationVO;
	}

}
