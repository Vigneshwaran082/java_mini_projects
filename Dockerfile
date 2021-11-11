FROM openjdk:11

MAINTAINER  Author vigneshwarancareer@gmail.com

COPY ./bin/ /root/mini_project/
WORKDIR /root/mini_project/
CMD ["java","home.learning.designprinciples.application.improved.BookInventoryApplication"]



