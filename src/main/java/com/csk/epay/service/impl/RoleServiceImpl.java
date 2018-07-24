package com.csk.epay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csk.epay.dao.RoleDao;
import com.csk.epay.domain.Role;
import com.csk.epay.service.RoleService;
import com.csk.epay.vo.PaginationVO;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource(name="roleDao")
	private RoleDao roleDao;
	
	@Override
	public void save(Role role) {
		roleDao.save(role);
	}

	@Override
	public void delele(Integer[] ids) {
		for (Integer id : ids) {
			roleDao.deleteById(id);			
		}
	}

	@Override
	public Role getById(Integer id) {
		return roleDao.getById(id);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public PaginationVO<Role> getByPage(Integer pageNo, Integer pageSize) {
		PaginationVO<Role> paginationVO = new PaginationVO<Role>();
		paginationVO.setTotal(roleDao.getTotal());
		paginationVO.setDataList(roleDao.getRolesByPage((pageNo-1)*pageSize, pageSize));
		return paginationVO;
	}

}
