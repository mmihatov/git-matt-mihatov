package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AccountHolder;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AccountHolderService {

    public static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public AccountHolder[] getContactList() {
        AccountHolder[] accountHolders = null;
        try {
            ResponseEntity<AccountHolder[]> response =
                    restTemplate.exchange(API_BASE_URL + "holder/contacts", HttpMethod.GET,
                            makeAuthEntity(), AccountHolder[].class);
            accountHolders = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountHolders;
    }

    public AccountHolder getCurrentAccountHolder() {
        AccountHolder accountHolder = null;
        try {
            ResponseEntity<AccountHolder> response =
                    restTemplate.exchange(API_BASE_URL + "holder/activeHolder", HttpMethod.GET, makeAuthEntity(),
                            AccountHolder.class);
            accountHolder = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return accountHolder;
    }

    public String getUsernameByAccountId(int accountId) {
        String username = getCurrentAccountHolder().getUsername();
        for (AccountHolder accountHolder : getContactList()) {
            if (accountHolder.getAccountId() == accountId) {
                username = accountHolder.getUsername();
            }
        }
        return username;
    }

    public HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
