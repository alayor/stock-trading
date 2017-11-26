package mockedIntegrationTests.tools;


import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class MockedServer extends NanoHTTPD {

    public MockedServer() {
        super(9999);
    }

    public void start() {
        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        String fixture = getFixture(session);
        return newFixedLengthResponse(Response.Status.OK, "application/json", fixture);
    }

    private String getFixture(IHTTPSession session) {
        InputStream inputStream = getClass().getResourceAsStream("fixtures" + session.getUri());
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return writer.toString();
    }
}