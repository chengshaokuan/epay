package com.csk.epay.dao;

import com.csk.epay.domain.OperationLog;
import com.csk.epay.vo.LogCondition;

import java.util.List;

public interface OperationLogDao {

    void save (OperationLog ol);

    void deleteById (Integer id);

    List<OperationLog> selectLog (LogCondition logCondition);

    Long getTotal (LogCondition logCondition);

}
