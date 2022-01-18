package com.example.demo.src.point;

import com.example.demo.src.magazine.model.GetMagazineRes;
import com.example.demo.src.point.model.GetPointHistoryRes;
import com.example.demo.src.point.model.GetPointRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PointDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetPointRes getPoint(int user_id){
        String getPointQuery = "select point from USER where user_id=?";
        int getPointParam = user_id;
        return this.jdbcTemplate.queryForObject(getPointQuery,
                (rs, rowNum) -> new GetPointRes(
                        rs.getInt("point")),
                getPointParam);
    }

    public List<GetPointHistoryRes> getPontHistory(int user_id){
        String getPointHistoryQuery = "select point_id, point, type, status, created_at from POINT_HISTORY where user_id = ?";
        int getPointHistoryParam = user_id;
        return this.jdbcTemplate.query(getPointHistoryQuery,
                (rs,rowNum) ->new GetPointHistoryRes(
                        rs.getInt("point_id"),
                        rs.getInt("point"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        rs.getTimestamp("created_at")),
                getPointHistoryParam);
    }
    public List<GetPointHistoryRes> getPontHistoryByType(int user_id, String type){
        String getPointHistoryQuery = "select point_id, point, type, status, created_at from POINT_HISTORY where user_id = ? and type = ?and status=1";
        Object[] getPointHistoryParam = new Object[]{user_id, type} ;
        return this.jdbcTemplate.query(getPointHistoryQuery,
                (rs,rowNum) ->new GetPointHistoryRes(
                        rs.getInt("point_id"),
                        rs.getInt("point"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        rs.getTimestamp("created_at")),
                getPointHistoryParam);
    }
    public List<GetPointHistoryRes> getCancelPontHistory(int user_id){
        String getPointHistoryQuery = "select point_id, point, type, status, created_at from POINT_HISTORY where user_id = ? and status=0 ";
        int getPointHistoryParam = user_id;
        return this.jdbcTemplate.query(getPointHistoryQuery,
                (rs,rowNum) ->new GetPointHistoryRes(
                        rs.getInt("point_id"),
                        rs.getInt("point"),
                        rs.getString("type"),
                        rs.getInt("status"),
                        rs.getTimestamp("created_at")),
                getPointHistoryParam);
    }


}
