package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetUserRes getUser(int user_id){
        String getUserQuery = "select user_id, name, id, email, nickname, password, phone_num from USER where user_id = ?";

        int getUserParams = user_id;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("password"),
                        rs.getString("phone_num")),
                getUserParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into USER (id, email, password, phone_num, name, nickname) VALUES (?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                postUserReq.getId(),
                postUserReq.getEmail(),
                postUserReq.getPassword(),
                postUserReq.getPhone_num(),
                postUserReq.getName(),
                postUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select user_id, id, password, phone_num, email, name, nickname, point from USER where id = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("user_id"),
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("phone_num"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("nickname"),
                        rs.getInt("point")
                ),
                getPwdParams
                );
    }
    public User getUserId(int user_id){
        String getPwdQuery = "select user_id, id, password, phone_num, email, name, nickname, point from USER where user_id = ?";
        int getPwdParams = user_id;

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("user_id"),
                        rs.getString("id"),
                        rs.getString("password"),
                        rs.getString("phone_num"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("nickname"),
                        rs.getInt("point")
                ),
                getPwdParams
        );

    }

    public int deleteUser(PatchDeleteUserReq patchDeleteUserReq) {
        String deleteUserQuery = "update USER set status = ? where user_id = ? ";
        Object[] deleteUserParams =  new Object[]{patchDeleteUserReq.getStatus(), patchDeleteUserReq.getUser_id()};

        return this.jdbcTemplate.update(deleteUserQuery,deleteUserParams);
    }
    public GetFindIdRes findId (String name, String phoneNum){
        String getFindIdQuery = "select id from USER where name = ? and phone_num =?";
        Object[] getFindIdParams = new Object[]{name, phoneNum};
        return this.jdbcTemplate.queryForObject(getFindIdQuery,
                (rs, rowNum) -> new GetFindIdRes(
                        rs.getString("id")),
                getFindIdParams);
    }

    public int modifyNickName(PatchUserReq patchUserReq) {
        String modifyNickNameQuery = "update USER set nickname = ? where user_id = ? ";
        Object[] modifyNickNameParams = new Object[]{patchUserReq.getChangeStr(),patchUserReq.getUser_id()};
        return this.jdbcTemplate.update(modifyNickNameQuery,modifyNickNameParams);
    }
    public int modifyPhoneNum(PatchUserReq patchUserReq) {
        String modifyPhoneNumQuery = "update USER set phone_num = ? where user_id = ? ";
        Object[] modifyPhoneNumParams = new Object[]{patchUserReq.getChangeStr(),patchUserReq.getUser_id()};
        return this.jdbcTemplate.update(modifyPhoneNumQuery,modifyPhoneNumParams);
    }
    public int modifyEmail(PatchUserReq patchUserReq) {
        String modifyEmailQuery = "update USER set email = ? where user_id = ? ";
        Object[] modifyEmailParams = new Object[]{patchUserReq.getChangeStr(),patchUserReq.getUser_id()};
        return this.jdbcTemplate.update(modifyEmailQuery,modifyEmailParams);
    }
    public int checkUserId(int user_id){
        String checkUserIdQuery = "select exists(select user_id from USER where user_id = ?)";
        int checkUserIdParams = user_id;
        return this.jdbcTemplate.queryForObject(checkUserIdQuery,
                int.class,
                checkUserIdParams);
    }
    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from USER where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }
    public int checkId(String id){
        String checkIdQuery = "select exists(select id from USER where id = ?)";
        String checkIdParams = id;
        return this.jdbcTemplate.queryForObject(checkIdQuery,
                int.class,
                checkIdParams);
    }
    public int checkNickname(String nickname){
        String checkNicknameQuery = "select exists(select nickname from USER where nickname = ?)";
        String checkNicknameParams = nickname;
        return this.jdbcTemplate.queryForObject(checkNicknameQuery,
                int.class,
                checkNicknameParams);
    }
    public int checkPhoneNum(String phone_num){
        String checkNicknameQuery = "select exists(select phone_num from USER where phone_num = ?)";
        String checkNicknameParams = phone_num;
        return this.jdbcTemplate.queryForObject(checkNicknameQuery,
                int.class,
                checkNicknameParams);
    }
    public int checkStatus(String id){
        String checkStatusQuery = "select status from USER where id = ?";
        String checkStatusParams = id;
        return this.jdbcTemplate.queryForObject(checkStatusQuery,
                int.class,
                checkStatusParams);
    }
}
