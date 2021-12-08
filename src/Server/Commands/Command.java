package Server.Commands;

import Lib.User;

public interface Command {
    String getName();
    String getDescr();
    boolean startExecute(String arg, Object o, User user);
}