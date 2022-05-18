import { describe } from 'https://jslib.k6.io/functional/0.0.3/index.js';
import { Httpx, Request, Get, Post } from 'https://jslib.k6.io/httpx/0.0.2/index.js';
import { randomIntBetween, randomItem } from "https://jslib.k6.io/k6-utils/1.1.0/index.js";

import { sleep } from 'k6';

const USERNAME = 'admin';
const PASSWORD = 'admin';
let session = new Httpx({baseURL: 'http://localhost:8080/'});

export let options = {
    vus: 2,         // virtual users
    duration: '200s' // duration of the test
}

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

    let newLocationId;
    describe('02. Creating new location', (t) => {
        let locationBody = JSON.stringify({
            longitude: "49.444452",
            latitude: "39.847446"
        });
        let response = session.post(`location`, locationBody);
        newLocationId = response.json("locationId");
        console.log('location id ' + newLocationId);

        t.expect(response.status).as("Created").toEqual(201)
            .and(response).toHaveValidJson();
    })

    describe('03. Getting location by id', (t) => {
        let response = session.get(`location/${newLocationId}`);
        t.expect(response.status).as("OK").toEqual(200)
            .and(response).toHaveValidJson();
    })

    describe('04. Updating location', (t) => {
        let newLocationBody = JSON.stringify({
            locationId: newLocationId,
            longitude: "99.99999",
            latitude: "11.11111"
        });
        let response = session.put(`location`, newLocationBody);
        t.expect(response.status).as("Created").toEqual(201)
            .and(response).toHaveValidJson();
    })

    describe('05. Deleting location', (t) => {
        let response = session.delete(`location/${newLocationId}`);
        t.expect(response.status).as("Ok").toEqual(200)
            .and(response).toHaveValidJson();
    })

    sleep(1);
};
