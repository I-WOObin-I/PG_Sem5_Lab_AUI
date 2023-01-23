import {getBackendUrl} from '../js/configuration.js';


window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));
});

function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();

    xhttp.open("POST", getBackendUrl() + '/api/starSystems', true);

    const request = {
        'name': document.getElementById('name').value,
        'starCount': parseInt(document.getElementById('starCount').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
    window.location.replace('../starsystem_list/starsystem_list.html');
}

