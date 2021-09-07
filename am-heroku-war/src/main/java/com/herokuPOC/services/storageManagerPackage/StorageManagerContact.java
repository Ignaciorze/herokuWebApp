package com.herokuPOC.services.storageManagerPackage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.herokuPOC.entity.ContactEntity;
import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.services.ContainerManager;
import com.herokuPOC.services.MailManager;

import javax.ejb.EJB;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class StorageManagerContact implements StorageManagerImp {


    @EJB
    private ContainerManager recordFacade;

    @EJB
    private MailManager mailManager;


    @Override
    public boolean getRecordsFromFile(String date, String fileNameId, FileContainer fileContainer) throws IOException {
        List<ContactEntity> listToDb = new ArrayList<ContactEntity>();

        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
        String fileObjKeyName = null;
        String bucketName = null;
        Regions clientRegion = null;
        AmazonS3 s3Client = null;

        bucketName = System.getenv("S3_BUCKET_NAME");
        System.out.println("bucketName: " + bucketName);
        clientRegion = Regions.EU_WEST_1;
        try {
            s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new EnvironmentVariableCredentialsProvider())
                    .build();

            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, date + "/" + fileNameId));

            listToDb = getRecordList(fullObject.getObjectContent(), fileContainer);

            if (listToDb.isEmpty())
                return false;

            for (ContactEntity contactEntity : listToDb) {
                if (contactEntity.getFileContainer() != null) {
                    recordFacade.createRecord(contactEntity);
                }
            }

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com", "Amadeus heroku Web App Storage Error", "Hi, we have an error on Storage: \n The call was transmitted successfully, but Amazon S3 couldn't process it, so it returned an error response. \n" + e.getLocalizedMessage());
            return false;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com", "Amadeus heroku Web App Storage Error", "Hi, we have an error on Storage: \n Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3. \n" + e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        } finally {
            // To ensure that the network connection doesn't remain open, close any open input streams.
            if (fullObject != null) {
                fullObject.close();
            }
        }
        return true;
    }

    // TO DO -> this function should return an Array of File Records
    private List<ContactEntity> getRecordList(InputStream input, FileContainer fileContainer) throws IOException {
        List<ContactEntity> listToDb = new ArrayList<>();
        ContactEntity contactEntityToDb = new ContactEntity();
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        // while there is a new line and this new line is not the header record
        int i = 1;
        while ((line = reader.readLine()) != null) {
            if (i == 1) {
                i++;
                continue;
            }
            System.out.println(line);

            String[] arrOfStr = line.split("\\|");
            StringTokenizer st1 =
                    new StringTokenizer(line, "|");
            try {

                contactEntityToDb.setFile_line(Integer.parseInt(arrOfStr[0]));
                contactEntityToDb.setErr_type(arrOfStr[1]);
                contactEntityToDb.setErr_msg(arrOfStr[2]);
                contactEntityToDb.setContact_flag(arrOfStr[3]);
                contactEntityToDb.setAccount_flag(arrOfStr[4]);
                contactEntityToDb.setOwner_flag(arrOfStr[5]);
                contactEntityToDb.setSfcontact_id(arrOfStr[6]);
                contactEntityToDb.setContact_org(arrOfStr[7]);
                contactEntityToDb.setSalutation(arrOfStr[8]);
                contactEntityToDb.setFirstname(arrOfStr[9]);
                contactEntityToDb.setMidname(arrOfStr[10]);
                contactEntityToDb.setLastname(arrOfStr[11]);
                contactEntityToDb.setMail(arrOfStr[17]);
                contactEntityToDb.setCellular(arrOfStr[13]);
                //doubts about these three parameters, I do not know in the csv to which it corresponds
                contactEntityToDb.setAccount_segmentation(arrOfStr[22]);
                contactEntityToDb.setSfaccount_id(arrOfStr[23]);
                contactEntityToDb.setSfowner_id(arrOfStr[24]);


                /*contactEntityToDb.setFile_line(Integer.parseInt(arrOfStr[0]));
                contactEntityToDb.setErr_type(arrOfStr[1]);
                contactEntityToDb.setErr_msg(arrOfStr[2]);
                contactEntityToDb.setContactFlag(arrOfStr[3]);
                contactEntityToDb.setAccountFlag(arrOfStr[4]);
                contactEntityToDb.setSalesTeamFlag(arrOfStr[5]);
                contactEntityToDb.setCategoryFlag(arrOfStr[6]);
                contactEntityToDb.setContactIdLocal(arrOfStr[7]);
                contactEntityToDb.setContactId(arrOfStr[8]);
                contactEntityToDb.setContactOrganization(arrOfStr[9]);
                contactEntityToDb.setSalutation(arrOfStr[10]);
                contactEntityToDb.setMr_Ms(arrOfStr[11]);
                contactEntityToDb.setFirstname(arrOfStr[12]);
                contactEntityToDb.setMidname(arrOfStr[13]);
                contactEntityToDb.setLastname(arrOfStr[14]);
                contactEntityToDb.setCellular(arrOfStr[15]);
                contactEntityToDb.setWorkPhone(arrOfStr[16]);
                contactEntityToDb.setWorkFax(arrOfStr[17]);
                contactEntityToDb.setWorkPhoneExtension(arrOfStr[18]);
                contactEntityToDb.setHomePhone(arrOfStr[19]);
                contactEntityToDb.setJobTitle(arrOfStr[20]);
                contactEntityToDb.setJobPosition(arrOfStr[21]);
                contactEntityToDb.setDecisionMaker(arrOfStr[22]);
                contactEntityToDb.setMailingCorrespondence(arrOfStr[23]);
                contactEntityToDb.setMail(arrOfStr[24]);
                contactEntityToDb.setBirthDate(arrOfStr[25]);
                contactEntityToDb.setPreferredLanguage(arrOfStr[26]);
                contactEntityToDb.setComments(arrOfStr[27]);
                contactEntityToDb.setLastUpdateBy(arrOfStr[28]);
                contactEntityToDb.setLastUpdateDate(arrOfStr[29]);
                contactEntityToDb.setDepartment(arrOfStr[30]);
                contactEntityToDb.setAccountName(arrOfStr[31]);
                contactEntityToDb.setLocation(arrOfStr[32]);
                contactEntityToDb.setLocalAccount(arrOfStr[33]);
                contactEntityToDb.setAccountOrganization(arrOfStr[34]);
                contactEntityToDb.setCentralAccount(arrOfStr[35]);
                contactEntityToDb.setSalesTeamPrimaryFlag(arrOfStr[36]);
                contactEntityToDb.setSalesTeamLogin(arrOfStr[37]);
                contactEntityToDb.setSalesTeamLastName(arrOfStr[38]);
                contactEntityToDb.setSalesTeamFirstName(arrOfStr[39]);
                contactEntityToDb.setSalesTeamOrganization(arrOfStr[40]);
                contactEntityToDb.setSalesTeamDivision(arrOfStr[41]);
                contactEntityToDb.setCategoryName(arrOfStr[42]);
                contactEntityToDb.setCategoryValue(arrOfStr[43]);
                contactEntityToDb.setUserFlag(arrOfStr[44]);
                contactEntityToDb.setOfficeId(arrOfStr[45]);
                contactEntityToDb.setSign(arrOfStr[46]);
                contactEntityToDb.setLogin(arrOfStr[47]);
                contactEntityToDb.setStartDate(arrOfStr[48]);
                contactEntityToDb.setEndDate(arrOfStr[49]);
                contactEntityToDb.setDelegatedAdminFlag(arrOfStr[50]);
                contactEntityToDb.setPortalLanguage(arrOfStr[51]);
                contactEntityToDb.setEmailOnSolve(arrOfStr[52]);
                contactEntityToDb.setEmailOnClose(arrOfStr[53]);
                contactEntityToDb.setEmailOnReject(arrOfStr[54]);
                contactEntityToDb.setNeverCall(arrOfStr[55]);
                contactEntityToDb.setNeverEmail(arrOfStr[56]);
                contactEntityToDb.setMarketingMailAllowed(arrOfStr[57]);
                contactEntityToDb.setPolicyAgreement(arrOfStr[58]);
                contactEntityToDb.seteServiceContactStatus(arrOfStr[59]);
                contactEntityToDb.setPreferredContactMethod(arrOfStr[60]);
                contactEntityToDb.setContactCreationSource(arrOfStr[61]);
                contactEntityToDb.setRegistrationSource(arrOfStr[62]);
                contactEntityToDb.setTimeZone(arrOfStr[63]);
                contactEntityToDb.setResponsibility(arrOfStr[64]);
                contactEntityToDb.setUserType(arrOfStr[65]);
                contactEntityToDb.setTrainingDelegation(arrOfStr[66]);
                contactEntityToDb.setUserList(arrOfStr[67]);
                contactEntityToDb.setStatus(arrOfStr[68]);
                contactEntityToDb.setTouchPoint(arrOfStr[69]);
                contactEntityToDb.setRelevantSales(arrOfStr[70]);
                contactEntityToDb.setFirstNameLocal(arrOfStr[71]);
                contactEntityToDb.setLastNameLocal(arrOfStr[72]);
                contactEntityToDb.setSalesForceContactID(arrOfStr[73]);
                contactEntityToDb.setlSSID(arrOfStr[74]);
                contactEntityToDb.setlSSStatus(arrOfStr[75]);
                contactEntityToDb.setAccountAction(arrOfStr[76]);*/

                contactEntityToDb.setFileContainer(fileContainer);


            } catch (NoSuchElementException nsee) {
                nsee.printStackTrace();
            }
            listToDb.add(contactEntityToDb);
            contactEntityToDb = new ContactEntity();
            i++;
        }

        return listToDb;
    }

}
