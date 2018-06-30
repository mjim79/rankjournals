package com.f1000.exercise.rankjournals.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JournalRank {

	private final JournalScore journalScore;
	private final int rank;

}
