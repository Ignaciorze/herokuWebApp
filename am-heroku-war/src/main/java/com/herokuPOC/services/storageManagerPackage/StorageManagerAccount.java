package com.herokuPOC.services.storageManagerPackage;

import com.herokuPOC.entity.ContactEntity;
import com.herokuPOC.entity.FileContainer;
import com.herokuPOC.services.ContainerManager;
import com.herokuPOC.services.MailManager;

import javax.ejb.EJB;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StorageManagerAccount implements StorageManagerImp {

    @EJB
    private ContainerManager recordFacade;

    @EJB
    private MailManager mailManager;

    @Override
    public boolean getRecordsFromFile(String date, String fileNameId, FileContainer fileContainer) throws IOException {
        return false;
    }

    private List<ContactEntity> getRecordList(String fileName, InputStream input, FileContainer fileContainer) throws IOException {
        return null;
    }

}
