package com.pocketmarket.mined.dto;

import java.math.BigDecimal;

/**
 * Created by mark on 12/1/17.
 */

public class BalanceAnalyticsDTO {
    private int id;

    private BigDecimal pesofixed;

    private BigDecimal dollar;

    private BigDecimal equity;

    public BalanceAnalyticsDTO(){
        super();
    }

    public BalanceAnalyticsDTO(int id, BigDecimal pesofixed, BigDecimal dollar, BigDecimal equity){
        this.id = id;

        this.pesofixed = pesofixed;

        this.dollar = dollar;

        this.equity = equity;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPesofixed() {
        return pesofixed;
    }

    public void setPesofixed(BigDecimal pesofixed) {
        this.pesofixed = pesofixed;
    }

    public BigDecimal getDollar() {
        return dollar;
    }

    public void setDollar(BigDecimal dollar) {
        this.dollar = dollar;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public void setEquity(BigDecimal equity) {
        this.equity = equity;
    }
}
