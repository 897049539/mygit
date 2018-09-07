package com.neuedu.controller;

import com.google.gson.Gson;
import com.neuedu.common.IpUtils;
import com.neuedu.common.MD5Utils;
import com.neuedu.exception.BusinessException;
import com.neuedu.po.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;
import com.neuedu.string_const.Const;
import sun.security.provider.MD5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet  extends HttpServlet {
    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        resp.setContentType("text/html;charset=utf-8");
        String operation = req.getParameter("operation");
        if (operation == null || operation.equals("")) {
            throw BusinessException.createException(httpSession, "operation的参数为空", "3S后跳转到登陆页面", "login.jsp");
        }
        if (operation.equals("1")) {   //登陆操作
            login(req, resp);
        } else if (operation.equals("2")) {//注册
            register(req, resp);
        } else if (operation.equals("3")) { //修改密码 根据username
            findQuestionByUsername(req,resp);
        } else if (operation.equals("4")) { //获取用户信息

        } else if (operation.equals("5")) { //登陆状态下重置密码

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void register(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");// id,username,password,email,question,answer,role
        String email = req.getParameter("email");
        String question = req.getParameter("question");
        String answer = req.getParameter("answer");
        int role = 0;
        String phone = req.getParameter("phone");
        if (username == null || username.equals("")) {
            throw BusinessException.createException(httpSession, "用户名不能为空", "三秒后跳转到登陆页面", "login.jsp");
        }
        if (password == null || password.equals("")) {
            throw BusinessException.createException(httpSession, "密码不能为空", "三秒后跳转到登陆页面", "login.jsp");
        }
        if (email == null || email.equals("")) {
            throw BusinessException.createException(httpSession, "邮箱不能为空", "三秒后跳转到登陆页面", "login.jsp");
        }
        if (phone == null || phone.equals("")) {
            throw BusinessException.createException(httpSession, "电话不能为空", "三秒后跳转到登陆页面", "login.jsp");
        }
        if (question == null || question.equals("")) {
            throw BusinessException.createException(httpSession, "问题不能为空", "三秒后跳转到登陆页面", "login.jsp");
        }
        if (answer == null || answer.equals("")) {
            throw BusinessException.createException(httpSession, "答案不能为空", "三秒后跳转到登陆页面", "login.jsp");
        }

        UserInfo userInfo1 = userService.findUserByUsername(username);
        UserInfo userInfo2 = userService.findUserByUserEmail(email);
        if (userInfo1.getUsername() != null) {
            throw BusinessException.createException(httpSession, "用户名重复", "三秒后跳转到登陆页面", "login.jsp");
        }
        if (userInfo2.getEmail() != null) {
            throw BusinessException.createException(httpSession, "邮箱重复", "三秒后跳转到登陆页面", "login.jsp");
        }


            //插入这个用户
            password = MD5Utils.GetMD5Code(password);
            int rs = userService.Register(username, password, phone, question, answer, role, email);
            System.out.println("成功插入" + rs + "条数据");
        if(rs>0){
            throw BusinessException.createException(httpSession, "注册成功 即将跳转到登陆页面", "三秒后跳转到登陆页面", "login.jsp");
        }else{
            throw BusinessException.createException(httpSession, "注册失败 即将跳转到注册页面", "三秒后跳转到注册页面", "register.jsp");
        }

    }



    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession httpSession = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // password = MD5Utils.GetMD5Code(password);
        if (username == null || username.equals("") || password == null || password.equals("")) {
            throw BusinessException.createException(httpSession,"用户名和密码不能为空","三秒后跳转到登陆页面","login.jsp");
        }
        UserInfo userInfo = userService.login(username, password);
        System.out.println(userInfo.toString());
        if (userInfo.getUsername() != null){
            //查询成功 cookie实现自动登陆
            /*Cookie username_cookie = new Cookie(Const.USERNAME_COOKIE,username);
            Cookie password_cookie  = new Cookie(Const.PASSWORD_COOKIE,password);
            username_cookie.setMaxAge(60*60*24*7);//一周免登录
            password_cookie.setMaxAge(60*60*24*7);
            resp.addCookie(username_cookie);
            resp.addCookie(password_cookie);*/
            String ip = IpUtils.getRemoteAddress(req);
            String macAddress = IpUtils.getMACAddress(ip);
            String token = MD5Utils.GetMD5Code(macAddress);
            Cookie cookie= new Cookie(Const.AUTOLOGIN_TOKEN,token);
            cookie.setMaxAge(60*60*24*7);
            resp.addCookie(cookie);
            //token保存到数据库
            userService.updateTokenById(token,userInfo.getId());
            Gson gson = new Gson();
            String json = gson.toJson(userInfo);
            httpSession.setAttribute(Const.CURRENT_USER,userInfo);
            //   httpSession.setAttribute(Const.AUTOLOGIN_TOKEN,token);
            PrintWriter printWriter = resp.getWriter();
            printWriter.print(json);

            req.getRequestDispatcher("manage/home.jsp").forward(req,resp);
        }else{
            System.out.println("用户名密码错误");
            //用户名或密码错误
        }
    }

    public void findQuestionByUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        HttpSession session = req.getSession();
        if(username==null || username.equals("")){
            throw BusinessException.createException(session,"用户名不能为空哦","即将跳转","findPassword.jsp");
        }
        IUserService userService = new UserServiceImpl();
        String question = userService.findQuestionByUser(session,username);
        session.setAttribute("question",question);
        req.getRequestDispatcher("answer.jsp").forward(req,resp) ;

    }

}
