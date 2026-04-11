package vn.edu.nlu.fit.mythuatshop.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.nlu.fit.mythuatshop.Model.Contact;
import vn.edu.nlu.fit.mythuatshop.Model.Users;
import vn.edu.nlu.fit.mythuatshop.Service.ContactService;

import java.io.IOException;

@WebServlet(name = "ContactController", value = "/contact")
public class ContactController extends HttpServlet {

    private ContactService contactService;

    @Override
    public void init() throws ServletException {
        contactService = new ContactService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("Contact.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("currentUser");

        String fullName;
        String email;
        String phone;
        String message = request.getParameter("message");

        if (currentUser != null) {

            fullName = currentUser.getFullName();
            email = currentUser.getEmail();
            phone = currentUser.getPhoneNumber();
        } else {

            fullName = request.getParameter("fullname");
            email = request.getParameter("email");
            phone = request.getParameter("phone");
        }


        if (fullName != null) fullName = fullName.trim();
        if (email != null) email = email.trim();
        if (phone != null) phone = phone.trim();
        if (message != null) message = message.trim();


        if (fullName == null || fullName.isEmpty()
                || email == null || email.isEmpty()
                || phone == null || phone.isEmpty()
                || message == null || message.isEmpty()) {

            request.setAttribute("errorMsg", "Vui lòng nhập đầy đủ thông tin liên hệ.");
            request.setAttribute("inputFullName", fullName);
            request.setAttribute("inputEmail", email);
            request.setAttribute("inputPhone", phone);
            request.setAttribute("inputMessage", message);
            request.getRequestDispatcher("Contact.jsp").forward(request, response);
            return;
        }

        Contact contact = new Contact();
        contact.setUserId(currentUser != null ? currentUser.getId() : null);
        contact.setFullName(fullName);
        contact.setEmail(email);
        contact.setPhoneNumber(phone);
        contact.setMessage(message);


        contactService.addContact(contact);


        contactService.sendContactToAdmin(contact);

        request.setAttribute("successMsg", "Gửi liên hệ thành công!");
        request.getRequestDispatcher("Contact.jsp").forward(request, response);
    }
}