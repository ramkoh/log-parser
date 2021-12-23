package com.example.demo;

import com.example.demo.dao.RequirementRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Requirement;
import com.example.demo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    RequirementRepository requirementRepository;
    @Autowired
    UserRepository userRepository;

    User clientUser;
    User customerAUser;
    User customerBUser;
    Requirement clientRequirement;
    Requirement customerARequirement;
    Requirement customerBRequirement;

    @BeforeEach
    void setup () {
        // Setup the client user
        clientUser = new User();
        clientUser.setName("Client User");
        clientUser.setOrganization("Client");
        userRepository.save(clientUser);
        // Setup the Customer A user
        customerAUser = new User();
        customerAUser.setName("Customer A User");
        customerAUser.setOrganization("Customer A");
        userRepository.save(customerAUser);
        // Setup the Customer B user
        customerBUser = new User();
        customerBUser.setName("Client User");
        customerBUser.setOrganization("Customer B");
        userRepository.save(customerBUser);
        // Setup the Client Requirement
        clientRequirement = new Requirement();
        clientRequirement.setName("Client Requirement");
        clientRequirement.setContent("Client Requirement Content");
        requirementRepository.save(clientRequirement);
        // Setup the Customer A Requirement
        customerARequirement = new Requirement();
        customerARequirement.setName("Customer A Requirement");
        customerARequirement.setContent("Customer A Requirement Content");
        requirementRepository.save(customerARequirement);
        // Setup the Customer B Requirement
        customerBRequirement = new Requirement();
        customerBRequirement.setName("Customer B Requirement");
        customerBRequirement.setContent("Customer B Requirement Content");
        requirementRepository.save(customerBRequirement);
    }

    @Test
    void testUserAccess() {
        // Client can see all requirements
        Assertions.assertTrue(clientUser.getRequirementContent(clientRequirement) != null);
        Assertions.assertTrue(clientUser.getRequirementContent(customerARequirement) != null);
        Assertions.assertTrue(clientUser.getRequirementContent(customerBRequirement) != null);
        // Has purchased client requirement and can view their own requirement
        Assertions.assertTrue(customerAUser.getRequirementContent(clientRequirement) != null);
        Assertions.assertTrue(customerAUser.getRequirementContent(customerARequirement) != null);
        // Should not see other customer data
        Assertions.assertTrue(customerAUser.getRequirementContent(customerBRequirement) == null);
        // Has not purchased requirement so can only see their own requirement
        Assertions.assertTrue(customerBUser.getRequirementContent(clientRequirement) == null);
        // Should not see other customer data
        Assertions.assertTrue(customerBUser.getRequirementContent(customerARequirement) == null);
        Assertions.assertTrue(customerBUser.getRequirementContent(customerBRequirement) != null);
    }
}
