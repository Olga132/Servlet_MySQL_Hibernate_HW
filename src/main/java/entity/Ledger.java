package entity;

import jakarta.persistence.*;

import java.sql.Date;


@Entity
public class Ledger {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "specification")
    private String specification;
    @Basic
    @Column(name = "sum")
    private Double sum;
    @Basic
    @Column(name = "movement_type")
    private String movementType;

    public Ledger(Date date, String specification, Double sum, String movementType) {
        this.date = date;
        this.specification = specification;
        this.sum = sum;
        this.movementType = movementType;
    }

    public Ledger(int id, Date date, String specification, Double sum, String movementType) {
        this.id = id;
        this.date = date;
        this.specification = specification;
        this.sum = sum;
        this.movementType = movementType;
    }

    public Ledger() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ledger ledger = (Ledger) o;

        if (id != ledger.id) return false;
        if (date != null ? !date.equals(ledger.date) : ledger.date != null) return false;
        if (specification != null ? !specification.equals(ledger.specification) : ledger.specification != null)
            return false;
        if (sum != null ? !sum.equals(ledger.sum) : ledger.sum != null) return false;
        if (movementType != null ? !movementType.equals(ledger.movementType) : ledger.movementType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (specification != null ? specification.hashCode() : 0);
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (movementType != null ? movementType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ledger{" +
                "id=" + id +
                ", date=" + date +
                ", specification='" + specification + '\'' +
                ", sum=" + sum +
                ", movementType='" + movementType + '\'' +
                '}';
    }
}
