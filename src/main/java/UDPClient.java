//Должен быть реализован на не блокируемых сетевых каналах

import commands.Execute_script;
import exceptions.ArgumentFromFileException;
import utility.*;
import utility.Console;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class UDPClient {
    static final int PORT = 50111;
    private static final Console console = new Console();
    private static final Validator validator = new Validator(console);
    static ByteBuffer inputBuffer = ByteBuffer.allocate(10240);
    static ByteBuffer outputBuffer = ByteBuffer.allocate(10240);
    static Selector selector;
    static DatagramChannel channel;
    static boolean exitStatus = false;
    static SessionWorker sessionWorker = new SessionWorker(console);
    static Session session;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        selector = Selector.open();
        channel = DatagramChannel.open();
        while (true){
            System.out.println("\t\tВведите имя пользователя");
            String username = console.nextLine();
            System.out.println("\t\tВведите пароль(поле может быть пустым)");
            String password = console.nextLine();
            if (password.equals("")){
                password = null;
            }
            session = sessionWorker.createSession(username,password);
            if (session != null){
                break;
            }
        }
        sendSession(session);
        if(!receiveSessionStatus(session)){
            System.out.println("Authorization error");
            System.exit(1);
        }
        System.out.println("Authorization successful");
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_WRITE);


        while (true) {
            if(selector.select(10000)>0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            read(key);
                        } else {
                            if (key.isWritable()) {
                                write(key);
                                if (exitStatus) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (exitStatus) {
                    break;
                }
            }else{
                System.out.println("\t\tPacket lost\n");
                channel.register(selector, SelectionKey.OP_WRITE);
            }
        }
        console.close();
        selector.close();
        channel.close();
    }

    static private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        inputBuffer.clear();
        channel.socket().setSoTimeout(10);
        channel.receive(inputBuffer);
        inputBuffer.flip();
        ObjectInputStream objectInputStream =
                new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
        Respond respond = (Respond) objectInputStream.readObject();

        System.out.println(respond.answer + "\n");

        key.interestOps(SelectionKey.OP_WRITE);
    }

    static private void write(SelectionKey key) throws IOException {
        if (!console.readFromFileStatus) {
            System.out.println("\t\tEnter the command");
        }
        String newCommand = console.nextLine();
        Command command = validator.validateCommand(newCommand);
        if (command != null) {
            if (command.getCommandName().equals("exit")) {
                exitStatus = true;
            }else {
                try {
                    command.setArgument(validator.validateArgument(command));
                    if (command.getCommandName().equals("execute_script")) {
                        new Execute_script(console).execute((String) command.getArgument());
                    } else {
                        command.setUsername(session.username);
                        outputBuffer.put(serialize(command));
                        outputBuffer.flip();
                        channel.send(outputBuffer, new InetSocketAddress(InetAddress.getLocalHost(), PORT));
                        outputBuffer.clear();
                        key.interestOps(SelectionKey.OP_READ);
                    }
                } catch (ArgumentFromFileException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println("\t\tWrong command\n");
        }

    }

    private static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    private static void sendSession(Session session) throws IOException {
        outputBuffer.put(serialize(session));
        outputBuffer.flip();
        channel.send(outputBuffer, new InetSocketAddress(InetAddress.getLocalHost(), PORT));
        outputBuffer.clear();
    }
    private static boolean receiveSessionStatus(Session session) throws IOException, ClassNotFoundException {
        inputBuffer.clear();
        channel.socket().setSoTimeout(10);
        channel.receive(inputBuffer);
        inputBuffer.flip();
        ObjectInputStream objectInputStream =
                new ObjectInputStream(new ByteArrayInputStream(inputBuffer.array()));
        Respond respond = (Respond) objectInputStream.readObject();
        return Boolean.parseBoolean(respond.answer);
    }
}
