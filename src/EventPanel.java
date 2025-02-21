import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * The EventPanel class represents a visual component to display an Event.
 */
public class EventPanel extends JPanel {
    private Event event;
    private JPanel eventDetailsPanel;
    private JLabel nameLabel, startTimeLabel, endTimeLabel, durationLabel, locationLabel, dateLabel, completedLabel;
    private JButton completeButton;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM d, yyyy");  // Format for the dates
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm"); // Using a 24-hour format

    // Constructor for Events
    EventPanel(Event event) {
        this.event = event;

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        eventDetailsPanel = new JPanel();
        eventDetailsPanel.setLayout(new BoxLayout(eventDetailsPanel, BoxLayout.Y_AXIS));
        eventDetailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(eventDetailsPanel, BorderLayout.CENTER);

        // Gets the name of the event
        nameLabel = new JLabel("" + event.getName());
        //nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
        eventDetailsPanel.add(nameLabel);
        //eventDetailsPanel.add(Box.createVerticalGlue());
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

        if (event instanceof Completable completable) {
            completedLabel = new JLabel("Completed: " + completable.isComplete());
            eventDetailsPanel.add(completedLabel);

            completeButton = new JButton(completable.isComplete() ? "Completed" : "Mark Complete");
            completeButton.setEnabled(!completable.isComplete());
            completeButton.addActionListener(e -> {
                completable.complete();
                completedLabel.setText("Completed: " + completable.isComplete());
                completeButton.setText("Completed");
                completeButton.setEnabled(false); // Disable the button after marking as completed
            });
            buttonPanel.add(completeButton);
        }
        add(buttonPanel, BorderLayout.EAST);
    }
}