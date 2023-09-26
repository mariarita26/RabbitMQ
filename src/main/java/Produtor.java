import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Produtor {

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);
        try (
                Connection connection = connectionFactory.newConnection();
                Channel canal = connection.createChannel();
        ) {
            String mensagem = "Oi eu sou Maria Rita...";
            String NOME_FILA = "teste";

            canal.queueDeclare(NOME_FILA, true, false, false, null);

            canal.basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes());
            System.out.println("Mensagem enviada: " + mensagem);

        }
    }
}


