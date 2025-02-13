public interface Completable {
    // Marks an event as complete
    void complete();
    // Checks if the event is complete
    boolean isCompleted();
}
