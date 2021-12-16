package cn.itcast.travel.service;

import cn.itcast.travel.domain.*;

import java.util.List;

public interface RouteService {
    PageBean<Route> pageQuery(int cid, int currentPage, int entryPerPage, String rname);

    Route routeQuery(String rid);
}
