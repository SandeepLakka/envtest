# envtest

Project to test injection of externalized application properties from custom sources like Database

:grimacing: Found out that **Spring Cloud Config** allows us to load properties from Database. This project implements this feature in a crude way ( without profiles support )

This Application tries to inject custom properties from custom sources like Database.

Check if Table BOPARAMS exists by checking `/h2-console`
if Table doesn't exist, fire `/create-schema` endpoint to create Table
If table doesn't have entries with tm.* properties, then fire `/load-data` endpoint to load configuration properties into DB table
If schema and data are loaded with endpoints, then restart app. do not clean the project

Check DB property (name_) and its values(value_) in BOPARAMS table.

checking endpoints `/wish1`, `/wish2` should show the values from DB.

** *Note*: DB values overrides the application properties

To validate this: check the response of `/wish3` and it will show you the property value from application property if DB property doesn't exist. If DB property exists prior to application startup, then value from DB is shown ( overriding application property)

Note that firing endpoint `/addDesert` will create property 'prepared.desert' in DB table. Need to restart application to let application take this property in consideration. 

Now as we have property 'prepared.desert' in both application.properties and in DB as well. value from DB is picked up and shown when we fire endpoint `/wish3`
You can also check the application log for more info on the properties sourced from DB with source name `boParams`
