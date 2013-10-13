package org.bgcokehack.github_recommender.controller;

public class UserCommand {

	private String interests;
	private String verifierCode;
	private String githubAuthCode;
	
	public UserCommand(){
		this.githubAuthCode = System.getenv().get("GITOAUTHTOKEN");
	}

	public String getVerifierCode() {
		return verifierCode;
	}

	public void setVerifierCode(String verifierCode) {
		this.verifierCode = verifierCode;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getGithubAuthCode() {
			return (githubAuthCode == null ? "" : githubAuthCode);

	}



}
