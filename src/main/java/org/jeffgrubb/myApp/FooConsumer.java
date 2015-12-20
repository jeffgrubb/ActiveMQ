/**
 * Created by jgrubb on 12/13/15.
 */

package org.jeffgrubb.myApp;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileNotFoundException;


public class FooConsumer implements Runnable
{
    @Override
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

            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("TEST.FOO");

            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive(1000);

            if(message != null) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                } else {
                    System.out.println("Received:" + message);
                }
            }

            consumer.close();
            session.close();
            connection.close();
        } catch(Exception ex) {
            System.out.println("Caught exception: " + ex);
            ex.printStackTrace();
        }
    }
}
