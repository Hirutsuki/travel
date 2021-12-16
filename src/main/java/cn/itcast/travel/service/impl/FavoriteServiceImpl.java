package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDAO;
import cn.itcast.travel.dao.impl.FavoriteDAOImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDAO dao = new FavoriteDAOImpl();

    @Override
    public boolean favoriteQuery(int rid, int uid) {
        return dao.favoriteQuery(rid, uid) != null;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        dao.addFavorite(rid, uid);
    }
}
