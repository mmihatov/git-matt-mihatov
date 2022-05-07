package com.techelevator.tenmo.model;

import com.techelevator.tenmo.services.AccountHolderService;

import java.math.BigDecimal;
import java.util.List;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private String transferType;
    private int transferStatusId;
    private String transferStatus;
    private int accountToId;
    private int accountFromId;
    private BigDecimal transferAmount;

    public Transfer(int transferId, int transferTypeId, String transferType, int transferStatusId, String transferStatus, int accountToId, int accountFromId, BigDecimal transferAmount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferType = transferType;
        this.transferStatusId = transferStatusId;
        this.transferStatus = transferStatus;
        this.accountToId = accountToId;
        this.accountFromId = accountFromId;
        this.transferAmount = transferAmount;
    }

    public Transfer() {
    }

    public String goThroughUsernames(AccountHolder[] contacts, String toOrFrom) {
        String usernameToReturn = null;
        for (AccountHolder accountHolder : contacts) {
            if (toOrFrom.equals("From")) {
                if (accountHolder.getAccountId() == accountFromId) {
                    usernameToReturn = accountHolder.getUsername();
                }
            } else {
                if (accountHolder.getAccountId() == accountToId) {
                    usernameToReturn = accountHolder.getUsername();
                }
            }
        }
        return usernameToReturn;
    }

    public String viewTransferToString(AccountHolderService accountHolderService, boolean isTransfer) {
        int currentUser = accountHolderService.getCurrentAccountHolder().getAccountId();
        String toOrFrom = (accountFromId == currentUser) ? "To" : "From";
        String nameOnAccount = goThroughUsernames(accountHolderService.getContactList(), toOrFrom);
        if(isTransfer){
            return transferId + "          " + toOrFrom + " " + nameOnAccount + "          $ " + transferAmount;
        } else
            return transferId + "          " + nameOnAccount + "                 $ " + transferAmount;
    }

    public String toString(AccountHolderService accountHolderService) {
        return  "Id: " + transferId + "\n" +
                "From: " + accountHolderService.getUsernameByAccountId(accountFromId) + "\n" +
                "To: " + accountHolderService.getUsernameByAccountId(accountToId) + "\n" +
                "Type: " + transferType + "\n" +
                "Status: " + transferStatus + "\n" +
                "Amount: $" + transferAmount;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}
