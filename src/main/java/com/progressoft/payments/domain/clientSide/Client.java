package com.progressoft.payments.domain.clientSide;

public class Client {
    private String account;
    private String name;
    private String address1;
    private String address2;

    public Client() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Override
    public String toString() {
        return "Client{" +
                "account='" + account + '\'' +
                (name != null? ", name='" + name + '\'' : "")+
                (address1 != null?", address1='" + address1 + '\'' : "") +
                (address2 != null? ", address2='" + address2 + '\'' : "") +
                '}';
    }
}
