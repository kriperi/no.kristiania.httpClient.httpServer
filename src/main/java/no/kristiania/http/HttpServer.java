package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        Socket clientSocket = serverSocket.accept();

        String requestLine = HttpClient.readLine(clientSocket);

        System.out.println(requestLine);

        String response = "<h1>Hello world!</h1>";

        clientSocket.getOutputStream().write((
                        "HTTP/1.1 200 OK \r\n" +
                        "Content-Length: " + response.length() + "\r\n" +
                        "Connection: Close\r\n" +
                        "\r\n"  +
                        response
        ).getBytes());
    }
}
