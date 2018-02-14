 
 AMF_LIB_LOCATION=$1
 DIALECTS=$2
 INPUT_FILE=$3
 
 java -jar $AMF_LIB_LOCATION parse -in "RAML 1.0" -mime-in "application/yaml" --dialects $DIALECTS $INPUT_FILE
