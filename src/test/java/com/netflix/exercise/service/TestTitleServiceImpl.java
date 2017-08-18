package com.netflix.exercise.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.netflix.exercise.query.dao.TitleDao;
import com.netflix.exercise.query.model.Title;

import jersey.repackaged.com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class TestTitleServiceImpl {

	private static final String[] HORROR_ANIMATION = new String[] {"horror", "animation"};
	private static final String[] HORROR_ANIMATION_ROMENTIC = new String[] {"horror", "animation", "romentic"};

	private final Title title1 = Mockito.mock(Title.class);
	private final Title title2 = Mockito.mock(Title.class);
	private final Title title3 = Mockito.mock(Title.class);

	@InjectMocks
	private TitleServiceImpl cut;

	@Mock
	private TitleDao mockDao;

	private final Set<Title> expectedResult = Sets.newHashSet(title1, title2, title3);

	@Before
	public void setup() {
		when(title1.getGenres()).thenReturn(Arrays.asList(HORROR_ANIMATION));
		when(title2.getGenres()).thenReturn(Arrays.asList(HORROR_ANIMATION));
		when(title3.getGenres()).thenReturn(Arrays.asList(HORROR_ANIMATION_ROMENTIC));
	}

	@Test
	public void testTitlesByYearAndType() {
		final int year = 2017;
		final String type = "movie";
		when(mockDao.getTitles(year, type)).thenReturn(expectedResult);
		final Set<Title> actualResult = cut.getTitles(year, type, Optional.empty());
		assertEquals("Expected all three titles but actual result was different", expectedResult, actualResult);
	}

	@Test
	public void testTitlesByYearTypeAndGenre() {
		final int year = 2017;
		final String type = "movie";
		when(mockDao.getTitles(year, type)).thenReturn(expectedResult);
		final Set<Title> actualResult = cut.getTitles(2017, type, Optional.of("romentic"));
		assertEquals("Only expected a set with title3(title with romentic genre) but actual output was different", Sets.newHashSet(title3), actualResult);
	}

}
