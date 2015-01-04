package com.woutwoot.tickets;

import com.woutwoot.tickets.commands.QuestionCommand;
import com.woutwoot.tickets.commands.ReportCommand;
import com.woutwoot.tickets.commands.TicketCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author woutwoot
 *         Created by on 1/01/2015 - 22:00.
 */
public class Main extends JavaPlugin {

    private static Main instance;
    private static TicketManager manager;

    private TicketCommand ticketCommand = new TicketCommand();
    private ReportCommand reportCommand = new ReportCommand();
    private QuestionCommand questionCommand = new QuestionCommand();

    @Override
    public void onEnable(){
        instance = this;
        manager = new TicketManager();
        this.getCommand("ticket").setExecutor(ticketCommand);
        this.getCommand("report").setExecutor(reportCommand);
        this.getCommand("question").setExecutor(questionCommand);
        manager.loadTickets();
    }

    @Override
    public void onDisable(){
        manager.saveTickets();
    }

    public static Main getInstance() {
        return instance;
    }

    public static TicketManager getManager() {
        return manager;
    }

}
