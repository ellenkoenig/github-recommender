package org.bgcokehack.github_recommender.controller;

public class RepositoryUserScore implements Comparable<RepositoryUserScore> {
	private Repository r;
	private float score;

	public RepositoryUserScore(Repository repository, float overlap) {
		this.r = repository;
		this.score = overlap;
	}

	@Override
	public int compareTo(RepositoryUserScore o) {
		return Float.compare(this.getScore(), o.getScore());
	}

	public float getScore() {
		return score;
	}

	public Repository getRepository() {
		return r;
	}

}
