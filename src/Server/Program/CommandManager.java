package Server.Program;

import Lib.Exceptions.WrongCommandException;
import Lib.User;
import Server.Commands.AbstractCommand;
import Server.Commands.Command;
import Server.ResponseOut;

import java.util.*;

public class CommandManager {
    private HashMap<String, Command> commands = new HashMap<String, Command>();
    private Stack<String> scriptStack;

    public CommandManager(Command... commands) {
        for (Command command : commands){
            this.commands.put(command.getName(), command);
        }
        scriptStack = new Stack<>();
    }



    /**
     * Add new command to CM
     * **/
    public void addCommand(AbstractCommand command){
        commands.put(command.getName(),command);
    }

    /**
     * Get all commands
     * **/
    public HashMap<String, Command> getCommands(){
        return this.commands;
    }

    public boolean contains(String commandName){
        try{
            if (!commands.containsKey(commandName)) throw new WrongCommandException("Команды "+ commandName + " нет в программе");
            return true;
        } catch(WrongCommandException e){
            Console.println(e.getMessage());
            return false;
        }
    }
    /**
     * Launchs the command.
     * @return Exit status of executable command
     */
    public boolean startExecuteCommand(String command, String key, Object o, User user) {
        if(contains(command)){
            switch (command) {
                case "help": return help();
                default: return commands.get(command).startExecute(key, o, user);
            }
        } else {return false;}
    }


    /**
     * Executes HelpCommand
     * @return exit status of command
     * **/
    public boolean help(){
        if(contains("help")) {
            String result = "";
            HashMap<String, Command> commands = this.commands;

            for(Map.Entry<String, Command> entry : commands.entrySet()) {
                String key = entry.getKey();
                Command command = entry.getValue();
                result += String.format("%-30s%-1s%n",command.getName(), command.getDescr() + "\n");
            }
            ResponseOut.println(result);
            return true;
        } else {
            return false;
        }

    }

}