package com.robosh.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import lombok.SneakyThrows;

public class Client {

  @SneakyThrows
  public static void main(String[] args) {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("169.254.190.155", 9090));

    BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));

    BufferedReader socketReader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));

    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

    printMessageFromSocket(socketReader);
    String userInput;
    while ((userInput = terminalReader.readLine()) != null) {
      printWriter.println(userInput);
      printWriter.flush();
      printMessageFromSocket(socketReader);
    }

  }

  private static void printMessageFromSocket(BufferedReader socketReader) throws IOException {
    String line;
    while ((line = socketReader.readLine()) != null) {
      if (line.isEmpty()) {
        break;
      }
      System.out.println(line);
    }
  }
}
