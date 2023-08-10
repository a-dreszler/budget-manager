package pl.adreszler.budgetmanager;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

class Transaction {

    private Long id;
    private String description;
    private BigDecimal amount;
    @DateTimeFormat (pattern = "YYYY-MM-dd")
    private LocalDate date;
    private Type type;

    public Transaction(Long id, String description, BigDecimal amount, LocalDate date, Type type) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.id = id;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
