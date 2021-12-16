package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDAO;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> queryAll() {
        String sql = "select * from tab_category";
        List<Category> categories = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return categories;
    }

    @Override
    public Category categoryQuery(int cid) {
        String sql = "select * from tab_category where cid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class), cid);
    }
}
