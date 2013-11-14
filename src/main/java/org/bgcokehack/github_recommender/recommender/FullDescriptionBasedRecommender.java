package org.bgcokehack.github_recommender.recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bgcokehack.github_recommender.model.Repository;
import org.bgcokehack.github_recommender.model.RepositoryUserScore;

public class FullDescriptionBasedRecommender implements Recommender {

    @Override
    public List<RepositoryUserScore> recommend(final Set<String> userInterest, final Set<Repository> recoCandidates,
            final int numRecos) {
        float smallestOverlap = Float.MAX_VALUE;
        List<RepositoryUserScore> recos = new ArrayList<RepositoryUserScore>();
        Set<Pattern> compiledUserRegex = new HashSet<Pattern>();
        for (String interest : userInterest) {
            compiledUserRegex.add(Pattern.compile(getWholeWordRegex(interest)));
        }

        for (Repository repository : recoCandidates) {
            float overlap = overLap(compiledUserRegex,
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

    static String getWholeWordRegex(final String interest) {
        return "\\b" + interest.toLowerCase() + "\\b";
    }

    static float overLap(final Set<Pattern> compiledUserRegexes, final String repositoryDetails) {
        int overlap = 0;
        Matcher regexMatcher;
        for (Pattern regex : compiledUserRegexes) {
            if (regex.matcher(repositoryDetails).find()) {
                overlap += 1;
            }

        }

        return overlap * 100f / compiledUserRegexes.size();
    }

}
