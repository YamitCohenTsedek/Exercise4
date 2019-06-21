package com.example.Exercise4;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSide {
    private String ip;
    private int port;
    private Socket clientSocket = null;
    private Boolean isConnectedToSimulator = false;
    private OutputStream stream;
    private PrintWriter writer;
    private static ClientSide instance = null;

    private ClientSide() {
    }

    public static ClientSide getInstance() {
        if(instance == null)
            instance = new ClientSide();
        return instance;
    }

    public void setIpAndPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void Connect() {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    InetAddress serverAddress = InetAddress.getByName(ip);
                    clientSocket = new Socket(serverAddress, port);
                    stream = clientSocket.getOutputStream();
                    writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    // send the commands to the simulator
    public void SendCommandsToSimulator(final String command)
    {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    if (clientSocket != null && writer != null) {
                        writer.println(command);
                        writer.flush();
                    }
                }
                catch(Exception e) {
                    Log.e("TCP", "S: Error", e);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /* close the connection with the server */
    public void closeSocket() {
        try {
            if(clientSocket != null) {
                clientSocket.close();
                isConnectedToSimulator = false;
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}