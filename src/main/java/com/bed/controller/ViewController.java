package com.bed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ViewController {

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        //如果当前session中有msg属性的话，直接前端页面插入一段提示脚本
        if(request.getSession().getAttribute("msg") != null){
            PrintWriter writer = response.getWriter();
            writer.println(request.getSession().getAttribute("msg"));
            writer.close();
            //移除msg属性
            request.getSession().removeAttribute("msg");
        }
        return "index";
    }

    @RequestMapping("/home")
    public ModelAndView home(HttpServletRequest request){
        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("account",session.getAttribute("account"));

        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
}
