package app.quizbee.main;

import app.quizbee.client.Client;
import app.quizbee.gui.Dashboard;
import app.quizbee.gui.LoadingScreen;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.SwingUtilities;

public class Application {

    private static Client client;
    private static Dashboard dashboard;

    public static void main(String[] args) throws IOException, URISyntaxException {
        FlatCyanLightIJTheme.setup();
        client = new Client();
        dashboard = new Dashboard();
        SwingUtilities.invokeLater(() -> {
            new LoadingScreen(null, false).setVisible(true);
        });
    }

    public static Dashboard getDashboard() {
        return dashboard;
    }

    public static void setDashboard(Dashboard d) {
        dashboard = d;
    }

    public static Client getClient() {
        return client;
    }
}
