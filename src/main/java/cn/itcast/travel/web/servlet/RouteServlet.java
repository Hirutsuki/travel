package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class RouteServlet extends BaseServlet {

    private int parse(String parsingString, int defaultValue) {
        if (parsingString != null && parsingString.length() > 0 && !"null".equals(parsingString)) {
            return Integer.parseInt(parsingString);
        } else {
            return defaultValue;
        }
    }

    private RouteService service = new RouteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String entryPerPageStr = request.getParameter("entryPerPage");
        String rname = request.getParameter("rname");
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }
        int cid = parse(cidStr, 0);
        int currentPage = parse(currentPageStr, 1);
        int entryPerPage = parse(entryPerPageStr, 5);
        PageBean<Route> bean = service.pageQuery(cid, currentPage, entryPerPage, rname);
        jsonSerialize(bean, response);
    }

    public void detailQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rid = request.getParameter("rid");
        Route route = service.routeQuery(rid);
        jsonSerialize(route, response);
    }

    FavoriteService favoriteService = new FavoriteServiceImpl();

    public boolean favoriteQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ridStr = request.getParameter("rid");
        int rid = parse(ridStr, 0);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        int uid = user.getUid();
        boolean favorite = favoriteService.favoriteQuery(rid, uid);
        jsonSerialize(favorite, response);
        return favorite;
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ridStr = request.getParameter("rid");
        int rid = parse(ridStr, 0);
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return;
        }
        int uid = user.getUid();
        favoriteService.addFavorite(rid,uid);
    }
}
