package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.*;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDAO routeDAO = new RouteDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    private RouteImgDAO routeImgDAO = new RouteImgDAOImpl();
    private SellerDAO sellerDAO = new SellerDAOImpl();
    private FavoriteDAO favoriteDAO = new FavoriteDAOImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int entryPerPage, String rname) {
        PageBean<Route> bean = new PageBean<Route>();
        bean.setCurrentPage(currentPage);
        bean.setEntryPerPage(entryPerPage);
        int totalEntry = routeDAO.entryCount(cid, rname);
        bean.setTotalEntry(totalEntry);
        int totalPage = (totalEntry % entryPerPage) == 0 ? (totalEntry / entryPerPage) : (totalEntry / entryPerPage) + 1;
        bean.setTotalPage(totalPage);
        int offset = entryPerPage * (currentPage - 1);
        List<Route> routes = routeDAO.pageContentQuery(cid, offset, entryPerPage, rname);
        bean.setPageContent(routes);
        return bean;
    }

    @Override
    public Route routeQuery(String ridStr) {
        int rid = 0;
        if (ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)) {
            rid = Integer.parseInt(ridStr);
        }
        Route route = routeDAO.routeQuery(rid);

        Category category = categoryDAO.categoryQuery(route.getCid());
        route.setCategory(category);

        List<RouteImg> routeImgs = routeImgDAO.imgQuery(route.getRid());
        route.setRouteImgList(routeImgs);

        Seller seller = sellerDAO.sellerQuery(route.getSid());
        route.setSeller(seller);

        int count=favoriteDAO.countQuery(rid);
        route.setCount(count);

        return route;
    }

}
