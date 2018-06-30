package com.f1000.exercise.rankjournals.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JournalScore implements Comparable<JournalScore> {
	private final Journal journal;
	private final double score;

	@Override
	public int compareTo(JournalScore journalScore) {

		return this.score != journalScore.score ? Double.compare(journalScore.score, this.score)
				: this.journal.getName().compareTo(journalScore.getJournal().getName());
	}
}
