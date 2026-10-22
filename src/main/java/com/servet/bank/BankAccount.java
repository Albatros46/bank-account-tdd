package com.servet.bank;

/**
 * Basit bir banka hesabı: para yatırma, çekme ve hesaplar arası transfer.
 * TDD ile yazıldı (Red → Green → Refactor); testler: BankAccountTest.
 */
public class BankAccount {

    private final String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Hesap numarası boş olamaz");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Başlangıç bakiyesi negatif olamaz: " + initialBalance);
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        requirePositive(amount);
        balance += amount;
    }

    public void withdraw(double amount) {
        requirePositive(amount);
        if (amount > balance) {
            throw new InsufficientFundsException("Yetersiz bakiye: bakiye " + balance + ", istenen " + amount);
        }
        balance -= amount;
    }

    public void transfer(BankAccount target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("Hedef hesap boş olamaz");
        }
        if (target == this) {
            throw new IllegalArgumentException("Kendi hesabına transfer yapılamaz");
        }
        // Sıra önemli: önce çek, sonra yatır. Çekme başarısız olursa hedef hesaba hiç dokunulmaz.
        withdraw(amount);
        target.deposit(amount);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    private static void requirePositive(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Tutar pozitif olmalı: " + amount);
        }
    }
}
