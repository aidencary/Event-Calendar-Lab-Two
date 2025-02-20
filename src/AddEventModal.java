import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The AddEventModal class provides a dialog to add a new event.
 */
public class AddEventModal extends JDialog {
    final int panelLength = 400;
    final int panelWidth = 600;
    private JTextField nameField;
    private JComboBox<String> eventTypeBox;
    private JTextField startDateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField locationField;
    private EventListPanel eventListPanel;

    /**
     * Constructor for AddEventModal.
     * @param parent The parent panel where the event will be added.
     */
    public AddEventModal(EventListPanel parent) {
        this.eventListPanel = parent;
        setTitle("Add Event");
        setSize(panelWidth, panelLength);
        setLayout(new GridLayout(7, 1));

        // UI components
        add(new JLabel("Event Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Event Type:"));
        eventTypeBox = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        add(eventTypeBox);

        add(new JLabel("Event Date (MM/DD/YYYY):"));
        startDateField = new JTextField();
        add(startDateField);

        add(new JLabel("Start Time (HH:MM, 24-hour format, for meetings only):"));
        startTimeField = new JTextField();
        startTimeField.setEnabled(false);
        add(startTimeField);

        add(new JLabel("End Time (HH:MM, 24-hour format):"));
        endTimeField = new JTextField();
        add(endTimeField);

        add(new JLabel("Location (For meetings only):"));
        locationField = new JTextField();
        locationField.setEnabled(false);
        add(locationField);

        eventTypeBox.addActionListener(e -> {
            boolean isMeeting = "Meeting".equals(eventTypeBox.getSelectedItem());
            startTimeField.setEnabled(isMeeting);
            locationField.setEnabled(isMeeting);
        });

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(e -> addEvent());
        add(addButton);

        setVisible(true);
    }

    /**
     * Handles event creation and adds it to the event list with specific error messages.
     */
    private void addEvent() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Event name cannot be empty.");
            }

            String type = (String) eventTypeBox.getSelectedItem();
            LocalDateTime date;
            try {
                date = LocalDateTime.parse(startDateField.getText().trim() + " 00:00", DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Use MM/DD/YYYY.");
            }

            Event event = null;
            if ("Deadline".equals(type)) {
                LocalDateTime endTime;
                try {
                    endTime = LocalDateTime.parse(startDateField.getText().trim() + " " + endTimeField.getText().trim(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid end time format. Use HH:MM (24-hour format).");
                }
                event = new Deadline(name, endTime);
            } else if ("Meeting".equals(type)) {
                LocalDateTime startTime, endTime;
                try {
                    startTime = LocalDateTime.parse(startDateField.getText().trim() + " " + startTimeField.getText().trim(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid start time format. Use HH:MM (24-hour format).");
                }
                try {
                    endTime = LocalDateTime.parse(startDateField.getText().trim() + " " + endTimeField.getText().trim(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid end time format. Use HH:MM (24-hour format).");
                }
                String location = locationField.getText().trim();
                if (location.isEmpty()) {
                    throw new IllegalArgumentException("Location cannot be empty for a meeting.");
                }
                event = new Meeting(name, startTime, endTime, location);
            }

            if (event != null) {
                eventListPanel.addEvent(event);
            }
            dispose(); // Close the dialog after adding
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
