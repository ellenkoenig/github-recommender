package org.bgcokehack.github_recommender.recommender;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class FullDescriptionBasedRecommenderTest {

    @Test
    public void testOverLapFuntion() {

        Set<Pattern> compiledUserRegexes = new HashSet<Pattern>();
        compiledUserRegexes.add(Pattern.compile(FullDescriptionBasedRecommender.getWholeWordRegex("java")));

        // test
        float overLap = FullDescriptionBasedRecommender.overLap(compiledUserRegexes, "I contain javascript");
        Assert.assertEquals(0, overLap, 0);

        compiledUserRegexes.add(Pattern.compile(FullDescriptionBasedRecommender.getWholeWordRegex("javascript")));

        overLap = FullDescriptionBasedRecommender.overLap(compiledUserRegexes, "I contain javascript");
        Assert.assertEquals(50, overLap, 0);

    }

}
