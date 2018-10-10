package pro.thaipad.todos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import pro.thaipad.todos.objects.Task;
import pro.thaipad.todos.objects.User;
import pro.thaipad.todos.services.TasksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("list")
public class LoginController {

    private static final int WEAK_STRENGTH = 1;
    private static final int FEAR_STRENGTH = 5;
    private static final int STRONG_STRENGTH = 8;

    private static final String WEAK_COLOR = "#FF0000";
    private static final String FEAR_COLOR = "#FF9900";
    private static final String STRONG_COLOR = "#0099CC";

    @Autowired
    private TasksService tasksService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        return new ModelAndView("login", "user", new User());
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.GET)
    public ModelAndView mainAgain() {
        return new ModelAndView("login", "user", new User());
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public ModelAndView checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                                  ModelAndView modelAndView, HttpSession session) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login");
        } else if (!tasksService.registeringUser(user)) {
            modelAndView.setViewName("failed-login");
        } else {
            session.setAttribute("list", tasksService.getAllTask());
            modelAndView.setViewName("redirect:/redir-to-tasks");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/redir-to-tasks", method = RequestMethod.GET)
    public ModelAndView mainAgain(HttpServletRequest request) {
        List<Task> list = (ArrayList<Task>) request.getSession().getAttribute("list");
        return new ModelAndView("tasks", "list", list);
    }

    @RequestMapping(value = "/failed-login", method = RequestMethod.GET)
    public ModelAndView loginAgain() {
        return new ModelAndView("failed-login", "user", new User());
    }

    @RequestMapping(value = "/check-strength", method = RequestMethod.GET, produces = {"text/html; charset=UTF-8"})
    @ResponseBody
    public String checkStrength(@RequestParam String password) {
        String result = "<span style=\"color:%s;\">%s</span>";
        if (password.length() < FEAR_STRENGTH ) {
            return String.format(result, WEAK_COLOR, "weak");
        } else if (password.length()>= FEAR_STRENGTH && password.length() < STRONG_STRENGTH) {
            return String.format(result, FEAR_COLOR, "fear");
        } else {
            return String.format(result, STRONG_COLOR, "strong");
        }
    }


    @RequestMapping(value = "/get-json-tasks", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Task> getJsonTasks(@RequestParam("email") String email) {
        logger.info(email);
        User user = tasksService.findUserByEmail(email);
        if (user == null) {
            return new ArrayList<>();
        }
        return tasksService.getAllTask();
    }

}