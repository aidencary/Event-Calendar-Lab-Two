import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime; // The time when the meeting ends
    private String location; // Location of the meeting
    private boolean complete; // Tracks whether the meeting is complete

    // Constructor for the Meeting class
    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start);
        this.endDateTime = end;
        this.location = location;
        this.complete = false;
    }

    public void complete() {
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    // Get the end time of the meeting
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    // Gets the duration of the meeting
    public Duration getDuration() {
        return Duration.between(dateTime, endDateTime);
    }

    public String getLocation() {
        return location;
    }

    public void setEndDateTime(LocalDateTime end) {
        this.endDateTime = end;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Returns the name of the meeting
    @Override
    public String getName() {
        return this.name;
    }

}
