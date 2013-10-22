package org.bgcokehack.github_recommender.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bgcokehack.github_recommender.api_access.GitHubApiService;
import org.bgcokehack.github_recommender.api_access.XingApi;
import org.bgcokehack.github_recommender.api_access.XingApiService;
import org.bgcokehack.github_recommender.model.Repository;
import org.bgcokehack.github_recommender.recommender.FullDescriptionBasedRecommender;
import org.bgcokehack.github_recommender.recommender.Recommender;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
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
	GitHubApiService gitHubApiService;
	
	@RequestMapping("/start")
	public ModelAndView startPage(Model model) {
		OAuthService service = new ServiceBuilder()
		.provider(XingApi.class)
		.apiKey("0d640e73bb4266f3b284")
		.apiSecret("44d8402996c9106f8c656a269bf1c49bcc359e55").build();

		Token requestToken = service.getRequestToken();

		model.addAttribute("requestToken", requestToken);
		model.addAttribute("authUrl", service.getAuthorizationUrl(requestToken));

		return new ModelAndView("startpage", "command", new UserCommand());
	}

	@RequestMapping(value = "/recommendations", method = RequestMethod.POST)
	public ModelAndView getGitHubDescription(Model model,
			@ModelAttribute("verifierValue") UserCommand user,
			@ModelAttribute("requestToken") Token requestToken) {

		Set<String> userInterests = preProcessUserInterests(user, requestToken);
		Set<Repository> repos = gitHubApiService.FetchRepoData();

		Recommender recommender = new FullDescriptionBasedRecommender();
		List<Repository> recommendations = recommender.recommend(userInterests, repos, 5);

		return new ModelAndView("recommendations", "recommendations",recommendations);
	}

	private Set<String> preProcessUserInterests(UserCommand user, Token requestToken) {
		String allInterests = "";
		if(!user.getInterests().isEmpty()){
			allInterests += user.getInterests();
		}
		if(!user.getVerifierCode().isEmpty()) {
			allInterests += xingApiService.fetchInterests(requestToken, user);
		}
		return new HashSet<String>(Arrays.asList(allInterests.split(",")));
	}

}