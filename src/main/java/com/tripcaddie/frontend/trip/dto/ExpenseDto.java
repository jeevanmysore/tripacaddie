package com.tripcaddie.frontend.trip.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.tripcaddie.backend.trip.model.Expense;

public class ExpenseDto implements Serializable {

	private static final long serialVersionUID = -7389912689813883559L;
	private int expenseId;
	private TripMemberDto tripMemberDto;
	private String title;
	private Calendar expenseDate;
	private Date expensDate;
	private String expensdateformatted;
	private Double amount;
	private String createdBy;
	private Calendar createdDate;
	private String lastUpdatedBy;
	private Calendar lastUpdatedDate;
	
	public int getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}
	public TripMemberDto getTripMemberDto() {
		return tripMemberDto;
	}
	public void setTripMemberDto(TripMemberDto tripMemberDto) {
		this.tripMemberDto = tripMemberDto;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Calendar getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Calendar expenseDate) {
		this.expenseDate = expenseDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Calendar getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Calendar lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public static void populate(ExpenseDto expenseDto,Expense expense) {
		
		expenseDto.setAmount(expense.getAmount());
		expenseDto.setCreatedBy(expense.getCreatedBy());
		expenseDto.setCreatedDate(expense.getCreatedDate());
		expenseDto.setExpenseDate(expense.getExpenseDate());
		expenseDto.setExpenseId(expense.getExpenseId());
		expenseDto.setLastUpdatedBy(expense.getLastUpdatedBy());
		expenseDto.setLastUpdatedDate(expense.getLastUpdatedDate());
		expenseDto.setTitle(expense.getTitle());
		expenseDto.setExpensDate(expense.getExpenseDate().getTime());
		DateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		expenseDto.setExpensdateformatted(dateFormat.format(expense.getExpenseDate().getTime()));
		expenseDto.setTripMemberDto(TripMemberDto.instantiate(expense.getTripMember()));
	}
	
	public static ExpenseDto instantiate(Expense expense) {
		
		ExpenseDto expenseDto=new ExpenseDto();
		populate(expenseDto, expense);
		return expenseDto;
	}
	public Date getExpensDate() {
		return expensDate;
	}
	public void setExpensDate(Date expensDate) {
		this.expensDate = expensDate;
	}
	public String getExpensdateformatted() {
		return expensdateformatted;
	}
	public void setExpensdateformatted(String expensdateformatted) {
		this.expensdateformatted = expensdateformatted;
	}

}
