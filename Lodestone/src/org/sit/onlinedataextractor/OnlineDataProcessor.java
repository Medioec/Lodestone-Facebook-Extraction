
package org.sit.onlinedataextractor;


import javax.swing.JPanel;

import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessor;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorCallback;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorProgressMonitor;
import org.openide.util.lookup.ServiceProvider;
import org.sleuthkit.datamodel.Host;

@ServiceProvider(service = DataSourceProcessor.class)
public class OnlineDataProcessor implements DataSourceProcessor {

    private final String modulename = "Extraction data from online cloud sources";
    private final OnlineDataProcessorPanel processorPanel;

    public OnlineDataProcessor() {
        processorPanel = new OnlineDataProcessorPanel();     
    }

    @Override
    public String getDataSourceType() {
        return modulename;
    }

    @Override
    public JPanel getPanel() {
        return processorPanel;
    }

    @Override
    public boolean isPanelValid() {
        return processorPanel.validatePanel();
    }

    @Override
    public void run(DataSourceProcessorProgressMonitor progressMonitor, DataSourceProcessorCallback callback) {
        //WebDriver driver = new ChromeDriver();
        //driver.get("http://www.yahoo.com/");
        //new Thread(new addDeviceDataTask(processorPanel.getPanelSettings(), progressMonitor, callback)).start();
    }

    @Override // Live
    public void run(Host host, DataSourceProcessorProgressMonitor progressMonitor, DataSourceProcessorCallback callback) {
       
        new Thread(new addOnlineDataTask(host, processorPanel.getPanelSettings(), progressMonitor, callback)).start();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void reset() {
        processorPanel.resetPanel();
    }

}
