package com.example.servlet_mysql_hibernate_hw;

import entity.Ledger;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
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
                            ("select e " +
                                    "from Ledger e " +
                                    "where concat(e.id,'.', e.date , '.', e.sum, '.'," +
                                    " e.specification,'.', e.movementType) like :strSearch")
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

    public List<Ledger> gettingLedgerInDateRange(LocalDate localDate1, LocalDate localDate2) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Date date1 = Date.valueOf(localDate1);
        Date date2 = Date.valueOf(localDate2);

        List entries;

        try {
            transaction.begin();
            entries = entityManager.createQuery("select l from Ledger l " +
                            "where l.date between :date1 and :date2")
                    .setParameter("date1", date1)
                    .setParameter("date2", date2)
                    .getResultList();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return entries;
    }

    public List<Date> dateLedgers() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<Date> dateList;
        try {
            transaction.begin();
            dateList = entityManager.createQuery("select l.date from Ledger l", Date.class).getResultList();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return dateList;
    }

    public List<Ledger> sortedLedgerByDate() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List entries;
        try {
            transaction.begin();
            entries = entityManager.createNamedQuery("sorted_by_date").getResultList();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return entries;
    }

    public Ledger getLedgerMaxSumByMovementType(String movementType) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Ledger ledger;
        try {
            transaction.begin();
            ledger = entityManager
                    .createQuery("select l " +
                            "from Ledger l " +
                            "where l.movementType = :movementType " +
                            "order by l.sum desc", Ledger.class)
                    .setParameter("movementType",movementType)
                    .setMaxResults(1)
                    .getSingleResult();
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


    public Double getSumByMovementTypeAndMont(String movementType, int monthL, int yearL) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Double sumL;

        try {
            transaction.begin();
            sumL = entityManager
                    .createNamedQuery("get_sum_by_movement_type_for_the_month_of_the_year", Double.class)
                    .setParameter("moveType", movementType)
                    .setParameter("monthL", monthL)
                    .setParameter("yearL", yearL).getSingleResult();
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }

        return sumL;
    }
}
