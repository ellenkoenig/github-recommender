package org.bgcokehack.github_recommender.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bgcokehack.github_recommender.controller.Repository;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class GitHubApiService {
	
	CloseableHttpClient httpclient = HttpClients.createDefault();
	String gitHubAuthCode;

	public GitHubApiService() {
		this.gitHubAuthCode = System.getenv().get("GITOAUTHTOKEN");
	}
	
	public Set<Repository> FetchRepoData() {

		String readmeUrlSuffix = "/readme";

		Set<Repository> repositories = new HashSet<Repository>();

		for (String repoUrl :  fetchRepoUrls()) {
			Repository repo = new Repository(repoUrl.replaceFirst("https://api.github.com/repos/", ""));
			HttpGet httpGetDescription = new HttpGet(repoUrl);			
			HttpGet httpGetReadme = new HttpGet(repoUrl + readmeUrlSuffix);

			if(gitHubAuthCode != null && !gitHubAuthCode.isEmpty()) {
				httpGetDescription.addHeader("Authorization", "token " + gitHubAuthCode);
				httpGetReadme.addHeader("Authorization", "token " + gitHubAuthCode);
			}
			CloseableHttpResponse repositoryInformation = null;
			try {
				String description = fetchRepositoryMetaData(httpGetDescription).get("description");
				repo.setDescription(description != null? description : "");

				Map<String, String> resultReadme = fetchRepositoryMetaData(httpGetReadme);
				if(resultReadme == null || resultReadme.get("content") == null) {
					repo.setReadme("");
				} else {
					repo.setReadme(processReadme(resultReadme.get("content")));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (repositoryInformation != null) {
						repositoryInformation.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			repositories.add(repo);
		}
		return repositories;
	}


	private List<String> fetchRepoUrls() {

		List<String> repos = new ArrayList<String>();
		HttpGet httpGetRepositories = new HttpGet("https://api.github.com/repositories");

		CloseableHttpResponse repositoryInformation = null;
		try {
			repositoryInformation = httpclient.execute(httpGetRepositories);
			HttpEntity entityRepos = repositoryInformation
					.getEntity();
			if (entityRepos != null) {
				@SuppressWarnings("unchecked")
				List<Map<String, String>> parsedRepos = new ObjectMapper()
				.readValue(EntityUtils.toString(entityRepos),
						List.class);
				for(Map<String, String> rawRepoData: parsedRepos) {
					repos.add(rawRepoData.get("url"));
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (repositoryInformation != null) {
					repositoryInformation.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return repos;
	}

	private Map<String, String> fetchRepositoryMetaData(HttpGet httpGetUrl) 
			throws IOException, ClientProtocolException, JsonParseException, JsonMappingException {
		CloseableHttpResponse repositoryInformation;
		repositoryInformation = httpclient.execute(httpGetUrl);
		HttpEntity entity = repositoryInformation
				.getEntity();
		if (entity != null) {
			@SuppressWarnings("unchecked")
			Map<String, String> resultProfile = new ObjectMapper().readValue(EntityUtils.toString(entity), HashMap.class);
			return resultProfile;	
		}
		return new HashMap<String, String>();
	}

	private String processReadme(String rawReadme) {
		String readme = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(rawReadme));
		return (readme.length() > 1000) ? readme.substring(0, 1000): readme;
	}

}
