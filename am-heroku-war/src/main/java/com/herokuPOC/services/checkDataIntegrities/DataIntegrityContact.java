package com.herokuPOC.services.checkDataIntegrities;

import com.herokuPOC.services.MailManager;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

public class DataIntegrityContact implements DataIntegrityImp {

    @PersistenceContext(unitName = "com.amadeus.websolutions_herokuPOC")
    private EntityManager em;

    @EJB
    private MailManager mailManager;

    @Override
    public boolean checkDataIntegrity(int fileContainerId) {
        boolean bReturn = true;
        try {
            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("public.checkdataintegrity");
            // set parameters
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
}
