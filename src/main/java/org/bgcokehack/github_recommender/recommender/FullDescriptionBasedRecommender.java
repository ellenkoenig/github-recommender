package org.bgcokehack.github_recommender.recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bgcokehack.github_recommender.model.Repository;
import org.bgcokehack.github_recommender.model.RepositoryUserScore;

public class FullDescriptionBasedRecommender implements Recommender {

    @Override
    public List<RepositoryUserScore> recommend(final Set<String> userInterest, final Set<Repository> recoCandidates,
            final int numRecos) {
        float smallestOverlap = Float.MAX_VALUE;
        List<RepositoryUserScore> recos = new ArrayList<RepositoryUserScore>();
        for (Repository repository : recoCandidates) {
            float overlap = overLap(userInterest,
                    repository.getDescription().toLowerCase() + repository.getReadme().toLowerCase());
            if (overlap > 0 && recos.size() < numRecos) {
                recos.add(new RepositoryUserScore(repository, overlap));
                if (overlap <= smallestOverlap) {
                    smallestOverlap = overlap;
                }
            } else if (overlap > smallestOverlap) {
                recos.add(new RepositoryUserScore(repository, overlap));
                Collections.sort(recos, Collections.reverseOrder());
                recos.remove(recos.size() - 1);
                smallestOverlap = recos.get(recos.size() - 1).getScore();

            }
        }

        return recos;
    }

    private float overLap(final Set<String> userInterest, final String repositoryDetails) {
        int overlap = 0;
        for (String interest : userInterest) {
            if (repositoryDetails.contains(interest.toLowerCase())) {
                overlap += 1;
            }

        }

        return overlap * 100f / userInterest.size();
    }

}
