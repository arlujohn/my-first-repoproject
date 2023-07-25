FROM centos:7
RUN yum update -y
RUN yum install -y httpd
RUN yum install zip
RUN yum install unzip
RUN yum update -y
ADD https://www.free-css.com/assets/files/free-css-templates/download/page254/photogenic.zip /var/www/html/
WORKDIR /var/www/html/
RUN unzip photogenic.zip
RUN cp -rvf photogenic/* .
RUN rm -rf photogenic photogenic.zip
CMD [ "/usr/sbin/httpd","-D","FOREGROUND" ]
EXPOSE 80 22