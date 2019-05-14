package br.edu.ifpb.chatgroup.service;

import br.edu.ifpb.chatgroup.abstration.CommandFileService;
import br.edu.ifpb.chatgroup.model.Message;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import java.io.*;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

import br.edu.ifpb.chatgroup.model.Message;
import java.time.LocalDateTime;

public class CommandFileServiceImpl implements CommandFileService {

    private final Logger log = Logger.getAnonymousLogger();

    @Override
    public ArrayList<Message> readFile(SmbFile file) {

        String conteudo = "";
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new SmbFileInputStream(file)));
        } catch (UnknownHostException e) {
            log.log(Level.WARNING, "Erro: Host não encontrado.\n" + e.getMessage());
        } catch (SmbException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Erro: URL mal formada.\n" + e.getMessage());
        }

        ArrayList<Message> result = new ArrayList<Message>();
        try {
            //
            String line = null;
            do {
                line = reader.readLine();//Unmarshal
                //unmarshal
                if (line != null){
                    String[] attrs = line.split("\\|\\|");
                    Message m = new Message(attrs[0], attrs[1], LocalDateTime.parse(attrs[2]));
                    result.add(m);
                }
            } while(line != null);
            reader.close();
            //
            return result;
        } catch (IOException e) {
            log.log(Level.WARNING, "Erro: Não foi possível ler o arquivo.\n" + e.getMessage());
            return null;
        }
    }

    @Override
    public void writeFile(SmbFile file, Message message) {

        BufferedWriter writer = null;

        try {
            //
            StringBuilder sb = new StringBuilder();
            sb.append(message.getId());
            sb.append("||");
            sb.append(message.getMensagem());
            sb.append("||");
            sb.append(message.getTimestamp());
            //
            writer = new BufferedWriter(new OutputStreamWriter(new SmbFileOutputStream(file, true)));
            writer.write(sb.toString());//Marshal
            writer.newLine();
        } catch (UnknownHostException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (SmbException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, e.getMessage());
        } catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.log(Level.WARNING, e.getMessage());
                }
            }
        }
    }

}
