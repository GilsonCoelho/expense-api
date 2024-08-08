package br.com.gsc.expense.controller.dto;

import br.com.gsc.expense.domain.model.Expense;
import br.com.gsc.expense.domain.model.TypeExpense;

public record TypeExpenseDto(
        Long id,
        String description) {

    public TypeExpenseDto(TypeExpense model) {
        this(model.getId(), model.getDescription());
    }

    public TypeExpense toModel(){
        TypeExpense model = new TypeExpense();
        model.setId(this.id);
        model.setDescription(this.description);
        return model;
    }
}
