package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ActivateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //地址栏接收到的激活码
        String code = request.getParameter("code");
        if (code == null || code.length() == 0) {
            return;
        }
        //激活提示
        String msg=null;
        //查询是否存在具有此激活码的用户，有则激活
        UserService service = new UserServiceImpl();
        boolean activated = service.activate(code);
        if (activated) {
            msg="激活成功，请<a href='login.html'>登录</a>";
        } else {
            msg="激活失败";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
