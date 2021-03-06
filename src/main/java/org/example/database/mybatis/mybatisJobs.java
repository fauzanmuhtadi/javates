package org.example.database.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.database.rabbitmq.Sender;
import org.example.restapi.model.Jobs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class mybatisJobs {
    public void go() throws Exception {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        List<Jobs> getJob = session.selectList("User.getJob");

        JSONArray ja = new JSONArray();

        for(Jobs job : getJob ) {
            Map m = new LinkedHashMap(11);
            m.put("id", job.getId());
            m.put("type", job.getType());
            m.put("url", job.getUrl());
            m.put("created_at", job.getCreated_at());
            m.put("company", job.getCompany());
            m.put("company_url", job.getCompany_url());
            m.put("location", job.getLocation());
            m.put("title", job.getTitle());
            m.put("description", job.getDescription());
            m.put("how_to_apply", job.getHow_to_apply());
            m.put("company_logo", job.getCompany_logo());

            ja.add(m);
        }

        JSONObject json = new JSONObject();
        json.put("getAllJobs", ja);
        String message = json.toString();

        Sender sdr = new Sender();
        sdr.sendFeedback(message);

        session.commit();
        session.close();
    }
}
