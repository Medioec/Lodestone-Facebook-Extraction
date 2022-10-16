package org.gbies.iosdevicedataextractor;
/** Localizable strings for {@link org.gbies.iosdevicedataextractor}. */
class Bundle {
    /**
     * @return <i>A module caused an error listening to DeviceDataProcessorPanel updates.</i>
     * @see IOSDataProcessorPanel
     */
    static String DeviceDataProcessorPanel_moduleErrorMessage_body() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "DeviceDataProcessorPanel.moduleErrorMessage.body");
    }
    /**
     * @return <i>Module Error</i>
     * @see IOSDataProcessorPanel
     */
    static String DeviceDataProcessorPanel_moduleErrorMessage_title() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "DeviceDataProcessorPanel.moduleErrorMessage.title");
    }
    /**
     * @return <i>iOS device connection problem, </i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_connect_problem() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.connect.problem");
    }
    /**
     * @return <i>Creating encrypted backup...</i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_creating_encrypted_backup() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.creating.encrypted.backup");
    }
    /**
     * @return <i>Creating unencrypted backup...</i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_creating_unencrypted_backup() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.creating.unencrypted.backup");
    }
    /**
     * @return <i>Error add files to new DataSources</i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_error_add_files_dataSources() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.error.add.files.dataSources");
    }
    /**
     * @return <i>Incorrect password, backup decryption problem or backup directory not found!</i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_error_extract_backup() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.error.extract.backup");
    }
    /**
     * @return <i>Extracting backup...</i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_extracting_backup() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.extracting.backup");
    }
    /**
     * @return <i>Backup problem (Manifest.db file not found), </i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_manifest_file_not_created() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.manifest.file.not.created");
    }
    /**
     * @return <i>Reading information from backup...</i>
     * @see addDeviceDataTask
     */
    static String addDeviceDataTask_reading_backup_information() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "addDeviceDataTask.reading.backup.information");
    }
    private Bundle() {}
}
