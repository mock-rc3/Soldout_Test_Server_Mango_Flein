package com.example.demo.src.magazine;

import com.example.demo.src.magazine.model.GetMagazineDetailRes;
import com.example.demo.src.magazine.model.GetMagazineRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class MagazineDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetMagazineRes> getAllMagazines(){
        String getMagazineQuery = "select magazine_id, type, title, image_url, created_at from MAGAZINE where status = 1";
        return this.jdbcTemplate.query(getMagazineQuery,
                (rs,rowNum) ->new GetMagazineRes(
                        rs.getInt("magazine_id"),
                        rs.getString("type"),
                        rs.getString("title"),
                        rs.getString("image_url"),
                        rs.getTimestamp("created_at")));
    }

    public List<GetMagazineRes> getMagazinesByType(String type){
        String getAddressQuery = "select magazine_id, type, title, image_url, created_at from MAGAZINE where type = ? and status = 1";
        String getMagazineParam = type;
        return this.jdbcTemplate.query(getAddressQuery,
                (rs,rowNum) ->new GetMagazineRes(
                        rs.getInt("magazine_id"),
                        rs.getString("type"),
                        rs.getString("title"),
                        rs.getString("image_url"),
                        rs.getTimestamp("created_at")),
                getMagazineParam);
    }

    public GetMagazineDetailRes getMagazineDetail(int magazine_id){
        String getMagazineDetailQuery = "select type, title, contents_url from MAGAZINE where magazine_id = ? and status = 1";

        int getMagazineDetailParams = magazine_id;
        return this.jdbcTemplate.queryForObject(getMagazineDetailQuery,
                (rs, rowNum) -> new GetMagazineDetailRes(
                        rs.getString("type"),
                        rs.getString("title"),
                        rs.getString("contents_url")),
                getMagazineDetailParams);
    }

    public int checkMagazineId(int magazine_id){
        String checkMagazineIdQuery = "select exists(select magazine_id from MAGAZINE where magazine_id = ?)";
        int checkMagazineIdParams = magazine_id;
        return this.jdbcTemplate.queryForObject(checkMagazineIdQuery,
                int.class,
                checkMagazineIdParams);
    }

    public int checkMagazineType(String type){
        String checkMagazineTypeQuery = "select exists(select type from MAGAZINE where type = ?)";
        String checkMagazineTypeParams = type;
        return this.jdbcTemplate.queryForObject(checkMagazineTypeQuery,
                int.class,
                checkMagazineTypeParams);
    }



}
