package client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.print("Podaj nazwę użytkownika: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.print("Podaj ścieżkę do pliku CSV: ");
        String filepath = scanner.nextLine();
        sendData(name,filepath);
    }

    public static void sendData(String name, String filepath){

        try {
            Socket socket = new Socket("localhost", 1234);
            PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
            printWriter.println(name);
            Files.lines(Path.of(filepath)).forEach(line -> {
                printWriter.println(line);
                try{
                        Thread.sleep(1);
//                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Sent: " + line);
            });
//            printWriter.println("Bye~");
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

