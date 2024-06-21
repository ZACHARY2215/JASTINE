import java.util.List;

public class ReservationService {
    private ReservationDatabase database = new ReservationDatabase();

    public List<Reservation> getAllReservations() {
        return database.getAllReservations();
    }

    public Reservation getReservationById(int id) {
        return database.getReservationById(id);
    }

    public void createReservation(String guestName, String roomType, String checkInDateTime, String checkOutDateTime) {
        Reservation reservation = new Reservation(0, guestName, roomType, checkInDateTime, checkOutDateTime);
        database.addReservation(reservation);
    }

    public void updateReservation(int id, String guestName, String roomType, String checkInDateTime,
            String checkOutDateTime) {
        Reservation reservation = new Reservation(id, guestName, roomType, checkInDateTime, checkOutDateTime);
        database.updateReservation(reservation);
    }

    public void deleteReservation(int id) {
        database.deleteReservation(id);
    }
}
