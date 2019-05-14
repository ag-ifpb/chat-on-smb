package br.edu.ifpb.chatgroup.controller;

import br.edu.ifpb.chatgroup.abstration.CommandFileService;
import br.edu.ifpb.chatgroup.model.Message;
import br.edu.ifpb.chatgroup.service.CommandFileServiceImpl;
import jcifs.smb.SmbFile;

import java.util.*;

import br.edu.ifpb.chatgroup.model.Message;

public class CommandFileController {

    private CommandFileService commandFileService;

    public CommandFileController (CommandFileServiceImpl commandFileServiceImpl) {
        this.commandFileService = commandFileServiceImpl;
    }

    public ArrayList<Message> readFile(SmbFile file) {
        return commandFileService.readFile(file);
    }

    public void writeFile(SmbFile file, Message message){
        commandFileService.writeFile(file, message);
    }

}
