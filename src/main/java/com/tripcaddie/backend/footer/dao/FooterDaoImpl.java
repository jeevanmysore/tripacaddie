package com.tripcaddie.backend.footer.dao;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("footerDao")
public class FooterDaoImpl  implements FooterDao{

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void insertEntity(Object entity) {
		hibernateTemplate.save(entity);
		
	}

}
