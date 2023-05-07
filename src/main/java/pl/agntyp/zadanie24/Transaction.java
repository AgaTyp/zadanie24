package pl.agntyp.zadanie24;

import java.time.LocalDate;

public class Transaction {
    private Integer id;
    private String type;
    private String description;
    private double amount;
    private LocalDate date;

    public Transaction() {
    }

    public Transaction(String type, String description, double amount, LocalDate date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(Integer id, String type, String description, double amount, LocalDate date) {
        this(type, description, amount, date);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
