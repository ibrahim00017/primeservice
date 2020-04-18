package com.mpd.prime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mpd.prime.web.rest.TestUtil;

public class ChangementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Changement.class);
        Changement changement1 = new Changement();
        changement1.setId(1L);
        Changement changement2 = new Changement();
        changement2.setId(changement1.getId());
        assertThat(changement1).isEqualTo(changement2);
        changement2.setId(2L);
        assertThat(changement1).isNotEqualTo(changement2);
        changement1.setId(null);
        assertThat(changement1).isNotEqualTo(changement2);
    }
}
