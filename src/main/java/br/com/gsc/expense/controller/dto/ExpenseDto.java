package br.com.gsc.expense.controller.dto;

import br.com.gsc.expense.domain.model.Expense;
import br.com.gsc.expense.domain.model.TypeCard;

import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

public record ExpenseDto(
        Long id,
        String description,
        BigDecimal amountPaid,
        TypeCardDto typeCard,
        TypeExpenseDto typeExpense,
        CardDto card) {

    public ExpenseDto(Expense model){
        this(
                model.getId(),
                model.getDescription(),
                model.getAmountPaid(),
                ofNullable(model.getTypeCard()).map(TypeCardDto::new).orElse(null),
                ofNullable(model.getTypeExpense()).map(TypeExpenseDto::new).orElse(null),
                ofNullable(model.getCard()).map(CardDto::new).orElse(null)
        );
    }

    public Expense toModel(){
        Expense model =  new Expense();
        model.setId(this.id);
        model.setDescription(this.description);
        model.setAmountPaid(this.amountPaid);
        model.setTypeCard(ofNullable(this.typeCard).map(TypeCardDto::toModel).orElse(null));
        model.setTypeExpense(ofNullable(this.typeExpense).map(TypeExpenseDto::toModel).orElse(null));
        model.setCard(ofNullable(this.card).map(CardDto::toModel).orElse(null));
        return model;
    }
}
