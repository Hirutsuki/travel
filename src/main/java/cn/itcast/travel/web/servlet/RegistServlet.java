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

//@WebServlet(name = "RegistServlet",value = "/user/")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取输入验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        //获取生成验证码
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //移除验证码
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equals(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //序列化注册结果对象为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //传输json
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //接收ajax数据
        Map<String, String[]> map = request.getParameterMap();
        //创建bean对象
        User user = new User();
        //BeanUtils填充bean对象
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service
        UserService service = new UserServiceImpl();
        //返回注册结果
        boolean flag = service.register(user);
        //创建结果对象
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        if (!flag) {
            //注册失败操作，封装结果对象
            info.setErrorMsg("注册失败");
        }
        //序列化注册结果对象为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //传输json
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
