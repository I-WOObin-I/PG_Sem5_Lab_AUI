import {getBackendUrl} from '../js/configuration.js';
import {getParameterByName} from '../js/dom_utils.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));
});

function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();

    xhttp.open("POST", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets', true);

    const request = {
        'name': document.getElementById('name').value,
        'type': parseInt(document.getElementById('type').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
    window.location.replace('../starsystem/starsystem.html?starSystem=' + getParameterByName('starSystem'));
}