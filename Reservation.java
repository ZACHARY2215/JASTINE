public class Reservation {
    private int id;
    private String guestName;
    private String roomType;
    private String checkInDateTime;
    private String checkOutDateTime;

    public Reservation(int id, String guestName, String roomType, String checkInDateTime, String checkOutDateTime) {
        this.id = id;
        this.guestName = guestName;
        this.roomType = roomType;
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckInDateTime() {
        return checkInDateTime;
    }

    public void setCheckInDateTime(String checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    public String getCheckOutDateTime() {
        return checkOutDateTime;
    }

    public void setCheckOutDateTime(String checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
    }

    @Override
    public String toString() {
        return id + "," + guestName + "," + roomType + "," + checkInDateTime + "," + checkOutDateTime;
    }
}
