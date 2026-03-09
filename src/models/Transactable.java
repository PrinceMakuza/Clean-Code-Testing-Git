package models;

public interface Transactable {
    boolean processTransaction(double amount, String type);
}