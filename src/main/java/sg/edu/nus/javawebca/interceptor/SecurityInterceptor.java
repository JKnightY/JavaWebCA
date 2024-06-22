package sg.edu.nus.javawebca.interceptor;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("Intercepting: " + request.getRequestURI());

        HttpSession session = request.getSession();

        // Perform security check
        if (session.getAttribute("user") == null) {
            // Redirect to login page if user is not logged in
            response.sendRedirect("/login");
            LOGGER.warn("Unauthorized access attempt to: " + request.getRequestURI());
            return false;
        }

        LOGGER.info("SecurityInterceptor preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        LOGGER.info("SecurityInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            LOGGER.error("Request resulted in exception: ", ex);
        }
        LOGGER.info("SecurityInterceptor afterCompletion");
    }
}