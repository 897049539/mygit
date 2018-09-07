package com.neuedu.dao.impl;

import com.neuedu.common.JDBCUtils;
import com.neuedu.dao.IUserDao;
import com.neuedu.po.UserInfo;
import jdk.nashorn.internal.scripts.JD;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements IUserDao {


    @Override
    public UserInfo login(String username, String password) {
        UserInfo userInfo = new UserInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,username,password,email,question,phone,answer,role from neuedu_user where username=? and password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                int userid =  resultSet.getInt("id");
                String phone = resultSet.getString("phone");
                String _username = resultSet.getString("username");
                String _password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                int role = resultSet.getInt("role");
                userInfo.setId(userid);
                userInfo.setUsername(_username);
                userInfo.setPassword(_password);
                userInfo.setEmail(email);
                userInfo.setQuestion(question);
                userInfo.setAnswer(answer);
                userInfo.setRole(role);
                userInfo.setPhone(phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement,resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

          return userInfo;
    }

    @Override
    public int updateTokenById(int userid, String token) {
        UserInfo userInfo = new UserInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rs = 0;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "update neuedu_user set token = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,token);
            preparedStatement.setInt(2,userid);
            rs = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    //查找用户名是否存在
    @Override
    public UserInfo findUserByUsername(String username) {
        UserInfo userInfo = new UserInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select username from neuedu_user where username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                String _username = resultSet.getString("username");
                userInfo.setUsername(_username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    //查找邮箱是否存在
    @Override
    public UserInfo findUserByUserEmail(String email) {
        UserInfo userInfo = new UserInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select email from neuedu_user where email=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                String _email = resultSet.getString("email");
                userInfo.setEmail(_email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    //注册一个用户
    public int Register(String username,String password,String phone,String question ,String answer,int role,String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rs = 0;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into neuedu_user(username,password,email,phone,question,answer,role) values(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,phone);
            preparedStatement.setString(5,question);
            preparedStatement.setString(6,answer);
            preparedStatement.setInt(7,role);
            rs = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    @Override
    public UserInfo findUserinfoByToken(String token) {
        UserInfo userInfo = new UserInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select * from neuedu_user where token=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                int userid =  resultSet.getInt("id");
                String phone = resultSet.getString("phone");
                String _username = resultSet.getString("username");
                String _password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                int role = resultSet.getInt("role");
                userInfo.setId(userid);
                userInfo.setUsername(_username);
                userInfo.setPassword(_password);
                userInfo.setEmail(email);
                userInfo.setQuestion(question);
                userInfo.setAnswer(answer);
                userInfo.setRole(role);
                userInfo.setPhone(phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    @Override
    public String findQuestionByUser(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String question = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select * from neuedu_user where username=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
                question = resultSet.getString("question");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return question;
    }

    @Override
    public int checkAnswer(String username, String question, String answer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rs = 0;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select count(1) from neuedu_user where username=? and question=? and answer=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,question);
            preparedStatement.setString(3,answer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.first()){
              rs =  resultSet.getInt(1);

              return rs;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JDBCUtils.Close(connection,preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rs;
    }
}
