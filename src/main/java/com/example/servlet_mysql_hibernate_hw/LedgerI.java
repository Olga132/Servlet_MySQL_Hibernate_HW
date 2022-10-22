package com.example.servlet_mysql_hibernate_hw;

import entity.Ledger;

import java.util.List;

public interface LedgerI {

    void addEntry(Ledger ledger);

    Ledger selectEntry(int id);

    List<Ledger> selectAllEntry();

    void deleteEntry(int id);

    void updateEntry(Ledger ledger);

    List<Ledger> getEntriesByCondition(String str);
}
