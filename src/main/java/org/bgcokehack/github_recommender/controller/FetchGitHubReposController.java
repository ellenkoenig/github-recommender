package org.bgcokehack.github_recommender.controller;

import org.bgcokehack.github_recommender.api_access.GitHubApiService;
import org.bgcokehack.github_recommender.data_storage.GitHubMetaDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FetchGitHubReposController {
	
	@Autowired
	GitHubMetaDataDAO dao;
	
	@Autowired
	GitHubApiService gitHubApiService;
	
	@RequestMapping("/initialFetch")
	public ModelAndView initialFetch(Model model) {
		dao.deleteAll();
		dao.saveRepositories(gitHubApiService.FetchRepoData());
		return new ModelAndView("fetching_successful", "repos", dao.fetchAllRepositories());
	}

}
