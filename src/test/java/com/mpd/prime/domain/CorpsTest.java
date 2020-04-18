package com.mpd.prime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mpd.prime.web.rest.TestUtil;

public class CorpsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corps.class);
        Corps corps1 = new Corps();
        corps1.setId(1L);
        Corps corps2 = new Corps();
        corps2.setId(corps1.getId());
        assertThat(corps1).isEqualTo(corps2);
        corps2.setId(2L);
        assertThat(corps1).isNotEqualTo(corps2);
        corps1.setId(null);
        assertThat(corps1).isNotEqualTo(corps2);
    }
}
