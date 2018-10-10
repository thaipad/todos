package pro.thaipad.todos.interceptors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pro.thaipad.todos.objects.User;
import pro.thaipad.todos.services.TasksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckUserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TasksService tasksService;
    private static final Logger logger = Logger.getLogger(CheckUserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        long time = System.currentTimeMillis() - Long.valueOf(request.getAttribute("startTime").toString());
        logger.info("time of controller executing (mls) = " + time);
    }
}
