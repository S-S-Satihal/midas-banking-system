package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DatabaseConduit {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public DatabaseConduit(UserRepository userRepository,
                           TransactionRepository transactionRepository,
                           RestTemplate restTemplate) {

        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    // Used by UserPopulator
    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public void processTransaction(long senderId,
                                   long recipientId,
                                   float amount) {

        UserRecord sender = userRepository.findById(senderId);
        UserRecord recipient = userRepository.findById(recipientId);

        // Validate users
        if (sender == null || recipient == null) {
            return;
        }

        // Validate balance
        if (sender.getBalance() < amount) {
            return;
        }

        // Create transaction for Incentive API
        Transaction transaction =
                new Transaction(senderId, recipientId, amount);

        // Call Incentive API
        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        float incentiveAmount = 0;

        if (incentive != null) {
            incentiveAmount = incentive.getAmount();
        }

        // Update balances
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(
                recipient.getBalance() + amount + incentiveAmount
        );

        userRepository.save(sender);
        userRepository.save(recipient);

        // Save transaction
        TransactionRecord record =
                new TransactionRecord(
                        sender,
                        recipient,
                        amount,
                        incentiveAmount
                );

        transactionRepository.save(record);

        // Print Wilbur's current balance after every transaction
        UserRecord wilbur = userRepository.findById(9L);

        if (wilbur != null) {
            System.out.println("FINAL WILBUR = " + wilbur.getBalance());
        }
    }
}