//Khandaker Abid    115478345   214 R30
import java.io.*;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
/**
 * The Mailbox classes houses the Mailbox object, a singular savable object with references to an inbox folder, a trash
 *      folder, and some other custom folders, as well as the main method responsible for the user interface of the program.
 *      Meant to be used as a singular object in the program, as multiple mailboxes aren't necessary for one user.
 *      Implements Serializable and imports FileInputStream, FileOutputStream, ObjectInputStream and ObjectOutputStream to save
 *      and/or load a Mailbox object save file in the relative directory. If this is the user's first time using the program,
 *      it creates a save named "Mailbox.obj" upon ending gracefully which is loaded every time after when the main program starts.
 *      Houses a boolean variable which switches whenever the menu needs to be in respect to the mailbox or a specific folder.
 *      Besides the main method and Mailbox object methods, there's a normalizing method which turns Scanner inputs to uppercase if possible.
 */
public class Mailbox implements Serializable {
    private Folder inbox;
    private Folder trash;
    private ArrayList<Folder> folders;
    private static Mailbox mailbox;
    /**
     * The no-arg constructor for a mailbox which is used for this program. Initializes all necessary fields itself.
     */
    public Mailbox() {
        inbox = new Folder("Inbox");
        trash = new Folder("Trash");
        folders = new ArrayList<>();
        mailbox = null;
    }
    /**
     * Constructor meant to "paste" a mailbox's contents into the new one. Never actually used nor necessary.
     * @param m
     */
    public Mailbox(Object m) {
        if(m instanceof Mailbox) {
            mailbox = (Mailbox) m;
            inbox = mailbox.inbox;
            trash = mailbox.trash;
            folders = mailbox.folders;
        }
    }
    /**
     * Setter method for the static mailbox data field, can act as a loading reference.
     * @param mailbox the mailbox being "loaded" for reference.
     */
    public void setMailbox(Mailbox mailbox) {
        Mailbox.mailbox = mailbox;
    }
    /**
     * Getter method for the referenced mailbox save.
     * @return the referenced mailbox save.
     */
    public Mailbox getMailbox() {
        return mailbox;
    }
    public static void main(String[] args) {
        Mailbox m = new Mailbox();
        try {
            FileInputStream file = new FileInputStream("mailbox.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            m.setMailbox(m);
            m = (Mailbox) fin.readObject();
            System.out.println("Save found. Loading...\n");
            file.close();
        } catch(IOException a){
            System.out.println("Save not found, starting with empty mailbox...\n");
        } //catches if file not found
        catch(ClassNotFoundException c){}
        Scanner s = new Scanner(System.in);
        char input1; String input2; boolean mode = true; Folder cur = null; String name; int i;
        String menu1 = "A – Add folder\n" +
                "R – Remove folder\n" +
                "C – Compose email\n" +
                "F – Open folder\n" +
                "I – Open Inbox\n" +
                "T – Open Trash\n" +
                "E = Empty Trash\n" +
                "Q – Quit\n\n" +
                "Enter a user option: ";
        String menu2 = "M – Move email\n" +
                "D – Delete email\n" +
                "V – View email contents\n" +
                "SA – Sort by subject line in ascending order\n" +
                "SD – Sort by subject line in descending order\n" +
                "DA – Sort by date in ascending order\n" +
                "DD – Sort by date in descending order\n" +
                "R – Return to mailbox\n\n" +
                "Enter a user option: ";
        //if(file.exists()) {
           // m = new Mailbox(file); //****
            //System.out.println("Save found. Loading...\n");
        //} else {
            //m = new Mailbox();
            //System.out.println("Save not found, starting with empty mailbox...\n");
        //}
        while(true) {
            while (mode) {
                try {
                    System.out.println(m.toString());
                    System.out.print(menu1);
                    input1 = norm(s.nextLine().charAt(0));
                    System.out.println();
                    switch (input1) {
                        case 'A':
                            System.out.print("Sure thing! Please state the name of the new custom folder: ");
                            name = s.nextLine();
                            try{
                                m.addFolder(new Folder(name));
                                System.out.println("Done! Added " + name + ".");
                            } catch(IllegalArgumentException ex) {
                                System.out.println("Sorry, this folder's name already exists. Please try to use another name for the new folder.");
                            }
                            break;
                        case 'R':
                            System.out.print("Sure thing! Please state the name of the custom folder to remove: ");
                            name = s.nextLine();
                            try {
                                m.deleteFolder(name);
                                System.out.println("Done! Removed " + name + ".");
                            } catch(IllegalArgumentException ex1) {
                                System.out.println("Sorry, we cannot remove the inbox or trash folders, as they are essential to the mailbox. Please try another name.");
                            } catch(InaccessibleObjectException ex2) {
                                System.out.println("Sorry, this folder doesn't exist in the mailbox. Please try another name.");
                            }
                            break;
                        case 'C':
                            System.out.println("Sure! Entering prompting...");
                            m.composeEmail();
                            break;
                        case 'F':
                            System.out.print("Alright! Please enter the custom folder's name: ");
                            name = s.nextLine();
                            cur = m.getFolder(name);
                            if(cur == null) {
                                System.out.println("Sorry, there is no custom folder with this name. Please try another name.");
                            } else {
                                System.out.println("Alright! Zooming in to " + name + "...");
                                mode = false;
                            }
                            break;
                        case 'I':
                            System.out.println("Alright! Zooming into inbox folder...");
                            cur = m.inbox;
                            mode = false;
                            break;
                        case 'T':
                            System.out.println("Alright! Zooming into trash folder...");
                            cur = m.trash;
                            mode = false;
                            break;
                        case 'E':
                            i = m.trash.getEmails().size();
                            m.clearTrash();
                            System.out.println(i + " email(s) successfully deleted!");
                            break;
                        case 'Q':
                            System.out.println("Sure thing! Thank you for using this program. Saving and terminating gracefully...");
                            //*** save to mailbox.obj
                            try {
                                FileOutputStream file = new FileOutputStream("mailbox.obj");
                                ObjectOutputStream fout = new ObjectOutputStream(file);
                                fout.writeObject(m);
                                fout.close();
                            } catch (IOException ex) {System.out.println("The save has been corrupted! Mayday!");}
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Sorry, the input doesn't match with any option. Please try again.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Sorry, invalid input type. Please try again.\n");
                }
                System.out.println();
            }
            while (!mode) {
                try {
                    System.out.println(cur.toString());
                    System.out.print(menu2);
                    input2 = s.nextLine().toUpperCase();
                    System.out.println();
                    switch (input2) {
                        case "M":
                            System.out.print("Sure thing! Please state the index of the email you want moved: ");
                            i = s.nextInt();
                            s.nextLine();
                            System.out.print("\n" + m.toString() + "\nNow, please state the name of the custom folder you want to move the email to: ");
                            name = s.nextLine();
                            try {
                                m.moveEmail(cur.removeEmail(i - 1), m.getFolder(name));
                                if(m.getFolder(name) == null) {
                                    System.out.println("Sorry, the folder you asked for isn't currently registered in the mailbox. The email has been moved to the inbox instead if it isn't a duplicate.");
                                } else if (name.equalsIgnoreCase("trash")){
                                    System.out.println("Moved email " + i + " to the trash if there's no duplicate in it.");
                                } else {
                                    System.out.println("Done! Moved email " + i + " to " + name + ".");
                                }
                            } catch(IndexOutOfBoundsException ex1) {
                                System.out.println("Sorry, there is no email under that index. Please try again and state the index of a valid email under this folder.");
                            } catch(IllegalArgumentException ex2) {
                                System.out.println("Sorry, this folder does not exist. Please try to use another name for the new folder.");
                            }
                            break;
                        case "D":
                            System.out.print("Sure thing! Please state the index of the email you want removed: ");
                            i = s.nextInt();
                            s.nextLine();
                            try {
                                m.deleteEmail(cur.removeEmail(i-1));
                                System.out.println("Done! The email has been moved to the trash.");
                            } catch(IndexOutOfBoundsException ex) {
                                System.out.println("Sorry, there is no email with the specified index. Please try another index.");
                            }
                            break;
                        case "V":
                            System.out.print("Sure thing! Please state the index of the email you want to see: ");
                            i = s.nextInt();
                            s.nextLine();
                            try {
                                System.out.println("\n" + cur.getEmail(i-1)); //1 based
                            } catch(IndexOutOfBoundsException ex) {
                                System.out.println("Sorry, there is no email with the specified index. Please try another index.");
                            }
                            break;
                        case "SA":
                            System.out.println("Alright! Sorting in subject-ascending...");
                            cur.sortBySubjectAscending();
                            System.out.println("Sorted!");
                            break;
                        case "SD":
                            System.out.println("Alright! Sorting in subject-descending...");
                            cur.sortBySubjectDescending();
                            System.out.println("Sorted!");
                            break;
                        case "DA":
                            System.out.println("Alright! Sorting in date-ascending...");
                            cur.sortByDateAscending();
                            System.out.println("Sorted!");
                            break;
                        case "DD":
                            System.out.println("Alright! Sorting in date-descending...");
                            cur.sortByDateDescending();
                            System.out.println("Sorted!");
                            break;
                        case "R":
                            System.out.println("Alright! Returning to main menu...");
                            mode = true;
                            break;
                        default:
                            System.out.println("Sorry, the input doesn't match with any option. Please try again.\n");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Sorry, invalid input type. Please try again.\n");
                }
                System.out.println();
            }
        }
    }
    /**
     * Adds a folder to the mailbox's list of custom folders
     * @param folder the folder object being appended
     * @throws IllegalArgumentException if the argument is a duplicate inbox or trash folder (not wanted as the mailbox already has those)
     */
    public void addFolder(Folder folder) throws IllegalArgumentException {
        if(folder.getName().equalsIgnoreCase("inbox")||folder.getName().equalsIgnoreCase("trash")) {
            throw new IllegalArgumentException();
        }
        Iterator<Folder> x = folders.iterator();
        while(x.hasNext()) {
            Folder y = x.next();
            if(y.getName().equalsIgnoreCase(folder.getName())) {
                throw new IllegalArgumentException();
            }
        }
        folders.add(folder);
    }
    /**
     * Deletes a custom folder within the mailbox that has the same name as the argument
     * @param name the name of the folder being removed
     * @throws IllegalArgumentException if the name references the inbox or trash folder (it would be problematic to delete those).
     * @throws InaccessibleObjectException if there is no folder of that name in the mailbox
     */
    public void deleteFolder(String name) throws IllegalArgumentException, InaccessibleObjectException {
        if(name.equalsIgnoreCase("inbox") || name.equalsIgnoreCase("trash")) {
            throw new IllegalArgumentException();
        }
        Iterator<Folder> x = folders.iterator();
        while(x.hasNext()) {
            Folder y = x.next();
            if (y.getName().equalsIgnoreCase(name)) {
                folders.remove(y);
                return;
            }
        }
        throw new InaccessibleObjectException();
    }
    /**
     * Calls the process to create an email with Scanner inputs. Adds email to inbox specifically.
     */
    public void composeEmail() {
        Scanner s = new Scanner(System.in);
        String a, b, c, d, e; char f;
        boolean done = false;
        try {
            System.out.printf("Please state the recipient of this email: ");
            a = s.nextLine();
            System.out.printf("Please state the cc of this email: ");
            b = s.nextLine();
            System.out.printf("Please state the bcc of this email: ");
            c = s.nextLine();
            System.out.printf("Please state the subject line of this email: ");
            d = s.nextLine();
            System.out.println("Almost done! Now, please write your entire email and click enter/return when finished.");
            e = s.nextLine();
            do {
                System.out.println("Are you satisfied with your email? (Y/N)");
                f = norm(s.nextLine().charAt(0));
            } while (f != 'Y' && f != 'N');
            if (f == 'N') {
                System.out.println("No problem! Exiting prompting...");
                return;
            } else {
                inbox.addEmail(new Email(a, b, c, d, e));
                System.out.println("Done! This email has been added to the inbox.");
            }
        } catch (Exception x) {  //input exceptions w Scanner
            System.out.println("Sorry, your input is invalid. Please try again. Exiting prompting...");
            return;
        }
    }
    /**
     * Adds the given email argument to the trash folder (doesn't actually remove from specified folder)
     * @param email the email being added to the trash
     */
    public void deleteEmail(Email email) {
        if(!trash.getEmails().contains(email)) {
            trash.addEmail(email);
        }
    }
    /**
     * Deletes all emails in the trash folder.
     */
    public void clearTrash() {
        trash.getEmails().clear(); //old folder cleared by garbage collection
    }
    /**
     * Moves the email argument to the given folder
     * @param email the email being moved
     * @param target the folder the email is being moved to
     */
    public void moveEmail(Email email, Folder target) {
        if(!folders.contains(target)) {
            if(target.equals(trash)) {
                if(trash.getEmails().contains(email)) {
                    return; //leaves if email was in trash already
                } else {
                    trash.addEmail(email);
                    return;
                }
            }
            if(inbox.getEmails().contains(email)) {
                return; //leaves if email was originally in inbox already
            }
            inbox.addEmail(email);
        } else {
            folders.get(folders.indexOf(target)).addEmail(email);
        }
    }
    /**
     * Getter method for a specific folder in the mailbox by name
     * @param name the name of the custom folder to be returned
     * @return the appropriate folder if in the mailbox, null if there isn't any
     */
    public Folder getFolder(String name) {
        Iterator<Folder> x = folders.iterator();
        while(x.hasNext()) {
            Folder y = x.next();
            if(y.getName().equalsIgnoreCase(name)) {
                return y;
            }
        }
        if(name.equalsIgnoreCase("inbox")) {
            return inbox;
        }
        if(name.equalsIgnoreCase("trash")) {
            return trash;
        }
        return null; //if no folder of the name in the custom folders list and not inbox or trash
    }
    /**
     * Normalizing function which shifts a character to uppercase if it's a lowercase letter; makes switch statements bearable with Scanner inputs.
     * @param x the character being normalized
     * @return itself if not a lowercase letter, and if otherwise, the uppercase counterpart character
     */
    public static char norm(char x) {
        if(x>=97 && x<=122) {
            x = (char)(x-32);
        }
        return x;
    }
    /**
     * Called to give the string representation of a single mailbox and its folders.
     * @return the string representation of the instance mailbox
     */
    public String toString() { //only used to print all folders
        String y = ("Mailbox:\n--------\nInbox\nTrash\n");
        for (Folder folder : folders) {
            y += folder.getName() + "\n";
        }
        return y;
    }
}
