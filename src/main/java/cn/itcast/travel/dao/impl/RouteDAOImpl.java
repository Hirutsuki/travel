package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDAO;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDAOImpl implements RouteDAO {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int entryCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 = 1";
        StringBuilder builder = new StringBuilder(sql);
        List args=new ArrayList();
        if (cid != 0) {
            builder.append(" and cid = ?");
            args.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            builder.append(" and rname like ?");
            args.add("%"+rname+"%");
        }
        sql= builder.toString();
        return template.queryForObject(sql, Integer.class, args.toArray());
    }

    @Override
    public List<Route> pageContentQuery(int cid, int offset, int entryPerPage, String rname) {
        String sql = "select * from tab_route where 1 = 1";
        StringBuilder builder = new StringBuilder(sql);
        List args=new ArrayList();
        if (cid != 0) {
            builder.append(" and cid = ?");
            args.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            builder.append(" and rname like ?");
            args.add("%"+rname+"%");
        }
        builder.append(" limit ?, ?");
        args.add(offset);
        args.add(entryPerPage);
        sql=builder.toString();
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), args.toArray());
    }

    @Override
    public Route routeQuery(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }

}
