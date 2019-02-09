/*
 * incident-edit.js
 * This file was last modified at 2019-02-09 23:22 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

// rootURL - The root URL for the RESTful services
// Example: var rootURL = "http://localhost:8080/ServiceItDesk/rest/api/v1/incidents";

var currentIncident;

function setTriggers() {
    // Nothing to delete in initial application state
    $('#btnDelete').hide();

    // Register listeners
    $('#btnSearch').click(function() {
        search($('#name').val());
        return false;
    });

    // Trigger search when pressing 'Return' on search key input field
    $('#searchKey').keypress(function(e){
        if(e.which === 13) {
            search($('#name').val());
            e.preventDefault();
            return false;
        }
    });

    $('#btnAdd').click(function() {
        newIncident();
        return false;
    });

    $('#btnSave').click(function() {
        if ($('#incidentId').val() === '')
            addIncident();
        else
            updateIncident();
        return false;
    });

    $('#btnDelete').click(function() {
        deleteIncident();
        return false;
    });

    $('#incidentList a').click(function() {
        findById($(this).data('identity'));
    });
}

function search(searchKey) {
	if (searchKey === '')
		findAll();
	else
		findByName(searchKey);
}

function newIncident() {
	$('#btnDelete').hide();
    $('#btnSave').html('Add');
	currentIncident = {};
	renderDetails(currentIncident); // Display empty form
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

// TODO remove
function findAllGroups() {
    console.log('findAllGroups');
    $.ajax({
        type: 'GET',
        url: groupRootURL,
        dataType: "json", // data type of response
        success: renderListGroup
    });
}

function findAllUsers() {
    console.log('findAllUsers');
    $.ajax({
        type: 'GET',
        url: userRootURL,
        dataType: "json", // data type of response
        success: renderListUser
    });
}

function findAllStatuses() {
    console.log('findAllStatuses');
    $.ajax({
        type: 'GET',
        url: statusRootURL,
        dataType: "json", // data type of response
        success: renderListStatus
    });
}

function find() {
    findAll();
    findAllStatuses();
    // findAllGroups();
    findAllUsers();
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

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id + '/messages',
		dataType: "json",
		success: function(data){
            $('#btnDelete').show();
            $('#btnSave').html('Save');
			console.log('findById success: ' + data.name);
			currentIncident = data;
			renderDetails(currentIncident);
		}
	});
}

function addIncident() {
	console.log('addIncident');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
        statusCode: {
		    201: function(data, textStatus, jqXHR){
                console.log("Incident created successfully" + textStatus);
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#incidentId').val(data.id);
                setTimeout(function(){location.reload();}, 750);
		    },
            406: function(data, textStatus, jqXHR){
                alert('addIncident error: ' + textStatus);
            },
            500: function(data, textStatus, jqXHR){
                alert('addIncident FATAL error: ' + textStatus);
            }
        }
	});
}

function updateIncident() {
	console.log('updateIncident');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            console.log("Incident updated successfully: " + textStatus);
            $('#btnDelete').show();
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateIncident error: ' + textStatus);
		}
	});
}

function deleteIncident() {
	console.log('deleteIncident');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#incidentId').val(),
		success: function(data, textStatus, jqXHR){
			alert('Incident deleted successfully');
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteIncident error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#incidentList li').remove();
	$.each(list, function(index, incident) {
		$('#incidentList').append('<li><a href="#" data-identity="' + incident.id + '">' + incident.title + '</a></li>');
	});
	setTriggers();
}

function renderListStatus(data) {
    console.log('renderListGroup');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var statusSelect = $('#status');
    var statusId = Number(statusSelect.find('option:selected').val());

    $.each(list, function(index, status) {
        if (statusId !== status.id) {
            console.log('append: ' + status.id + ' ' + status.status); // TODO remove
            statusSelect.append('<option value="' + status.id + '">' + status.status + '</option>');
        }
    });
    $('.dropdown-sin-2').dropdown({
        readOnly: false,
        input: '<input status="text" maxLength="20" placeholder="Search">'
    });
}

// TODO remove
function renderListGroup(data) {
    console.log('renderListGroup');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var groupSelect = $('#group');
    var groupId = Number(groupSelect.find('option:selected').val());
    console.log('renderListGroup groupId:' + groupId);

    $.each(list, function(index, group) {
        if (groupId !== group.id) {
            console.log('renderListGroup append: ' + group.id + ' ' + group.name); // TODO remove
            groupSelect.append('<option value="' + group.id + '">' + group.name + '</option>');
        }
    });
    $('.dropdown-sin-3').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
}

function renderListUser(data) {
    console.log('renderListUser');
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var consumerSelect = $('#consumer');
    var consumerId = Number(consumerSelect.find('option:selected').val());

    $.each(list, function(index, user) {
        if (consumerId !== user.id) {
            console.log('renderListUser consumer append: ' + user.id + ' ' + user.name);
            consumerSelect.append('<option value="' + user.id + '">' + user.name + '</option>');
        }
    });
    $('.dropdown-sin-1').dropdown({
        readOnly: false,
        input: '<input type="text" maxLength="20" placeholder="Search">'
    });
}

function renderMessagesList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);
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
    function renderDropdown(num, selectId, selectName, id, name) {
        var divDropdownSin = $('#div-dropdown-sin-' + num);

        divDropdownSin.empty();
        divDropdownSin.html(
            '<div class="dropdown-sin-' + num + ' dropdown-single">' +
            '<select form="incidentForm" id="' +
            selectId + '" name="' + selectName +
            '" style="display:none" placeholder="Select"></select></div>'
        );
        $('#' + selectId).append('<option value="' + id + '" selected>' + name + '</option>');
    }

    console.log('renderDetails');

	$('#incidentId').val(incident.id);
	$('#title').val(incident.title);

	renderDropdown('1', 'consumer', 'consumer', incident.consumer.id, incident.consumer.name);
    findAllUsers();
    renderDropdown('2', 'status', 'status', incident.status.id, incident.status.status);
    findAllStatuses();

	$('#description').val(incident.description);

	renderMessagesList(incident.messages)
}

// Helper function to serialize all the form fields into a JSON string
function messageToJSON() {
    var incidentId = $('#incidentId').val();

    var consumer = $('#consumer');
    var incidentConsumerId = consumer.find('option:selected').val();
    var incidentConsumerName = consumer.find('option:selected').text();

    var status = $('#status');
    var incidentStatusId = status.find('option:selected').val();
    var incidentStatusName = status.find('option:selected').text();

    return JSON.stringify({
        "id": incidentId === "" ? Number("0") : Number(incidentId),
        "title": $('#title').val(),
        "consumer": {"id": Number(incidentConsumerId), "name": incidentConsumerName, "description": null},
        "status": {"id": Number(incidentStatusId), "status": incidentStatusName, "description": null},
        "description": $('#description').val()
    });
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var incidentId = $('#incidentId').val();

    var consumer = $('#consumer');
    var incidentConsumerId = consumer.find('option:selected').val();
    var incidentConsumerName = consumer.find('option:selected').text();

    var status = $('#status');
    var incidentStatusId = status.find('option:selected').val();
    var incidentStatusName = status.find('option:selected').text();

	return JSON.stringify({
		"id": incidentId === "" ? Number("0") : Number(incidentId),
		"title": $('#title').val(),
        "consumer": {"id": Number(incidentConsumerId), "name": incidentConsumerName, "description": null},
        "status": {"id": Number(incidentStatusId), "status": incidentStatusName, "description": null},
		"description": $('#description').val()
	});
}

// Retrieve incident list when application starts
jQuery(document).ready(find());

