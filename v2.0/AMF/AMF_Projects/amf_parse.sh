#export CLASSPATH=$CLASSPATH:.:amf-client_2.12-1.0.0-SNAPSHOT.jar:

java -cp amf-client_2.12-1.0.0-SNAPSHOT.jar:amf-core_2.12-1.0.0-SNAPSHOT.jar:amf-vocabularies_2.12-1.0.0-SNAPSHOT.jar:amf-validation_2.12-1.0.0-SNAPSHOT.jar:amf_2.12-1.0.0-SNAPSHOT.jar  amf.Main  parse --dialects Dialects/EventDialect.raml -in RAML examples/EventedAPI_Banking.raml 


#java -cp . amf-client_2.12-1.0.0-SNAPSHOT.jar:amf-core_2.12-1.0.0-SNAPSHOT.jar:amf-vocabularies_2.12-1.0.0-SNAPSHOT.jar:amf-validation_2.12-1.0.0-SNAPSHOT.jar amf.Main  parse --dialects Dialects/EventDialect.raml -in RAML examples/EventedAPI_Banking.raml 



#java -jar amf-client_2.12-1.0.0-SNAPSHOT.jar -cp . amf-core_2.12-1.0.0-SNAPSHOT.jar amf-vocabularies_2.12-1.0.0-SNAPSHOT.jar amf-validation_2.12-1.0.0-SNAPSHOT.jar  parse --dialects Dialects/EventDialect.raml -in RAML examples/EventedAPI_Banking.raml 

#java -jar amf-client_2.12-1.0.0-SNAPSHOT.jar -cp amf-core_2.12-1.0.0-SNAPSHOT.jar,amf-vocabularies_2.12-1.0.0-SNAPSHOT.jar,amf-validation_2.12-1.0.0-SNAPSHOT.jar  parse --dialects Dialects/EventDialect.raml -in RAML examples/EventedAPI_Banking.raml > evented_api_banking.json 



#amf-validation_2.12-1.0.0-SNAPSHOT.jar
#amf-vocabularies_2.12-1.0.0-SNAPSHOT.jar
#amf-core_2.12-1.0.0-SNAPSHOT.jar
#amf_2.12-1.0.0-SNAPSHOT.jar