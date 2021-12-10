# ASE-Group-6-Backend

To run this application you first need to have all the data added to mongoDB. This can be added from the application but as the dataset is around 1.2M rows each in both Property and PricePaidData, it is recommended that you install the following files directly into the mongoDB database.
`https://drive.google.com/file/d/1Qqn00T_uvckePXtVRIaNzlDlgn7s6g2H/view?usp=sharing`

1. property.bson
2. property.metadata.bson
3. pricePaidData.bson
4. pricePaidData.metadata.bson

To add the files to the database the following commands need to be run using mongorestore (File found in same directory as above files).These commands should be run outside the mongo shell (eg. in command prompt on windows or in the linux terminal).

1. mongorestore -d Group6ASE -c pricePaidData pricePaidData.bson
2. mongorestore -d Group6ASE -c property property.bson

Next a user needs to be created with the following credentials:

` USERNAME: ADMIN,
  PASSWORD: ASEG6ANKEET1`


After the data is added, run the command `java -jar ase-group-6-LATEST.JAR` to initialise the server.
