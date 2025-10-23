package com.lz.base.project.dataset.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private Long id;

    private String date;
    private Long clientId;
    private Long cardId;
    private BigDecimal amount;
    private String useChip;
    private Long merchantId;
    private String merchantCity;
    private String merchantState;
    private String zip;
    private String mcc;
    private String errors;

    public TransactionEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getUseChip() { return useChip; }
    public void setUseChip(String useChip) { this.useChip = useChip; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getMerchantCity() { return merchantCity; }
    public void setMerchantCity(String merchantCity) { this.merchantCity = merchantCity; }

    public String getMerchantState() { return merchantState; }
    public void setMerchantState(String merchantState) { this.merchantState = merchantState; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public String getMcc() { return mcc; }
    public void setMcc(String mcc) { this.mcc = mcc; }

    public String getErrors() { return errors; }
    public void setErrors(String errors) { this.errors = errors; }
}
