package org.bgcokehack.github_recommender.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bgcokehack.github_recommender.api_access.XingApiService;
import org.bgcokehack.github_recommender.data_storage.GitHubMetaDataDAO;
import org.bgcokehack.github_recommender.model.Repository;
import org.bgcokehack.github_recommender.model.RepositoryUserScore;
import org.bgcokehack.github_recommender.recommender.FullDescriptionBasedRecommender;
import org.bgcokehack.github_recommender.recommender.Recommender;

import org.scribe.model.Token;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(value = "requestToken", types = Token.class)
public class StartPageController {

    @Autowired
    XingApiService xingApiService;

    @Autowired
    GitHubMetaDataDAO dao;

    @RequestMapping("/start")
    public ModelAndView startPage(final Model model) {

        return new ModelAndView("startpage", "command", new UserCommand());
    }

    @RequestMapping(value = "/recommendations", method = RequestMethod.POST)
    public ModelAndView getGitHubDescription(final Model model,
            @ModelAttribute("verifierValue") final UserCommand user) {

        Set<String> userInterests = preProcessUserInterests(user);
        Set<Repository> repos = new HashSet<>(dao.fetchAllRepositories());

        Recommender recommender = new FullDescriptionBasedRecommender();
        List<RepositoryUserScore> recommendations = recommender.recommend(userInterests, repos, 5);

        return new ModelAndView("recommendations", "recommendations", recommendations);
    }

    private Set<String> preProcessUserInterests(final UserCommand user) {
        String allInterests = "";
        if (!user.getInterests().isEmpty()) {
            allInterests += user.getInterests();
        }

        if (!user.getVerifierCode().isEmpty()) {
            allInterests += xingApiService.fetchInterests(user);
        }

        return new HashSet<String>(Arrays.asList(allInterests.split(",")));
    }

}
