package com.netflix.exercise.query.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
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

	private static final String TITLES_BY_CAST = " select t, r from Title t left join t.titleRating r join fetch t.primaryCast p where p.name_id = :castId";
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public Title getTitle(String titleId) {
		final Session session = sessionFactory.getCurrentSession();
		final Query query = session.createQuery("from Title t  where t.titleId = :titleId");
		query.setParameter("titleId", titleId);
		return (Title) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Title> getTitles(int year, String type) {
		final Session session = sessionFactory.getCurrentSession();
		final List<Title> result = session.createCriteria(Title.class)
				.add(Restrictions.eq("startYear", year))
				.add(Restrictions.eq("type", type))
				.setFetchMode("primaryCast", FetchMode.JOIN)
				.setFetchMode("titleRating", FetchMode.JOIN)
				.list();
		return new HashSet<>(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Title> getTitlesForCast(String castId) {
		final Session session = sessionFactory.getCurrentSession();
		session.setFlushMode(FlushMode.MANUAL);
		final Query query = session.createQuery(TITLES_BY_CAST);
		query.setParameter("castId", castId);
		return new HashSet<>(query.list());
	}
}
