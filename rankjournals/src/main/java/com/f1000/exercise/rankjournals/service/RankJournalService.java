package com.f1000.exercise.rankjournals.service;

import java.util.List;

import com.f1000.exercise.rankjournals.model.JournalRank;
import com.f1000.exercise.rankjournals.model.JournalScore;

@FunctionalInterface
public interface RankJournalService {

	List<JournalRank> getJournalsRanking(List<JournalScore> journalScores);

}
