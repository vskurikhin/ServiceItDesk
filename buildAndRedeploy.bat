@ECHO OFF
CHCP 65001
mvn clean package dependency:copy-dependencies glassfish:redeploy
