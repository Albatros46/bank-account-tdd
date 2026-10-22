package com.servet.bank;

/**
 * Basit bir banka hesabı.
 *
 * TDD görevi: Önce BankAccountTest'i çalıştır (kırmızı), sonra aşağıdaki
 * metotları testleri geçecek şekilde doldur (yeşil), en son kodu temizle (refactor).
 */
public class BankAccount {

    private final String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        // TODO: accountNumber boş/null olamaz, initialBalance negatif olamaz -> IllegalArgumentException
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Tutar pozitif olmalı: " + amount);
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        // TODO: amount <= 0 ise IllegalArgumentException
        // TODO: amount > balance ise InsufficientFundsException("Yetersiz bakiye...")
        throw new UnsupportedOperationException("Henüz yazılmadı");
    }

    public void transfer(BankAccount target, double amount) {
        // TODO: target null olamaz, kendine transfer yapılamaz; withdraw + target.deposit
        throw new UnsupportedOperationException("Henüz yazılmadı");
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}