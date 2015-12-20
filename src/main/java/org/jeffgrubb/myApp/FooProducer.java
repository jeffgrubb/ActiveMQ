/**
 * Created by jgrubb on 12/13/15.
 */

package org.jeffgrubb.myApp;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class FooProducer implements Runnable
{
    @Override
    public void run()
    {
        try {
            //ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://activemq.jeffgrubb.org");

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://activemq.jeffgrubb.org:61616?trace=false&soTimeout=60000");

            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("TEST.FOO");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            String txt = "Hello World! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
            TextMessage message = session.createTextMessage(txt);

            System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);

            session.close();
            connection.close();
        }
        catch(Exception ex)
        {
            System.out.println("Caught exception: " + ex);
            ex.printStackTrace();
        }
    }
}
