#estagio intermediario para compilar e gerar o war
FROM nexus.tre-ce.jus.br/maven:latest as build
WORKDIR /usr/src/app
ARG DEPLOY=package

#copiar arquivos para gerar .war
COPY ./fontes/blank .
RUN mvn clean ${DEPLOY}

# imagem de wildfly que rodará o war gerado
FROM nexus.tre-ce.jus.br/wildfly:26.1.2.Final-jdk17

# Definindo o Ambiente homologação ou produção via --build-arg 
ARG AMBIENTE_TRE
ARG BRANCH

ENV AMBIENTE_TRE=${AMBIENTE_TRE}

#copiando arquivos de configuração
COPY ./docker/oracle/ /opt/jboss/wildfly/modules/system/layers/base/com/oracle/

COPY ./docker/standalone${BRANCH}.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
#copiando war do container anterior
COPY --from=build /usr/src/app/target/blank.war /opt/jboss/wildfly/standalone/deployments/
