/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.entity;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @author jigonzalez
 */
@Entity
@Table(name = "record")
public class ContactEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer recordId;
    private Integer file_line;

    private String err_type;
    private String err_msg;
    private String contact_flag;
    private String account_flag;
    private String owner_flag;
    private String sfcontact_id;
    private String contact_org;
    private String salutation;
    private String firstname;
    private String midname;
    private String lastname;
    private String mail;
    private String cellular;
    private String account_segmentation;
    private String validated;
    private String flagto_sf;
    private String sfaccount_id;
    private String sfowner_id;

    private String contactFlag;
    private String accountFlag;
    private String salesTeamFlag;
    private String categoryFlag;
    private String contactIdLocal;
    private String contactId;
    private String contactOrganization;
    private String mr_Ms;
    private String workPhone;
    private String workFax;
    private String workPhoneExtension;
    private String homePhone;
    private String jobTitle;
    private String jobPosition;
    private String decisionMaker;
    private String mailingCorrespondence;
    private String birthDate;
    private String preferredLanguage;
    private String comments;
    private String lastUpdateBy;
    private String lastUpdateDate;
    private String department;
    private String accountName;
    private String location;
    private String localAccount;
    private String accountOrganization;
    private String centralAccount;
    private String salesTeamPrimaryFlag;
    private String salesTeamLogin;
    private String salesTeamLastName;
    private String salesTeamFirstName;
    private String salesTeamOrganization;
    private String salesTeamDivision;
    private String categoryName;
    private String categoryValue;
    private String userFlag;
    private String officeId;
    private String sign;
    private String login;
    private String startDate;
    private String endDate;
    private String delegatedAdminFlag;
    private String portalLanguage;
    private String emailOnSolve;
    private String emailOnClose;
    private String emailOnReject;
    private String neverCall;
    private String neverEmail;
    private String marketingMailAllowed;
    private String policyAgreement;
    private String eServiceContactStatus;
    private String preferredContactMethod;
    private String contactCreationSource;
    private String registrationSource;
    private String timeZone;
    private String responsibility;
    private String userType;
    private String trainingDelegation;
    private String userList;
    private String status;
    private String touchPoint;
    private String relevantSales;
    private String firstNameLocal;
    private String lastNameLocal;
    private String salesForceContactID;
    private String lSSID;
    private String lSSStatus;
    private String accountAction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fileContainerId")
    private FileContainer fileContainer;


    public ContactEntity() {
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getFile_line() {
        return file_line;
    }

    public void setFile_line(Integer file_line) {
        this.file_line = file_line;
    }

    public String getErr_type() {
        return err_type;
    }

    public void setErr_type(String err_type) {
        this.err_type = err_type;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getContact_flag() {
        return contact_flag;
    }

    public void setContact_flag(String contact_flag) {
        this.contact_flag = contact_flag;
    }

    public String getAccount_flag() {
        return account_flag;
    }

    public void setAccount_flag(String account_flag) {
        this.account_flag = account_flag;
    }

    public String getOwner_flag() {
        return owner_flag;
    }

    public void setOwner_flag(String owner_flag) {
        this.owner_flag = owner_flag;
    }

    public String getSfcontact_id() {
        return sfcontact_id;
    }

    public void setSfcontact_id(String sfcontact_id) {
        this.sfcontact_id = sfcontact_id;
    }

    public String getContact_org() {
        return contact_org;
    }

    public void setContact_org(String contact_org) {
        this.contact_org = contact_org;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    public String getAccount_segmentation() {
        return account_segmentation;
    }

    public void setAccount_segmentation(String account_segmentation) {
        this.account_segmentation = account_segmentation;
    }

    public String getValidated() {
        return validated;
    }

    public void setValidated(String validated) {
        this.validated = validated;
    }

    public String getFlagto_sf() {
        return flagto_sf;
    }

    public void setFlagto_sf(String flagto_sf) {
        this.flagto_sf = flagto_sf;
    }

    public String getSfaccount_id() {
        return sfaccount_id;
    }

    public void setSfaccount_id(String sfaccount_id) {
        this.sfaccount_id = sfaccount_id;
    }

    public String getSfowner_id() {
        return sfowner_id;
    }

    public void setSfowner_id(String sfowner_id) {
        this.sfowner_id = sfowner_id;
    }

    public String getContactFlag() {
        return contactFlag;
    }

    public void setContactFlag(String contactFlag) {
        this.contactFlag = contactFlag;
    }

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public String getSalesTeamFlag() {
        return salesTeamFlag;
    }

    public void setSalesTeamFlag(String salesTeamFlag) {
        this.salesTeamFlag = salesTeamFlag;
    }

    public String getCategoryFlag() {
        return categoryFlag;
    }

    public void setCategoryFlag(String categoryFlag) {
        this.categoryFlag = categoryFlag;
    }

    public String getContactIdLocal() {
        return contactIdLocal;
    }

    public void setContactIdLocal(String contactIdLocal) {
        this.contactIdLocal = contactIdLocal;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactOrganization() {
        return contactOrganization;
    }

    public void setContactOrganization(String contactOrganization) {
        this.contactOrganization = contactOrganization;
    }

    public String getMr_Ms() {
        return mr_Ms;
    }

    public void setMr_Ms(String mr_Ms) {
        this.mr_Ms = mr_Ms;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkFax() {
        return workFax;
    }

    public void setWorkFax(String workFax) {
        this.workFax = workFax;
    }

    public String getWorkPhoneExtension() {
        return workPhoneExtension;
    }

    public void setWorkPhoneExtension(String workPhoneExtension) {
        this.workPhoneExtension = workPhoneExtension;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getDecisionMaker() {
        return decisionMaker;
    }

    public void setDecisionMaker(String decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    public String getMailingCorrespondence() {
        return mailingCorrespondence;
    }

    public void setMailingCorrespondence(String mailingCorrespondence) {
        this.mailingCorrespondence = mailingCorrespondence;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocalAccount() {
        return localAccount;
    }

    public void setLocalAccount(String localAccount) {
        this.localAccount = localAccount;
    }

    public String getAccountOrganization() {
        return accountOrganization;
    }

    public void setAccountOrganization(String accountOrganization) {
        this.accountOrganization = accountOrganization;
    }

    public String getCentralAccount() {
        return centralAccount;
    }

    public void setCentralAccount(String centralAccount) {
        this.centralAccount = centralAccount;
    }

    public String getSalesTeamPrimaryFlag() {
        return salesTeamPrimaryFlag;
    }

    public void setSalesTeamPrimaryFlag(String salesTeamPrimaryFlag) {
        this.salesTeamPrimaryFlag = salesTeamPrimaryFlag;
    }

    public String getSalesTeamLogin() {
        return salesTeamLogin;
    }

    public void setSalesTeamLogin(String salesTeamLogin) {
        this.salesTeamLogin = salesTeamLogin;
    }

    public String getSalesTeamLastName() {
        return salesTeamLastName;
    }

    public void setSalesTeamLastName(String salesTeamLastName) {
        this.salesTeamLastName = salesTeamLastName;
    }

    public String getSalesTeamFirstName() {
        return salesTeamFirstName;
    }

    public void setSalesTeamFirstName(String salesTeamFirstName) {
        this.salesTeamFirstName = salesTeamFirstName;
    }

    public String getSalesTeamOrganization() {
        return salesTeamOrganization;
    }

    public void setSalesTeamOrganization(String salesTeamOrganization) {
        this.salesTeamOrganization = salesTeamOrganization;
    }

    public String getSalesTeamDivision() {
        return salesTeamDivision;
    }

    public void setSalesTeamDivision(String salesTeamDivision) {
        this.salesTeamDivision = salesTeamDivision;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDelegatedAdminFlag() {
        return delegatedAdminFlag;
    }

    public void setDelegatedAdminFlag(String delegatedAdminFlag) {
        this.delegatedAdminFlag = delegatedAdminFlag;
    }

    public String getPortalLanguage() {
        return portalLanguage;
    }

    public void setPortalLanguage(String portalLanguage) {
        this.portalLanguage = portalLanguage;
    }

    public String getEmailOnSolve() {
        return emailOnSolve;
    }

    public void setEmailOnSolve(String emailOnSolve) {
        this.emailOnSolve = emailOnSolve;
    }

    public String getEmailOnClose() {
        return emailOnClose;
    }

    public void setEmailOnClose(String emailOnClose) {
        this.emailOnClose = emailOnClose;
    }

    public String getEmailOnReject() {
        return emailOnReject;
    }

    public void setEmailOnReject(String emailOnReject) {
        this.emailOnReject = emailOnReject;
    }

    public String getNeverCall() {
        return neverCall;
    }

    public void setNeverCall(String neverCall) {
        this.neverCall = neverCall;
    }

    public String getNeverEmail() {
        return neverEmail;
    }

    public void setNeverEmail(String neverEmail) {
        this.neverEmail = neverEmail;
    }

    public String getMarketingMailAllowed() {
        return marketingMailAllowed;
    }

    public void setMarketingMailAllowed(String marketingMailAllowed) {
        this.marketingMailAllowed = marketingMailAllowed;
    }

    public String getPolicyAgreement() {
        return policyAgreement;
    }

    public void setPolicyAgreement(String policyAgreement) {
        this.policyAgreement = policyAgreement;
    }

    public String geteServiceContactStatus() {
        return eServiceContactStatus;
    }

    public void seteServiceContactStatus(String eServiceContactStatus) {
        this.eServiceContactStatus = eServiceContactStatus;
    }

    public String getPreferredContactMethod() {
        return preferredContactMethod;
    }

    public void setPreferredContactMethod(String preferredContactMethod) {
        this.preferredContactMethod = preferredContactMethod;
    }

    public String getContactCreationSource() {
        return contactCreationSource;
    }

    public void setContactCreationSource(String contactCreationSource) {
        this.contactCreationSource = contactCreationSource;
    }

    public String getRegistrationSource() {
        return registrationSource;
    }

    public void setRegistrationSource(String registrationSource) {
        this.registrationSource = registrationSource;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTrainingDelegation() {
        return trainingDelegation;
    }

    public void setTrainingDelegation(String trainingDelegation) {
        this.trainingDelegation = trainingDelegation;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTouchPoint() {
        return touchPoint;
    }

    public void setTouchPoint(String touchPoint) {
        this.touchPoint = touchPoint;
    }

    public String getRelevantSales() {
        return relevantSales;
    }

    public void setRelevantSales(String relevantSales) {
        this.relevantSales = relevantSales;
    }

    public String getFirstNameLocal() {
        return firstNameLocal;
    }

    public void setFirstNameLocal(String firstNameLocal) {
        this.firstNameLocal = firstNameLocal;
    }

    public String getLastNameLocal() {
        return lastNameLocal;
    }

    public void setLastNameLocal(String lastNameLocal) {
        this.lastNameLocal = lastNameLocal;
    }

    public String getSalesForceContactID() {
        return salesForceContactID;
    }

    public void setSalesForceContactID(String salesForceContactID) {
        this.salesForceContactID = salesForceContactID;
    }

    public String getlSSID() {
        return lSSID;
    }

    public void setlSSID(String lSSID) {
        this.lSSID = lSSID;
    }

    public String getlSSStatus() {
        return lSSStatus;
    }

    public void setlSSStatus(String lSSStatus) {
        this.lSSStatus = lSSStatus;
    }

    public String getAccountAction() {
        return accountAction;
    }

    public void setAccountAction(String accountAction) {
        this.accountAction = accountAction;
    }

    public FileContainer getFileContainer() {
        return fileContainer;
    }

    public void setFileContainer(FileContainer fileContainer) {
        this.fileContainer = fileContainer;
    }
}
