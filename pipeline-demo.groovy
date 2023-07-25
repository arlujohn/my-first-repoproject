node{
    
    stage('Git Checkout'){
        git branch: 'main', url: 'https://github.com/arlujohn/my-first-repoproject.git'
    }
    
    stage('Sending Dockerfile to Ansible Server via SSH'){
        sshagent(['ansible_demo']) {
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.31.83.21'
            sh 'scp /var/lib/jenkins/workspace/pipeline-demo/* ubuntu@172.31.83.21:/home/ubuntu'
        }
    }
    stage('Docker Image Building'){
        sshagent(['ansible_demo']) {
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.31.83.21 cd /home/ubuntu/'
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.31.83.21 docker image build -t $JOB_NAME:v1.$BUILD_ID .'
        }
    }
    stage('Docker Image Tagging'){
        sshagent(['ansible_demo']) {
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.31.83.21 cd /home/ubuntu/'
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.31.83.21 docker image tag $JOB_NAME:v1.$BUILD_ID arlujohn1/$JOB_NAME:v1.$BUILD_ID '
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@172.31.83.21 docker image tag $JOB_NAME:v1.$BUILD_ID arlujohn1/$JOB_NAME:latest '
        }
    }
}