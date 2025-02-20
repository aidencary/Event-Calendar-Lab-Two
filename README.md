# Event Calendar Application

## Description
The **Event Calendar Application** is a Java-based GUI program that allows users to manage their events, deadlines, and meetings. Users can add, filter, sort, and complete events through an interactive graphical interface.

## Features
- **Add Events**: Supports adding `Deadline` and `Meeting` events.
- **Sorting**: Sort events by name or date (both ascending and descending).
- **Filtering**:
  - Hide completed events
  - Filter general events
  - Filter deadlines
  - Filter meetings
- **Mark Events as Completed**: Users can mark events as complete, and completed events will no longer be editable.
- **Event Display**: Events are displayed dynamically with relevant details.

## Components
### 1. `Event` (Abstract Class)
   - Base class for all event types.
   - Implements `Comparable<Event>` for sorting.
   - Contains fields for event name and date.

### 2. `Completable` (Interface)
   - Allows events to be marked as completed.

### 3. `Deadline` (Class)
   - Extends `Event` and implements `Completable`.
   - Represents a deadline with an end time.

### 4. `Meeting` (Class)
   - Extends `Event` and implements `Completable`.
   - Includes start and end times along with a location.

### 5. `EventPanel` (Class)
   - Displays individual event details in the GUI.
   - Includes a completion button for `Completable` events.

### 6. `EventListPanel` (Class)
   - Displays all events and supports sorting and filtering.
   - Allows users to add new events.

### 7. `AddEventModal` (Class)
   - Handles event creation.
   - Validates user input with detailed error messages.

### 8. `EventPlanner` (Class)
   - Main entry point for the application.
   - Initializes the `JFrame` and `EventListPanel`.

## Installation & Setup
### Prerequisites
- **Java 17+**
- **Maven (Optional)**

### Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/EventCalendar.git
   ```
2. Navigate to the project folder:
   ```sh
   cd EventCalendar
   ```
3. Compile the Java files:
   ```sh
   javac -d bin src/*.java
   ```
4. Run the program:
   ```sh
   java -cp bin EventPlanner
   ```

## Usage
1. Click **Add Event** to open the event creation modal.
2. Enter details based on the event type (Deadline or Meeting).
3. Click **Add Event** to save it to the list.
4. Use sorting and filtering options to manage events.
5. Mark events as **Complete** when finished.

## Future Enhancements
- **Reminder System**: Notifications for upcoming events.
- **Recurring Events**: Support for daily, weekly, or monthly recurring events.
- **Calendar View**: A visual calendar representation of events.
- **Data Persistence**: Save and load events from a file or database.

## Contributors
- **Your Name** (Your Email/Username)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.


