package org.example.database.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.database.rabbitmq.Sender;
import org.example.restapi.model.User;

import java.io.Reader;
import java.util.List;

public class mybatisLoginUser {
    public void go(String username, String password) throws Exception{
        String message = "";

        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        List<User> user = session.selectList("User.getAllUsers");

        for(User usr : user) {
            if (usr.getUsername().equals(username) && usr.getPassword().equals(password)) {
                message = Long.toString(usr.getId());
            } else {
                message = "User tidak terdaftar!";
            }
        }

        Sender sdr = new Sender();
        sdr.sendFeedback(message);

        session.commit();
        session.close();
    }
}
