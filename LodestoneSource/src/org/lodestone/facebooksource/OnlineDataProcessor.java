package org.lodestone.facebooksource;

import java.io.File;
import javax.swing.JPanel;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessor;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorCallback;
import org.sleuthkit.autopsy.corecomponentinterfaces.DataSourceProcessorProgressMonitor;
import org.openide.util.lookup.ServiceProvider;
import org.sleuthkit.datamodel.Host;

@ServiceProvider(service = DataSourceProcessor.class)
public class OnlineDataProcessor implements DataSourceProcessor {

    private final String modulename = "Data extraction from Facebook via Selenium";
    private final OnlineDataProcessorPanel processorPanel;
    private final String projectDir; // For IDE release

    public OnlineDataProcessor() {
        processorPanel = new OnlineDataProcessorPanel();
        // For IDE release
        this.projectDir = new File("").getAbsolutePath();
        System.setProperty("webdriver.chrome.driver",projectDir+"\\chromedriver.exe");
        // For NBM release, put chromedriver.exe in Autopsy Program Files [E.g. C:\Program Files\Autopsy-4.19.3\chromedriver.exe]
        //System.setProperty("webdriver.chrome.driver","chromedriver.exe");
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
    public void run(DataSourceProcessorProgressMonitor progressMonitor, DataSourceProcessorCallback callback) {}

    @Override // Live
    public void run(Host host, DataSourceProcessorProgressMonitor progressMonitor, DataSourceProcessorCallback callback) {
        System.out.println("Starting Processor");
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

