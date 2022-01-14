package com.example.demo.src.products;

import com.example.demo.src.products.model.*;
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

    public List<GetSearchRes> getSearchProduct(){
        String getContentQuery = "SELECT I.url,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "\tSELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "\tIN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "\tGROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " GROUP BY product_name \n";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getString("url"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                )
        );
    }

    public List<GetSearchRes> getSearchKeywordFilter(String keyword){
        String getContentQuery = "SELECT I.url,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "\tSELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "\tIN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "\tGROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " WHERE product_name regexp ?\n" +
                " GROUP BY product_name \n";
        String getSearchFilterParam = keyword;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getString("url"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                ),getSearchFilterParam
        );
    }

    public List<GetSearchRes> getSearchCategoryFilter(String category){
        String getContentQuery = "SELECT I.url,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "\tSELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "\tIN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "\tGROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " WHERE P.category = ?\n" +
                " GROUP BY product_name \n";
        String getSearchFilterParam = category;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getString("url"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                ),getSearchFilterParam
        );
    }

    public List<GetSearchRes> getSearchFilter(String category,String keyword){
        String getContentQuery = "SELECT I.url,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "\tSELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "\tIN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "\tGROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " WHERE P.category = ?" +
                "AND P.product_name regexp ?\n" +
                " GROUP BY product_name \n";
        String getSearchFilterParam = category;
        String getSearchKeywordParam = keyword;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getString("url"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                ),getSearchFilterParam,getSearchKeywordParam
        );
    }


    public List<GetRankRes> getRankProductFilter(String category){
        String getContentQuery = "SELECT I.url ,P.product_name, P.price FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE P.price != 0\n" +
                "AND P.category = ?\n" +
                "GROUP BY product_name\n" +
                "ORDER BY click_count desc\n" +
                "LIMIT 30";
        String getRankFilterParam = category;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetRankRes(
                        rs.getString("url"),
                        rs.getString("product_name"),
                        rs.getInt("price")
                ),getRankFilterParam
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

    public List<GetHotissueRes> getHotissue(){
        String getContentQuery = "SELECT P.product_name,P.release_day,I.url FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE category = 'sneakers' \n" +
                "GROUP BY product_name\n" +
                "ORDER BY P.click_count DESC";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetHotissueRes(
                        rs.getString("product_name"),
                        rs.getString("release_day"),
                        rs.getString("url")
                )
        );
    }
}
