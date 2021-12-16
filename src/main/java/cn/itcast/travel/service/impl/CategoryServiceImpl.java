package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDAO;
import cn.itcast.travel.dao.impl.CategoryDAOImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO dao = new CategoryDAOImpl();

    @Override
    public List<Category> queryAll() {

        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> tuples = jedis.zrangeWithScores("category", 0, -1);
        List<Category> categoryList = null;
        if (tuples == null || tuples.size() == 0) {
            categoryList = dao.queryAll();
            for (Category category : categoryList) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        }
        categoryList = new ArrayList<Category>();
        for (Tuple t : tuples) {
            Category category = new Category();
            category.setCname(t.getElement());
            category.setCid((int) t.getScore());
            categoryList.add(category);
        }
        return categoryList;
    }
}
