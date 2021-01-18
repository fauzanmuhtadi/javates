package org.example.restapi.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.example.restapi.model.Jobs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Map;

public class Receiver {
    private final static String QUEUE_NAME = "feedbackDB";
    JSONArray job;
    Jobs dtlJob = null;
    String pesan = "";

    public void go() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        GetResponse response;
        do {
            response = channel.basicGet(QUEUE_NAME, true);
        } while (response == null);

        String message = new String(response.getBody(), "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
        this.pesan = message;

        System.out.println(" [x] Done");

        channel.close();
        connection.close();
    }

    public void goAryJob() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        GetResponse response;
        do {
            response = channel.basicGet(QUEUE_NAME, true);
        } while (response == null);

        String message = new String(response.getBody(), "UTF-8");
        System.out.println(" [x] Received '" + message + "'");

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(message);
        JSONArray ja = (JSONArray) json.get("getAllJobs");

        this.job = ja;

        System.out.println(" [x] Done");

        channel.close();
        connection.close();
    }

    public void goObj() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        GetResponse response;
        do {
            response = channel.basicGet(QUEUE_NAME, true);
        } while (response == null);

        String message = new String(response.getBody(), "UTF-8");
        System.out.println(" [x] Received '" + message + "'");

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(message);
        Map select = ((Map) json.get("getDetailJob"));

        String id = String.valueOf(select.get("id"));
        String type = String.valueOf(select.get("type"));
        String url = String.valueOf(select.get("url"));
        String created_at = String.valueOf(select.get("created_at"));
        String company = String.valueOf(select.get("company"));
        String company_url = String.valueOf(select.get("company_url"));
        String location = String.valueOf(select.get("location"));
        String title = String.valueOf(select.get("title"));
        String description = String.valueOf(select.get("description"));
        String how_to_apply = String.valueOf(select.get("how_to_apply"));
        String company_logo = String.valueOf(select.get("company_logo"));

        this.dtlJob = new Jobs(id, type, url, created_at, company, company_url, location, title, description, how_to_apply, company_logo);

        System.out.println(" [x] Done");

        channel.close();
        connection.close();
    }

    public String isi() {
        return pesan;
    }
    public JSONArray isiJob() {
        return job;
    }
    public Jobs detailJob() { return dtlJob; }
}
