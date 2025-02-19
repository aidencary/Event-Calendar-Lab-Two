import javax.swing.*;
import java.awt.*;

/**
 * The EventPlanner class serves as the main entry point for the event calendar application.
 */



public class EventPlanner {
    public static void main(String[] args) {

        final int frameWidth = 800;

        SwingUtilities.invokeLater(() -> {
            // Create the main application window
            JFrame frame = new JFrame("Event Planner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(frameWidth, 500);

            // Create and add the EventListPanel
            EventListPanel eventListPanel = new EventListPanel();
            frame.add(eventListPanel, BorderLayout.CENTER);

            // Add default events
            addDefaultEvents(eventListPanel);

            // Make the frame visible
            frame.setVisible(true);
        });
    }

    /**
     * Adds default events to the event list.
     * @param events The EventListPanel to which default events will be added.
     */
    private static void addDefaultEvents(EventListPanel events) {
        events.addEvent(new Deadline("Project Submission", java.time.LocalDateTime.now().plusDays(3)));
        events.addEvent(new Meeting("Team Meeting", java.time.LocalDateTime.now().plusHours(5), java.time.LocalDateTime.now().plusHours(6), "Conference Room"));
    }
}

