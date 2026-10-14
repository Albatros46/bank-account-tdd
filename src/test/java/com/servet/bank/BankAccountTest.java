package com.servet.bank;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("BankAccount")
class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("TR001", 100.0);
    }

    @Nested
    @DisplayName("Hesap oluşturma")
    class Creation {

        @Test
        @DisplayName("Başlangıç bakiyesi doğru atanmalı")
        void initialBalanceIsSet() {
            assertThat(account.getBalance()).isEqualTo(100.0);
            assertThat(account.getAccountNumber()).isEqualTo("TR001");
        }

        @Test
        @DisplayName("Negatif başlangıç bakiyesi reddedilmeli")
        void negativeInitialBalanceIsRejected() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new BankAccount("TR002", -1.0));
        }

        @ParameterizedTest(name = "Hesap no = \"{0}\"")
        @NullAndEmptySource
        @ValueSource(strings = { "   " })
        @DisplayName("Boş hesap numarası reddedilmeli")
        void blankAccountNumberIsRejected(String accountNumber) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> new BankAccount(accountNumber, 10.0));
        }
    }

    @Nested
    @DisplayName("Para yatırma")
    class Deposit {

        @Test
        @DisplayName("Para yatırınca bakiye artmalı")
        void depositIncreasesBalance() {
            account.deposit(50.0);
            assertThat(account.getBalance()).isEqualTo(150.0);
        }

        @ParameterizedTest(name = "{0} yatırılınca bakiye {1} olmalı")
        @CsvSource({ "0.01, 100.01", "1, 101", "900, 1000" })
        void depositVariousAmounts(double amount, double expected) {
            account.deposit(amount);
            assertThat(account.getBalance()).isEqualTo(expected);
        }

        @ParameterizedTest(name = "Geçersiz tutar: {0}")
        @ValueSource(doubles = { 0, -0.01, -10 })
        void nonPositiveDepositIsRejected(double amount) {
            assertThatIllegalArgumentException().isThrownBy(() -> account.deposit(amount));
            assertThat(account.getBalance()).isEqualTo(100.0);
        }
    }

    @Nested
    @DisplayName("Para çekme")
    class Withdraw {

        @Test
        @DisplayName("Para çekince bakiye azalmalı")
        void withdrawDecreasesBalance() {
            account.withdraw(30.0);
            assertThat(account.getBalance()).isEqualTo(70.0);
        }

        @Test
        @DisplayName("Bakiyenin tamamı çekilebilmeli")
        void withdrawEntireBalance() {
            account.withdraw(100.0);
            assertThat(account.getBalance()).isZero();
        }

        @Test
        @DisplayName("Bakiyeden fazla para çekilemez")
        void withdrawMoreThanBalanceThrows() {
            assertThatThrownBy(() -> account.withdraw(500.0))
                    .isInstanceOf(InsufficientFundsException.class)
                    .hasMessageContaining("Yetersiz bakiye");
            assertThat(account.getBalance()).isEqualTo(100.0);
        }

        @ParameterizedTest(name = "Geçersiz tutar: {0}")
        @ValueSource(doubles = { 0, -5 })
        void nonPositiveWithdrawIsRejected(double amount) {
            assertThatIllegalArgumentException().isThrownBy(() -> account.withdraw(amount));
        }
    }

    @Nested
    @DisplayName("Transfer")
    class Transfer {

        private BankAccount target;

        @BeforeEach
        void setUp() {
            target = new BankAccount("TR999", 0.0);
        }

        @Test
        @DisplayName("Transfer iki hesabı da güncellemeli")
        void transferMovesMoney() {
            account.transfer(target, 40.0);
            assertThat(account.getBalance()).isEqualTo(60.0);
            assertThat(target.getBalance()).isEqualTo(40.0);
        }

        @Test
        @DisplayName("Yetersiz bakiyede hiçbir hesap değişmemeli")
        void failedTransferChangesNothing() {
            assertThatThrownBy(() -> account.transfer(target, 1000.0))
                    .isInstanceOf(InsufficientFundsException.class);
            assertThat(account.getBalance()).isEqualTo(100.0);
            assertThat(target.getBalance()).isZero();
        }

        @Test
        @DisplayName("Kendi hesabına transfer yapılamaz")
        void transferToSelfIsRejected() {
            assertThatIllegalArgumentException().isThrownBy(() -> account.transfer(account, 10.0));
        }

        @Test
        @DisplayName("Hedef hesap null olamaz")
        void transferToNullIsRejected() {
            assertThatIllegalArgumentException().isThrownBy(() -> account.transfer(null, 10.0));
        }
    }
}