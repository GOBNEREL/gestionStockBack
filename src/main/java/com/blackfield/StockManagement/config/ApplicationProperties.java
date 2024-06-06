package com.blackfield.StockManagement.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Upload upload = new Upload();
    private final Resources resources = new Resources();

    public Upload getUpload() {
        return upload;
    }

    public Resources getResources() {
        return resources;
    }

    public static class Resources {
        private String locationExcelFile;
        private String locationPdfFile;

        private Resources() {}

        public String getLocationExcelFile() {
            return locationExcelFile;
        }

        public void setLocationExcelFile(String locationExcelFile) {
            this.locationExcelFile = locationExcelFile;
        }

        public String getLocationPdfFile() {
            return locationPdfFile;
        }

        public void setLocationPdfFile(String locationPdfFile) {
            this.locationPdfFile = locationPdfFile;
        }
    }

    public static class Upload {
        private String resourcesServerStore;
        private String resourcesServerStoreUrl;

        public String getResourcesServerStore() {
            return resourcesServerStore;
        }

        public void setResourcesServerStore(String resourcesServerStore) {
            this.resourcesServerStore = resourcesServerStore;
        }

        public String getResourcesServerStoreUrl() {
            return resourcesServerStoreUrl;
        }

        public void setResourcesServerStoreUrl(String resourcesServerStoreUrl) {
            this.resourcesServerStoreUrl = resourcesServerStoreUrl;
        }
    }
}