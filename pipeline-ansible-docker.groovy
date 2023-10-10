pipeline {
    agent any
    
    options {
        timestamps()
    }
    
    environment {
        githubCredentialsID = "git-mb"
        ansibleDeployerCredentialsID = "ansible-deployer"
    }
    
    parameters {
        string(name: 'branch', defaultValue: 'main', description: 'branch to checkout')
        string(name: 'gitUrl', defaultValue: 'https://github.com/mateuszboberekunicredit/j-ansible.git', description: 'Git url to checkout')
    }

    stages {
        stage("Checkout") {
            steps {
              git url: gitUrl, branch: branch, changelog: true, credentialsId: githubCredentialsID, poll: true
            }
        }
        stage("Run ansible") {
            steps {
                ansiblePlaybook become: true, credentialsId: ansibleDeployerCredentialsID, inventory: 'hosts', playbook: 'playbook.yaml'
            }
        }

        }
}
