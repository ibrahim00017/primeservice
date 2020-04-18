package com.mpd.prime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mpd.prime.web.rest.TestUtil;

public class AllouerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allouer.class);
        Allouer allouer1 = new Allouer();
        allouer1.setId(1L);
        Allouer allouer2 = new Allouer();
        allouer2.setId(allouer1.getId());
        assertThat(allouer1).isEqualTo(allouer2);
        allouer2.setId(2L);
        assertThat(allouer1).isNotEqualTo(allouer2);
        allouer1.setId(null);
        assertThat(allouer1).isNotEqualTo(allouer2);
    }
}
