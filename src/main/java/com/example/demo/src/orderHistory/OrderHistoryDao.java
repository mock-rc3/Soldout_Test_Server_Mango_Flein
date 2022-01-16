package com.example.demo.src.orderHistory;

import com.example.demo.src.orderHistory.model.GetDealNumberRes;
import com.example.demo.src.products.model.GetSearchRes;
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

    public List<GetDealNumberRes> getDealNumber(int userId){
        String getContentQuery = "SELECT\n" +
                "\t(SELECT COUNT(*) FROM ORDER_HISTORY O\n" +
                "\tWHERE user_id = ? AND order_state = 0\n" +
                "\tAND type = 'buy'\n" +
                "\tGROUP BY type) AS buy_bidding\n" +
                "    ,\n" +
                "\t(SELECT COUNT(*) FROM ORDER_HISTORY O\n" +
                "\tWHERE user_id = ? AND order_state = 1\n" +
                "\tAND type = 'buy'\n" +
                "\tGROUP BY type) AS buy_end\n" +
                "    ,\n" +
                "\t(SELECT COUNT(*) FROM ORDER_HISTORY O\n" +
                "\tWHERE user_id = ? AND order_state = 0\n" +
                "\tAND type = 'SELL'\n" +
                "\tGROUP BY type) AS sell_bidding\n" +
                "    ,\n" +
                "\t(SELECT COUNT(*) FROM ORDER_HISTORY O\n" +
                "\tWHERE user_id = ? AND order_state = 1\n" +
                "\tAND type = 'SELL'\n" +
                "\tGROUP BY type) AS sell_end\n";
        int getSearchFilterParam = userId;
        return this.jdbcTemplate.query(getContentQuery,
                (rs,rowNum) -> new GetDealNumberRes(
                        rs.getInt("buy_bidding"),
                        rs.getInt("buy_end"),
                        rs.getInt("sell_bidding"),
                        rs.getInt("sell_end")
                ),getSearchFilterParam,getSearchFilterParam,getSearchFilterParam,getSearchFilterParam
        );
    }
}
