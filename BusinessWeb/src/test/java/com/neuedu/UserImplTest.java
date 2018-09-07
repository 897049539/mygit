package com.neuedu;

import com.neuedu.common.JDBCUtils;
import com.neuedu.common.MD5Utils;
import com.neuedu.dao.IUserDao;
import com.neuedu.dao.impl.UserDaoImpl;
import com.neuedu.po.UserInfo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserImplTest {

    IUserDao userDao;

    @Before
    public void before(){
        userDao = new UserDaoImpl();
    }

    @Test
    public void testLogin(){
       UserInfo us = userDao.login("admin","123456");
       System.out.println(us.toString());
    }
    @Test
    public void testToken(){
        int rs = userDao.updateTokenById(1,"xxx");
        System.out.println(rs);
    }

    @Test
    public void registerTest(){
        String password = "123";
        password = MD5Utils.GetMD5Code(password);
        userDao.Register("战士",password,"1458784","荷 你好","不胜利毋宁死",0,"878455@qq.com");
    }

    @Test
    public void findquestion(){
       String  question =  userDao.findQuestionByUser("程逸飞");
        System.out.println(question);
    }

    @Test
    public void checkanswer(){
        int x = userDao.checkAnswer("程逸飞","忽如一夜春风来","千树万树梨花开");
        System.out.println(x);
    }
}
