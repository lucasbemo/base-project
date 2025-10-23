package com.lz.base.project.dataset.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;

    private Integer currentAge;
    private Integer retirementAge;
    private Integer birthYear;
    private Integer birthMonth;
    private String gender;
    private String address;
    private Double latitude;
    private Double longitude;
    private BigDecimal perCapitaIncome;
    private BigDecimal yearlyIncome;
    private BigDecimal totalDebt;
    private Integer creditScore;
    private Integer numCreditCards;

    public UserEntity() {}

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCurrentAge() { return currentAge; }
    public void setCurrentAge(Integer currentAge) { this.currentAge = currentAge; }

    public Integer getRetirementAge() { return retirementAge; }
    public void setRetirementAge(Integer retirementAge) { this.retirementAge = retirementAge; }

    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }

    public Integer getBirthMonth() { return birthMonth; }
    public void setBirthMonth(Integer birthMonth) { this.birthMonth = birthMonth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public BigDecimal getPerCapitaIncome() { return perCapitaIncome; }
    public void setPerCapitaIncome(BigDecimal perCapitaIncome) { this.perCapitaIncome = perCapitaIncome; }

    public BigDecimal getYearlyIncome() { return yearlyIncome; }
    public void setYearlyIncome(BigDecimal yearlyIncome) { this.yearlyIncome = yearlyIncome; }

    public BigDecimal getTotalDebt() { return totalDebt; }
    public void setTotalDebt(BigDecimal totalDebt) { this.totalDebt = totalDebt; }

    public Integer getCreditScore() { return creditScore; }
    public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }

    public Integer getNumCreditCards() { return numCreditCards; }
    public void setNumCreditCards(Integer numCreditCards) { this.numCreditCards = numCreditCards; }
}
