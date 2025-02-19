import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * The EventPanel class represents a visual component to display an Event.
 */

public class EventPanel extends JPanel {
    private Event event;
    private JPanel eventDetailsPanel;
    private JLabel nameLabel, startTimeLabel, endTimeLabel, durationLabel, locationLabel, dateLabel;
    private JButton completeButton; // Button to mark event as complete
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d, yyyy");  // Format for the dates
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm"); // Using a 24-hour format

    // Constructor
    EventPanel(Event event) {
        this.event = event;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        eventDetailsPanel = new JPanel();
        eventDetailsPanel.setLayout(new BoxLayout(eventDetailsPanel, BoxLayout.Y_AXIS));
        eventDetailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(eventDetailsPanel, BorderLayout.CENTER);

        // Name
        nameLabel = new JLabel("" + event.getName());
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        eventDetailsPanel.add(nameLabel);
        eventDetailsPanel.add(Box.createVerticalGlue());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Meeting output
        if (event instanceof Meeting meeting) {
            dateLabel = new JLabel("Date: " + meeting.getDateTime().format(dateFormat));
            eventDetailsPanel.add(dateLabel);

            startTimeLabel = new JLabel("Start Time: " + meeting.getStartDateTime().format(timeFormat));
            eventDetailsPanel.add(startTimeLabel);

            endTimeLabel = new JLabel("End Time: " + meeting.getEndDateTime().format(timeFormat));
            eventDetailsPanel.add(endTimeLabel);

            durationLabel = new JLabel("Duration: " + meeting.getDuration());
            eventDetailsPanel.add(durationLabel);

            locationLabel = new JLabel("Location: " + meeting.getLocation());
            eventDetailsPanel.add(locationLabel);
        }

        // Deadline Output
        if (event instanceof Deadline deadline) {
            dateLabel = new JLabel("Date: " + deadline.getDateTime().format(dateFormat));
            eventDetailsPanel.add(dateLabel);

            endTimeLabel = new JLabel("Due On: " + deadline.getDateTime().format(timeFormat));
            eventDetailsPanel.add(endTimeLabel);
        }


        if (event instanceof Completable completableEvent) {
            completeButton = new JButton(completableEvent.isComplete() ? "Completed" : "Complete");
            completeButton.setEnabled(!completableEvent.isComplete()); // Disable if already completed

            completeButton.addActionListener(e -> {
                // Toggle completion status
                if (completableEvent.isComplete()) {
                    completableEvent.complete();  // Unmark as completed
                    completeButton.setText("Complete");
                    completeButton.setEnabled(true);
                } else {
                    completableEvent.complete();  // Mark as completed
                    completeButton.setText("Completed");
                    completeButton.setEnabled(false); // After something is marked completed, it cannot be marked uncompleted
                }
            });

            add(completeButton, BorderLayout.SOUTH);
        }
    }

    // Updates the display, such as disabling the complete button if completed
    private void updateDisplay() {
        if (event instanceof Completable completableEvent) {
            if (completableEvent.isComplete() && completeButton != null) {
                completeButton.setEnabled(false);
                completeButton.setText("Completed");
            } else {
                completeButton.setEnabled(true);
                completeButton.setText("Complete");
            }
        }
    }
}
