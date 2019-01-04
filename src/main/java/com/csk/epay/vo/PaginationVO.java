package com.csk.epay.vo;

import lombok.Data;
import java.util.List;

/**
 * @author STELLA
 */
@Data
public class PaginationVO<T> {

	private Long total;
	private List<T> dataList;
}
