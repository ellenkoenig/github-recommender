package org.bgcokehack.github_recommender.recommender;

import java.util.List;
import java.util.Set;

import org.bgcokehack.github_recommender.model.Repository;
import org.bgcokehack.github_recommender.model.RepositoryUserScore;

import org.springframework.beans.factory.annotation.Autowired;

public class TagBasedRecommender implements Recommender {

    @Autowired

    // YahooContentanalysisResponseExtractor yahooTagExtractor;
    @Override
    public List<RepositoryUserScore> recommend(final Set<String> userInterest, final Set<Repository> repositories,
            final int numRecos) {

// if(userInterest.size()==0){
// return new ArrayList<Repository>();
// }
// int numoverlap;
// for (Repository repository : repositories) {
// List<String> tags = repository.getTags();
// tags.retainAll(userInterest);
// numoverlap=tags.size()*100f/repository.getTags().size();
// }
// int numInterests = userInterest.size();
// //intersect
// userInterest.retainAll(repositoryInterests);
// return userInterest.size()*100/numInterests;
//
        return null;
    }

}
