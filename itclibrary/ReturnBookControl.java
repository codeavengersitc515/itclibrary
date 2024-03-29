// Initiate to fix "ReturnBookControl.java" file
public class ReturnBookControl {

    private ReturnBookUI returnBookUI; // Create an object of ReturnBookUI class to use the functionality of ReturnBookUI
    private enum ControlState { INITIALISED, READY, INSPECTING }; //Create an enum "ControlState" and initialize to INITIALISED, READY, and INSPECTING
    private ControlState controlState; // Create an object of ControlState class to use the functionality of ControlState

    private Library library;  // Create an object of Library class to use the functionality of Library
    private Loan currentLoan; // Create an object of Loan class to use the functionality of Loan

    // Create a constructor of ReturnBookControl
    // Referring library object to the singleton of Library class
    // Assign the state to controlState as "INITIALISED"
    public ReturnBookControl() {
        this.library = Library.getInstance();   // Referring library object of ReturnBookControl to the singleton of Library class
        controlState = ControlState.INITIALISED;// Assign controlState as "INITIALISED" state
    }

    // Create setUI method which sets user interface to the settings which is passing to the method
    // This method requires ReturnBookUI object to set user interface
    public void setUI(ReturnBookUI returnBookUI) {
        // If controlState is not in the state of "INITIALISED" then throw an exception error of following text
        if (!controlState.equals(ControlState.INITIALISED)) { // If controlState is not in the state of "INITIALISED" then follows the code in IF block
            throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state"); // Throw an exception error of following text
        }
        this.returnBookUI = returnBookUI; // Referring library object of ReturnBookControl to the ReturnBookControl provided from the method
        returnBookUI.setState(ReturnBookUI.UIState.READY);  // Set the state of returnBookUI object to "READY" by calling their method "setState" and passing the static enum of ReturnBookUI
        controlState = ControlState.READY; // Set the state of controlState to "READY" state
    }

    // Create bookScanned method which finds book in Library by using bookId. If the book is found, then display book, current loan and over dues.
    // Otherwise shows errors
    // This method requires bookId integer to find valid book
    public void bookScanned(int bookId) {
        // If controlState is not in the state of "READY" then throw an exception error of following text
        if (!controlState.equals(ControlState.READY)) { // If controlState is not in the state of "READY" then follows the code in IF block
            throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");  // Throw an exception error of following text
        }
        Book currentBook = library.getBook(bookId); // Create a Book object "currentBook" and assign it to the Book object from Library class's method "getBook" by passing bookId to the method

        // If getBook method of Library class return null as there is no book which reflects the bookId
        // Then ReturnBookUI display "Invalid Book Id" message
        // And Exit the bookScanned method
        if (currentBook == null) { // If currentBook object is null
            returnBookUI.display("Invalid Book Id"); // returnBookUI display "Invalid Book Id" message
            return; // Exit the bookScanned method
        }

        // If currentBook is not on Loan
        // Then ReturnBookUI display "Book has not been borrowed" message
        // And Exit the bookScanned method
        if (!currentBook.isOnLoan()) { // If currentBook is not on Loan
            returnBookUI.display("Book has not been borrowed"); // returnBookUI display "Book has not been borrowed" message
            return; // Exit the bookScanned method
        }

        currentLoan = library.getLoanByBookId(bookId); // Assign currentLoan to the Loan object from Library class's method "getLoan" by passing bookId to the method
        double overDueFine = 0.0; // Initialize overDueFine and assign it to 0.0

        // If currentLoan is over due then assign overDueFine to fine on over due by using the "calculateOverDueFine" method of Library class
        if (currentLoan.isOverDue()) { // If currentLoan is over due then follows the code in IF block
            overDueFine = library.calculateOverDueFine(currentLoan); // Assign overDueFine to fine on over due by using the "calculateOverDueFine" method of Library class
        }

        returnBookUI.display("Inspecting"); // returnBookUI display "Inspecting" message
        returnBookUI.display(currentBook.toString()); // returnBookUI display currentBook message by using toString() method
        returnBookUI.display(currentLoan.toString()); // returnBookUI display currentLoan message by using toString() method

        // If currentLoan is over due then display over due fine by using "display" method of returnBookUI on User Interface
        if (currentLoan.isOverDue()) {  // If currentLoan is over due then follows the code in IF block
            returnBookUI.display(String.format("\nOverdue fine : $%.2f", overDueFine)); // Display over due fine
        }

        returnBookUI.setState(ReturnBookUI.UIState.INSPECTING);  // Set the state of returnBookUI object to "INSPECTING" by calling their method "setState" and passing the static enum of ReturnBookUI
        controlState = ControlState.INSPECTING;  // Set the state of controlState to "INSPECTING" state
    }

    // Create scanningComplete method which changes the state of returnBookUI to "COMPLETED"
    public void scanningComplete() {
        // If controlState is not in the state of "READY" then throw an exception error of following text
        if (!controlState.equals(ControlState.READY)) { // If controlState is not in the state of "READY" then follows the code in IF block
            throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state"); // Throw an exception error of following text
        }
        returnBookUI.setState(ReturnBookUI.UIState.COMPLETED); // Set the state of controlState to "COMPLETED" state
    }

    // Create dischargeLoan method which discharge the loan according to the "isDamaged" boolean which is passing to this method.
    // This method requires isDamaged boolean to discharge loan functionality of library class.
    public void dischargeLoan(boolean isDamaged) {
        // If controlState is not in the state of "INSPECTING" then throw an exception error of following text
        if (!controlState.equals(ControlState.INSPECTING)) { // If controlState is not in the state of "INSPECTING" then follows the code in IF block
            throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");  // Throw an exception error of following text
        }

        library.dischargeLoan(currentLoan, isDamaged); // Discharge loan by using method "dischargeLoan" of Library class and pass currentLoan as a Loan object and isDamaged boolean
        currentLoan = null; // Assign currentLoan to null
        returnBookUI.setState(ReturnBookUI.UIState.READY); // Set the state of returnBookUI object to "READY" by calling their method "setState" and passing the static enum of ReturnBookUI
        controlState = ControlState.READY; // Set the state of controlState to "READY" state
    }
}
// Finalize "ReturnBookControl.java" file
