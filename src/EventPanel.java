import javax.swing.*;
import java.awt.*;

/**
 * The EventPanel class represents a visual component to display an Event.
 */
public class EventPanel extends JPanel {
    private Event event; // The event to be displayed
    private JButton completeButton; // Button to mark event as complete

    /**
     * Constructor for EventPanel.
     * @param event The event to be displayed.
     */
    public EventPanel(Event event) {
        this.event = event;
        setLayout(new GridLayout(0, 1)); // Vertical layout for event details
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Display event details
        add(new JLabel("Name: " + event.getName()));
        add(new JLabel("Date & Time: " + event.getDateTime()));


        // Check if event is Completable and add a complete button
        if (event instanceof Completable) {
            Completable completableEvent = (Completable) event;
            completeButton = new JButton("Complete");
            completeButton.addActionListener(e -> {
                completableEvent.complete();
                updateDisplay();
            });
            add(completeButton);
        }
    }

    /**
     * Updates the display, such as disabling the complete button if completed.
     */
    private void updateDisplay() {
        if (event instanceof Completable) {
            Completable completableEvent = (Completable) event;
            if (completableEvent.isComplete() && completeButton != null) {
                completeButton.setEnabled(false);
                completeButton.setText("Completed");
            }
        }
    }
}
