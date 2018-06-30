package com.f1000.exercise.rankjournals.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Journal {

	private final String name;
	private final boolean review;

}
