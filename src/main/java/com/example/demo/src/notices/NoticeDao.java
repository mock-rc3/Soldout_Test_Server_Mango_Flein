package com.example.demo.src.notices;

import com.example.demo.src.notices.model.PostNoticeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class NoticeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createNotice(PostNoticeReq postNoticeReq){
        String createSeriesQuery = "INSERT INTO NOTICE(user_id, product_id) VALUES(?,?)";
        Object[] createSeriesParams = new Object[]{postNoticeReq.getUser_id(),postNoticeReq.getProduct_id()};
        this.jdbcTemplate.update(createSeriesQuery, createSeriesParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

}
