package offlineIntegrationTests;

import offlineIntegrationTests.tools.MockedServerEndpoint;
import org.glassfish.tyrus.server.Server;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.model.DiffOrderResult;
import service.tools.web_socket.BitsoEndpoint;
import service.tools.web_socket.BitsoMessageHandler;
import service.tools.web_socket.BitsoWebSocketClient;

import javax.websocket.DeploymentException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BitsoWebSocketClient_OfflineITest {
    private BitsoWebSocketClient client;
    private BitsoMessageHandler clientMessageHandler;
    private BitsoEndpoint clientEndpoint;
    private static Server server =
      new Server("localhost", 8025, "/bitso", null, MockedServerEndpoint.class);

    @BeforeClass
    public static void beforeClass() throws DeploymentException {
        server.start();
    }

    @Before
    public void setUp() throws Exception
    {
        clientMessageHandler = new BitsoMessageHandler();
        clientEndpoint = new BitsoEndpoint(clientMessageHandler);
        client = new BitsoWebSocketClient(new URI("ws://localhost:8025/bitso/mock"), clientEndpoint);
    }

    @Test
    public void shouldSubscribeSuccessfully() throws Exception {
        // when
        client.connect();
        // then
        int count = 5;
        while(count-- > 0) {
            Thread.sleep(1000);
            if( clientMessageHandler.wasSuccessfullySubscribed()) {
                return;
            }
        }
        throw new AssertionError("No subscription response message found.");
    }

    @Test
    public void shouldReturnLastOrders() throws Exception {
        // given
        client.connect();
        List<DiffOrderResult> list = new ArrayList<>();
        // when
        list.add(clientMessageHandler.getNext());
        list.add(clientMessageHandler.getNext());
        clientEndpoint.sendMessage("{\"stop\": \"true\"}");
        // then
        assertEquals(2, list.size());
    }

}
