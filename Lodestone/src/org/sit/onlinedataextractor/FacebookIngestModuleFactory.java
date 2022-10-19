/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.sit.onlinedataextractor;

import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.sleuthkit.autopsy.ingest.FileIngestModule;
import org.sleuthkit.autopsy.ingest.IngestModuleFactory;
import org.sleuthkit.autopsy.ingest.IngestModuleFactoryAdapter;
import org.sleuthkit.autopsy.ingest.IngestModuleGlobalSettingsPanel;
import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettings;

/**
 *
 * @author Eric
 */
@ServiceProvider(service = IngestModuleFactory.class)
public class FacebookIngestModuleFactory extends IngestModuleFactoryAdapter{
    
    private static final String VERSION_NUMBER = "1.0.0";
    
    static String getModuleName(){
        return "FacebookIngestModuleFactory";
    }

    @Override
    public boolean hasGlobalSettingsPanel() {
        return false;
    }
    
    @Override
    public String getModuleDisplayName() {
        String name = "FacebookIngestModule";
        return name;
    }

    @Override
    public String getModuleDescription() {
        String desc = "Produces artifacts from downlaoded Facebook information";
        return desc;
    }

    @Override
    public String getModuleVersionNumber() {
        return VERSION_NUMBER;
    }

    @Override
    public boolean isFileIngestModuleFactory() {
        return true;
    }

    @Override
    public FileIngestModule createFileIngestModule(IngestModuleIngestJobSettings settings) {
        return new FacebookFileIngestModule();
    }
}
