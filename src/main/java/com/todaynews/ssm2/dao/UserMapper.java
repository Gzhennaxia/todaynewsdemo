package com.todaynews.ssm2.dao;


import com.todaynews.ssm2.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Apr 20 10:49:29 CST 2018
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Apr 20 10:49:29 CST 2018
     */
    int updateByPrimaryKey(User record);

    @Select("select count(*) from `user` where username = #{username} and state = 0;")
    int isExistUsername(String username);

    @Insert("insert into `user` values (null, #{username}, #{password}, #{headUrl}, 0)")
    int insertUser(User user);

    @Select("select * from `user` where username = #{username} and password = #{password} and state = 0;")
    User findUserByUsernameAndPassword(User user);

    //@Select("select * from `user` where id = #{id} and state = 0;")
    User findUserById(Integer id);

    @Select("select * from `user` where username = #{username} and state = 0;")
    User findUserByUsername(String username);
}