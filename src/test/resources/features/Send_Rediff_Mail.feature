@Mail
Feature: Managing Mails
  Creation and Deletion of Mails

  Background: Logged In
    Given user logged in rediff application
    And naviagated to mail box page as 'Hi ! murali chimmili'

  @CreateMail
  Scenario Outline: Send a mail from rediff application
    When user click on write mail in mail page
    And navigated to compose mail page
    And enter mail details
      | Email   | Subject   | Text   |
      | <Email> | <Subject> | <Text> |
    And click on send button
    When click on sent link
    And navigated to sent mail page
    Then verify the mail sent details

    Examples: 
      | Email                    | Subject   | Text            |
      | muralichimmili@gmail.com | Test Mail | Testing Purpose |
      
  @DeleteMail
  Scenario Outline: Delete a sent mail from rediff application
    When click on sent link
    And navigated to sent mail page
    And select a checkbox of sent mail
    And click on delete button
    And click on ok button


    Examples: 
      | Email                    | Subject   | Text            |
      | muralichimmili@gmail.com | Test Mail | Testing Purpose |
