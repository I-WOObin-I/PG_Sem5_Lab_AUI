FROM ubuntu:latest

RUN apt-get update -y
RUN apt-get install mc -y
RUN apt-get install systemctl -y
RUN apt-get install unzip gnupg2 curl wget -y
RUN wget https://releases.hashicorp.com/consul/1.8.4/consul_1.8.4_linux_amd64.zip
RUN unzip consul_1.8.4_linux_amd64.zip
RUN mv consul /usr/local/bin/
RUN groupadd --system consul
RUN useradd -s /sbin/nologin --system -g consul consul
RUN mkdir -p /var/lib/consul
RUN mkdir /etc/consul.d
RUN chown -R consul:consul /var/lib/consul
RUN chmod -R 775 /var/lib/consul
RUN chown -R consul:consul /etc/consul.d

COPY consul.service /etc/systemd/system

RUN systemctl daemon-reload

COPY config.json /etc/consul

#RUN systemctl start consul
#RUN systemctl enable consul







