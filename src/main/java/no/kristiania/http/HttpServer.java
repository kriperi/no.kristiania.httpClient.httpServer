package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {
    private final ServerSocket socket;

    public HttpServer(int serverPort) throws IOException {
        socket = new ServerSocket(serverPort);
        new Thread(this::handleClients).start();
    }

    private void handleClients() {
        try {
            Socket clientSocket = socket.accept();

            String[] requestLine = HttpClient.readLine(clientSocket).split(" ");
            String requestTarget = requestLine[1];

            if (requestTarget.equals("/hello")) {
                String responseText = "Hello world";

                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseText.length() + "\r\n" +
                        "\r\n" + responseText;
                clientSocket.getOutputStream().write(response.getBytes());

            } else {
                String responseText = "File not found: " + requestTarget;

                String response = "HTTP/1.1 404 NOT FOUND\r\n" +
                        "Content-Length: " + responseText.length() + "\r\n" +
                        "\r\n" + responseText;
                clientSocket.getOutputStream().write(response.getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new HttpServer (1962);

    }

    public int getPort() {
        return socket.getLocalPort();
    }
}
