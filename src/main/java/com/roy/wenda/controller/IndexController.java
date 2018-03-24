package com.roy.wenda.controller;

import com.roy.wenda.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpSession httpSession) {
        return "Hello World"+httpSession.getAttribute("msg");
    }

    @RequestMapping("/profile/{userId}")
    @ResponseBody
    public String haha(@PathVariable("userId") int userId,
                         @RequestParam("type") int type,
                         @RequestParam(value = "key",defaultValue = "zz",required = true) String key){
        return String.format("Profile Page of %d,type:%d,key:%s",userId,type,key);
    }

    @RequestMapping("/vm")
    public String vm(Model mode) {
        mode.addAttribute("value1","123456");
        List<String> colors = Arrays.asList(new String[] {"red","yello","blue","orange"});
        mode.addAttribute("colors",colors);
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        mode.addAttribute("map",map);
        mode.addAttribute("name",new User("roy"));
        return "home";
    }

    @RequestMapping(value = "/request",method = RequestMethod.GET)
    @ResponseBody
    public String request(Model mode, HttpServletResponse response,
                     HttpServletRequest request,
                     HttpSession httpSession) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> header = request.getHeaderNames();
        while(header.hasMoreElements()){
            String name = header.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURL() + "<br>");
        return sb.toString();
    }

    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code, HttpSession httpSession) {
        httpSession.setAttribute("msg"," jump from redirect "+String.valueOf(code));
        RedirectView red = new RedirectView("/",true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }


}
