/*
 * incidents.js
 * This file was last modified at 2019-02-11 23:23 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

var currentIncident;

function setTriggers() {
    $('#btnStatus1').click(function () {
        let value = $('#message-text').val();
        if (value.length > 0) {
            currentIncident.status.id = 2;
            statusToWork();
        }
        else {
            alert("Напишите пожалуйста комментарий")
        }
        return false;
    });

    $('#btnAddMessage').click(function () {
        let value = $('#message-text').val();
        if (value.length > 0) {
            addMessage();
        }
        else {
            alert("Напишите пожалуйста комментарий")
        }
        return false;
    });

    $('#btnStatus2').click(function () {
        let value = $('#message-text').val();
        if (value.length > 0) {
            resolution();
        }
        else {
            alert("Напишите пожалуйста комментарий")
        }
        return false;
    });

    $('#asideList a').click(function() {
        findById($(this).data('identity'));
    });
}

function findAll() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderList
	});
}

function findById(id) {
    console.log('findById: ' + id);
    $.ajax({
        type: 'GET',
        url: rootURL + '/' + id + '/messages',
        dataType: "json",
        success: function(data){
            $('#btnStatus' + data.status.id).show();
            console.log('findById success: ' + data.name);
            currentIncident = data;
            renderDetails(currentIncident);
        }
    });
}

function findByName(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type: 'GET',
		url: rootURL + '/search/' + searchKey,
		dataType: "json",
		success: renderList
	});
}

function statusToWork() {
    console.log('statusToWork');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL + '/' + $('#incidentId').val() + '/to-work',
        dataType: "json",
        data: formToJSON(STATUS_WORK),
        success: function(data, textStatus, jqXHR){
            console.log("Incident's status updated successfully: " + textStatus);
            $('#btnStatus1').hide();
            $('#btnAddMessage').show();
            $('#btnStatus2').show();
            findById(currentIncident.id);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('change status to work error: ' + textStatus);
        }
    });
}

function addMessage() {
    console.log('statusToWork');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL + '/' + $('#incidentId').val() + '/add-message',
        dataType: "json",
        data: formToJSON(STATUS_NONE),
        success: function(data, textStatus, jqXHR){
            console.log("Incident's status updated successfully: " + textStatus);
            findById(currentIncident.id);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('change status to work error: ' + textStatus);
        }
    });
}

function resolution() {
    console.log('resoluton');
    // noinspection JSUnusedLocalSymbols
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: rootURL + '/' + $('#incidentId').val() + '/resolution',
        dataType: "json",
        data: formToJSON(STATUS_CLOSED),
        success: function(data, textStatus, jqXHR){
            console.log("Incident's status updated successfully: " + textStatus);
            $('#btnAddMessage').hide();
            $('#btnStatus2').hide();
            findById(currentIncident.id);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('change status to work error: ' + textStatus);
        }
    });
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	let list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#asideList li').remove();
	$.each(list, function(index, incident) {
		$('#asideList').append('<li><a href="#" data-identity="' + incident.id + '">' + incident.title + '</a></li>');
	});
	setTriggers();
}

function renderMessagesList(data) {
    let list = data == null ? [] : (data instanceof Array ? data : [data]);
    var rows = [];

    $('#message-text').prop('disabled', false);
    $('#div-messages').empty();
    $.each(list.reverse(), function(index, message) {
        var row = '<tr><td class="tg0-0lax">' + message.text + '</td><td>';
        rows.push(row);
    });
    $('<table class="tg0"></table>').append(rows.join('')).appendTo('#div-messages');
}

function renderDetails(incident) {
    console.log('renderDetails');

	$('#incidentId').val(incident.id);
	$('#title').val(incident.title);
	$('#consumer').html('Инициатор: ' + incident.consumer.name);
	$('#status').html('Статус: ' + incident.status.status);
	$('#description').val(incident.description);

	renderMessagesList(incident.messages)
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON(incidentStatusId) {
    console.log('formToJSON');
	let incidentId = $('#incidentId').val();

	return JSON.stringify({
		"id": incidentId === "" ? Number("0") : Number(incidentId),
        "status-id": incidentStatusId === "" ? Number("0") : Number(incidentStatusId),
		"message": $('#message-text').val()
	});
}

function find() {
    findAll();
}

// Retrieve incidents list when application starts
jQuery(document).ready(find());
