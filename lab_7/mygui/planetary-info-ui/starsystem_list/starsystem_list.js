import {clearElementChildren, createLinkCell, createButtonCell, createTextCell} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayStarSystems();
    document.getElementById('article').appendChild(createLinkCell('add star system','../starsystem_add/starsystem_add.html'));
});

/**
 * Fetches all users and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayStarSystems() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayStarSystem(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/starSystems', true);
    xhttp.send();
}

/**
 * Updates the DOM tree in order to display starSystems.
 *
 * @param {{starSystems: string[]}} starSystems
 */
function displayStarSystem(starSystems) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    starSystems.starSystems.forEach(starSystem => {
        tableBody.appendChild(createTableRow(starSystem));
    })
}

/**
 * Creates single table row for entity.
 *
 * @param {string} starSystem
 * @returns {HTMLTableRowElement}
 */
function createTableRow(starSystem) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(starSystem.name));
    tr.appendChild(createLinkCell('view', '../starsystem/starsystem.html?starSystem=' + starSystem.name));
    tr.appendChild(createLinkCell('edit', '../starSystem_edit/starSystem_edit.html?starSystem=' + starSystem.name));
    tr.appendChild(createButtonCell('delete', () => deleteStarSystem(starSystem.name)));
    return tr;
}

/**
 * Deletes entity from backend and reloads table.
 *
 * @param {string } starSystemId to be deleted
 */
function deleteStarSystem(starSystemId) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayStarSystems();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/starSystems/' + starSystemId, true);
    xhttp.send();
}
