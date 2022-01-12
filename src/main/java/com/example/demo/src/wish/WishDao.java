package com.example.demo.src.wish;

import com.example.demo.src.wish.model.GetWishRes;
import com.example.demo.src.wish.model.PostWishReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WishDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetWishRes> getWish(int userId){
        String getWishQuery = "select P.product_name, P.color, P.brand, P.price, S.size_name from FAVORITE F join PRODUCT P  on P.product_id = F.product_id join SIZE S on S.size_id = F.size_id where F.user_id = ? and F.status = 1";
        int getWishParams = userId;
        return this.jdbcTemplate.query(getWishQuery,
                (rs,rowNum) ->new GetWishRes(
                        rs.getString("product_name"),
                        rs.getString("color"),
                        rs.getString("brand"),
                        rs.getInt("price"),
                        rs.getString("size_name")
                        ), getWishParams
        );
    }
    public int createWish(int userId, PostWishReq postWishReq) {
        String createWishQuery = "insert into FAVORITE(user_id, product_id, size_id) values (?,?,?)";
        Object[] createWishParams = new Object[]{
                userId,
                postWishReq.getProduct_id(),
                postWishReq.getSize_id()
                };
        this.jdbcTemplate.update(createWishQuery, createWishParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

    }

    public int deleteWish(int favoriteId) {
        String deleteWishQuery = "update FAVORITE set status = 0 where favorite_id = ? ";
        int deleteWishParams = favoriteId;

        return this.jdbcTemplate.update(deleteWishQuery, deleteWishParams);
    }


}


