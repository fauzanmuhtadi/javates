package org.example.database.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.example.database.mybatis.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

public class Receiver {
    public static void go() throws Exception {
        mybatisJobs mj = new mybatisJobs();
        mybatisJobId mji = new mybatisJobId();
        mybatisLoginUser mlu = new mybatisLoginUser();

        String QUEUE_NAME = "user";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1); // accept only one unack-ed message at a time (see below)
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

            JSONParser parser = new JSONParser();
            try {
                JSONObject json = (JSONObject) parser.parse(message);
                if (json.get("getJobs") != null) {
                    try {
                        mj.go();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println(" [x] Done");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    }
                } else if (json.get("getDetailJob") != null) {
                    try {
                        String id = (String) json.get("getDetailJob");
                        mji.go(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println(" [x] Done");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    }
                } else if (json.get("loginUser") != null) {
                    try {
                        Map loginUser = ((Map) json.get("loginUser"));
                        String username = String.valueOf(loginUser.get("username"));
                        String password = String.valueOf(loginUser.get("password"));
                        mlu.go(username, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println(" [x] Done");
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }
}
