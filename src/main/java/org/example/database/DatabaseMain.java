package org.example.database;

import org.example.database.rabbitmq.Receiver;
import org.example.database.util.Tabel;

public class DatabaseMain {
    public static void main(String[] args) throws Exception {
        Tabel tbl = new Tabel();
        Receiver rcv = new Receiver();

        tbl.goJob();
        rcv.go();
    }
}
