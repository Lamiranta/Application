package de.ifmo.Server;

import de.ifmo.Transmittable.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server
{
    private final Commander commander;
    private final int port;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;

    public Server(String fileName, int port)
    {
        this.commander = new Commander(fileName);
        this.port = port;
    }

    public void startSession()
    {
        openConnection();
        System.out.println("Welcome to the server terminal! Type 'save' to save current collection or 'exit' to end the session!");

        while(!isStopped())
        {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped())
                {
                    System.out.println("The server session is ended!");
                    return;
                }
                throw new RuntimeException("Error accepting client connection!", e);
            }
            try {
                processClientRequest(clientSocket);
            } catch (Exception e) { e.printStackTrace(); }
        }
        System.out.println("The server session is ended!");
    }

    private void processClientRequest(Socket clientSocket)
    {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            Message obj = (Message) inputStream.readObject();
            String result = this.commander.executeCommand(obj);
            obj.setResult(result);
            outputStream.writeObject(obj);
        } catch (Exception e) { System.out.println("Error, server not working!"); }
    }

    private boolean isStopped() { return this.isStopped; }

    public void stopSession()
    {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) { throw new RuntimeException("Error closing server!", e); }
    }

    private void openConnection()
    {
        try {
            this.serverSocket = new ServerSocket(this.port);
            new Thread(() ->
            {
                Scanner sc = new Scanner(System.in);
                String serverCommand;
                while (!(serverCommand = sc.nextLine()).equals("exit"))
                {
                    if ("save".equals(serverCommand))
                        System.out.println(this.commander.save());
                    else System.out.println("Unknown command: " + serverCommand);
                }
                stopSession();
            }).start();
        } catch (IOException e) { throw new RuntimeException("Cannot connect to the port " + this.port, e); }
    }

    public static void main(String[] args)
    {
        try {
            String fileName = System.getenv("fileName");
            String[] temp = fileName.split("\\.");
            if (fileName.equals("") || !temp[1].equals("csv") || temp.length > 2)
                throw new Exception("The name of your file is invalid!");
            Server server = new Server(fileName, 5454);
            server.startSession();
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
}
