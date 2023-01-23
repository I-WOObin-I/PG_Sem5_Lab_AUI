import {
    clearElementChildren,
    createLinkCell,
    createButtonCell,
    createTextCell,
    getParameterByName
} from '../js/dom_utils.js';
import {getBackendUrl} from '../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayFiles();
    document.getElementById('article').appendChild(createLinkCell('upload file','../file/file.html'));
});

/**
 * Fetches all users and modifies the DOM tree in order to display them.
 */
function fetchAndDisplayFiles() {
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayFiles(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/files', true);
    xhttp.send();
}

function displayFiles(files) {
    let tableBody = document.getElementById('tableBody');
    console.log(files.toString());
    clearElementChildren(tableBody);
    files.files.forEach(file => {
        tableBody.appendChild(createTableRow(file));
    })
}

function createTableRow(file) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(file.id));
    tr.appendChild(createTextCell(file.author));
    tr.appendChild(createButtonCell('download', () => downloadFile(file.id)));
    return tr;
}

function downloadFile(fileId) {
    window.open(getBackendUrl()+'/api/files/download/'+fileId);
    // fetch(getBackendUrl()+'/api/files/download/'+fileId)
    //     .then(response => {
    //         console.log(response)
    //         var filename =  response.headers.get('Content-Disposition').split('filename=')[1];
    //         console.log(filename)
    //         filename.replace(new RegExp('"', 'g'), '');
    //         console.log(filename)
    //
    //         response.blob().then(blob => {
    //             let url = window.URL.createObjectURL(blob);
    //             let a = document.createElement('a');
    //             a.href = url;
    //             a.download = filename;
    //             a.responseType='arraybuffer';
    //             a.click();
    //         });
    //     })

}

/*
function downloadFile(fileId) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayFiles();
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/files/download/' + fileId, true);
    xhttp.send();

}
 */

