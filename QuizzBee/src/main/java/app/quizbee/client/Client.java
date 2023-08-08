package app.quizbee.client;

import app.quizbee.client.service.ClientService;
import app.quizbee.entity.Question;
import app.quizbee.entity.User;
import app.quizbee.event.CallbackData;
import app.quizbee.modal.MailModal;
import app.quizbee.modal.PlayerModal;
import app.quizbee.modal.RoomModal;
import app.quizbee.modal.ScoreModal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {

    private final String SERVER_IP;
    private final int PORT = 3000;
    private final Socket clientIO;
    private final Service service;

    public Client() throws FileNotFoundException {
        service = new Service();
        SERVER_IP = getServerIP();
        this.clientIO = IO.socket(URI.create("http://" + SERVER_IP + ':' + PORT), new IO.Options());
    }

    public void disconnect() {
        clientIO.disconnect();
    }

    public void connect() {
        clientIO.open();
    }

    public Socket getIO() {
        return clientIO;
    }

    public Service getService() {
        return service;
    }

    private String getServerIP() throws FileNotFoundException {
        FileInputStream input = new FileInputStream(getConfigFile());
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String readline = br.readLine();
            line = readline.substring(readline.indexOf("=") + 1).trim();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        return line;
    }

    private File getConfigFile() {
        File f = Paths.get(System.getProperty("user.dir")).toFile();
        File[] listFiles = new File[1];
        do {
            listFiles = f.listFiles((File dir, String name) -> {
                return name.equalsIgnoreCase("config.txt");
            });
            f = f.getParentFile();
            if (listFiles.length > 0) {
                break;
            }
        } while (listFiles.length == 0);
        return listFiles[0];
    }

    public class Service implements ClientService {

        public Service() {
        }

        @Override
        public void findUser(User user, CallbackData<Boolean> callback) {
            clientIO.emit("find", user.toJSON(), new Ack() {
                @Override
                public void call(Object... os) {
                    callback.call(Boolean.valueOf(os[0].toString()));
                }
            });
        }

        @Override
        public void sendMail(String toEmail, CallbackData<MailModal> callbackData) {
            clientIO.emit("sendMail", toEmail, new Ack() {
                @Override
                public void call(Object... os) {
                    Boolean success = Boolean.valueOf(os[0].toString());
                    String message = os[1].toString();
                    String code = os[2].toString();
                    MailModal mail = new MailModal(success, message, code);
                    callbackData.call(mail);
                }
            });
        }

        @Override
        public void register(User user) {
            clientIO.emit("register", user.toJSON());
        }

        @Override
        public void checkActive(User user, CallbackData<User> callback) {
            clientIO.emit("checkActive", user.toJSON(), new Ack() {
                @Override
                public void call(Object... os) {
                    User user = new User((JSONObject) os[0]);
                    callback.call(user);
                }
            });
        }

        @Override
        public void login(User user) {
            clientIO.emit("login", user.toJSON());
        }

        @Override
        public void logout(User user) {
            clientIO.emit("logout", user.toJSON());
        }

        @Override
        public void addRoom(RoomModal room) {
            clientIO.emit("addRoom", room.toJSON());
        }

        @Override
        public void autoFillRooms(CallbackData<List<RoomModal>> data) {
            clientIO.on("fillRooms", (Object... os) -> {
                List<RoomModal> temp = new ArrayList<>();

                JSONArray arrJSon = (JSONArray) os[0];

                for (int i = 0; i < arrJSon.length(); i++) {
                    RoomModal rm = null;
                    try {
                        rm = new RoomModal(arrJSon.getJSONObject(i));
                    } catch (JSONException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    temp.add(rm);
                }
                data.call(temp);
            });
        }

        @Override
        public void disconnect(User user) {
            clientIO.emit("disconnectServer", user.toJSON());
        }

        @Override
        public void removeRoom(RoomModal room) {
            clientIO.emit("removeRoom", room.toJSON());
        }

        @Override
        public void findRoom(String roomID) {
        }

        @Override
        public void addPlayer(PlayerModal player) {
            clientIO.emit("addPlayer", player.toJSON());
        }

        @Override
        public void getPlayers(String idRoom, CallbackData<List<PlayerModal>> players) {
            clientIO.on("getPlayers", (Object... os) -> {
                List<PlayerModal> temp = new ArrayList<>();

                JSONArray arrJSon = (JSONArray) os[0];

                for (int i = 0; i < arrJSon.length(); i++) {
                    PlayerModal rm = null;
                    try {
                        rm = new PlayerModal(arrJSon.getJSONObject(i));
                    } catch (JSONException ex) {
                        Logger.getLogger(Client.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                    temp.add(rm);
                }

                List<PlayerModal> trueList = temp.stream()
                        .filter(p -> p.getRoomID()
                        .equalsIgnoreCase(idRoom))
                        .collect(Collectors.toList());

                players.call(trueList);
            });
        }

        @Override
        public void updateStatus(PlayerModal playerModal) {
            clientIO.emit("updateStatus", playerModal.toJSON());
        }

        public void letPlay(CallbackData<List<Question>> data) {
            clientIO.on("letPlay", (Object... os) -> {
                List<Question> qList = null;
                try {
                    qList = new ObjectMapper().readValue(((JSONArray) os[0]).toString(),
                            new TypeReference<List<Question>>() {
                    });
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }

                data.call(qList);
            });
        }

        @Override
        public void checkReady(CallbackData<Boolean> started) {
            clientIO.emit("checkReady", (Ack) (Object... os) -> {
                started.call(Boolean.valueOf(os[0].toString()));
            });
        }

        @Override
        public void startGame(String idRoom, CallbackData<List<PlayerModal>> data) {
            clientIO.emit("startGame", idRoom, new Ack() {
                @Override
                public void call(Object... args) {
                    List<PlayerModal> qList = null;
                    try {
                        qList = new ObjectMapper().readValue(
                                ((JSONArray) args[0]).toString(),
                                new TypeReference<List<PlayerModal>>() {
                        });
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    data.call(qList);
                }
            });
        }

        @Override
        public void updateScore(ScoreModal sm) {
            clientIO.emit("updateScore", sm.toJSON());
        }

        @Override
        public void autoUpdateScore(CallbackData<List<ScoreModal>> callbackData) {
            clientIO.on("getTopScore", (Object... os) -> {
                List<ScoreModal> qList = null;
                try {
                    qList = new ObjectMapper().readValue(
                            ((JSONArray) os[0]).toString(),
                            new TypeReference<List<ScoreModal>>() {
                    });
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }

                callbackData.call(qList);
            });
        }

        public void addScore(ScoreModal scoreModal) {
            clientIO.emit("addScore", scoreModal.toJSON());
        }
    }
}
