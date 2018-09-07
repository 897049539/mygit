package com.neuedu.dao;

import com.neuedu.po.UserInfo;

import java.sql.SQLException;

public interface IUserDao {
    //登陆
    public UserInfo login(String username, String password);

    //修改token
    public int updateTokenById(int userid,String token);

    //找一个用户是否存在
    public UserInfo findUserByUsername(String username);

    //查找一个邮箱是否已经存在
    public UserInfo findUserByUserEmail(String email);

    //插入一个用户
    public int Register(String username, String password, String phone, String question, String answer, int role, String email);

    //根据token查询用户信息
    public UserInfo findUserinfoByToken(String token);

    //根据用户名找到问题
    public String findQuestionByUser(String username);

    //校验答案
    public int checkAnswer(String username,String quesiton,String answer);
}