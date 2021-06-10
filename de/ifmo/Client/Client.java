package de.ifmo.Client;

import de.ifmo.Transmittable.Message;

import java.io.*;
import java.net.Socket;

public class Client
{
    private final String server;
    private final int port;
    private final Reader reader;

    public Client(String server, int port)
    {
        this.server = server;
        this.port = port;
        this.reader = new Reader();
    }

    public void startSession() throws Exception
    {
        if (this.reader.getScriptFile().equals(""))
            System.out.println("Welcome to the application! Type 'help' to see all possible commands!");
        String command;
        BufferedReader br;
        if (reader.getScriptFile().equals(""))
            br = new BufferedReader(new InputStreamReader(System.in));
        else
            br = new BufferedReader(new FileReader(reader.getScriptFile()));

        while (!(command = br.readLine()).equals("exit"))
        {
            try {
                Message msg = this.reader.readCommand(command);
                processRequest(msg);
                if (msg.getCommand().equals("execute_script"))
                {
                    this.reader.setScriptFile(msg.getKey());
                    startSession();
                    this.reader.setScriptFile("");
                }
            } catch (Exception e) { System.err.println(e.getMessage()); }
        }
        System.out.println("See you next time!");
    }

    public void processRequest(Message obj) throws Exception
    {
        try (Socket clientSocket = new Socket(server, port)) {
            ObjectOutputStream outputObj = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputObj = new ObjectInputStream(clientSocket.getInputStream());

            outputObj.writeObject(obj);
            outputObj.flush();
            Message result = (Message) inputObj.readObject();
            System.out.println(result.getResult());
        } catch (Exception e) { throw new Exception("Connection interrupted!"); }
    }

    public static void main(String[] args)
    {
        try {
            Client client = new Client("localhost",5454);
            client.startSession();
        } catch (Exception e) { System.err.println(e.getMessage()); }
    }
}
