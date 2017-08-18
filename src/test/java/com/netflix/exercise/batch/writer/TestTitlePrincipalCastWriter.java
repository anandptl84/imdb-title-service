package com.netflix.exercise.batch.writer;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.netflix.exercise.batch.model.TitlePrincipalCastRow;

import jersey.repackaged.com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class TestTitlePrincipalCastWriter {

	private static String TEST_TITLE_ID = "testTitleId";
	private static String CAST_ID1 = "castId1";
	private static String CAST_ID2 = "castId2";
	private static String CAST_ID3 = "castId3";

	@Captor
	private ArgumentCaptor<Map<String, String>[]> paramCaptor;

	private final List<String> castList = Lists.newArrayList(CAST_ID1, CAST_ID2, CAST_ID3);
	private final TitlePrincipalCastRow testItem = new TitlePrincipalCastRow();
	private final NamedParameterJdbcTemplate mockTemplate = Mockito.mock(NamedParameterJdbcTemplate.class);

	private final TitlePrincipalCastWriter cut = new TitlePrincipalCastWriter(mockTemplate);

	@Before
	public void init() {

		testItem.setTitleId(TEST_TITLE_ID);
		testItem.setPrincipalCasts(String.join(",", castList));
	}

	@Test
	public void testWriteWithValidParameters() throws Exception {
		cut.write(Lists.newArrayList(testItem));
		Mockito.verify(mockTemplate, Mockito.times(1)).batchUpdate(Mockito.anyString(), paramCaptor.capture());
		final Map<String, String>[] actualResult = paramCaptor.getValue();

		assertTrue(3 ==  actualResult.length);
		for (int index = 0; index < 3; index++) {
			assertTrue(2 == actualResult[index].size());
			assertTrue(actualResult[index].values().contains(TEST_TITLE_ID));
			assertTrue(actualResult[index].values().contains(castList.get(index)));
		}
	}

	@Test
	public void testWriterWithNullChildrenProducesNoInserts() throws Exception {
		testItem.setPrincipalCasts(null);
		cut.write(Lists.newArrayList(testItem));
		Mockito.verify(mockTemplate, Mockito.never()).batchUpdate(Mockito.anyString(), paramCaptor.capture());

	}
}
