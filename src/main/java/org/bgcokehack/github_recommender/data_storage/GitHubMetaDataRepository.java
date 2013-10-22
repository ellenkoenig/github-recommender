package org.bgcokehack.github_recommender.data_storage;

import org.bgcokehack.github_recommender.model.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GitHubMetaDataRepository extends MongoRepository<Repository, String> {

}
