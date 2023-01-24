import {getBackendUrl} from '../js/configuration.js';
import {getParameterByName} from "../js/dom_utils.js";

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');
    const imageForm = document.getElementById('imageForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));
    imageForm.addEventListener('submit', event => uploadImageAction(event));
    
    fetchAndDisplayPlanet();

    /*
    function sendData() {
        const XHR = new XMLHttpRequest();
        const FD = new FormData(form);
        XHR.open("PUT", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets/' + getParameterByName('planet'), true);

        XHR.setRequestHeader("Content-Type", "application/json");
        XHR.onreadystatechange = function () {
            if (XHR.readyState === 4 && XHR.status === 200) {
                console.log(JSON.parse(XHR.responseText));
                var json = JSON.parse(XHR.responseText);
                console.log(json.name + ", " + json.pure);
            }
        };
        var data = JSON.stringify({"name": FD.get("name"), "type": FD.get("type")});
        XHR.send(data);
    }
    const form = document.getElementById("infoForm");
    form.addEventListener("submit", (event) => {
        event.preventDefault();
        sendData();
        window.location.replace('../starSystem/starSystem.html?starSystem=' + getParameterByName('starSystem'));
    });

     */

     
});

function fetchAndDisplayPlanet() {
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
    xhttp.open("GET", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets/' + getParameterByName('planet'), true);
    xhttp.send();
}

/**
 * Action event handled for updating planet info.
 * @param {Event} event dom event
 */
function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            fetchAndDisplayCharacter();
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets/'
        + getParameterByName('planet'), true);

    const request = {
        'name': document.getElementById('name').value,
        'type': document.getElementById('type').value
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
    window.location.replace('../starsystem/starsystem.html?starSystem=' + getParameterByName('starSystem'));
}

/**
 * Updates the DOM tree in order to display planet.
 *
 * @param {{name: string, type: string, artist: string}} planet
 */
function displayPlanet(planet) {
    document.getElementById('name').value = planet.name;
    document.getElementById('type').value = planet.type;
}

function uploadImageAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.open("PUT", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets/'
        + getParameterByName('planet') + '/image', true);

    let request = new FormData();
    request.append("image", document.getElementById('image').files[0]);

    xhttp.send(request);
    window.location.replace('../starsystem/starsystem.html?starSystem=' + getParameterByName('starSystem'));


}
