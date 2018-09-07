package com.neuedu.service.impl;

import com.neuedu.dao.IUserDao;
import com.neuedu.dao.impl.UserDaoImpl;
import com.neuedu.exception.BusinessException;
import com.neuedu.po.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.string_const.Const;

import javax.servlet.http.HttpSession;
import java.util.UUID;

public class UserServiceImpl implements IUserService {
    IUserDao iUserDao = new UserDaoImpl();

    @Override
    public UserInfo login(String username, String password) {
        return iUserDao.login(username,password);
    }

    @Override
    public int updateTokenById(String token, int id) {
        return iUserDao.updateTokenById(id,token);
    }

    @Override
    public UserInfo findUserByUsername(String username) {
        return iUserDao.findUserByUsername(username);
    }

    @Override
    public UserInfo findUserByUserEmail(String email) {
        return iUserDao.findUserByUserEmail(email);
    }

    @Override
    public int Register(String username, String password, String phone, String question, String answer, int role, String email) {
        return iUserDao.Register(username,password,phone,question,answer,role,email);
    }

    @Override
    public UserInfo findUserinfoByToken(String token) {
        return iUserDao.findUserinfoByToken(token);
    }



    //根据用户名找到问题
    @Override
    public String findQuestionByUser(HttpSession session ,String username){

        UserInfo userInfo = iUserDao.findUserByUsername(username);
        if(userInfo.getUsername()==null){
          throw BusinessException.createException(session,"用户名不存在","即将跳转到找回密码界面","findPassword.jsp");
        }
        return iUserDao.findQuestionByUser(username);
    }

    @Override
    public int checkAnswer(HttpSession session ,String username, String quesiton, String answer) {
        UserInfo userInfo = iUserDao.findUserByUsername(username);
        if(userInfo.getUsername()==null){
            throw BusinessException.createException(session,"用户名不存在","即将跳转到找回密码界面","findPassword.jsp");
        }
        int count = iUserDao.checkAnswer(username,quesiton,answer);
        if(count>0){ //答案正确
            String forget_token = UUID.randomUUID().toString();
        }else{      //答案错误
            return 0;
        }
        return 0;
    }


}
