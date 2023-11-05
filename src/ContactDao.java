import java.util.ArrayList;
import java.util.List;

public class ContactDao {
    private List<Contact> contacts;

    public ContactDao() {
        contacts = new ArrayList<>();
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void updateContact(int index, Contact updatedContact) {
        contacts.set(index, updatedContact);
    }

    public void deleteContact(int index) {
        contacts.remove(index);
    }
}
