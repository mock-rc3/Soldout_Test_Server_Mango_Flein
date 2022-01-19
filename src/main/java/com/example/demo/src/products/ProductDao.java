package com.example.demo.src.products;

import com.example.demo.src.products.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //신규 발매 상품 조회
    public List<GetNewRes> getNewProduct(){
        String getContentQuery = "SELECT P.product_id, P.product_name, P.price,I.url,P.release_day FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE category = 'sneakers' AND P.release_day < NOW()\n" +
                "GROUP BY product_name\n" +
                "ORDER BY P.release_day DESC";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetNewRes(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("url"),
                        rs.getString("release_day")
                )
        );
    }

    //날짜별 발매 상품
    public List<GetDayAlarmRes> getDayAlarm(){
        String getContentQuery = "SELECT date(P.release_day) as release_day,P.product_id,P.product_name,I.url,BI.img_url as brand_image, 'N' AS alarm_check FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "LEFT JOIN BRAND_IMAGE BI\n" +
                "ON BI.brand_img_id = P.brand_img_id\n" +
                "WHERE  release_day between CURDATE() - INTERVAL 30 DAY AND CURDATE() + INTERVAL 30 DAY\n" +
                "GROUP BY product_name, release_day\n" +
                "ORDER BY release_day desc";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetDayAlarmRes(
                        rs.getString("release_day"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("url"),
                        rs.getString("brand_image"),
                        rs.getString("alarm_check")
                )
        );
    }

    //날짜별 발매 상품 API (로그인 한경우)
    public List<GetDayAlarmRes> getDayAlarmFilter(int userId){
        String getContentQuery = "SELECT date(P.release_day) as release_day,P.product_id,P.product_name,I.url,BI.img_url as brand_image, IF(nullif(N.product_id,0)>0,'Y','N') AS alarm_check   FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "LEFT JOIN BRAND_IMAGE BI\n" +
                "ON BI.brand_img_id = P.brand_img_id\n" +
                "LEFT JOIN (SELECT product_id FROM NOTICE \n" +
                "WHERE user_id = ? ) AS N\n" +
                "ON P.product_id = N.product_id\n" +
                "WHERE  release_day between CURDATE() - INTERVAL 30 DAY AND CURDATE() + INTERVAL 30 DAY\n" +
                "GROUP BY product_name, release_day\n" +
                "ORDER BY release_day desc";
        int getUserIdFilterParam = userId;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetDayAlarmRes(
                        rs.getString("release_day"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("url"),
                        rs.getString("brand_image"),
                        rs.getString("alarm_check")
                ),getUserIdFilterParam
        );
    }


    // 상품 랭킹 조회
    public List<GetRankRes> getRankProduct(){
        String getContentQuery = "SELECT P.product_id,I.url ,P.product_name, P.price FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE P.price != 0\n" +
                "GROUP BY product_name\n" +
                "ORDER BY click_count desc\n" +
                "LIMIT 30";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetRankRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("product_name"),
                        rs.getInt("price")
                )
        );
    }


    //메인페이지 배너, 특정 검색어 제품  조회
    public List<GetGoodsRes> getGoodsList(){
        String getContentQuery = "SELECT P.product_id, I.url , P.product_name,\n" +
                "(\n" +
                "SELECT total_price FROM ORDER_HISTORY OH\n" +
                "WHERE OH.product_id = P.product_id AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(select ROUND(((a.max_count-a.pre_max_count)/a.pre_max_count)*100,1) as percent\n" +
                "from \n" +
                "(\n" +
                "    select \n" +
                "(\n" +
                "SELECT total_price FROM ORDER_HISTORY OH\n" +
                "WHERE  OH.product_id = P.product_id AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(\n" +
                "\n" +
                "SELECT total_price FROM ORDER_HISTORY OH\n" +
                "WHERE  OH.product_id = P.product_id AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1,1\n" +
                ")AS pre_max_count\n" +
                ")as  a) as percent\n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "GROUP BY product_name \n" +
                "HAVING  max_count IS NOT NULL";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetGoodsRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("product_name"),
                        rs.getInt("max_count"),
                        rs.getInt("percent")
                )
        );
    }

    //메인페이지 배너, 특정 검색어 제품  조회 (검색어 있는경우)
    public List<GetGoodsRes> getGoodsListFilter(String search){
        String getContentQuery = "SELECT P.product_id, I.url , P.product_name,\n" +
                "(\n" +
                "SELECT total_price FROM ORDER_HISTORY OH\n" +
                "WHERE OH.product_id = P.product_id AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(select ROUND(((a.max_count-a.pre_max_count)/a.pre_max_count)*100,1) as percent\n" +
                "from \n" +
                "(\n" +
                "    select \n" +
                "(\n" +
                "SELECT total_price FROM ORDER_HISTORY OH\n" +
                "WHERE  OH.product_id = P.product_id AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(\n" +
                "\n" +
                "SELECT total_price FROM ORDER_HISTORY OH\n" +
                "WHERE  OH.product_id = P.product_id AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1,1\n" +
                ")AS pre_max_count\n" +
                ")as  a) as percent\n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "GROUP BY product_name \n" +
                "HAVING  product_name regexp ? \n" +
                "LIMIT 5";
        String getSearchFilterParam = search;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetGoodsRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("product_name"),
                        rs.getInt("max_count"),
                        rs.getInt("percent")
                ),getSearchFilterParam
        );
    }


    //새로운 즉시 판매가 상품 조회
    public List<GetGoodsRes> getNewSellList(){
        String getContentQuery = "SELECT P.product_id, I.url , P.product_name,\n" +
                "(\n" +
                "SELECT hope_price FROM ORDER_HISTORY OH\n" +
                "WHERE OH.product_id = P.product_id AND order_state = 0\n" +
                "    AND type = 'sell'\n" +
                "ORDER BY created_at DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(select ROUND(((a.max_count-a.pre_max_count)/a.pre_max_count)*100,1) as percent\n" +
                "from \n" +
                "(\n" +
                "    select \n" +
                "(\n" +
                "SELECT hope_price FROM ORDER_HISTORY OH\n" +
                "WHERE OH.product_id = P.product_id AND order_state = 0\n" +
                "AND type = 'sell'\n" +
                "ORDER BY created_at DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(\n" +
                "\n" +
                "SELECT hope_price FROM ORDER_HISTORY OH\n" +
                "WHERE OH.product_id = P.product_id AND order_state = 0\n" +
                "AND type = 'sell'\n" +
                "ORDER BY created_at DESC LIMIT 1,1\n" +
                ")AS pre_max_count\n" +
                ")as  a) as percent\n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "GROUP BY product_name \n" +
                "HAVING max_count IS NOT NULL\n" +
                "\n";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetGoodsRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("product_name"),
                        rs.getInt("max_count"),
                        rs.getInt("percent")
                )
        );
    }

    //검색페이지 조회
    public List<GetSearchRes> getSearchProduct(){
        String getContentQuery = "SELECT P.product_id, I.url,P.brand,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "SELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "IN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "GROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " GROUP BY product_name \n";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("brand"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                )
        );
    }

    //검색페이지 조회 (키워드 있는경우)
    public List<GetSearchRes> getSearchKeywordFilter(String keyword){
        String getContentQuery = "SELECT  P.product_id,I.url,P.brand,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "SELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "IN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "GROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " WHERE product_name regexp ?\n" +
                " GROUP BY product_name \n";
        String getSearchFilterParam = keyword;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("brand"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                ),getSearchFilterParam
        );
    }

    //검색페이지 조회 (카테고리 있는경우)
    public List<GetSearchRes> getSearchCategoryFilter(String category){
        String getContentQuery = "SELECT  P.product_id,I.url,P.brand,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "SELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "IN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "GROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " WHERE P.category = ?\n" +
                " GROUP BY product_name \n";
        String getSearchFilterParam = category;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("brand"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                ),getSearchFilterParam
        );
    }

    //검색페이지 조회 (키워드,카테고리 둘다 있는경우)
    public List<GetSearchRes> getSearchFilter(String category,String keyword){
        String getContentQuery = "SELECT  P.product_id,I.url,P.brand,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "SELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "IN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time ) \n" +
                "GROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " WHERE P.category = ?" +
                "AND P.product_name regexp ?\n" +
                " GROUP BY product_name \n";
        String getSearchFilterParam = category;
        String getSearchKeywordParam = keyword;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSearchRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("brand"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                ),getSearchFilterParam,getSearchKeywordParam
        );
    }

    //연관 추천 상품 조회
    public List<GetRelateRecommendRes> getRelateRecommend( String brandName){
        String getContentQuery = "SELECT P.product_id,I.url,BI.img_url  as brand_image  , P.product_name, OH.total_price \n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "LEFT JOIN  ( \n" +
                "SELECT * FROM ORDER_HISTORY  WHERE order_state = 1   AND complete_time \n" +
                "IN (  SELECT MAX(complete_time) FROM ORDER_HISTORY group by complete_time )\n" +
                "GROUP BY product_id \n" +
                ") OH\n" +
                " ON OH.product_id = P.product_id\n" +
                " WHERE P.brand = ?\n" +
                " GROUP BY product_name \n" +
                " LIMIT 10";
        String getRelateBrandNameParam = brandName;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetRelateRecommendRes(
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getInt("total_price")
                ),getRelateBrandNameParam
        );
    }


    // 상품 상단 정보 조회
    public List<GetTopInforRes> getDetailTopInformation(int productId){
        String getContentQuery = "SELECT BI.img_url  as brand_image  , P.product_name,P.product_name_eng,\n" +
                "(\n" +
                "SELECT total_price FROM SOLDOUT.ORDER_HISTORY\n" +
                "WHERE product_id =? AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(select (a.max_count-a.pre_max_count) as diff_count\n" +
                "from \n" +
                "(\n" +
                "    select \n" +
                "(\n" +
                "SELECT total_price FROM SOLDOUT.ORDER_HISTORY\n" +
                "WHERE product_id =? AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(\n" +
                "\n" +
                "SELECT total_price FROM SOLDOUT.ORDER_HISTORY\n" +
                "WHERE product_id = ? AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1,1\n" +
                ")AS pre_max_count\n" +
                ")as  a) as diff_count,\n" +
                "(select round(((a.max_count-a.pre_max_count)/a.pre_max_count)*100,1) as percent\n" +
                "from \n" +
                "(\n" +
                "    select \n" +
                "(\n" +
                "SELECT total_price FROM SOLDOUT.ORDER_HISTORY\n" +
                "WHERE product_id =? AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1\n" +
                ")AS max_count,\n" +
                "(\n" +
                "\n" +
                "SELECT total_price FROM SOLDOUT.ORDER_HISTORY\n" +
                "WHERE product_id = ? AND order_state = 1\n" +
                "ORDER BY complete_time DESC LIMIT 1,1\n" +
                ")AS pre_max_count\n" +
                ")as  a) as percent\n" +
                "FROM PRODUCT P \n" +
                "LEFT JOIN IMAGE I  \n" +
                "ON P.product_id = I.product_id \n" +
                "LEFT JOIN BRAND_IMAGE BI \n" +
                "ON BI.brand_img_id = P.brand_img_id \n" +
                "WHERE P.product_id = ?\n" +
                "GROUP BY product_name ";
        int getProductIdParam = productId;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetTopInforRes(
                        rs.getString("brand_image"),
                        rs.getString("product_name"),
                        rs.getString("product_name_eng"),
                        rs.getInt("max_count"),
                        rs.getString("diff_count"),
                        rs.getString("percent")
                ),getProductIdParam,getProductIdParam,getProductIdParam,getProductIdParam,getProductIdParam,getProductIdParam
        );
    }

    //최근거래 조회
    public List<GetRecentRes> getDetailRecent(int productId){
        String getContentQuery =  "SELECT \n" +
                "CASE\n" +
                "    WHEN TIMESTAMPDIFF(HOUR,OH.complete_time,NOW()) <1 THEN CONCAT(TIMESTAMPDIFF(MINUTE,OH.complete_time,NOW()),'분전')\n" +
                "WHEN TIMESTAMPDIFF(HOUR,OH.complete_time,NOW()) <25 THEN CONCAT(TIMESTAMPDIFF(HOUR,OH.complete_time,NOW()),'시간전')\n" +
                "    ELSE DATE_FORMAT(OH.complete_time, '%Y-%m-%d')  \n" +
                "END AS times,OH.complete_time,S.size_name,OH.total_price FROM ORDER_HISTORY OH\n" +
                "LEFT JOIN SIZE S\n" +
                "ON S.size_id = OH.size_id\n" +
                "WHERE product_id = ? AND order_state = 1\n" +
                "ORDER BY complete_time DESC";
        int getProductIdParam = productId;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetRecentRes(
                        rs.getString("times"),
                        rs.getString("complete_time"),
                        rs.getString("size_name"),
                        rs.getInt("total_price")
                ),getProductIdParam
        );
    }

    // 입찰 현황 조회
    public List<GetDealRes> getDetailDeal(int productId,String types){
        String getContentQuery = "SELECT \n" +
                "1 AS amount,S.size_name,OH.hope_price FROM ORDER_HISTORY OH\n" +
                "LEFT JOIN SIZE S\n" +
                "ON S.size_id = OH.size_id\n" +
                "WHERE product_id = ? AND order_state = 0 AND OH.type = ? \n" +
                "ORDER BY complete_time DESC";

        int getProductIdParam = productId;
        String getTypeParam = types;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetDealRes(
                        rs.getInt("amount"),
                        rs.getString("size_name"),
                        rs.getInt("hope_price")
                ),getProductIdParam,getTypeParam
        );
    }

    //사이즈별 거래가 조회
    public List<GetSizePriceRes> getSizeTransPrice(int productId, String types){
        String getContentQuery = " (SELECT O.product_id,O.order_id,O.size_id, 'ALL' AS size_name, MIN(hope_price) AS hope_price FROM SOLDOUT.ORDER_HISTORY O\n" +
                "LEFT JOIN SIZE S\n" +
                "ON S.size_id = O.size_id\n" +
                "WHERE O.product_id = ? AND O.order_state = 0  AND O.type = ?)\n" +
                "UNION\n" +
                "SELECT S.product_id, S.order_id,S.size_id, S.size_name, S.hope_price FROM(\n" +
                "        (SELECT    O.product_id,O.order_id,O.size_id,S.size_name, O.hope_price FROM SOLDOUT.ORDER_HISTORY O\n" +
                "        LEFT JOIN SIZE S\n" +
                "        ON S.size_id = O.size_id\n" +
                "        WHERE O.product_id = ? AND O.order_state = 0\n" +
                "AND O.type = ?\n" +
                "        GROUP BY O.size_id\n" +
                "        )\n" +
                "    ORDER BY O.size_id\n" +
                ") AS S\n";
        int getProductIdParam = productId;
        String getTypeParam = types;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetSizePriceRes(
                        rs.getInt("product_id"),
                        rs.getInt("order_id"),
                        rs.getInt("size_id"),
                        rs.getString("size_name"),
                        rs.getInt("hope_price")
                ),getProductIdParam,getTypeParam,getProductIdParam,getTypeParam
        );
    }

    //상세페이지 상품 이미지 조회
    public List<GetProductImageRes> getDetailProductImage(int productId){
        String getContentQuery = "SELECT I.url FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I\n" +
                "ON P.product_id = I.product_id \n" +
                "WHERE P.product_id = ?";

        int getProductIdParam = productId;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetProductImageRes(
                        rs.getString("url")
                ),getProductIdParam
        );
    }

    //상품 정보, 구매처 조회 A
    public List<GetInformationRes> getDetailInformation(int productId){
        String getContentQuery =   "SELECT  P.brand,  P.model_num, date_format( P.release_day,'%Y-%m-%d')as release_day,  P.color,  P.price,PC.purchase_name, PC.image_url FROM PRODUCT P\n" +
                "LEFT JOIN PURCHASE PC\n" +
                "ON PC.purchase_id = P.purchase_id\n" +
                "WHERE  P.product_id =?";
        ;
        int getProductIdParam = productId;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetInformationRes(
                        rs.getString("brand"),
                        rs.getString("model_num"),
                        rs.getString("release_day"),
                        rs.getString("color"),
                        rs.getInt("price"),
                        rs.getString("purchase_name"),
                        rs.getString("image_url")
                ),getProductIdParam
        );
    }






    //상품 랭킹 조회 (카테고리 있는경우)
    public List<GetRankRes> getRankProductFilter(String category){
        String getContentQuery = "SELECT  P.product_id, I.url ,P.product_name, P.price FROM PRODUCT P\n" +
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
                        rs.getInt("product_id"),
                        rs.getString("url"),
                        rs.getString("product_name"),
                        rs.getInt("price")
                ),getRankFilterParam
        );
    }

    // 발매예정 조회
    public List<GetReleaseRes> getReleaseProduct(){
        String getContentQuery = "SELECT  P.product_id,P.product_name,P.release_day, P.price,I.url FROM PRODUCT P\n" +
                "LEFT JOIN IMAGE I \n" +
                "ON P.product_id = I.product_id\n" +
                "WHERE category = 'sneakers' AND P.release_day > NOW()\n" +
                "GROUP BY product_name\n" +
                "ORDER BY P.release_day ASC";
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetReleaseRes(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("price"),
                        rs.getString("url"),
                        rs.getString("release_day")
                )
        );
    }

    // 핫이슈 인기 상품 조회
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
