# Lodestone
![Lodestone](https://user-images.githubusercontent.com/91124693/199466991-e15412e7-4da0-4b38-85d3-159c78024f90.png)
## Info
An Autopsy plugin created for ICT2202 module

Downloads json data from Facebook from a given user account with supplied user login credentials

Parses json data and converts to Autopsy artifacts

![Facebook artifacts](https://user-images.githubusercontent.com/91124693/200100500-637c16ae-0ef9-46b2-8f33-ed7d3cb4be75.jpg)

## Requirements
- Download the latest version of Autopsy

- Download the latest version of Chrome Driver and put it in the root directory of the Autopsy install

- Ensure that Oracle JDK 1.8 is installed

## Installing from .nbm binary
- Download the binary release from this repository

- Install both nbm modules in Autopsy (Tools > Plugins > Downloaded > Add Plugins > Install to add nbm modules as Autopsy plugins)

## Installing from source
- Download the latest version of Apache Netbeans

- Open LodestoneSource and LodestoneIngest projects in Netbeans

- Right click on LodestoneIngest > Properties > Libraries

- Set Java Platform to the installed Oracle JDK 1.8

- Under NetBeans Platform, click Manage Platforms > Add Platform and select the install directory of Autopsy

- Set the Java Platform and NetBeans Platform for LodestoneSource as well

- Right click on LodestoneSource and LodestoneIngest on list on the left and select Create NBM.

- Navigate to build folder in each NetBeans project to find the .nbm files

- Install both nbm modules in Autopsy (Tools > Plugins > Downloaded > Add Plugins > Install to add nbm modules as Autopsy plugins)

## Using the plugin in Autopsy
- Start Autopsy, create a new case or open an existing case

- Download files from Facebook by clicking on Add Data Source > Next > Data extraction from Facebook via Selenium

- Enter login credentials into the fields. If there is no pending Facebook data export and no data download available (or if not known), leave both Data Export and Data Download checked. This will request for a new data export and data will be downloaded when ready. If it is known that there is a download available, uncheck Data Download in order to download the currently available data export.

- Select Facebook Ingest Module when on the Configure Ingest screen (The ingest module only works if json download is selected). 

- Wait for the download to complete. The download might take some time if a new request for data export is made.

- When the download is complete, information from json files will be available for analysis in the form of Autopsy Blackboard Artifacts (under Data Artifacts on the left panel).
