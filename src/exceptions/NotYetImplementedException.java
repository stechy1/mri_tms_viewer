package exceptions;

import javax.swing.JOptionPane;

public class NotYetImplementedException extends Exception {

    public NotYetImplementedException(String exceptionText) {
        super(exceptionText);
        //JOptionPane.showMessageDialog(null, exceptionText);
    }

    public NotYetImplementedException() {
        this("Not yet implemented");
    }
}
