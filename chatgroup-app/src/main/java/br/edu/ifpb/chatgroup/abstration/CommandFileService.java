package br.edu.ifpb.chatgroup.abstration;

import br.edu.ifpb.chatgroup.model.Message;
import jcifs.smb.SmbFile;

import br.edu.ifpb.chatgroup.model.Message;
import java.util.*;

public interface CommandFileService {

    //lê no arquivo
    ArrayList<Message> readFile(SmbFile file);

    //escreve no arquivo
    void writeFile(SmbFile file, Message message);

}
