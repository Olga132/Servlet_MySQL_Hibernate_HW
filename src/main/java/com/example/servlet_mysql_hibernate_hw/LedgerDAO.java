package com.example.servlet_mysql_hibernate_hw;

import entity.Ledger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class LedgerDAO implements LedgerI {
    @Override
    public void addEntry(Ledger ledger) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(ledger);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @Override
    public Ledger selectEntry(int id) {

        Ledger ledger;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ledger = entityManager.find(Ledger.class, id);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return ledger;
    }

    @Override
    public List<Ledger> selectAllEntry() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<Ledger> ledgerList;

        try {
            transaction.begin();
            ledgerList = entityManager.createQuery("SELECT e FROM Ledger e").getResultList();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return ledgerList;
    }

    @Override
    public void deleteEntry(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Ledger removeLedger = entityManager.find(Ledger.class, id);
            entityManager.remove(removeLedger);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

    }

    @Override
    public void updateEntry(Ledger ledger) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(ledger);
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @Override
    public List<Ledger> getEntriesByCondition(String str) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<Ledger> ledgerList;

        try {
            transaction.begin();
            ledgerList = entityManager
                    .createQuery
                            ("select e from Ledger e where concat(e.id,'.', e.date , '.', e.sum, '.', e.specification,'.', e.movementType) like :strSearch")
                    .setParameter("strSearch", "%" + str + "%").getResultList();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return ledgerList;
    }

    public double getSumEntries() {
        double sum = 0.0;
        List<Ledger> entries = selectAllEntry();
        for (Ledger e : entries) {
            if (e.getMovementType().equals("ПРИХОД")) {
                sum += e.getSum();
            } else sum -= e.getSum();
        }
        return sum;
    }
}
