package it.dstech.consultazionelibrispring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import it.dstech.consultazionelibrispring.models.Libro;
import it.dstech.consultazionelibrispring.models.User;
import it.dstech.consultazionelibrispring.service.LibroService;
import it.dstech.consultazionelibrispring.service.UserService;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private LibroService libroService;

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping(value="/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("home");
        return modelAndView;
    }
    
    @GetMapping("/addLibro")
	public ModelAndView addLibro(Libro libro) {
    	ModelAndView model = new ModelAndView();
    	model.setViewName("add-libro");
    	model.addObject("libro", new Libro());
		return model;
	}

	@PostMapping("/aggiungiLibro")
	public String salvaLibro(Libro libro, BindingResult result, Model model) {

		libroService.addLibro(libro);
		model.addAttribute("listaLibro", libroService.findAllLibri());
		model.addAttribute("libro", new Libro());
		return "add-libro";
	}
	
	 @GetMapping("/vediLibri")
		public ModelAndView vediLibri() {
	    	ModelAndView model = new ModelAndView();
	    	model.setViewName("lista-libri");
	    	model.addObject("listaLibro",  libroService.findAllLibri());
			return model;
		}


}
