package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDAO {
    Favorite favoriteQuery(int rid, int uid);

    int countQuery(int rid);

    void addFavorite(int rid, int uid);
}
