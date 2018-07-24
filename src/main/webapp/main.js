/*
 * Tutorials used to build this file
 * http://stackoverflow.com/questions/18260815/use-gapi-client-javascript-to-execute-my-custom-google-api
 * https://developers.google.com/appengine/docs/java/endpoints/consume_js
 * https://developers.google.com/api-client-library/javascript/reference/referencedocs#gapiclientload
 *
 */

var rootpath = "//" + window.location.host + "/_ah/api";

var files = [];

// Client ID and API key from the Developer Console
var CLIENT_ID = '614829792503-d52aoilleoc8hjs38jsvac9vvedi0ig3.apps.googleusercontent.com';
var API_KEY = 'AIzaSyAtjxDlZwV5hRRmRYpB-ZVI1xedJd2c2eg';

// Array of API discovery doc URLs for APIs used by the quickstart
var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/drive/v3/rest"];

// Authorization scopes required by the API; multiple scopes can be
// included, separated by spaces.
var SCOPES = 'https://www.googleapis.com/auth/drive.metadata.readonly';

var authorizeButton = document.getElementById('authorize_button');
var signoutButton = document.getElementById('signout_button');

function init() {
    gapi.client.load('driveTesting', 'v1', loadCallback, rootpath);
}

function loadCallback() {
    enableButtons();
}

function enableButtons() {
    var save_button = document.getElementById("save_to_db");
    save_button.onclick = function () { saveFiles() };

    save_button.value = "Save";

    var fetch_button = document.getElementById("fetch_from_db");
    fetch_button.onclick = function () { fetchFiles() };

    fetch_button.value = "Fetch";
}

function saveFiles() {

    var fileCollection = {files : self.files};

    var request = gapi.client.driveTesting.saveDriveFiles(fileCollection);

    request.execute(saveFilesCallback);
}

function saveFilesCallback(response){
    if(response){
        appendPre("Successfully saved", "save_result_content");
    }
}

function fetchFilesCallback(response) {
    // If response has failed it will be a boolean with value : false
    if(response){
        var files = response.files;
        var preContent = 'db_content';
        clearPre(preContent);

        if (files && files.length > 0) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                appendPre(file.name + ' (' + file.id + ')', preContent);

                self.files.push(file);
            }
        } else {
            appendPre('No files found.', preContent);
        }
    }
}

function fetchFiles() {
    var request = gapi.client.driveTesting.driveFiles()
    request.execute(fetchFilesCallback);
}



/**
 *  On load, called to load the auth2 library and API client library.
 */
function handleClientLoad() {
    gapi.load('client:auth2', initClient);
}

/**
 *  Initializes the API client library and sets up sign-in state
 *  listeners.
 */
function initClient() {
    gapi.client.init({
        apiKey: API_KEY,
        clientId: CLIENT_ID,
        discoveryDocs: DISCOVERY_DOCS,
        scope: SCOPES
    }).then(function () {
        // Listen for sign-in state changes.
        gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

        // Handle the initial sign-in state.
        updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        authorizeButton.onclick = handleAuthClick;
        signoutButton.onclick = handleSignoutClick;
    });
}

/**
 *  Called when the signed in status changes, to update the UI
 *  appropriately. After a sign-in, the API is called.
 */
function updateSigninStatus(isSignedIn) {

    if(authorizeButton == null){
        authorizeButton = document.getElementById('authorize_button');
    }
    if(signoutButton == null){
        signoutButton = document.getElementById('signout_button');
    }
    if (isSignedIn) {
        authorizeButton.style.display = 'none';
        signoutButton.style.display = 'block';
        listFiles();
    } else {
        authorizeButton.style.display = 'block';
        signoutButton.style.display = 'none';
    }
}

/**
 *  Sign in the user upon button click.
 */
function handleAuthClick(event) {
    gapi.auth2.getAuthInstance().signIn();
}

/**
 *  Sign out the user upon button click.
 */
function handleSignoutClick(event) {
    gapi.auth2.getAuthInstance().signOut();
}

/**
 * Append a pre element to the body containing the given message
 * as its text node. Used to display the results of the API call.
 *
 * @param {string} message Text to be placed in pre element.
 * @param {string} elementName id of element to be used.
 */
function appendPre(message, elementName) {
    var pre = document.getElementById(elementName);
    var textContent = document.createTextNode(message + '\n');
    pre.appendChild(textContent);
}

/**
 * Clears a pre element to allow new data to be displayed
 *
 * @param {string} elementName id of element to be used.
 */
function clearPre(elementName) {
    var pre = document.getElementById(elementName);
    while (pre.lastChild) {
        pre.removeChild(pre.lastChild);
    }
}

/**
 * Print files.
 */
function listFiles() {
    gapi.client.drive.files.list({
        'pageSize': 10,
        'fields': 'nextPageToken, files(id, name, webViewLink, ownedByMe, shared)'
    }).then(function(response) {
        var preContent = 'content';
        clearPre(preContent);
        appendPre('Files owned by me and shared:', preContent);
        var files = response.result.files;
        if (files && files.length > 0) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];

                if(file.ownedByMe && file.shared){
                    appendPre(file.name + ' (' + file.id + ')', preContent);
                    self.files.push(file);
                }
            }
        } else {
            appendPre('No files found.', preContent);
        }
    });
}




