package Server;

import Lib.Request;
import Lib.Response;
import Lib.User;
import Server.Program.CommandManager;

public class RequestIn {
    private CommandManager commandManager;

    public RequestIn(CommandManager commandManager) {
        this.commandManager = commandManager;
    }




    /**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
    public Response handle(Request request) {
        int responseCode = execute(request.getCommandName(), request.getCommandArgument(),
                request.getCommandObject(), request.getUser());
        return new Response(responseCode, ResponseOut.getToClient());
    }
    public int execute(String commandName, String commandArgument, Object commandObject, User user) {
        if (commandName == "exit") {
            return 2;
        }
        if (commandManager.startExecuteCommand(commandName, commandArgument, commandObject, user)) {
            return 1;
        }
        return 0;
    }

}