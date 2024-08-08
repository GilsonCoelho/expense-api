package br.com.gsc.expense.controller;

import br.com.gsc.expense.controller.dto.ExpenseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.com.gsc.expense.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/expenses")
@Tag(name = "Expenses Controller", description = "RESTful API for managing expenses.")
public record ExpenseController(ExpenseService expenseService) {

    @GetMapping
    @Operation(summary = "Get all expenses", description = "Retrieve a list of all registered expenses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<ExpenseDto>> findAll() {
        var expenses = expenseService.findAll();
        var expensesDto = expenses.stream().map(ExpenseDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(expensesDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a expense by ID", description = "Retrieve a specific expense based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseDto> findById(@PathVariable Long id) {
        var expense = expenseService.findById(id);
        return ResponseEntity.ok(new ExpenseDto(expense));
    }

    @PostMapping
    @Operation(summary = "Create a new expense", description = "Create a new expense and return the created expense's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid expense data provided")
    })
    public ResponseEntity<ExpenseDto> create(@RequestBody ExpenseDto userDto) {
        var expense = expenseService.create(userDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(expense.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ExpenseDto(expense));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a expense", description = "Update the data of an existing expense based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "422", description = "Invalid expense data provided")
    })
    public ResponseEntity<ExpenseDto> update(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        var expense = expenseService.update(id, expenseDto.toModel());
        return ResponseEntity.ok(new ExpenseDto(expense));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a expense", description = "Delete an existing expense based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
