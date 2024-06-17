package sg.edu.nus.javawebca.interceptor;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//public class SecurityInterceptor implements HandlerInterceptor {
//    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws IOException {
//        LOGGER.info("Intercepting: " + request.getRequestURI());
//        HttpSession session = request.getSession();
//        String username = (String) session.getAttribute("username");
//        // 排除 /error 路径
//        if (request.getRequestURI().equals("/error")) {
//            return true;
//        }
//
//        if (username == null) {
//            // No username, meaning not logged in yet
//            // Redirect to login page
//            response.sendRedirect("/login");
//            return false;
//        }
//        // Have logged-in, forward to Controller
//        return true;
//    }
//}