package br.com.gsc.expense.service.impl;

import br.com.gsc.expense.domain.model.Expense;
import br.com.gsc.expense.domain.repository.ExpenseRepository;
import br.com.gsc.expense.service.ExpenseService;
import br.com.gsc.expense.service.exception.BusinessException;
import br.com.gsc.expense.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> findAll() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Expense findById(Long id) {
        return expenseRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional
    public Expense create(Expense expenseToCreate) {
        ofNullable(expenseToCreate).orElseThrow(() -> new BusinessException("Expense to create must not be null."));
        ofNullable(expenseToCreate.getTypeCard()).orElseThrow(() -> new BusinessException("Expense type of card must not be null."));
        ofNullable(expenseToCreate.getCard()).orElseThrow(() -> new BusinessException("Expense card must not be null."));
        ofNullable(expenseToCreate.getTypeExpense()).orElseThrow(() -> new BusinessException("Expense type of expense must not be null."));
        ofNullable(expenseToCreate.getAmountPaid()).orElseThrow(() -> new BusinessException("Expense amount od paid must not be null."));


        this.validateChangeableId(expenseToCreate.getId(), "created");

        return this.expenseRepository.save(expenseToCreate);
    }

    @Override
    @Transactional
    public Expense update(Long id, Expense expenseToUpdate) {
        this.validateChangeableId(id, "updated");
        Expense dbExpense = this.findById(id);
        if (!dbExpense.getId().equals(expenseToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbExpense.setDescription(expenseToUpdate.getDescription());
        dbExpense.setTypeCard(expenseToUpdate.getTypeCard());
        dbExpense.setCard(expenseToUpdate.getCard());
        dbExpense.setTypeExpense(expenseToUpdate.getTypeExpense());
        dbExpense.setAmountPaid(expenseToUpdate.getAmountPaid());
        return this.expenseRepository.save(dbExpense);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        Expense dbExpense = this.findById(id);
        this.expenseRepository.delete(dbExpense);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("Expense with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}
