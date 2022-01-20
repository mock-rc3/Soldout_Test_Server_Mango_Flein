package com.example.demo.src.address;

import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PostAddressReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class AddressDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetAddressRes> getAddress(int userId){
        String getAddressQuery = "select address_name, recipient, phone_num, address, detail, basic from ADDRESS where user_id = ? and status = 1";
        int getAddressParams = userId;
        return this.jdbcTemplate.query(getAddressQuery,
                (rs,rowNum) ->new GetAddressRes(
                        rs.getString("address_name"),
                        rs.getString("recipient"),
                        rs.getString("phone_num"),
                        rs.getString("address"),
                        rs.getString("detail"),
                        rs.getInt("basic") ), getAddressParams
        );
    }

    public int createAddress(int userId, PostAddressReq postAddressReq) {
        String createAddressQuery = "insert into ADDRESS(user_id,address_name,recipient,phone_num,address,detail,basic) values (?,?,?,?,?,?,?)";
        Object[] createAddressParams = new Object[]{
                userId,
                postAddressReq.getAddress_name(),
                postAddressReq.getRecipient(),
                postAddressReq.getPhone_num(),
                postAddressReq.getAddress(),
                postAddressReq.getDetail(),
                postAddressReq.getBasic()};
        this.jdbcTemplate.update(createAddressQuery, createAddressParams);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

    }

    public int deleteAddress(int addressId) {
        String deleteAddressQuery = "update ADDRESS set status = 0 where address_id = ? ";
        int deleteAddressParams = addressId;
        return this.jdbcTemplate.update(deleteAddressQuery, deleteAddressParams);
    }
}


