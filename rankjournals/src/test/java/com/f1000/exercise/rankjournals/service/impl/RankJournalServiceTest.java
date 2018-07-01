package com.f1000.exercise.rankjournals.service.impl;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Test;

import com.f1000.exercise.rankjournals.model.Journal;
import com.f1000.exercise.rankjournals.model.JournalRank;
import com.f1000.exercise.rankjournals.model.JournalScore;
import com.f1000.exercise.rankjournals.service.RankJournalService;
import com.f1000.exercise.rankjournals.service.impl.RankJournalServiceImpl;

public class RankJournalServiceTest {

	private final RankJournalService rankJournalService = new RankJournalServiceImpl();

	private static final Journal JOURNAL_A = Journal.builder().name("A").review(false).build();
	private static final Journal JOURNAL_B = Journal.builder().name("B").review(false).build();
	private static final Journal JOURNAL_C = Journal.builder().name("C").review(false).build();
	private static final Journal JOURNAL_A_REVIEW = Journal.builder().name("A").review(true).build();

	private static final JournalScore SCENARIO_1_JOURNAL_SCORE_A = JournalScore.builder().journal(JOURNAL_A).score(5.6)
			.build();
	private static final JournalScore SCENARIO_1_JOURNAL_SCORE_B = JournalScore.builder().journal(JOURNAL_B).score(2.4)
			.build();
	private static final JournalScore SCENARIO_1_JOURNAL_SCORE_C = JournalScore.builder().journal(JOURNAL_C).score(3.1)
			.build();

	private static final JournalScore SCENARIO_2_JOURNAL_SCORE_A = JournalScore.builder().journal(JOURNAL_A).score(2.2)
			.build();
	private static final JournalScore SCENARIO_2_JOURNAL_SCORE_B = JournalScore.builder().journal(JOURNAL_B).score(6.2)
			.build();
	private static final JournalScore SCENARIO_2_JOURNAL_SCORE_C = JournalScore.builder().journal(JOURNAL_C).score(6.2)
			.build();

	private static final JournalScore SCENARIO_3_JOURNAL_SCORE_A = JournalScore.builder().journal(JOURNAL_A_REVIEW)
			.score(5.6).build();
	private static final JournalScore SCENARIO_3_JOURNAL_SCORE_B = JournalScore.builder().journal(JOURNAL_B).score(2.4)
			.build();
	private static final JournalScore SCENARIO_3_JOURNAL_SCORE_C = JournalScore.builder().journal(JOURNAL_C).score(3.1)
			.build();
	private final DecimalFormat scoreFormat = new DecimalFormat("#.#");

	@BeforeClass
	public static void init() {
		Locale.setDefault(Locale.UK);
	}
	
	@Test
	public void shouldGetJournalsOrdered() {

		final List<JournalScore> journalScores = new ArrayList<>(
				Arrays.asList(SCENARIO_1_JOURNAL_SCORE_A, SCENARIO_1_JOURNAL_SCORE_B, SCENARIO_1_JOURNAL_SCORE_C));

		final List<JournalRank> result = this.rankJournalService.getJournalsRanking(journalScores);

		assertEquals(1, result.get(0).getRank());
		assertEquals(SCENARIO_1_JOURNAL_SCORE_A, result.get(0).getJournalScore());
		assertEquals(2, result.get(1).getRank());
		assertEquals(SCENARIO_1_JOURNAL_SCORE_C, result.get(1).getJournalScore());
		assertEquals(3, result.get(2).getRank());
		assertEquals(SCENARIO_1_JOURNAL_SCORE_B, result.get(2).getJournalScore());

	}

	@Test
	public void shouldGetJournalsOrderedWithSharedRank() {

		final List<JournalScore> journalScores = new ArrayList<>(
				Arrays.asList(SCENARIO_2_JOURNAL_SCORE_A, SCENARIO_2_JOURNAL_SCORE_B, SCENARIO_2_JOURNAL_SCORE_C));

		final List<JournalRank> result = this.rankJournalService.getJournalsRanking(journalScores);

		assertEquals(1, result.get(0).getRank());
		assertEquals(SCENARIO_2_JOURNAL_SCORE_B, result.get(0).getJournalScore());
		assertEquals(2, result.get(1).getRank());
		assertEquals(SCENARIO_2_JOURNAL_SCORE_C, result.get(1).getJournalScore());
		assertEquals(3, result.get(2).getRank());
		assertEquals(SCENARIO_2_JOURNAL_SCORE_A, result.get(2).getJournalScore());

	}

	@Test
	public void shouldGetJournalsOrderedExcludingReviewJournals() {

		final List<JournalScore> journalScores = new ArrayList<>(
				Arrays.asList(SCENARIO_3_JOURNAL_SCORE_A, SCENARIO_3_JOURNAL_SCORE_B, SCENARIO_3_JOURNAL_SCORE_C));

		final List<JournalRank> result = this.rankJournalService.getJournalsRanking(journalScores);

		assertEquals(1, result.get(0).getRank());
		assertEquals(SCENARIO_3_JOURNAL_SCORE_C, result.get(0).getJournalScore());
		assertEquals(2, result.get(1).getRank());
		assertEquals(SCENARIO_3_JOURNAL_SCORE_B, result.get(1).getJournalScore());

	}
	
	@Test
	public void shouldGetJornalsOrderedPerformance() {
		
		final List<JournalScore> journalScores = getJournalsPerformance();
		
		final List<JournalRank> result = this.rankJournalService.getJournalsRanking(journalScores);
		
		assertEquals(1, result.get(0).getRank());
		assertEquals(result.size(), result.get(result.size()-1).getRank());
	}
	
	private List<JournalScore> getJournalsPerformance() {
		
		final List<JournalScore> journalScores = new ArrayList<>();
		journalScores.addAll(Arrays.asList(SCENARIO_1_JOURNAL_SCORE_A, SCENARIO_1_JOURNAL_SCORE_B, SCENARIO_1_JOURNAL_SCORE_C));
		journalScores.addAll(Arrays.asList(SCENARIO_2_JOURNAL_SCORE_A, SCENARIO_2_JOURNAL_SCORE_B, SCENARIO_2_JOURNAL_SCORE_C));
		journalScores.addAll(Arrays.asList(SCENARIO_3_JOURNAL_SCORE_A, SCENARIO_3_JOURNAL_SCORE_B, SCENARIO_3_JOURNAL_SCORE_C));
		
		IntStream.range(0, 1000000).forEach(i -> addRandomJournal(journalScores));
		
		return journalScores;
	}
	
	private void addRandomJournal(List<JournalScore> journalScores) {
		
		final Journal journal = Journal.builder()
					.name(Character.toString((char) (ThreadLocalRandom.current().nextInt(26) + 'A')))
					.review(false)
					.build(); 
		journalScores.add(JournalScore.builder()
					.journal(journal)
					.score(Double.valueOf(this.scoreFormat.format(ThreadLocalRandom.current().nextDouble(0.0, 10.0))))
					.build());
	}

}
