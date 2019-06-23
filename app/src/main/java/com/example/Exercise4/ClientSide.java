package com.example.Exercise4;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientSide {
    private String ip;
    private int port;
    private Socket clientSocket = null;
    private PrintWriter writer;
    private static ClientSide instance = null;

    // singleton design pattern
    public static ClientSide getInstance() {
        if(instance == null)
            instance = new ClientSide();
        return instance;
    }

    // set the ip and the port for the communication
    public void setIpAndPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // connect to the server
    public void Connect() {
        // define a runnable that applies the connection to the server
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    InetAddress serverAdd = InetAddress.getByName(ip);
                    // create a new socket
                    clientSocket = new Socket(serverAdd, port);
                    // get the output stream from the socket and wrap it with a writer
                    writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        // run the runnable in a separate thread
        Thread thread = new Thread(runnable);
        thread.start();
    }

    // send commands to the simulator (the server)
    public void SendCommandsToSimulator(final String command)
    {
        // define a runnable that writes messages to the server
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    if (clientSocket != null && writer != null) {
                        writer.print(command);
                        writer.flush();
                    }
                }
                catch(Exception e) {
                    Log.e("TCP", "S: Error", e);
                }
            }
        };
        // run the runnable in a separate thread
        Thread thread = new Thread(runnable);
        thread.start();
    }

    // close the connection to the server
    public void closeSocket() {
        try {
            if(clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}