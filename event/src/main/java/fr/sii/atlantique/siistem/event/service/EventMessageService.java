package fr.sii.atlantique.siistem.event.service;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.compat.Future;
import akka.stream.ActorMaterializer;
import akka.stream.alpakka.amqp.DefaultAmqpConnection;
import akka.stream.alpakka.amqp.IncomingMessage;
import akka.stream.alpakka.amqp.NamedQueueSourceSettings;
import akka.stream.alpakka.amqp.QueueDeclaration;
import akka.stream.alpakka.amqp.javadsl.AmqpSource;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sii.atlantique.siistem.event.model.Event;

/**
 * Created by sii on 19/07/2017.
 */
public class EventMessageService {

    public static final String QUEUE_NAME = "titi";
    public static final int BUFFER_SIZE = 10;

    public static void main(String[] args) {

        final QueueDeclaration queueDeclaration = QueueDeclaration.create(QUEUE_NAME);

        final Source<IncomingMessage, NotUsed> amqpSource = AmqpSource.create(
                NamedQueueSourceSettings.create(
                        DefaultAmqpConnection.getInstance(),
                        QUEUE_NAME
                ).withDeclarations(queueDeclaration),
                BUFFER_SIZE
        );

        amqpSource.map(IncomingMessage::bytes)
                .map(ByteString::utf8String)
                .map(v -> {
                    System.out.println(v);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(v, Event.class);
                })
                .runWith(Sink.seq(), ActorMaterializer.create(ActorSystem.create()));
    }

}
