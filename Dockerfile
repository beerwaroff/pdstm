FROM eclipse-temurin:11
EXPOSE 8080
ADD target/pdstm.jar pdstm.jar
ENTRYPOINT ["java","-jar","/pdstm.jar"]