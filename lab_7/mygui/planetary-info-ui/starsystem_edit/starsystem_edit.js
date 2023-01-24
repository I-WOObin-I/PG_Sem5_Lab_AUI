import {getBackendUrl} from '../js/configuration.js';
import {getParameterByName} from "../js/dom_utils.js";

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));

    fetchAndDisplayStarSystem();
});

function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            fetchAndDisplayStarSystem();
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem'), true);

    const request = {
        'starCount': parseInt(document.getElementById('starCount').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
    window.location.replace('../starsystem_list/starsystem_list.html');

}

function fetchAndDisplayStarSystem() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            for (const [key, value] of Object.entries(response)) {
                let input = document.getElementById(key);
                if (input) {
                    input.value = value;
                }
            }
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem'), true);
    xhttp.send();

}

/**
 * Updates the DOM tree in order to display starSystem.
 *
 * @param {{name: string, StarCount: string, year_of_creation: number}} starSystem
 */
function displayStarSystem(starSystem) {
    document.getElementById('starCount').value = starSystem.starCount;

}
