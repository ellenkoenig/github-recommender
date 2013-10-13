package org.bgcokehack.github_recommender.recommender;

import java.util.List;
import java.util.Set;

import org.bgcokehack.github_recommender.controller.Repository;

public interface Recommender {
	public List<Repository> recommend(Set<String> userInterest, Set<Repository> recoCandidates, int numRecos);
}
