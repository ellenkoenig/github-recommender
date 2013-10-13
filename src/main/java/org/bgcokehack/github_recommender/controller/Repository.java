package org.bgcokehack.github_recommender.controller;

import java.util.List;

public class Repository {
	private String name;
	private List<String> tags;
	private String description;
	private String readme;

	public Repository(String name) {
		this.name = name;
	}

	@Override
	public String toString() {

		return this.getName() + " " + this.getDescription();
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReadme() {
		return readme;
	}

	public void setReadme(String readme) {
		this.readme = readme;
	}

	public String getName() {
		return name;
	}

}
