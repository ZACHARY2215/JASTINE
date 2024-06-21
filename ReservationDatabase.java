import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDatabase {
    private List<Reservation> reservations = new ArrayList<>();
    private int currentId = 1;
    private final String filePath = "reservations.txt";

    public ReservationDatabase() {
        loadReservations();
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation getReservationById(int id) {
        return reservations.stream().filter(res -> res.getId() == id).findFirst().orElse(null);
    }

    public void addReservation(Reservation reservation) {
        reservation.setId(currentId++);
        reservations.add(reservation);
        saveReservations();
    }

    public void updateReservation(Reservation reservation) {
        int index = reservations.indexOf(getReservationById(reservation.getId()));
        if (index >= 0) {
            reservations.set(index, reservation);
            saveReservations();
        }
    }

    public void deleteReservation(int id) {
        reservations.removeIf(res -> res.getId() == id);
        saveReservations();
    }

    private void loadReservations() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    Reservation reservation = new Reservation(id, parts[1], parts[2], parts[3], parts[4]);
                    reservations.add(reservation);
                    currentId = Math.max(currentId, id + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
    }

    private void saveReservations() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Reservation reservation : reservations) {
                writer.println(reservation.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }
}
