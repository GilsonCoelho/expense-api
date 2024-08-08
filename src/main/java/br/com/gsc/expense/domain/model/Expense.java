package br.com.gsc.expense.domain.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = ("tb_expense"))
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_paid", precision = 13, scale = 2)
    private BigDecimal amountPaid;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private TypeCard typeCard;

    @ManyToOne(cascade = CascadeType.ALL)
    private TypeExpense typeExpense;

    @ManyToOne(cascade = CascadeType.ALL)
    private Card card;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeCard getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(TypeCard typeCard) {
        this.typeCard = typeCard;
    }

    public TypeExpense getTypeExpense() {
        return typeExpense;
    }

    public void setTypeExpense(TypeExpense typeExpense) {
        this.typeExpense = typeExpense;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
