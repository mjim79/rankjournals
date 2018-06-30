package com.f1000.exercise.rankjournals.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.f1000.exercise.rankjournals.model.JournalRank;
import com.f1000.exercise.rankjournals.model.JournalScore;
import com.f1000.exercise.rankjournals.service.RankJournalService;

@Service
public class RankJournalServiceImpl implements RankJournalService {

	@Override
	public List<JournalRank> getJournalsRanking(List<JournalScore> journalScores) {

		final List<JournalScore> journalsSorted = journalScores.stream().filter(this::isNotReview).sorted()
				.collect(Collectors.toList());
		return journalsSorted.stream().map(j -> getRankedJournal(j, journalsSorted.indexOf(j) + 1))
				.collect(Collectors.toList());

	}

	private JournalRank getRankedJournal(JournalScore journalScore, int rank) {
		return JournalRank.builder().journalScore(journalScore).rank(rank).build();
	}

	private boolean isNotReview(JournalScore journalScore) {
		return !journalScore.getJournal().isReview();
	}

}
