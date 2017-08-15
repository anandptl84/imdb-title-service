package com.netflix.exercise.query.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.netflix.exercise.query.model.Title;

@Component
@Repository
@Transactional
public class TitleDaoImpl implements TitleDao {
	
	@Autowired 
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public Title getTitle(String titleId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select t from Title t from Title t  where t.id = :titleId");
		query.setParameter("titleId", titleId);
		return (Title)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Title> getTitles(int year, String type) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Title t  join fetch t.primaryCast join fetch t.titleRating where t.startYear = :year and t.type = :type");
		query.setParameter("year", year);
		query.setParameter("type", type);
		return new HashSet<>(query.list());
	}
}
