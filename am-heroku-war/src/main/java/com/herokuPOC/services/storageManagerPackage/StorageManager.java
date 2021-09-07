package com.herokuPOC.services.storageManagerPackage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.herokuPOC.common.CONTAINER_TYPE;
import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.services.ContainerManager;
import com.herokuPOC.services.MailManager;
import org.primefaces.event.FileUploadEvent;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Stateless
public class StorageManager {

    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;

    @EJB
    private ContainerManager recordFacade;

    @EJB
    private MailManager mailManager;

    public StorageManager() {
    }

    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public boolean upload(FileUploadEvent fUEvent) throws IOException {
        String fileObjKeyName = null;
        String bucketName = null;
        Regions clientRegion = null;
        AmazonS3 s3Client = null;

        try {
            //
            String pattern = "yyyyMMdd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            System.out.println(date);

            String fileNameIn = fUEvent.getFile().getFileName();
            InputStream in = fUEvent.getFile().getInputstream();
            File tempFile = File.createTempFile("temp", "txt");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                copyStream(in, out);
            }

            // Upload a file as a new object with ContentType and title specified.
            bucketName = System.getenv("S3_BUCKET_NAME");
            System.out.println("bucketName: " + bucketName);
            clientRegion = Regions.EU_WEST_1;
            s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new EnvironmentVariableCredentialsProvider())
                    .build();
            System.out.println(fileNameIn);
            //fileObjKeyName = fileNameIn.replace("\\","_");
            //fileObjKeyName = fileNameIn.replace('/', '_');
            //fileObjKeyName = fileNameIn.replace(' ', '_');
            PutObjectRequest request = new PutObjectRequest(bucketName, date + "/" + fileNameIn, tempFile);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com", "Amadeus heroku Web App Storage Error", "Hi, we have an error on Storage: \n The call was transmitted successfully, but Amazon S3 couldn't process it, so it returned an error response. \n" + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3.
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com", "Amadeus heroku Web App Storage Error", "Hi, we have an error on Storage: \n Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3. \n" + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return true;
    }

    public boolean getRecordsFromFile(String date, String fileNameId, FileContainer fileContainer, CONTAINER_TYPE containerType) throws IOException {
        StorageManagerImp storeManager = getStoreManager(containerType);
        return storeManager.getRecordsFromFile(date, fileNameId, fileContainer);
    }


    private StorageManagerImp getStoreManager(CONTAINER_TYPE containerType) {
        if (CONTAINER_TYPE.CONTACT == containerType) {
            return new StorageManagerContact();
        }
        if (CONTAINER_TYPE.ACCOUNT == containerType) {
            return new StorageManagerAccount();
        }
        return null;
    }
}