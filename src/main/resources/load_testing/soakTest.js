import { describe } from 'https://jslib.k6.io/functional/0.0.3/index.js';
import { Httpx, Request, Get, Post } from 'https://jslib.k6.io/httpx/0.0.2/index.js';
import { randomIntBetween, randomItem } from "https://jslib.k6.io/k6-utils/1.1.0/index.js";

import { sleep } from 'k6';

const USERNAME = 'admin';
const PASSWORD = 'admin';
let session = new Httpx({baseURL: 'http://localhost:8080/'});

export let options = {
    stages: [
        { duration: '2m', target: 400 },    // ramp-up to 400 users
        { duration: '3h56m', target: 400 }, // stay at 400 users for 4 hours
        { duration: '2m', target: 0 }       // scale down (optional)
    ],
};

// generates token and passes it as an argument to the main function
export function setup() {
    let loginBody = JSON.stringify({
        username: USERNAME,
        password: PASSWORD
    })
    let response = session.post(`login`, loginBody);
    return response.headers.Authorization;
}

export default function (authToken) {
    session.addHeader('Authorization', authToken);
    session.addHeader('Content-Type', 'application/json');

    describe('01. Getting all locations', (t) => {
        let response = session.get(`location`);
        t.expect(response.status).as("OK").toBeBetween(200, 204)
            .and(response).toHaveValidJson();
    })

    describe('02. Getting all recognition results', (t) => {
        let response = session.get(`recognitionResult`);
        t.expect(response.status).as("OK").toBeBetween(200, 204)
            .and(response).toHaveValidJson();
    })

    describe('03. Getting all ml models', (t) => {
        let response = session.get(`mlModel`);
        t.expect(response.status).as("OK").toBeBetween(200, 204)
            .and(response).toHaveValidJson();
    })

    describe('04. Getting all users', (t) => {
        let response = session.get(`user`);
        t.expect(response.status).as("OK").toBeBetween(200, 204)
            .and(response).toHaveValidJson();
    })

    describe('05. Getting all medias', (t) => {
        let response = session.get(`media`);
        t.expect(response.status).as("OK").toBeBetween(200, 204)
            .and(response).toHaveValidJson();
    })

    describe('06. Getting all observed objects', (t) => {
        let response = session.get(`observedObject`);
        t.expect(response.status).as("OK").toBeBetween(200, 204)
            .and(response).toHaveValidJson();
    })

    describe('07. Getting all people', (t) => {
        let response = session.get(`person`);
        t.expect(response.status).as("OK").toBeBetween(200, 204)
            .and(response).toHaveValidJson();
    })

    sleep(1);
}