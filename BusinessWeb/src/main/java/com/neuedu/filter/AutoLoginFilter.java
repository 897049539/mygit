package com.neuedu.filter;

import com.neuedu.po.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;
import com.neuedu.string_const.Const;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/manage/*")
public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hrep = (HttpServletResponse) response;

        HttpSession  httpSession = hreq.getSession();
        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Const.CURRENT_USER);
        if(userInfo!=null){
            chain.doFilter(request, response);
            return ;
        }

        Cookie cookies[] = ((HttpServletRequest) request).getCookies();
        String token = null;
        if(cookies!=null){
            for(Cookie cookie : cookies){
               String cookieName = cookie.getName();
               if(cookieName.equals(Const.AUTOLOGIN_TOKEN)){
                   token = cookie.getValue();
               }
            }
        }
        if(token!=null){
            IUserService IS = new UserServiceImpl();
            UserInfo userInfo1 = IS.findUserinfoByToken(token);
            if(userInfo1.getUsername()!=null){
                //登陆成功
                HttpSession session = hreq.getSession();
                session.setAttribute(Const.CURRENT_USER,userInfo);
                chain.doFilter(request, response);
                return;
            }
        }
        hrep.sendRedirect("http://localhost:8080/BusinessWeb/login.jsp");
    }

    @Override
    public void destroy() {

    }
}
