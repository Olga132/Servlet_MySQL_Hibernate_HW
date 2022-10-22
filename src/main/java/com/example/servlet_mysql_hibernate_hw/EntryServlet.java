package com.example.servlet_mysql_hibernate_hw;

import entity.Ledger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/")
public class EntryServlet extends HttpServlet {

    private static LedgerDAO ledgerDAO;

    public void init() {
        ledgerDAO = new LedgerDAO();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertEntry(request, response);
                    break;
                case "/delete":
                    deleteEntry(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEntry(request, response);
                    break;
                case "/search":
                    searchEntry(request, response);
                    break;
                default:
                    listEntry(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String searchValue = request.getParameter("searchValue");
        List<Ledger> searchEntries = ledgerDAO.getEntriesByCondition(searchValue);
        request.setAttribute("listEntry", searchEntries);
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-list.jsp");
        dispatcher.forward(request, response);
    }

    private void listEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Ledger> listEntries = ledgerDAO.selectAllEntry();
        request.setAttribute("listEntry", listEntries);
        request.setAttribute("listSum", ledgerDAO.getSumEntries());
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Ledger existingEntry = ledgerDAO.selectEntry(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-form.jsp");
        request.setAttribute("entry", existingEntry);
        dispatcher.forward(request, response);
    }

    private void insertEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        java.util.Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
        java.sql.Date date = new java.sql.Date(selectedDate.getTime());

        String specification = request.getParameter("specification");
        Double sum = Double.parseDouble(request.getParameter("sum"));
        String movementTypeString = request.getParameter("movementTypeString");
        Ledger newEntry = new Ledger(date, specification, sum, movementTypeString);
//        Ledger newEntry = new Ledger();
//        newEntry.setDate(date);
//        newEntry.setMovementType(movementTypeString);
//        newEntry.setSpecification(specification);
//        newEntry.setSum(sum);
        ledgerDAO.addEntry(newEntry);
        response.sendRedirect("list");
    }

    private void updateEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {

        int id = Integer.parseInt(request.getParameter("id"));
        java.util.Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
        java.sql.Date date = new java.sql.Date(selectedDate.getTime());
        String specification = request.getParameter("specification");
        Double sum = Double.parseDouble(request.getParameter("sum"));
        String movementTypeString = request.getParameter("movementTypeString");
        Ledger updateEntry = new Ledger(id, date, specification, sum, movementTypeString);

        ledgerDAO.updateEntry(updateEntry);
        response.sendRedirect("list");
    }

    private void deleteEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ledgerDAO.deleteEntry(id);
        response.sendRedirect("list");
    }
}