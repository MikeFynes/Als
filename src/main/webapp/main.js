/*
 * Tutorials used to build this file
 * http://stackoverflow.com/questions/18260815/use-gapi-client-javascript-to-execute-my-custom-google-api
 * https://developers.google.com/appengine/docs/java/endpoints/consume_js
 * https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiclientload
 *
 */

var CLIENT_ID = "614829792503-d52aoilleoc8hjs38jsvac9vvedi0ig3.apps.googleusercontent.com";
var SCOPES = 'https://www.googleapis.com/auth/drive';

var rootpath = "//" + window.location.host + "/_ah/api";

function init() {
    gapi.client.load('driveTesting', 'v1', loadCallback, rootpath);
}

function loadCallback() {
    enableButtons();
}

function enableButtons() {
    var btn = document.getElementById("fetch_files");
    btn.onclick = function () {
        gapi.auth.authorize({
            client_id: CLIENT_ID,
            scope: SCOPES, immediate: true
        }, fetchFiles());
    };

    btn.value = "Fetch";
}

function fetchFiles() {

    var request = gapi.client.driveTesting.driveFiles();

    request.execute(fetchFilesCallback);
}

function fetchFilesCallback(response) {

    var list = document.getElementById("fetched_files_list");
    var items = response.items;
    var arrayLength = items.length;
    for (var i = 0; i < arrayLength; i++) {
        var item = items[i];
        var entry = document.createElement("li");
        var text = "Id : " + item.id + " Name : " + item.name + " Owned by me : " + item.ownedByMe + " Shared : " + item.shared;
        entry.appendChild(document.createTextNode(text));
        list.appendChild(entry);
    }
}




