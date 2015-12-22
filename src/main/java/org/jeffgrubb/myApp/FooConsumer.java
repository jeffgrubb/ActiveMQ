/**
 * Created by jgrubb on 12/13/15.
 */

package org.jeffgrubb.myApp;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class FooConsumer extends MQBase
{
    @Override
    public void run() {
        try {
            super.run();

            Session session = super._connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

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
            _connection.close();

        } catch(Exception ex) {
            System.out.println("Caught exception: " + ex);
            ex.printStackTrace();
        }
    }
}
