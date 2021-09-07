package com.herokuPOC.services.storageManagerPackage;

import com.herokuPOC.entity.FileContainer;

import java.io.IOException;

public interface StorageManagerImp {

    boolean getRecordsFromFile(String date, String fileNameId, FileContainer fileContainer) throws IOException;

}
