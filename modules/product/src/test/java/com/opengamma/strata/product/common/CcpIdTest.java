/*
 * Copyright (C) 2019 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.common;

import static com.opengamma.strata.collect.TestHelper.assertSerialization;
import static com.opengamma.strata.collect.TestHelper.coverPrivateConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.testng.annotations.Test;

/**
 * Test {@link CcpId}.
 */
@Test
public class CcpIdTest {

  public void test_of() {
    CcpId test = CcpId.of("GB");
    assertThat(test.getName()).isEqualTo("GB");
    assertThat(test).hasToString("GB");
    assertThatIllegalArgumentException().isThrownBy(() -> CcpId.of(""));
  }

  //-------------------------------------------------------------------------
  public void test_equalsHashCode() {
    CcpId a = CcpId.of("LCH");
    CcpId a2 = CcpIds.LCH;
    CcpId b = CcpId.of("CME");
    assertThat(a)
        .isEqualTo(a)
        .isEqualTo(a2)
        .isNotEqualTo(b)
        .isNotEqualTo("")
        .isNotEqualTo(null)
        .hasSameHashCodeAs(a2);
  }

  //-------------------------------------------------------------------------
  public void coverage() {
    coverPrivateConstructor(CcpIds.class);
  }

  public void test_serialization() {
    CcpId test = CcpId.of("LCH");
    assertSerialization(test);
  }

}
