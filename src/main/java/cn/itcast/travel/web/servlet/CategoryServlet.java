package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

//@WebServlet(name = "CategoryServlet",value = "/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService service = new CategoryServiceImpl();

    public void queryAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> categories = service.queryAll();
        jsonSerialize(categories, response);
    }
}
