//Khandaker Abid    115478345   214 R30
/**
 * The Email object is used as the primary unit of the program, as each unit in the program contains an email with a
 *      recipient (to), an optional CC and BCC address, a subject line and body, and a respective time of when the email
 *      was made. Also implements Serializable to be saved easily within Java's base classes, and GeorgianCalendar for time.
 */
import java.io.Serializable;
import java.util.GregorianCalendar;
public class Email implements Serializable {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private GregorianCalendar timestamp;
    /**
     * Primary constructor for the object, takes in every possible parameter
     * @param a who's receiving the email
     * @param b the CC recipients of the email
     * @param c the BCC recipients of the email
     * @param d the subject line of the email
     * @param e the content of the email itself (named the body)
     */
    public Email(String a, String b, String c, String d, String e) {
        to = a;
        cc = b;
        bcc = c;
        subject = d;
        body = e;
        timestamp = new GregorianCalendar();
    }
    /**
     * Getter method for the person getting the email.
     * @return the person getting the email.
     */
    public String getTo() {
        return to;
    }
    /**
     * Getter method for the person in the CC of the email.
     * @return the person in the CC of the email.
     */
    public String getCc() {
        return cc;
    }
    /**
     * Getter method for the person in the BCC of the email.
     * @return the person in the BCC of the email.
     */
    public String getBcc() {
        return bcc;
    }
    /**
     * Getter method for the subject line of the email.
     * @return the subject line of the email.
     */
    public String getSubject() {
        return subject;
    }
    /**
     * Getter method for the content of the email.
     * @return the content of the email.
     */
    public String getBody() {
        return body;
    }
    /**
     * Getter method for the time the email was made.
     * @return the time the email was made.
     */
    public GregorianCalendar getTimestamp() {
        return timestamp;
    }
    /**
     * Setter method for the person getting the email.
     * @param to the person getting the email
     */
    public void setTo(String to) {
        this.to = to;
    }
    /**
     * Setter method for the person in the CC of the email.
     * @param cc the person in the CC of the email
     */
    public void setCc(String cc) {
        this.cc = cc;
    }
    /**
     * Setter method for the person in the BCC of the email.
     * @param bcc the person in the BCC of the email
     */
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
    /**
     * Setter method for the subject line of the email.
     * @param subject the subject line of the email
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * Setter method for the contents of the email.
     * @param body the contents of the email
     */
    public void setBody(String body) {
        this.body = body;
    }
    /**
     * Setter method for the time the email was made.
     * @param timestamp the time the email was made
     */
    public void setTimestamp(GregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }
    /**
     * Called to give the string representation of a single email and its fields.
     * @return the string representation of the instance email
     */
    public String toString() {
        return("To: " + to + "\nCC: " + cc + "\nBCC: " + bcc + "\nSubject: " + subject + "\n" + body + "\n");
    }
}