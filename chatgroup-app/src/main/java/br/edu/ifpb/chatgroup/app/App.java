package br.edu.ifpb.chatgroup.app;

import br.edu.ifpb.chatgroup.controller.CommandFileController;
import br.edu.ifpb.chatgroup.model.Message;
import br.edu.ifpb.chatgroup.service.CommandFileServiceImpl;
import jcifs.smb.*;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;

public class App {
	private static String login = "";
	
	private static void printMessages(PrintStream consoleOuput, ArrayList<Message> messages, boolean withMe){
		for (Message m : messages){
			if (!m.getId().equals(login) || withMe){
				String line = String.format("%s (%s): %s", m.getId(), m.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE), m.getMensagem());
	            consoleOuput.print(line + "\n");
			}
			
        }
	}

    public static void main(String[] args) {

        CommandFileController commandFileController = new CommandFileController(new CommandFileServiceImpl());
        SmbFile file = null;

        final Logger log = Logger.getAnonymousLogger();

        //autentica a entrada na pasta compartilhada
        final String username = "rodger";
        final String password = "secret";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);

        //tentar acessar o arquivo
        try {
            file = new SmbFile("smb://10.42.0.1/chat-sd/db.txt", auth);
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Arquivo não encontrado!\n" + e.getMessage());
        }

        final int OPTION_LOGIN = 0;
        final int OPTION_MESSAGE = 1;
        final int OPTION_EXIT = 3;

        //autorização
        List<String> authorizedLogin = new ArrayList<String>();
        authorizedLogin.add("cliente01");
        authorizedLogin.add("cliente02");
        authorizedLogin.add("cliente03");
        authorizedLogin.add("cliente04");

        int optionMenu = OPTION_LOGIN;

        System.out.print("Digite seu nickname para login: ");
        Scanner scannerLogin = new Scanner(System.in);
        login = scannerLogin.nextLine();
        //
        final PrintStream consoleOuput = System.out;
        if (authorizedLogin.contains(login)){
        	
            //caso o login dê certo apresenta uma mensagem
            consoleOuput.println("\n--- Mensagens Anteriores ---");
            printMessages(consoleOuput, commandFileController.readFile(file), true);
            
            //TODO: como controlar para apresentar a última mensagem
            Scanner scannerMessage = new Scanner(System.in);
            while (optionMenu != OPTION_EXIT) {
                //ler do console e enviar para o arquivo
                consoleOuput.print("Eu: ");
                String messageText = scannerMessage.nextLine();//bloqueante
                //construir a mensagem e escrever no arquivo
                Message message = new Message(login, messageText, LocalDateTime.now());
                commandFileController.writeFile(file, message);
                //ler do arquivo //TODO: ler apenas o que precisa
                ArrayList<Message> messages = commandFileController.readFile(file);
                //escreve no arquivo
                printMessages(consoleOuput, messages, false);
            }

        } else consoleOuput.println("Login inválido!");

    }
    //TODO: recuperar apenas a última mensagem, depois que for lido as mensagens antigas
    //TODO: criar um looping para recuperar as mensagens e um timout para escrita (10s)
}
