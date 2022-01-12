package com.example.demo.src.products;

import com.example.demo.src.products.model.GetNewRes;
import com.example.demo.src.products.model.GetRankRes;
import com.example.demo.src.products.model.GetReleaseRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetNewRes> getNewProduct(){
        String getContentQuery = "SELECT P.product_name, P.price,I.url,P.release_day FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE category = 'sneakers' AND P.release_day < NOW()\n" +
                "GROUP BY product_name\n" +
                "ORDER BY P.release_day DESC";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetNewRes(
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("url"),
                        rs.getString("release_day")
                )
        );
    }

    public List<GetRankRes> getRankProduct(){
        String getContentQuery = "SELECT I.url ,P.product_name, P.price FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE P.price != 0\n" +
                "GROUP BY product_name\n" +
                "ORDER BY click_count desc\n" +
                "LIMIT 30";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetRankRes(
                        rs.getString("url"),
                        rs.getString("product_name"),
                        rs.getInt("price")
                )
        );
    }

    public List<GetReleaseRes> getReleaseProduct(){
        String getContentQuery = "SELECT P.product_name,P.release_day, P.price,I.url FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE category = 'sneakers' AND P.release_day > NOW()\n" +
                "GROUP BY product_name\n" +
                "ORDER BY P.release_day ASC";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetReleaseRes(
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("url"),
                        rs.getString("release_day")
                )
        );
    }
}
