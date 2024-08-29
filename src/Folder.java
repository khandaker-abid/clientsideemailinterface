//Khandaker Abid    115478345   214 R30
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.ArrayList;
/**
 * The Folder class is used as the data structure that holds emails within the main program. Implemented with a Java API
 *      arraylist, it has a given name and a field representing what sorting method it uses at default to sort emails within.
 * P.S: The subject based sorting assumes the case of the letters are ignored!! This is usually how it is from what I've seen in email apps.
 * Also, some of these methods have 0-based indexing, which is implemented to essentially be 1-based in the backend of the main program for simplicity to the user.
 */
public class Folder implements Serializable {
    private ArrayList<Email> emails;
    private String name;
    private String currentSortingMethod;
    /**
     * The typical constructor used to make a folder in this program. Automatically sets the default sorting method as subject descending.
     * @param n the name of the new folder
     */
    public Folder(String n) {
        name = n;
        emails = new ArrayList<Email>();
        currentSortingMethod = "Subject descending";
    }
    /**
     * Another constructor where a name and sorting method is both given by user. Never needed to be used for the main program's implementation.
     * @param n the name of the new folder
     * @param sm the default sorting method of the new folder.
     */
    public Folder(String n, String sm) {
        name = n;
        currentSortingMethod = sm;
        emails = new ArrayList<Email>();
    }
    /**
     * Getter method for a specific email the folder is holding
     * @param x the index of the email within the folder's data (the method itself is 0-based)
     * @return the email at index x of the folder
     * @throws IndexOutOfBoundsException if the argument's index doesn't have an email to return
     */
    public Email getEmail(int x) throws IndexOutOfBoundsException{
        return emails.get(x);
    }
    /**
     * Getter method for the entire list of emails of a folder
     * @return the entire list of emails of a folder
     */
    public ArrayList<Email> getEmails() {
        return emails;
    }
    /**
     * Getter method for the name of the folder
     * @return the name of the folder
     */
    public String getName() {
        return name;
    }
    /**
     * Getter method for the default sort method of the folder
     * @return the default sort method of the folder
     */
    public String getCurrentSortingMethod() {
        return currentSortingMethod;
    }
    /**
     * Setter method for the list containing the emails in the folder
     * @param emails the list containing the emails in the folder
     */
    public void setEmails(ArrayList<Email> emails) {
        this.emails = emails;
    }
    /**
     * Setter method for the name of the folder
     * @param name the name of the folder
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Setter method for the default sort method of the folder
     * @param currentSortingMethod the default sort method of the folder
     */
    public void setCurrentSortingMethod(String currentSortingMethod) {
        this.currentSortingMethod = currentSortingMethod;
    }
    /**
     * Appends an email to the list of emails within the data of the folder
     * @param email the email being appended
     */
    public void addEmail(Email email) {
        emails.add(email);
    }
    /**
     * Removes an email within the list of emails, signified by its index on the list
     * @param index the index of the email being removed (the method itself is 0-based)
     * @return the email that has been removed from the folder's data
     * @throws IndexOutOfBoundsException if there's no email at the argument index
     */
    public Email removeEmail(int index) throws IndexOutOfBoundsException{
        return emails.remove(index);
    }
    /**
     * Sorts the list of emails within the folder by subject line, ascending (generally A to Z)
     */
    public void sortBySubjectAscending() {
        emails.sort(new Comparator<Email>() {
            @Override
            public int compare(Email o1, Email o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getSubject(), o2.getSubject());
            }
        });
    }
    /**
     * Sorts the list of emails within the folder by subject line, descending order (generally Z to A)
     */
    public void sortBySubjectDescending() {
        emails.sort(new Comparator<Email>() {
            @Override
            public int compare(Email o1, Email o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o2.getSubject(), o1.getSubject());
            }
        });
    }
    /**
     * Sorts the list of emails within the folder by date created, ascending order (oldest to recent)
     */
    public void sortByDateAscending() {
        emails.sort(new Comparator<Email>() {
            @Override
            public int compare(Email o1, Email o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });
    }
    /**
     * Sorts the list of emails within the folder by date created, descending order (recent to oldesg)
     */
    public void sortByDateDescending() {
        emails.sort(new Comparator<Email>() {
            @Override
            public int compare(Email o1, Email o2) {
                return o2.getTimestamp().compareTo(o1.getTimestamp());
            }
        });
    }
    /**
     * Called to give the string representation of a single folder and its fields, including name and emails.
     * @return the string representation of the instance folder
     */
    public String toString() {
        String y = this.getName() + "\n\n" + String.format("%7s%15s%20s", "Inbox", "Time", "Subject") + "\n-----------------------------------------------\n";
        int counter = 1;
        for(Email e:emails) {
            SimpleDateFormat d = new SimpleDateFormat("hh:mma MM-dd-yyyy");
            y+=String.format("%5d%25s%10s", counter, d.format(e.getTimestamp().getTime()), e.getSubject()) + "\n";
            counter++;
        }
        return y;
    }
}
