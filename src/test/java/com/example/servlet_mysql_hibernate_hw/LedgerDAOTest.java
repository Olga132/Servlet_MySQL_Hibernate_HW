package com.example.servlet_mysql_hibernate_hw;

import entity.Ledger;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LedgerDAOTest {

    @Test
    void addEntry() {
        Ledger testLedger = new Ledger(new Date(2022,10,10),
                "HibernateInsertTest", 12450.1,"ПРИХОД");
        LedgerDAO ledgerDAO = new LedgerDAO();

        ledgerDAO.addEntry(testLedger);

        List<Ledger> ledgerList = ledgerDAO.selectAllEntry();

        for (Ledger l : ledgerList) {
            System.out.println(l);
        }
    }

    @Test
    void selectEntry() {
    }

    @Test
    void selectAllEntry() {
        LedgerDAO ledgerDAO = new LedgerDAO();

        List<Ledger> ledgerList = ledgerDAO.selectAllEntry();

        for (Ledger l : ledgerList) {
            System.out.println(l);
        }
    }

    @Test
    void deleteEntry() {
        LedgerDAO ledgerDAO = new LedgerDAO();

        ledgerDAO.deleteEntry(19);
    }

    @Test
    void updateEntry() {
    }

    @Test
    void getEntriesByCondition() {
        LedgerDAO ledgerDAO = new LedgerDAO();

        List<Ledger> ledgerList = ledgerDAO.getEntriesByCondition("ООО");

        for (Ledger l : ledgerList) {
            System.out.println(l);
        }
    }
}