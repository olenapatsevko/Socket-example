package com.robosh.server;

import static com.robosh.util.StringUtil.removeSpaces;

import com.robosh.service.Translator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.SneakyThrows;

public class MyServer implements Runnable {

  private Socket socket;
  private PrintWriter printWriter;

  public MyServer(Socket socket) {
    this.socket = socket;
  }


  @Override
  @SneakyThrows
  public void run() {
    BufferedReader socketInputReader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));
    printWriter = new PrintWriter(socket.getOutputStream(), true);

    printWriter.println("Hello! It is simple translator where we use TCP Socket. \n"
        + "Write code of language from which you want to translate, "
        + "than code of language to which you want to translate and your word. \n"
        + "Example: en ru Hello \n");

    String input;
    while ((input = socketInputReader.readLine()) != null) {
      printWriter.println("Translating...");
      System.out.println("\nReceived: " + input);
      String translated = getTranslatedWord(input);
//      String translated = "Response";
      System.out.println("Translate: " + translated);
      printWriter.println("Translate: " + translated);
      printWriter.println();
    }
  }

  private String getTranslatedWord(String input) {
    String[] inputWords = removeSpaces(input).split("\\s");
    System.out.println("From language: " + inputWords[0]);
    System.out.println("To language: " + inputWords[1]);
    int startIndex = input.indexOf(inputWords[2]);
    String word = input.substring(startIndex);
    System.out.println("Word: " + word);
    return Translator.translate(inputWords[0], inputWords[1], word);
  }
}
