#!/bin/bash

set -eu

# Edit these values to change the namespace or domain UID
NAMESPACE=demo-domani
DOMAIN_UID=demodomain

LONG_SECRETS=()

function check_secret_name {
  if [ ${#1} -gt 63 ]; then
    LONG_SECRETS+=($1)
  fi
}

function create_k8s_secret {
  SECRET_NAME=${DOMAIN_UID}-$1
  check_secret_name ${SECRET_NAME}
  kubectl -n $NAMESPACE delete secret ${SECRET_NAME} --ignore-not-found
  kubectl -n $NAMESPACE create secret generic ${SECRET_NAME} --from-literal=password=$2
  kubectl -n $NAMESPACE label secret ${SECRET_NAME} weblogic.domainUID=${DOMAIN_UID}
}

function create_paired_k8s_secret {
  SECRET_NAME=${DOMAIN_UID}-$1
  check_secret_name ${SECRET_NAME}
  kubectl -n $NAMESPACE delete secret ${SECRET_NAME} --ignore-not-found
  kubectl -n $NAMESPACE create secret generic ${SECRET_NAME} --from-literal=username=$2 --from-literal=password=$3
  kubectl -n $NAMESPACE label secret ${SECRET_NAME} weblogic.domainUID=${DOMAIN_UID}
}

# Update <user> and <password> for weblogic-credentials
create_paired_k8s_secret weblogic-credentials admin 12345678

# Update <user> and <password> for jdbc-greetingsdb
create_paired_k8s_secret jdbc-greetingsdb myuser 12345678

# Update <password> used to encrypt model and domain hashes
# This secret is only required for model-in-image deployments
# create_k8s_secret runtime-encryption-secret <password>

LONG_SECRETS_COUNT=${#LONG_SECRETS[@]}
if [ ${LONG_SECRETS_COUNT} -gt 0 ]; then
  echo ""
  echo "WARNING: These ${LONG_SECRETS_COUNT} secret names are too long to be mounted in a Kubernetes pod:"
  for NAME in "${LONG_SECRETS[@]}"; do
    echo "  ${NAME}"
  done
  echo ""
  echo "Secret names to be mounted in a Kubernetes pod should be limited to 63 characters."
  echo "To correct this, shorten the DOMAIN_UID or the secret key(s) in this generated script and re-execute."
  echo "Update the corresponding secret references in the WDT model(s) to match these values before deployment."
fi
