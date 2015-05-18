/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.message.jms;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.jboss.aerogear.unifiedpush.message.exception.MessageDeliveryException;

/**
 * Simplifies sending of messages to destination
 */
public abstract class AbstractJMSMessageProducer {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    /**
     * Sends message to destination
     */
    protected void send(Destination destination, Serializable message) {
        send(destination, message, null, null);
    }

    /**
     * Sends message to destination with given JMS message property name and value
     */
    protected void send(Destination destination, Serializable message, String propertyName, String propertValue) {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(destination);
            connection.start();
            ObjectMessage objectMessage = session.createObjectMessage(message);
            if (propertyName != null) {
                objectMessage.setStringProperty(propertyName, propertValue);
            }
            messageProducer.send(objectMessage);
        } catch (JMSException e) {
            throw new MessageDeliveryException("Failed to queue push message for further processing", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}