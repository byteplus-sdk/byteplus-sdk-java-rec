gen_saas_retail:
	protoc --java_out=src/main/java -I=src/main/resources src/main/resources/byteplus_saas_retail.proto

gen_saas_content:
	protoc --java_out=src/main/java -I=src/main/resources src/main/resources/byteplus_saas_content.proto