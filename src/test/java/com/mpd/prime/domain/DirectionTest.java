package com.mpd.prime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mpd.prime.web.rest.TestUtil;

public class DirectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Direction.class);
        Direction direction1 = new Direction();
        direction1.setId(1L);
        Direction direction2 = new Direction();
        direction2.setId(direction1.getId());
        assertThat(direction1).isEqualTo(direction2);
        direction2.setId(2L);
        assertThat(direction1).isNotEqualTo(direction2);
        direction1.setId(null);
        assertThat(direction1).isNotEqualTo(direction2);
    }
}
