package app.quizbee.client.service;

import app.quizbee.entity.User;
import app.quizbee.event.CallbackData;
import app.quizbee.modal.MailModal;
import app.quizbee.modal.PlayerModal;
import app.quizbee.modal.RoomModal;
import app.quizbee.modal.ScoreModal;
import java.util.List;

public interface ClientService {

    public void disconnect(User user);

    public void findUser(User user, CallbackData<Boolean> callback);

    public void register(User user);

    public void sendMail(String toEmail, CallbackData<MailModal> callback);

    public void checkActive(User user, CallbackData<User> callback);

    public void login(User user);

    public void logout(User user);

    public void autoFillRooms(CallbackData<List<RoomModal>> data);

    public void addRoom(RoomModal room);

    public void removeRoom(RoomModal room);

    public void findRoom(String roomID);

    public void addPlayer(PlayerModal player);

    public void getPlayers(String idRoom, CallbackData<List<PlayerModal>> players);

    public void updateStatus(PlayerModal playerModal);

    public void checkReady(CallbackData<Boolean> started);

    public void startGame(String idRoom, CallbackData<List<PlayerModal>> data);

    public void updateScore(ScoreModal sm);

    public void autoUpdateScore(CallbackData<List<ScoreModal>> callbackData);
}
