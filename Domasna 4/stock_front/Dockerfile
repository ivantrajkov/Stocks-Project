FROM node:alpine

RUN mkdir /app

ADD . /app

WORKDIR /app

RUN npm install

EXPOSE 3000

CMD npm start --bind 0.0.0.0:$PORT
