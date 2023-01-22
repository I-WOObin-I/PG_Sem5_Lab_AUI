import {
    getParameterByName,
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    setTextNode, createImageCell
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayStarSystem();
    fetchAndDisplayPlanets();
    document.getElementById('article').appendChild(createLinkCell('add planet','../planet_add/planet_add.html?starSystem=' + getParameterByName('starSystem')));
});

function fetchAndDisplayStarSystem() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayStarSystem(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem'), true);
    xhttp.send();
}

function displayStarSystem(starSystem) {
    setTextNode('starsystem_name', starSystem.name);
    setTextNode('starsystem_star_count', starSystem.starCount);
}


function fetchAndDisplayPlanets() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log(JSON.parse(this.responseText))
            displayPlanets(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets', true);
    xhttp.send();
}

function displayPlanets(planets) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    planets.planets.forEach(planet => {
        tableBody.appendChild(createTableRowPlanets(planet));
    })
}

function createTableRowPlanets(planet) {
    let tr = document.createElement('tr');
    tr.appendChild(createImageCell(getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets/' + planet.id + '/image'));
    tr.appendChild(createTextCell(planet.name));
    tr.appendChild(createLinkCell('view', '../planet/planet.html?starSystem=' + getParameterByName('starSystem') + '&planet=' + planet.id));
    tr.appendChild(createLinkCell('edit', '../planet_edit/planet_edit.html?starSystem=' + getParameterByName('starSystem') + '&planet=' + planet.id));
    tr.appendChild(createButtonCell('delete', () => deletePlanet(planet.id)));
    //tr.appendChild(createLinkCell('view', '../starSystems/planet.html?starsystem=' + getParameterByName('starsystem') + '&planet=' + planet.id));
    //tr.appendChild(createLinkCell('edit', '../planet_edit/planet_edit.html?starsystem=' + getParameterByName('starsystem') + '&planet=' + planet.id));
    //tr.appendChild(createButtonCell('delete', () => deletePlanet(planet.id)));
    return tr;
}




function deletePlanet(planet) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayPlanets();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem')
        + '/planets/' + planet, true);
    xhttp.send();
}

