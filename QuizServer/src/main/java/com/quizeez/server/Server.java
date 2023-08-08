package com.quizeez.server;

import app.quizbee.entity.User;
import app.quizbee.modal.PlayerModal;
import app.quizbee.modal.RoomModal;
import app.quizbee.modal.ScoreModal;
import app.quizbee.service.UserService;
import app.quizbee.service.MailService;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Server {

    private final UserService userService = new UserService();

    private final SocketIOServer serverIO;
    private final JTable tableControl;

    private final DefaultTableModel model;

    private final List<RoomModal> lstRoom;
    private final List<PlayerModal> lstPlayer;
    private List<ScoreModal> lstScore;

    public Server(int onPort, JTable tableControl) {
        this.lstPlayer = new ArrayList<>();
        this.lstRoom = new ArrayList<>();
        this.lstScore = new ArrayList<>();
        Configuration config = new Configuration();
        config.setPort(onPort);
        serverIO = new SocketIOServer(config);
        this.tableControl = tableControl;
        model = (DefaultTableModel) this.tableControl.getModel();
        initEvent();
    }

    public void connectEvent() {
        serverIO.addConnectListener((SocketIOClient sioc) -> {
            sioc.sendEvent("fillRooms", lstRoom);
            fillTable(true);
        });
    }

    public void disconnectEvent() {
        serverIO.addEventListener("disconnectServer", User.class,
                (SocketIOClient sioc, User t, AckRequest ar) -> {
                    if (userService.find(t) != null) {
                        userService.logout(t);
                    }
                });

        serverIO.addDisconnectListener((SocketIOClient sioc) -> {
            fillTable(false);
        });
    }

    private void fillTable(boolean isOnline) {
        model.setRowCount(0);

        serverIO.getAllClients().forEach(client -> {
            if (isOnline) {
                model.addRow(new Object[]{client.getSessionId(), "", "Connected"});
            } else {
                model.addRow(new Object[]{client.getSessionId(), "", "Disconnecteds"});
            }
        });
    }

    public void startServer() {
        serverIO.start();
    }

    public void shutDown() {

        serverIO.stop();
    }

    private void initEvent() {
        findUser();

        sendMail();

        register();

        checkActive();

        login();

        logout();

        addRoom();

        addPlayer();

        updateStatus();

        checkReady();

        startGame();

        addScore();

        updateScore();
    }

    public void updateScore() {
        serverIO.addEventListener("updateScore", ScoreModal.class,
                (SocketIOClient sioc, ScoreModal t, AckRequest ar) -> {
                    lstScore = lstScore.stream()
                            .sorted(Comparator.comparing(ScoreModal::getScore))
                            .collect(Collectors.toList());
                    for (ScoreModal sm : lstScore) {
                        if (sm.getName().equals(t.getName())) {
                            sm.setScore(t.getScore());
                            serverIO.getAllClients()
                                    .forEach(client
                                            -> client.sendEvent(
                                            "getTopScore",
                                            lstScore
                                    ));
                            break;
                        }
                    }
                });
    }

    public void addScore() {
        serverIO.addEventListener("addScore", ScoreModal.class,
                (SocketIOClient sioc, ScoreModal t, AckRequest ar) -> {
                    lstScore.add(t);
                });
    }

    public void startGame() {
        serverIO.addEventListener("startGame", String.class,
                (SocketIOClient sioc, String t, AckRequest ar) -> {
                    serverIO.getAllClients().forEach(client -> {
                        client.sendEvent("letPlay",
                                lstRoom.stream()
                                        .filter((room) -> room.getRoomID().equals(t))
                                        .toList()
                                        .get(0)
                                        .getList()
                        );
                    });
                    ar.sendAckData(lstPlayer);
                });
    }

    public void checkReady() {
        serverIO.addEventListener("checkReady", Void.class,
                (SocketIOClient sioc, Void t, AckRequest ar) -> {
                    boolean anyMatch = lstPlayer
                            .stream()
                            .allMatch(player -> player.isReady() == true);
                    ar.sendAckData(anyMatch);
                });
    }

    public void updateStatus() {
        serverIO.addEventListener("updateStatus",
                PlayerModal.class,
                (SocketIOClient sioc, PlayerModal t, AckRequest ar) -> {
                    for (PlayerModal pM : lstPlayer) {
                        if (pM.getPlayerID().equals(t.getPlayerID())) {
                            pM.setReady(t.isReady());
                            serverIO.getAllClients()
                                    .forEach(client
                                            -> client.sendEvent("getPlayers",
                                            lstPlayer));
                            break;
                        }
                    }
                });
    }

    public void addPlayer() {
        serverIO.addEventListener("addPlayer",
                PlayerModal.class,
                (SocketIOClient sioc, PlayerModal t, AckRequest ar) -> {
                    if (!lstPlayer.contains(t)) {
                        lstPlayer.add(t);
                    }
                    for (SocketIOClient client : serverIO.getAllClients()) {
                        client.sendEvent("getPlayers", lstPlayer);
                    }
                });
    }

    public void addRoom() {
        serverIO.addEventListener("addRoom", RoomModal.class,
                (SocketIOClient sioc, RoomModal t, AckRequest ar) -> {
                    if (!find(t)) {
                        lstRoom.add(t);
                    }
                    for (SocketIOClient client : serverIO.getAllClients()) {
                        client.sendEvent("fillRooms", lstRoom);
                    }
                });
    }

    public void logout() {
        serverIO.addEventListener("logout", User.class,
                (SocketIOClient sioc, User t, AckRequest ar) -> {
                    userService.logout(t);
                    sioc.disconnect();
                });
    }

    public void login() {
        serverIO.addEventListener("login", User.class,
                (SocketIOClient sioc, User t, AckRequest ar) -> {
                    userService.login(t);
                });
    }

    public void checkActive() {
        serverIO.addEventListener("checkActive", User.class,
                (SocketIOClient sioc, User t, AckRequest ar) -> {
                    ar.sendAckData(userService.find(t.getUsername()).toMap());
                });
    }

    public void register() {
        serverIO.addEventListener("register",
                User.class,
                (SocketIOClient sioc, User t, AckRequest ar) -> {
                    userService.insert(t);
                });
    }

    public void sendMail() {
        serverIO.addEventListener("sendMail",
                String.class,
                (SocketIOClient sioc, String t, AckRequest ar) -> {
                    MailService mail = new MailService();
                    String code = userService.generateCode();
                    Object[] sendMail = mail.sendMail(t, code);

                    ar.sendAckData(sendMail[0], sendMail[1], code);
                }
        );
    }

    public void findUser() {
        serverIO.addEventListener(
                "find",
                User.class,
                (SocketIOClient sioc, User t, AckRequest ar) -> {
                    Boolean c = userService.find(t);
                    ar.sendAckData(c);
                }
        );

        serverIO.addEventListener(
                "findUser",
                String.class,
                (SocketIOClient sioc, String t, AckRequest ar) -> {
                    ar.sendAckData(userService.find(t).toMap());
                }
        );
    }

    private boolean find(RoomModal t) {
        return lstRoom.stream()
                .anyMatch(room
                        -> room.getRoomID().equals(t.getRoomID())
                );
    }
}
