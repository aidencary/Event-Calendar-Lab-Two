import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The AddEventModal class provides a dialog to add a new event.
 */
public class AddEventModal extends JDialog {
    private JTextField nameField;
    private JComboBox<String> eventTypeBox;
    private JTextField startDateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField locationField;
    private EventListPanel eventListPanel;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructor for AddEventModal.
     * @param parent The parent panel where the event will be added.
     */
    public AddEventModal(EventListPanel parent) {
        this.eventListPanel = parent;
        setTitle("Add Event");
        setSize(400, 450);
        setLayout(new GridLayout(11, 1));

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

        add(new JLabel("Start Time (HH:MM, 24-hour format):"));
        startTimeField = new JTextField();
        startTimeField.setEnabled(false); // Initially disabled
        add(startTimeField);

        add(new JLabel("End Time (HH:MM, 24-hour format, for deadlines and meetings):"));
        endTimeField = new JTextField();
        //endTimeField.setEnabled(false); // Initially disabled
        add(endTimeField);

        add(new JLabel("Location (For meetings only):"));
        locationField = new JTextField();
        locationField.setEnabled(false); // Initially disabled
        add(locationField);

        eventTypeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isMeeting = "Meeting".equals(eventTypeBox.getSelectedItem());
                startTimeField.setEnabled(true); // Enable for both types
                locationField.setEnabled(isMeeting); // Enable location only for meetings
            }
        });

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEvent();
            }
        });
        add(addButton);

        setVisible(true);
    }

    /**
     * Handles event creation and adds it to the event list.
     */
    private void addEvent() {
        try {
            String name = nameField.getText();
            String type = (String) eventTypeBox.getSelectedItem();
            LocalDateTime startDateTime = LocalDateTime.parse(startDateField.getText() + " " + startTimeField.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));

            Event event = null;
            if ("Deadline".equals(type)) {
                LocalDateTime endTime = LocalDateTime.parse(startDateField.getText() + " " + endTimeField.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
                event = new Deadline(name, endTime);
            } else if ("Meeting".equals(type)) {
                LocalDateTime endDateTime = LocalDateTime.parse(startDateField.getText() + " " + endTimeField.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
                String location = locationField.getText();
                event = new Meeting(name, startDateTime, endDateTime, location);
            }

            if (event != null) {
                eventListPanel.addEvent(event);
            }

            dispose(); // Close the dialog after adding
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date or time format. Please use MM/DD/YYYY for date and HH:MM (24-hour format) for time.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
