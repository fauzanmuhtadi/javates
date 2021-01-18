package org.example.database.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.restapi.model.Jobs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class Tabel {
    private static final Logger log = LoggerFactory.getLogger(org.example.restapi.model.Jobs.class);
    String data;

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    public void goJob() throws IOException {
        log.info("Creating tables for job...");

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/final_project?useTimezone=true&serverTimezone=UTC&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("password123");

        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.execute("DROP TABLE IF EXISTS job");
        jdbcTemplate.execute("CREATE TABLE job(" +
                "id VARCHAR(255), type VARCHAR(255), url VARCHAR(255), created_at VARCHAR(255), company VARCHAR(255), company_url VARCHAR(255), location VARCHAR(255), title VARCHAR(255), description VARCHAR(255), how_to_apply VARCHAR(255), company_logo VARCHAR(255))");

        InputStream input = new URL("https://jobs.github.com/positions.json").openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            this.data = jsonText;
        } finally {
            input.close();
        }

        ObjectMapper mapper = new ObjectMapper();
        List<Jobs> jobs = Arrays.asList(mapper.readValue(this.data, Jobs[].class));

        log.info("[SAVE]");
        jobs.forEach(job -> {
            log.info("Saving...{}", job.getCompany());
            jdbcTemplate.update(
                    "insert into job (id, type, url, created_at, company, company_url, location, title, description, how_to_apply, company_logo) values(?,?,?,?,?,?,?,?,?,?,?)",
                    job.getId(), job.getType(), job.getUrl(), job.getCreated_at(), job.getCompany(), job.getCompany_url(), job.getLocation(), job.getTitle(), job.getDescription(), job.getHow_to_apply(), job.getCompany_logo());
        });
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
