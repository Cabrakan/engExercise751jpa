package ch.engenius.bank;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

public class BankRunnerTest {
    @Test
    public void testMainApplication() {
        assertThatNoException()
                .isThrownBy(() -> {
                    for (int i = 0; i < 50; i++)
                        BankRunner.main(null);
                });
    }
}