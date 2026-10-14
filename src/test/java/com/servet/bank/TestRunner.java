package com.servet.bank;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import java.io.PrintWriter;
import java.util.List;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

public class TestRunner {

    // Çalıştırılacak test sınıfları — sıra burada belirlenir
    private static final List<Class<?>> TEST_CLASSES = List.of(
            BankAccountTest.class
            // , TransactionTest.class
            // , AnotherTest.class
    );

    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();
        PrintWriter out = new PrintWriter(System.out);
        long totalFailures = 0;

        for (Class<?> testClass : TEST_CLASSES) {
            out.println("\n===== " + testClass.getSimpleName() + " =====");

            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(selectClass(testClass))
                    .build();

            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            launcher.execute(request, listener);

            TestExecutionSummary summary = listener.getSummary();
            out.printf("Başarılı: %d | Başarısız: %d | Toplam: %d%n",
                    summary.getTestsSucceededCount(),
                    summary.getTotalFailureCount(),
                    summary.getTestsFoundCount());
            summary.printFailuresTo(out, 5);
            out.flush();

            totalFailures += summary.getTotalFailureCount();
        }

        out.println("\n===== SONUÇ: " + (totalFailures == 0 ? "✅ HEPSİ GEÇTİ" : "❌ " + totalFailures + " HATA") + " =====");
        out.flush();
        System.exit(totalFailures == 0 ? 0 : 1);
    }
}