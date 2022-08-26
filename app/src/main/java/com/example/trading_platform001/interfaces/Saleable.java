package com.example.trading_platform001.interfaces;

import java.math.BigDecimal;

public interface Saleable {
    BigDecimal getPrice();
    String getName();
    Long getId();
}
