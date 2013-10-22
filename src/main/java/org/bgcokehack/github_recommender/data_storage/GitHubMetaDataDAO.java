package org.bgcokehack.github_recommender.data_storage;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import org.bgcokehack.github_recommender.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;


@Configuration
@EnableMongoRepositories
public class GitHubMetaDataDAO {

	@Autowired
	private GitHubMetaDataRepository repository;

	@Bean
	Mongo mongo() throws UnknownHostException {
		return new Mongo("localhost");
	}

	@Bean
	MongoTemplate mongoTemplate(Mongo mongo) {
		return new MongoTemplate(mongo, "github-repositories");
	}
	
	public List<Repository> fetchAllRepositories() {
		return repository.findAll();
	}
	
	
	public void saveRepositories(Set<Repository> repos) {
		repository.save(repos);
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
}
