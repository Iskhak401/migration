# Application deployment with Ansible

<!-- TOC -->

- [Application deployment with Ansible](#application-deployment-with-ansible)
  - [Install Ansible](#install-ansible)
    - [Dependencies](#dependencies)
  - [Modifying environment variables](#modifying-environment-variables)
    - [Encrypt/decrypt credentials with Ansible Vault](#encryptdecrypt-credentials-with-ansible-vault)
  - [Running the playbook](#running-the-playbook)
    - [Specific tasks](#specific-tasks)

<!-- /TOC -->

## Install Ansible

Install Ansible ≥ `2.10`, we recommend using
[pip](https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html)
to install it.

### Dependencies

- [kubectl](https://kubernetes.io/docs/tasks/tools/).

- Additional Python libraries are required, you can install them using `pip`:
  - [docker](https://pypi.org/project/docker/)
  - [openshift](https://pypi.org/project/openshift/)
  - [jinja2](https://pypi.org/project/Jinja2/) ≥ `3.0`

- Install Ansible Galaxy collections from the `requirements.yml` file:

  ```shell
  ansible-galaxy install -r requirements.yml
  ```

## Modifying environment variables

To add or modify environment variables, edit `group_vars/all/environment-variables.yml`
and then add specific values into `group_vars/<environment>/general.yml`.

Take note of how other variables are set and defined. The idea is to centralize
environment variables into one file, and reference all needed values from their
respective environment `group_var` or `all` folders.

If the values you will add are sensitive, then you will have to encrypt
(or edit an already encrypted file) using Ansible Vault.

### Encrypt/decrypt credentials with Ansible Vault

Add `vault.key` in the `ansible` path, which is ignored by git through `.gitignore`.
Then, using `ansible-vault`, you can encrypt, decrypt, edit and view credentials
in the vault file.

For example:

```shell
ansible-vault [encrypt|view|edit] group_vars/stage/vault/<service>.yml
```

## Running the playbook

The playbook sets up the following tasks:

- Builds and pushes the container image
- Environment variables
- Other K8s deployment tasks

Playbooks are located in the `playbooks` subdirectory:

```shell
ansible-playbook playbooks/<environment>.yml
```

Replace `<environment>` with `dev`, `stage`, `production`, or any group defined in the `hosts` file.

### Specific tasks

You can also run a specific task if it is tagged.
For example, to only update environment variables:

```shell
ansible-playbook playbooks/<environment>.yml -t env-vars
```

> Note: If you run into trouble, append `-vvv` to the `ansible-playbook` command.
