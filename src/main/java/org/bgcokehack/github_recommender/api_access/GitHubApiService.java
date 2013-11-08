package org.bgcokehack.github_recommender.api_access;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.bgcokehack.github_recommender.model.Repository;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.stereotype.Component;

@Component
public class GitHubApiService {

    CloseableHttpClient httpclient = HttpClients.createDefault();
    String gitHubAuthCode;
    String repoListUrl;

    public GitHubApiService() {
        this.gitHubAuthCode = System.getenv().get("GITOAUTHTOKEN");

    }

    public Set<Repository> fetchRepoDataBatch() {

        String readmeUrlSuffix = "/readme";
        this.repoListUrl = "https://api.github.com/repositories";

        Set<Repository> repositories = new HashSet<Repository>();

        for (String repoUrl : fetchRepoUrls()) {
            Repository repo = new Repository(repoUrl.replaceFirst("https://api.github.com/repos/", ""));
            HttpGet httpGetDescription = new HttpGet(repoUrl);
            HttpGet httpGetReadme = new HttpGet(repoUrl + readmeUrlSuffix);

            if (gitHubAuthCode != null && !gitHubAuthCode.isEmpty()) {
                httpGetDescription.addHeader("Authorization", "token " + gitHubAuthCode);
                httpGetReadme.addHeader("Authorization", "token " + gitHubAuthCode);
            }

            CloseableHttpResponse repositoryInformation = null;
            try {
                String description = fetchRepositoryMetaData(httpGetDescription).get("description");
                repo.setDescription(description != null ? description : "");

                Map<String, String> resultReadme = fetchRepositoryMetaData(httpGetReadme);
                if (resultReadme == null || resultReadme.get("content") == null) {
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

    public boolean weHaveMoreData() {
        return !this.repoListUrl.isEmpty();
    }

    private List<String> fetchRepoUrls() {

        List<String> repos = new ArrayList<String>();
        HttpGet httpGetRepositories = new HttpGet(this.repoListUrl);
        if (gitHubAuthCode != null && !gitHubAuthCode.isEmpty()) {
            httpGetRepositories.addHeader("Authorization", "token " + gitHubAuthCode);
        }

        CloseableHttpResponse repositoryInformation = null;
        try {
            repositoryInformation = httpclient.execute(httpGetRepositories);

            Header linkHeader = repositoryInformation.getFirstHeader("link");
            if (linkHeader != null) {
                String[] paginationUrls = linkHeader.getValue().split(",");
                boolean newUrlFound = false;
                for (String url : paginationUrls) {
                    if (url.contains("next")) {
                        this.repoListUrl = url.split(">")[0].substring(1);
                        newUrlFound = true;
                    }
                }

                if (!newUrlFound) {
                    this.repoListUrl = "";
                }

                HttpEntity entityRepos = repositoryInformation.getEntity();
                if (entityRepos != null) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, String>> parsedRepos =
                        new ObjectMapper().readValue(EntityUtils.toString(entityRepos), List.class);
                    for (Map<String, String> rawRepoData : parsedRepos) {
                        repos.add(rawRepoData.get("url"));
                    }
                }
            } else {
                this.repoListUrl = "";
                return new ArrayList<String>();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<String>();
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

    private Map<String, String> fetchRepositoryMetaData(final HttpGet httpGetRequest) throws IOException,
        ClientProtocolException, JsonParseException, JsonMappingException {
        CloseableHttpResponse repositoryInformation;
        repositoryInformation = httpclient.execute(httpGetRequest);

        HttpEntity entity = repositoryInformation.getEntity();
        if (entity != null) {
            @SuppressWarnings("unchecked")
            Map<String, String> resultProfile =
                new ObjectMapper().readValue(EntityUtils.toString(entity), HashMap.class);
            return resultProfile;
        }

        return new HashMap<String, String>();
    }

    private String processReadme(final String rawReadme) {
        String readme = new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(rawReadme));
        return (readme.length() > 1000) ? readme.substring(0, 1000) : readme;
    }

}
