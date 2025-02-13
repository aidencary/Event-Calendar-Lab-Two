import java.time.LocalDateTime;

public class Deadline extends Event implements Completable {
    private boolean complete;

    // Constructor for the Deadline class
    public Deadline(String name, LocalDateTime deadline) {
        super(name, deadline);
        this.complete = false;
    }

    // Marks the deadline as complete
    @Override
    public void complete() {
        this.complete = true;
    }

    // Checks if the deadline is complete
    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

