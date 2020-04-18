package com.mpd.prime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mpd.prime.web.rest.TestUtil;

public class PrimeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prime.class);
        Prime prime1 = new Prime();
        prime1.setId(1L);
        Prime prime2 = new Prime();
        prime2.setId(prime1.getId());
        assertThat(prime1).isEqualTo(prime2);
        prime2.setId(2L);
        assertThat(prime1).isNotEqualTo(prime2);
        prime1.setId(null);
        assertThat(prime1).isNotEqualTo(prime2);
    }
}
