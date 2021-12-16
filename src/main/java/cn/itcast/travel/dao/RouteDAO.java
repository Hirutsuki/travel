package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDAO {
    int entryCount(int cid, String rname);

    List<Route> pageContentQuery(int cid, int start, int entryPerPage, String rname);

    Route routeQuery(int rid);
}
