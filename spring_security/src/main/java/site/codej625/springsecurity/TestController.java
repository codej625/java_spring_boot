package site.codej625.springsecurity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/")
	public String main() {

		return "/test";
	}

	@GetMapping("/dev/test")
	public String dev() {

		return "/test";
	}

}
