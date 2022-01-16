package com.example.demo.src.orderHistory;

import com.example.demo.src.orderHistory.model.GetOrderPriceRes;
import com.example.demo.src.orderHistory.model.GetOrderRes;
import com.example.demo.src.orderHistory.model.PatchtradeBuyReq;
import com.example.demo.src.orderHistory.model.PatchtradeSellingReq;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrderHistoryDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //타입별 입찰 주문 내역 조회
    public List<GetOrderRes> getOrderByType(int product_id, int user_id, String type){
        String getOrderQuery = "SELECT O.order_id, S.size_name, O.hope_price, O.type, COUNT(S.size_name) AS quantity from ORDER_HISTORY O JOIN SIZE S on S.size_id = O.size_id WHERE O.product_id = ? AND O.user_id not in (?) AND O.type = ? AND O.status=1 AND O.order_state=0 AND DATE(NOW()) >= DATE_SUB(O.created_at, INTERVAL O.term DAY) GROUP BY S.size_name, O.hope_price order by (case when O.type = 'buy' then O.hope_price end )desc";

       Object[] getOrderParams = new Object[]{product_id, user_id, type};
        return this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrderRes(
                       rs.getInt("order_id"),
                        rs.getString("size_name"),
                        rs.getInt("hope_price"),
                        rs.getString("type"),
                        rs.getInt("quantity")), getOrderParams
                );
    }
    //타입,사이즈별 입찰 주문 내역 조회
    public List<GetOrderRes> getOrderBySize(int product_id, int user_id, String type,int size_id){
        String getOrderQuery = "SELECT O.order_id, S.size_name, O.hope_price, O.type, COUNT(O.hope_price) AS quantity from ORDER_HISTORY O JOIN SIZE S on S.size_id = O.size_id WHERE O.product_id = ? AND O.user_id not in (?) AND O.type = ? AND O.size_id = ? AND O.status=1 AND O.order_state=0 AND DATE(NOW()) >= DATE_SUB(O.created_at, INTERVAL O.term DAY) GROUP BY O.hope_price order by (case when O.type = 'buy' then O.hope_price end )desc";

        Object[] getOrderParams = new Object[]{product_id, user_id, type, size_id};
        return this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrderRes(
                        rs.getInt("order_id"),
                        rs.getString("size_name"),
                        rs.getInt("hope_price"),
                        rs.getString("type"),
                        rs.getInt("quantity")),
                getOrderParams);
    }
    //전체 즉시 판매가
    public  List<GetOrderPriceRes> getMaxPrice(int product_id, int user_id){
        String getOrderPriceQuery = "SELECT O.order_id, O.user_id, max(O.hope_price) AS imt_price , S.size_name from ORDER_HISTORY O JOIN SIZE S on S.size_id = O.size_id WHERE O.product_id = ? AND O.user_id not in (?) AND O.type = 'buy'  AND O.status=1 AND O.order_state=0 AND DATE(NOW()) >= DATE_SUB(O.created_at, INTERVAL O.term DAY) group by S.size_name";
        Object[] getOrderPriceParams = new Object[]{product_id, user_id};
        return this.jdbcTemplate.query(getOrderPriceQuery,
                (rs, rowNum) -> new GetOrderPriceRes(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getInt("imt_price"),
                        rs.getString("size_name")),
                getOrderPriceParams);
    }
    //전체 즉시 구매가
    public List<GetOrderPriceRes>  getMinPrice(int product_id, int user_id){
        String getOrderPriceQuery = "SELECT O.order_id, O.user_id, min(O.hope_price) AS imt_price, S.size_name from ORDER_HISTORY O JOIN SIZE S on S.size_id = O.size_id WHERE O.product_id = ? AND O.user_id not in (?) AND O.type = 'sell' AND O.status=1 AND O.order_state=0 AND DATE(NOW()) >= DATE_SUB(O.created_at, INTERVAL O.term DAY) group by S.size_name";
        Object[] getOrderPriceParams = new Object[]{product_id, user_id};
        return this.jdbcTemplate.query(getOrderPriceQuery,
                (rs, rowNum) -> new GetOrderPriceRes(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getInt("imt_price"),
                        rs.getString("size_name")),
                getOrderPriceParams);
    }
    public GetOrderPriceRes getMaxPriceBySize(int product_id, int user_id, int size_id){
        String getOrderPriceQuery = "SELECT O.order_id, O.user_id, O.hope_price AS imt_price, S.size_name from ORDER_HISTORY O JOIN SIZE S on S.size_id = O.size_id WHERE  O.product_id = ? AND O.user_id not in (?) AND O.type = 'buy' AND O.size_id = ? AND O.status=1 AND O.order_state=0 AND DATE(NOW()) >= DATE_SUB(O.created_at, INTERVAL O.term DAY) order by O.hope_price desc limit 1;";
        Object[] getOrderPriceParams = new Object[]{product_id, user_id,  size_id};
        return this.jdbcTemplate.queryForObject(getOrderPriceQuery,
                (rs, rowNum) -> new GetOrderPriceRes(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getInt("imt_price"),
                        rs.getString("size_name")),
                getOrderPriceParams);
    }
    public GetOrderPriceRes getMinPriceBySize(int product_id, int user_id, int size_id){
        String getOrderPriceQuery = "SELECT O.order_id, O.user_id, O.hope_price AS imt_price, S.size_name from ORDER_HISTORY O JOIN SIZE S on S.size_id = O.size_id WHERE O.product_id = ? AND O.user_id not in (?) AND O.type = 'sell' AND O.size_id = ? AND O.status=1 AND O.order_state=0 AND DATE(NOW()) >= DATE_SUB(O.created_at, INTERVAL O.term DAY) ORDER BY O.hope_price limit 1;";
        Object[] getOrderPriceParams = new Object[]{product_id, user_id, size_id};
        return this.jdbcTemplate.queryForObject(getOrderPriceQuery,
                (rs, rowNum) -> new GetOrderPriceRes(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getInt("imt_price"),
                        rs.getString("size_name")),
                getOrderPriceParams);
    }
    //즉시 판매하기
    public int tradeSelling(int trader_id, PatchtradeSellingReq patchtradeSellingReq) {
        String tradeSellingQuery = "update ORDER_HISTORY set trader_id = ? ,address = ? ,bank = ?, account = ?, seller_name = ? , total_price = hope_price-point, order_state = 1, complete_time = CURRENT_TIMESTAMP where order_id = ? ";
        Object[] tradeSellingParams =  new Object[]{
                trader_id,
                patchtradeSellingReq.getAddress(),
                patchtradeSellingReq.getBank(),
                patchtradeSellingReq.getAccount(),
                patchtradeSellingReq.getSeller_name(),
                patchtradeSellingReq.getOrder_id()};
        return this.jdbcTemplate.update(tradeSellingQuery,tradeSellingParams);
    }
    //즉시 구매하기
    public int tradeBuy(int trader_id, PatchtradeBuyReq patchtradeBuyReq) {
        String tradeBuyQuery = "update ORDER_HISTORY set trader_id = ? ,total_price = hope_price - ?, order_state = 1, complete_time = CURRENT_TIMESTAMP where order_id = ? ";
        Object[] tradeBuyParams =  new Object[]{
                trader_id,
                patchtradeBuyReq.getPoint(),
                patchtradeBuyReq.getOrder_id()};

        return this.jdbcTemplate.update(tradeBuyQuery,tradeBuyParams);
    }

    public int getPlusPoint(int order_id){
        String pointQuery = "select total_price*0.01 from ORDER_HISTORY where order_id = ? ";
        int pointParam = order_id;
        return this.jdbcTemplate.queryForObject(pointQuery, int.class, pointParam);
    }
    public int getMinusPoint(int order_id){
        String pointQuery = "select point from ORDER_HISTORY where order_id = ? ";
        int pointParam = order_id;
        return this.jdbcTemplate.queryForObject(pointQuery, int.class, pointParam);
    }
    public int getUserId(int order_id){
        String userQuery = "select user_id from ORDER_HISTORY where order_id = ? ";
        int userParam = order_id;
        return this.jdbcTemplate.queryForObject(userQuery, int.class, userParam);
    }

    public void plusPoint(int trader_id, int point) {
        String plusPointQuery = "update USER set point = point + ? where user_id = ?";
        Object[] plusPointParam =  new Object[]{point, trader_id};
         this.jdbcTemplate.update(plusPointQuery,plusPointParam);
    }

    public void minusPoint(int trader_id, int point) {
        String minusPointQuery = "update USER set point = point - ? where user_id = ? ";
        Object[] minusPointParam =  new Object[]{point, trader_id};
         this.jdbcTemplate.update(minusPointQuery,minusPointParam);
    }
    public void createPointHistory(int user_id, int order_id, int point, String type){
        String createUserQuery = "insert into POINT_HISTORY (user_id, order_id, point, type) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{user_id, order_id, point, type};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
         this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }




}
