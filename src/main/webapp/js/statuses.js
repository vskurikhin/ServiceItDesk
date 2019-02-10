/*
 * statuses.js
 * This file was last modified at 2019-02-11 00:22 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

var currentStatus;

function setTriggers() {
    // Nothing to delete in initial application state
    let btnDelete = $('#btnDelete');
    btnDelete.hide();

    // Register listeners
    $('#btnSearch').click(function() {
        search($('#status').val());
        return false;
    });

    // Trigger search when pressing 'Return' on search key input field
    $('#searchKey').keypress(function(e){
        if(e.which === 13) {
            search($('#status').val());
            e.preventDefault();
            return false;
        }
    });

    $('#btnAdd').click(function() {
        newStatus();
        return false;
    });

    $('#btnSave').click(function() {
        if ($('#statusId').val() === '')
            addStatus();
        else
            updateStatus();
        return false;
    });

    btnDelete.click(function() {
        deleteStatus();
        return false;
    });

    $('#statusList a').click(function() {
        findById($(this).data('identity'));
    });
}

function search(searchKey) {
	if (searchKey === '')
		findAll();
	else
		findByName(searchKey);
}

function newStatus() {
	$('#btnDelete').hide();
    $('#btnSave').html('Add');
	currentStatus = {};
	renderDetails(currentStatus); // Display empty form
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
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
            $('#btnDelete').show();
            $('#btnSave').html('Save');
			console.log('findById success: ' + data.status);
			currentStatus = data;
			renderDetails(currentStatus);
		}
	});
}

function addStatus() {
	console.log('addStatus');
	// noinspection JSUnusedLocalSymbols
    $.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
        statusCode: {
		    201: function(data, textStatus, jqXHR){
                console.log("Status created successfully" + textStatus);
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#statusId').val(data.id);
                setTimeout(function(){location.reload();}, 750);
		    },
            406: function(data, textStatus, jqXHR){
                alert('addStatus error: ' + textStatus);
            },
            500: function(data, textStatus, jqXHR){
                alert('addStatus FATAL error: ' + textStatus);
            }
        }
	});
}

function updateStatus() {
	console.log('updateStatus');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            console.log("Status updated successfully: " + textStatus);
            $('#btnDelete').show();
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateStatus error: ' + textStatus);
		}
	});
}

function deleteStatus() {
	console.log('deleteStatus');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#statusId').val(),
		success: function(data, textStatus, jqXHR){
			alert('Status deleted successfully');
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteStatus error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	let list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#statusList li').remove();
	$.each(list, function(index, status) {
		$('#statusList').append('<li><a href="#" data-identity="' + status.id + '">' + status.status + '</a></li>');
	});
	setTriggers();
}

function renderDetails(status) {
	$('#statusId').val(status.id);
	$('#status').val(status.status);
	$('#description').val(status.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	let statusId = $('#statusId').val();

	return JSON.stringify({
		"id": statusId === "" ? Number("0") : Number(statusId),
		"status": $('#status').val(),
		"description": $('#description').val()
	});
}

// Retrieve status list when application starts
jQuery(document).ready(findAll());

