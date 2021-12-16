package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
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
            //序列化注册结果对象为json，传输json
            jsonSerialize(info, response);
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
        //调用service，返回注册结果
        boolean flag = service.register(user);
        //创建结果对象
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        if (!flag) {
            //注册失败操作，封装结果对象
            info.setErrorMsg("注册失败");
        }
        //序列化注册结果对象为json，传输json
        jsonSerialize(info, response);
    }

    public void activate(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        //地址栏接收到的激活码
        String code = request.getParameter("code");
        if (code == null || code.length() == 0) {
            return;
        }
        //查询是否存在具有此激活码的用户，有则激活
        UserService service = new UserServiceImpl();
        boolean activated = service.activate(code);
        //激活提示
        String msg;
        if (activated) {
            msg = "激活成功，请<a href='login.html'>登录</a>";
        } else {
            msg = "激活失败";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        //调用service，返回登录结果
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
            request.getSession().setAttribute("user", user);
            info.setFlag(true);
        }
        jsonSerialize(info, response);
    }

    public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user = request.getSession().getAttribute("user");
        jsonSerialize(user, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}
