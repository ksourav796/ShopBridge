package com.hyva;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {



	@RequestMapping("/home")
	public String home(@CookieValue(value = "accessToken", required = false) String accessToken, Model model) throws Exception {
			if (accessToken!=null) {
				model.addAttribute("pageName", "login");
			}
			else {
				model.addAttribute("pageName", "dashboard");
			}

		return "index";
	}
}
