package org.example.restapi.controller;

import org.example.restapi.model.Jobs;
import org.example.restapi.rabbitmq.Receiver;
import org.example.restapi.rabbitmq.Sender;
import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

@RestController
@RequestMapping("/")
public class Controller {
    Sender sdr = new Sender();
    private String data;

    @PostMapping("login/{username}/{password}")
    public String getUserByEmailPass(@PathVariable String username, @PathVariable String password) {
        Receiver rcv = new Receiver();
        try {
            sdr.loginUser(username, password);
            rcv.go();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        return rcv.isi();
    }

    @GetMapping("getAllJobs")
    public JSONArray listAllJobs() {
        Receiver rcv = new Receiver();
        try {
            sdr.listJob();
            rcv.goAryJob();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        return rcv.isiJob();
    }

    @GetMapping("getJobById/{id}")
    public Jobs detailJob(@PathVariable String id) {
        Receiver rcv = new Receiver();
        try {
            sdr.detailJob(id);
            rcv.goObj();
        }catch (Exception e){
            System.out.println("error = " + e);
        }
        return rcv.detailJob();
    }

    @PostMapping("downloadAllJobs")
    public void downloadAllJobs() {
        try {
            InputStream input = new URL("https://jobs.github.com/positions.json").openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            int i = 0;
            while ((i = reader.read()) != -1) {
                this.data += String.valueOf((char) i);
            }
            reader.close();
            input.close();

            FileOutputStream fout = new FileOutputStream("D:\\positions.csv");
            byte b[] = this.data.getBytes();
            fout.write(b);
            fout.close();
            System.out.println("Successfully download!");
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
    }
}