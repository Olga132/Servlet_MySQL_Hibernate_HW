package com.example.servlet_mysql_hibernate_hw;

import entity.Ledger;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


class LedgerDAOTest {

    @Test
    void addEntry() {
        Ledger testLedger = new Ledger(new Date(2022, 10, 10),
                "HibernateInsertTest", 12450.1, "ПРИХОД");
        LedgerDAO ledgerDAO = new LedgerDAO();

        ledgerDAO.addEntry(testLedger);

        List<Ledger> ledgerList = ledgerDAO.selectAllEntry();

        ledgerList.forEach(System.out::println);
    }

    @Test
    void selectEntry() {
    }

    @Test
    void selectAllEntry() {
        LedgerDAO ledgerDAO = new LedgerDAO();

        List<Ledger> ledgerList = ledgerDAO.selectAllEntry();

        ledgerList.forEach(System.out::println);
    }

//    @Test
//    void deleteEntry() {
//        LedgerDAO ledgerDAO = new LedgerDAO();
//
//        ledgerDAO.deleteEntry(19);
//    }

    @Test
    void updateEntry() {
    }

    @Test
    void getEntriesByCondition() {
        LedgerDAO ledgerDAO = new LedgerDAO();
        String condition = "ООО";
        List<Ledger> ledgerList = ledgerDAO.getEntriesByCondition(condition);

        System.out.println("List entries containing the condition: \"" + condition + "\"" );
        ledgerList.forEach(System.out::println);
    }

    @Test
    void dateLedgers() {
        LedgerDAO ledgerDAO = new LedgerDAO();
        List<Date> dateList = ledgerDAO.dateLedgers();
        System.out.println("List date");
        dateList.forEach(System.out::println);
    }

    @Test
    void gettingLedgerInDateRange() {
        LocalDate date1 = LocalDate.of(2022, 10, 19);
        LocalDate date2 = LocalDate.of(2022, 10, 23);

        LedgerDAO ledgerDAO = new LedgerDAO();

        List<Ledger> ledgerList = ledgerDAO.gettingLedgerInDateRange(date1, date2);

        System.out.println("Ledger in date range from " + date1 + " to " + date2);
        ledgerList.forEach(System.out::println);
    }


    @Test
    void sortedLedgerByDate() {
        LedgerDAO ledgerDAO = new LedgerDAO();

        List<Ledger> ledgerList = ledgerDAO.sortedLedgerByDate();

        System.out.println("Sort by date");
        ledgerList.forEach(System.out::println);
    }

    @Test
    void getLedgerMaxSumByMovementType() {
        LedgerDAO ledgerDAO = new LedgerDAO();
        String str = "РАСХОД";

        Ledger ledger = ledgerDAO.getLedgerMaxSumByMovementType(str);

        System.out.println("Ledger max sum by movement type " + str + ":");
        System.out.println(ledger);
    }

    @Test
    void getSumByMovementTypeAndMont() {
        LedgerDAO ledgerDAO = new LedgerDAO();

        Double sumL = ledgerDAO.getSumByMovementTypeAndMont("ПРИХОД", 11, 2021);

        System.out.println("Movements per month : " + sumL);
    }
}