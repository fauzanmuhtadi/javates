package org.example.database.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Sender {
    public void sendFeedback(String feedback) {
        String QUEUE_NAME = "feedbackDB";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, feedback.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + feedback + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
