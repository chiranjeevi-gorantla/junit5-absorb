package com.chirango.junit5absorb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

  ContactManager contactManager;

  @BeforeAll
  public void setupAll() {
    System.out.println("Should Print Before All Tests");
  }

  @BeforeEach
  public void setUp() {
    contactManager = new ContactManager();
    System.out.println("Should Execute Before Each Test");
  }

  @Test
  public void shouldCreateContact() {
    contactManager.addContact("Sanjay", "Sahu", "0456773223");
    Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    Assertions.assertEquals(1, contactManager.getAllContacts().size());
    Assertions.assertTrue(
        contactManager.getAllContacts().stream()
            .anyMatch(
                contact ->
                    contact.getFirstName().equals("Sanjay")
                        && contact.getLastName().equals("Sahu")
                        && contact.getPhoneNumber().equals("0456773223")));
  }

  @Test
  @DisplayName("Should Not Create Contact when First Name is Null")
  public void shouldThrowRuntimeExceptionwhenFirstNameIsNull() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          contactManager.addContact(null, "Sahu", "0456773223");
        });
  }

  @Test
  @DisplayName("Should Not Create Contact when Last Name is Null")
  public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          contactManager.addContact("Sanjay", null, "0456773223");
        });
  }

  @Test
  @DisplayName("Should Not Create Contact when Phone Number is Null")
  public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          contactManager.addContact("Sanjay", "Sahu", null);
        });
  }

  @AfterEach
  public void tearDown() {
    System.out.println("Should Execute After Each Test");
  }

  @AfterAll
  public void tearDownAll() {
    System.out.println("Should be executed at the end of the Test");
  }

  @Test
  @DisplayName("Should Not Create Contact on MAC OS")
  @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on MAC OS")
  public void ShouldCreateContactOnlyOnMac() {
    contactManager.addContact("Sanjay", "Sahu", "0456773223");
    Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    Assertions.assertEquals(1, contactManager.getAllContacts().size());
    Assertions.assertTrue(
        contactManager.getAllContacts().stream()
            .anyMatch(
                contact ->
                    contact.getFirstName().equals("Sanjay")
                        && contact.getLastName().equals("Sahu")
                        && contact.getPhoneNumber().equals("0456773223")));
  }

  @Test
  @DisplayName("Should Not Create Contact on Windows OS")
  @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
  public void ShouldNotCreateContactOnWindows() {
    contactManager.addContact("Sanjay", "Sahu", "0456773223");
    Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
    Assertions.assertEquals(1, contactManager.getAllContacts().size());
    Assertions.assertTrue(
        contactManager.getAllContacts().stream()
            .anyMatch(
                contact ->
                    contact.getFirstName().equals("Sanjay")
                        && contact.getLastName().equals("Sahu")
                        && contact.getPhoneNumber().equals("0456773223")));
  }

  @Test
  @DisplayName("Test Contact Creation on Developer Machine")
  public void shouldTestContactCreationOnDEV() {
    Assumptions.assumeTrue("TEST".equals(System.getProperty("ENV")));
    contactManager.addContact("Sanjay", "Sahu", "0456773223");
    assertFalse(contactManager.getAllContacts().isEmpty());
    assertEquals(1, contactManager.getAllContacts().size());
  }

  @Nested
  class RepeatedNestedTests {
    @DisplayName("Repeat Contact Creation Test 5 Times")
    @RepeatedTest(
        value = 5,
        name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
    public void ShouldTestContactCreationRepeatedly() {
      contactManager.addContact("Sanjay", "Sahu", "0456773223");
      assertFalse(contactManager.getAllContacts().isEmpty());
      assertEquals(1, contactManager.getAllContacts().size());
    }
  }

  @Nested
  class ParameterizedNestedTests {
    @DisplayName("ValueSource Case - Repeat Contact Creation Test 5 Times - Parameterized Test")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "0456773223", "0456773224"})
    public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
      contactManager.addContact("Sanjay", "Sahu", phoneNumber);
      assertFalse(contactManager.getAllContacts().isEmpty());
      assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("MethodSource Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumbersList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
      contactManager.addContact("Sanjay", "Sahu", phoneNumber);
      assertFalse(contactManager.getAllContacts().isEmpty());
      assertEquals(1, contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumbersList() {
      return Arrays.asList("0123456789", "0456773223", "0456773224");
    }

    @DisplayName("CSVSource Case - Phone Number should match the required Format")
    @ParameterizedTest
    @CsvSource({"0123456789", "0456773223", "0456773224"})
    public void shouldTestPhoneNumberFormatUsingCsvSource(String phoneNumber) {
      contactManager.addContact("Sanjay", "Sahu", phoneNumber);
      assertFalse(contactManager.getAllContacts().isEmpty());
      assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("CSVFileSource Case - Phone Number should match the required Format")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldTestPhoneNumberFormatUsingCsvFileSource(String phoneNumber) {
      contactManager.addContact("Sanjay", "Sahu", phoneNumber);
      assertFalse(contactManager.getAllContacts().isEmpty());
      assertEquals(1, contactManager.getAllContacts().size());
    }
  }
}
