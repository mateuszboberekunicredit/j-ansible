pipeline {
    agent any
    
    options {
        timestamps()
    }
    
    environment {
        githubCredentialsID = "github-access-mgworkshop"
        ansibleDeployerCredentialsID = "ansible-deployer"
    }
    
    parameters {
        string(name: 'branch', defaultValue: 'main', description: 'branch to checkout')
        string(name: 'gitUrl', defaultValue: 'https://github.com/devopsit-mg/ansible.git', description: 'Git url to checkout')
    }

    stages {
        stage("Checkout") {
            steps {
              git url: gitUrl, branch: branch, changelog: true, credentialsId: githubCredentialsID, poll: true
            }
        }
        stage("Run ansible") {
            steps {
                ansiblePlaybook become: true, credentialsId: ansibleDeployerCredentialsID, inventory: 'hosts', playbook: 'playbook_workshop.yaml'
            }
        }

        }
}
