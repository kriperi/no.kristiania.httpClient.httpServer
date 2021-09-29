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

        String html = "<p>Hallååå</p>";
        String contentType = "text/html; charset=utf-8";

        String response = "HTTP/1.1 200 OK \r\n" +
                "Content-Length: " + html.getBytes().length + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: Close\r\n" +
                "\r\n"  +
                html;

        clientSocket.getOutputStream().write(response.getBytes());

    }
}
