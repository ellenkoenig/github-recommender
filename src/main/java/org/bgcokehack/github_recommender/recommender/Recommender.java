package org.bgcokehack.github_recommender.recommender;

import java.util.List;
import java.util.Set;

import org.bgcokehack.github_recommender.model.Repository;
import org.bgcokehack.github_recommender.model.RepositoryUserScore;

public interface Recommender {
    List<RepositoryUserScore> recommend(Set<String> userInterest, Set<Repository> recoCandidates, int numRecos);
}
