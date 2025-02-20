import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The EventListPanel class manages and displays a list of events.
 */
public class EventListPanel extends JPanel {
    private ArrayList<Event> events; // List of events
    private JPanel displayPanel; // Panel to hold EventPanels
    private JComboBox<String> sortDropDown; // Dropdown for sorting events
    private JCheckBox filterCompleted; // Checkbox to filter completed events
    private JCheckBox filterEvents; // Checkbox to filter general events
    private JCheckBox filterDeadlines; // Checkbox to filter deadlines
    private JCheckBox filterMeetings; // Checkbox to filter meetings

    /**
     * Constructor for the EventListPanel.
     * Initializes the event list and UI components.
     */
    public EventListPanel() {
        this.events = new ArrayList<>();
        setLayout(new BorderLayout());

        // Create and configure the display panel
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(displayPanel);

        // Control panel with sorting and filtering options
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Sorting dropdown
        String[] sortOptions = {"Sort by Name", "Sort by Date", "Sort by Name (Reverse)", "Sort by Date (Reverse)"};
        sortDropDown = new JComboBox<>(sortOptions);
        sortDropDown.addActionListener(e -> sortEvents());

        // Filter checkboxes
        filterCompleted = new JCheckBox("Hide Completed Events");
        filterEvents = new JCheckBox("Filter General Events");
        filterDeadlines = new JCheckBox("Filter Deadlines");
        filterMeetings = new JCheckBox("Filter Meetings");

        // Add action listeners for filters
        filterCompleted.addActionListener(e -> updateDisplay());
        filterEvents.addActionListener(e -> updateDisplay());
        filterDeadlines.addActionListener(e -> updateDisplay());
        filterMeetings.addActionListener(e -> updateDisplay());

        // Add event button
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> new AddEventModal(this));

        // Add components to control panel
        controlPanel.add(sortDropDown);
        controlPanel.add(filterCompleted);
        controlPanel.add(filterEvents);
        controlPanel.add(filterDeadlines);
        controlPanel.add(filterMeetings);
        controlPanel.add(addEventButton);

        // Add panels to the main panel
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Adds an event to the list and updates the display.
     * @param event The event to add.
     */
    public void addEvent(Event event) {
        events.add(event);
        updateDisplay();
    }

    /**
     * Updates the event display panel based on the filters.
     */
    private void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            boolean displaying = true;

            // Check filtering conditions
            if (filterCompleted.isSelected() && event instanceof Completable && ((Completable) event).isComplete()) {
                displaying = false;
            }

            if (filterEvents.isSelected() && (event instanceof Deadline || event instanceof Meeting)) {
                displaying = false;
            }

            if (filterDeadlines.isSelected() && event instanceof Deadline) {
                displaying = false;
            }

            if (filterMeetings.isSelected() && event instanceof Meeting) {
                displaying = false;
            }

            // Add event to the display if it passes all filters
            if (displaying) {
                displayPanel.add(new EventPanel(event));
            }
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    /**
     * Sorts the events based on the selected sorting option.
     */
    private void sortEvents() {
        events.sort((e1, e2) -> {
            String option = (String) sortDropDown.getSelectedItem();
            if ("Sort by Name".equals(option)) {
                return e1.getName().compareTo(e2.getName());
            } else if ("Sort by Date".equals(option)) {
                return e1.getDateTime().compareTo(e2.getDateTime());
            } else if ("Sort by Name (Reverse)".equals(option)) {
                return e2.getName().compareTo(e1.getName());
            } else if ("Sort by Date (Reverse)".equals(option)) {
                return e2.getDateTime().compareTo(e1.getDateTime());
            }
            return 0;
        });
        updateDisplay();
    }
}
