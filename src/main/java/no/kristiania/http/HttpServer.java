package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {
    private final ServerSocket socket;
    private Path rootDirectory;

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
                String responseText = "<p>Hello world</p>";

                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + responseText.length() + "\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        responseText;
                clientSocket.getOutputStream().write(response.getBytes());

            } else {
               if (rootDirectory != null && Files.exists(rootDirectory.resolve(requestTarget.substring(1)))) {
                   String responseText = Files.readString(rootDirectory.resolve(requestTarget.substring(1)));


                   String response = "HTTP/1.1 200 OK\r\n" +
                           "Content-Length: " + responseText.length() + "\r\n" +
                           "Content-Type: text/html\r\n" +
                           "\r\n" +
                           responseText;
                   clientSocket.getOutputStream().write(response.getBytes());
                   return;
               }



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

    public void setRoot(Path rootDirectory) {

        this.rootDirectory = rootDirectory;
    }
}
