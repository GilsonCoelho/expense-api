package br.com.gsc.expense.controller.dto;

import br.com.gsc.expense.domain.model.Expense;
import br.com.gsc.expense.domain.model.TypeCard;
import br.com.gsc.expense.domain.model.TypeExpense;

public record TypeCardDto(
        Long id,
        String description) {
    public TypeCardDto(Expense model){
        this(
                model.getId(),
                model.getDescription()
        );
    }

    public TypeCardDto(TypeCard model) {
        this(model.getId(), model.getDescription());
    }

    public TypeCard toModel(){
        TypeCard model = new TypeCard();
        model.setId(this.id);
        model.setDescription(this.description);
        return model;
    }
}
