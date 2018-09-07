package com.neuedu.po;

import java.io.Serializable;

public class UserInfo implements Serializable{
    private int id;
    private String username;
    private String password;
    private String email;
    private String question;
    private String answer;
    private int role; //0-管理员 1-普通用户
    private String create_time;
    private String update_time;
    private String phone;
    public UserInfo(){}

    public UserInfo(int id, String username, String password, String phone,String email, String question, String answer, int role, String create_time, String update_time) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.role = role;
        this.create_time = create_time;
        this.update_time = update_time;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", role=" + role +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
