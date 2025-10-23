package com.lz.base.project.dataset.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    private Long id;

    private Long clientId;
    private String cardBrand;
    private String cardType;
    private String cardNumber;
    private String expires;
    private String cvv;
    private String hasChip;
    private Integer numCardsIssued;
    private BigDecimal creditLimit;
    private String acctOpenDate;
    private Integer yearPinLastChanged;
    private String cardOnDarkWeb;

    public CardEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getCardBrand() { return cardBrand; }
    public void setCardBrand(String cardBrand) { this.cardBrand = cardBrand; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpires() { return expires; }
    public void setExpires(String expires) { this.expires = expires; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public String getHasChip() { return hasChip; }
    public void setHasChip(String hasChip) { this.hasChip = hasChip; }

    public Integer getNumCardsIssued() { return numCardsIssued; }
    public void setNumCardsIssued(Integer numCardsIssued) { this.numCardsIssued = numCardsIssued; }

    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) { this.creditLimit = creditLimit; }

    public String getAcctOpenDate() { return acctOpenDate; }
    public void setAcctOpenDate(String acctOpenDate) { this.acctOpenDate = acctOpenDate; }

    public Integer getYearPinLastChanged() { return yearPinLastChanged; }
    public void setYearPinLastChanged(Integer yearPinLastChanged) { this.yearPinLastChanged = yearPinLastChanged; }

    public String getCardOnDarkWeb() { return cardOnDarkWeb; }
    public void setCardOnDarkWeb(String cardOnDarkWeb) { this.cardOnDarkWeb = cardOnDarkWeb; }
}
