import {
    createImageCell,
    getParameterByName,
    setTextNode
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayPlanet();
});
/**
 * Fetches single user and modifies the DOM tree in order to display it.
 */
function fetchAndDisplayPlanet() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayPlanet(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets/' + getParameterByName('planet'), true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display planet.
 */
function displayPlanet(planet) {
    const img = document.getElementById('planet_image');
    img.width = 400;
    img.height = 400;
    img.src = getBackendUrl() + '/api/starSystems/' + getParameterByName('starSystem') + '/planets/' + planet.id + '/image';
    setTextNode('planet_name', planet.name);
    setTextNode('planet_type', planet.type);
}
