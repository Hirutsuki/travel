package cn.itcast.travel.service;

public interface FavoriteService {

    boolean favoriteQuery(int rid, int uid);

    void addFavorite(int rid, int uid);
}
