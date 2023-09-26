import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Consumidor {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection conexao = connectionFactory.newConnection();
        Channel canal = conexao.createChannel();

        canal.basicQos(1);

        String NOME_FILA = "teste"+ "";
        canal.queueDeclare(NOME_FILA, true, false, false, null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody()); 
            System.out.println("Eu" + consumerTag + " recebi: " + mensagem);  
            try {
                doWork(mensagem);   
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println(" [x] Done");
            }
            
        };
        boolean autoAck = true;

        canal.basicConsume(NOME_FILA, autoAck, callback, consumerTag -> {
            System.out.println("Cancelaram a fila: " + NOME_FILA);
        });
    }
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}


