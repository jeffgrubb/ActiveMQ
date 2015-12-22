package org.jeffgrubb.myApp;

/**
 * Created by jgrubb on 12/21/15.
 */

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public abstract class MQBase implements Runnable {

    protected Connection _connection = null;

    public void run() {

        try {

            Properties properties = new Properties();

            InputStream stream = getClass().getClassLoader().getResourceAsStream("config.properties");

            if(stream != null)
            {
                properties.load(stream);
            } else {
                throw new FileNotFoundException("Property file config.properties not found in classpath");
            }

            String server = properties.getProperty("ActiveMQServer");
            String port = properties.getProperty("ActiveMQPort");

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + server + ":" + port + "?trace=false&soTimeout=60000");

            _connection = connectionFactory.createConnection();
            _connection.start();

        } catch(Exception ex) {
            System.out.println("Caught exception " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
