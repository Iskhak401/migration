<!-- omit in toc -->
# Caden — Transformers

<!-- omit in toc -->
## Contents

- [Overview](#overview)
- [Build guidelines](#build-guidelines)
- [Environment variables](#environment-variables)
- [Deployment guidelines :dart:](#deployment-guidelines-dart)
- [Versioning and releases](#versioning-and-releases)
  - [Tags examples](#tags-examples)

## Overview

> The Trasnformers Microservices are made using [Spring Boot].

Transformers are cronjobs that are responsible for transforming the data extracted from Hotlink, and storing them into a graph database_ This project is part of the Knowledge Graph.

## Build guidelines

In order to generate a build with the full app (with all its microservices as a monolithic app) run: `mvn clean package`

In order to generate a build with a specific microservice run:

```shell
mvn clean package -Ptransformers-<INTAKE>
```

Where `<INTAKE>` possible values can be:

- uber
- airbnb
- amazon
- spotify
- netflix
- ahk
- mx
- megraph
- location

Examples:

```shell
mvn clean package -Ptransformers-uber
mvn clean package -Ptransformers-airbnb
mvn clean package -Ptransformers-amazon
mvn clean package -Ptransformers-spotify
mvn clean package -Ptransformers-netflix
mvn clean package -Ptransformers-ahk
mvn clean package -Ptransformers-mx
mvn clean package -Ptransformers-megraph
mvn clean package -Ptransformers-location
```

## Environment variables

To set environment variables for `dev`, `stage`, `uat` or `production`,
follow the instructions at [ansible/README.md](ansible/README.md).

## Deployment guidelines :dart:

The deployment is _currently_ delegated to Ansible, which in turn has
all the configurations and deployment specifications to update our
Kubernetes-managed clusters.

See the [Ansible README.md file](ansible/README.md) for more details.

The environment/branch mapping for automated deployments is as follows:

- Merges on `main` -> Automatic deployment to **dev**.
- Relases matching `vYYYYMMDD-XX-rc` -> Automatic deployments to **stage**.
- Relases matching `vYYYYMMDD-XX-uat` -> Automatic deployments to **uat**.
- Relases matching `vYYYYMMDD-XX` -> Automatic deployments to **production**.

## Versioning and releases

To create a new release for `stage`, `uat`, or `production`, a Git tag needs
to be created pointing to the desired commit. You can use annotated
tags or normal tags. The tag name should follow the `vYYYYMMDD-XX`
format, being X an integer.

Tags for _stage_ and _uat_ follow the same format as _production_ tags, but
post-fixing `-rc` and `-uat` respectively. The time format needs to be on UTF.
To generate the tags, you can use the following command:

```sh
git tag "v$(date --utc +%Y%m%d)-01-rc"
```

### Tags examples

- stage

`v20230103-01-rc`

- uat

`v20230103-01-uat`

- production

`v20230103-01`
