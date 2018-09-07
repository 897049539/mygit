package com.neuedu.filter;

import com.neuedu.po.UserInfo;
import com.neuedu.string_const.Const;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 用来保护后台页面 不登陆就跳转到登录页面
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest _request = (HttpServletRequest) request;
        HttpServletResponse _response = (HttpServletResponse) response;
        HttpSession httpSession = _request.getSession();
        Object object = httpSession.getAttribute(Const.CURRENT_USER);
        if(object!=null && object instanceof UserInfo){
            chain.doFilter(request,response);
        }else{
            _response.sendRedirect("http://localhost:8080/BusinessWeb/login.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
