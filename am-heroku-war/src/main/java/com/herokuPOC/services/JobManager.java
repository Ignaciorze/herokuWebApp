/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herokuPOC.services;

import com.herokuPOC.common.CONTAINER_TYPE;
import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.services.storageManagerPackage.StorageManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class JobManager {
    private List<FileContainer> listFromDb;
    private boolean success;


    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;

    @EJB
    private StorageManager storageManager;

    @EJB
    private ContainerManager fileUploadFacade;
    @EJB
    private MailManager mailManager;


    public void runDataValidation() {

        listFromDb = new ArrayList<>();

        listFromDb = fileUploadFacade.findAllUploadedToDb();
        if (listFromDb.size() >= 0) {
            for (FileContainer fileContainer : listFromDb) {
                //listFromDb.forEach((FileContainer fileContainer) -> {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                String date = format.format(fileContainer.getUpload_date());
                String fileName = fileContainer.getName();
                try {
                    boolean integrityDone = false;
                    CONTAINER_TYPE type = getTypes(fileContainer.getHeader());//get the container type
                    success = storageManager.getRecordsFromFile(date, fileName, fileContainer, type);

                    if (success) {
                        if (type == CONTAINER_TYPE.CONTACT) {
                            /**JUST CHECK INTEGRITY FOR THE CONTACT CONTAINER**/
                            integrityDone = this.checkDataIntegrity("public.checkdataintegrity", fileContainer.getId());//INTEGRATE_CONTACT_DATA
                        }

                        /*if (type == CONTAINER_TYPE.ACCOUNT) {
                            integrityDone = this.checkDataIntegrity("public.INTEGRATE_ACCOUNT_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.ACTIVITY) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_ACTIVITY_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.CONTRACT) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_CONTRACT_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.EXCHANGE_RATES) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_ER_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.GDS_IATA) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_GDS_IATA_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.INSTALLED_PRODUCTS) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_IP_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.OFFICE_ID) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_OI_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.OPPORTUNITY) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_OPPORTUNITY_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.SOLD_PRODUCT) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_SP_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.TECNICAL_INFO) {
                        integrityDone = this.checkDataIntegrity("public.INTEGRATE_TI_DATA", fileContainer.getId());
                        }
                        if (type == CONTAINER_TYPE.EMPTY) {
                            //IF THERE IS NOT HEADER
                        }
                        */

                        boolean update = fileUploadFacade.update(fileContainer);
                        if (integrityDone && update) {
                            String body = "The file " + fileName + " was validated and can be checked for errors in the records.\n";
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(StorageManager.class.getName()).log(Level.SEVERE, null, ex);
                    String body = "Error on process:" + "JOB3\n";
                    body = body + ex.getLocalizedMessage();
                    mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com", "Error on heroku POC WebApp", body);
                }
                if (success) {//IS THIS NECESARY??? I DONT THINK SO
                    boolean update = fileUploadFacade.update(fileContainer);
                }
            }
            ;
        }
        System.out.println("ENDED JOB 2");
    }

    public boolean checkDataIntegrity(String plIntegrityData, int fileContainerId) {
        boolean bReturn = true;
        try {
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery(plIntegrityData);
            storedProcedure.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter(2, Boolean.class, ParameterMode.OUT);
            storedProcedure.setParameter(1, fileContainerId);
            Boolean out = (Boolean) storedProcedure.getOutputParameterValue(2);

            System.out.println("out : " + out.toString());

        } catch (IllegalArgumentException | IllegalStateException | java.lang.ClassCastException iae) {
            // send email to central team
            String body = "Error on process: checkdataintegrity " + "JOB3" + "\n";
            body = body + iae.getLocalizedMessage();
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com", "Error on heroku POC WebApp", body);
            bReturn = false;
        }
        return bReturn;
    }

    public void updateSalesforce() {
        try {

            /***
             * JUST FOR TABLE RECORD -> CONCTAC CONTAINER, WHIT INTEGRATE_DATA_SF INTEGRATED ALL CONTAINERS
             * StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("public.INTEGRATE_DATA_SF");
             */

            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("public.integratedata_sf");
            storedProcedure.registerStoredProcedureParameter(1, Boolean.class, ParameterMode.OUT);
            Boolean out = (Boolean) storedProcedure.getOutputParameterValue(1);
            System.out.println("integratedata_sf output : " + out.toString());
            String body = "The Container files have now been processed! You can go and check the Status of the records on the Web App!";
            mailManager.sendMail2User("herokuwebapp@amadeus.com", "WebApp - Containers validated", body);

        } catch (IllegalArgumentException | IllegalStateException iae) {
            // send email to central team
            String body = "Error on process: integratedata_sf " + "JOB3" + "\n";
            body = body + iae.getLocalizedMessage();
            mailManager.sendMail2CentralTeam("herokuwebapp@amadeus.com", "Error on heroku POC WebApp", body);

        }
        System.out.println("ENDED JOB 2");
    }

    private CONTAINER_TYPE getTypes(String header) {
        if (header.startsWith("AC")) {
            return CONTAINER_TYPE.ACCOUNT;
        }
        if (header.startsWith("AV")) {
            return CONTAINER_TYPE.ACTIVITY;
        }
        if (header.startsWith("CO")) {
            return CONTAINER_TYPE.CONTACT;
        }
        if (header.startsWith("CT")) {
            return CONTAINER_TYPE.CONTRACT;
        }
        if (header.startsWith("EX")) {
            return CONTAINER_TYPE.EXCHANGE_RATES;
        }
        if (header.startsWith("GO")) {
            return CONTAINER_TYPE.GDS_IATA;
        }
        if (header.startsWith("IP")) {
            return CONTAINER_TYPE.INSTALLED_PRODUCTS;
        }
        if (header.startsWith("OI")) {
            return CONTAINER_TYPE.OFFICE_ID;
        }
        if (header.startsWith("OP")) {
            return CONTAINER_TYPE.OPPORTUNITY;
        }
        if (header.startsWith("SP")) {
            return CONTAINER_TYPE.SOLD_PRODUCT;
        }
        if (header.startsWith("TI")) {
            return CONTAINER_TYPE.TECNICAL_INFO;
        }
        return CONTAINER_TYPE.EMPTY;
    }

}