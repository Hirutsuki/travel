package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//@WebServlet(name = "LoginServlet",value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //接收ajax传输的数据
        Map<String, String[]> map = request.getParameterMap();
        //创建bean对象
        User user = new User();
        try {
            //数据封装到bean对象中
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service
        UserService service = new UserServiceImpl();
        //返回登录结果
        user = service.login(user);
        //创建结果对象
        ResultInfo info = new ResultInfo();
        if (user == null) {
            //登录失败操作，封装结果对象
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        } else if (!"Y".equals(user.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("请激活账户");
        } else {
            request.getSession().setAttribute("user",user);
            info.setFlag(true);
        }
        response.setContentType("application/json;charset=utf-8");
        String json = new ObjectMapper().writeValueAsString(info);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
