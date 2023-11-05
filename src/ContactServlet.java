import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/contacts")
public class ContactServlet extends HttpServlet {
    private ContactDao contactDao;

    public void init() {
        contactDao = new ContactDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Contact> contacts = contactDao.getAllContacts();
        request.setAttribute("contacts", contacts);
        request.getRequestDispatcher("contacts.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            Contact contact = new Contact(name, address, phone);
            contactDao.addContact(contact);
        } else if ("update".equals(action)) {
            int index = Integer.parseInt(request.getParameter("index"));
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            Contact contact = new Contact(name, address, phone);
            contactDao.updateContact(index, contact);
        } else if ("delete".equals(action)) {
            int index = Integer.parseInt(request.getParameter("index"));
            contactDao.deleteContact(index);
        }

//        response.sendRedirect(request.getContextPath() + "/contacts");
        request.getRequestDispatcher("contacts.jsp").forward(request, response);
    }
}

