package com.servet.bank;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Bank Regression Suite")
@SelectClasses({ BankAccountTest.class /* , TransactionTest.class */ })
// alternatif: @SelectPackages("com.servet.bank")
class RegressionSuite {
}