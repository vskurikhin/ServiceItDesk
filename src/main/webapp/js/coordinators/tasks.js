/*
 * tasks.js
 * This file was last modified at 2019-02-16 18:55 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

var currentTask;

function setTriggers() {
    $('#btnStatus1').click(function () {
        var value = $('#message-text').val();
        if (value.length > 0) {
            currentTask.status.id = 2;
            statusToWork();
        }
        else {
            alert("Напишите пожалуйста комментарий")
        }
        return false;
    });

    $('#btnAddMessage').click(function () {
        var value = $('#message-text').val();
        if (value.length > 0) {
            addMessage();
        }
        else {
            alert("Напишите пожалуйста комментарий")
        }
        return false;
    });

    $('#btnStatus2').click(function () {
        var value = $('#message-text').val();
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
            currentTask = data;
            renderDetails(currentTask);
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
        url: rootURL + '/' + $('#taskId').val() + '/to-work',
        dataType: "json",
        data: formToJSON(STATUS_WORK),
        success: function(data, textStatus, jqXHR){
            console.log("Task's status updated successfully: " + textStatus);
            findById(currentTask.id);
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
        url: rootURL + '/' + $('#taskId').val() + '/add-message',
        dataType: "json",
        data: formToJSON(STATUS_NONE),
        success: function(data, textStatus, jqXHR){
            console.log("Task's status updated successfully: " + textStatus);
            findById(currentTask.id);
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
        url: rootURL + '/' + $('#taskId').val() + '/resolution',
        dataType: "json",
        data: formToJSON(STATUS_CLOSED),
        success: function(data, textStatus, jqXHR){
            console.log("Task's status updated successfully: " + textStatus);
            findById(currentTask.id);
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('change status to work error: ' + textStatus);
        }
    });
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	var list = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#asideList li').remove();
	$.each(list, function(index, task) {
		$('#asideList').append('<li><a href="#" data-identity="' + task.id + '">' + task.title + '</a></li>');
	});
	setTriggers();
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

function renderDetails(task) {
    console.log('renderDetails');

    $('#taskId').val(task.id);
    $('#title').val(task.title);
    $('#consumer').html('Инициатор: ' + task.consumer.name);
    $('#status').html('Статус: ' + task.status.status);
    $('#description').val(task.description);

    if (1 === task.status.id) {
        $('#btnStatus1').show();
        $('#btnAddMessage').hide();
        $('#btnStatus2').hide();
    }
    else if (2 === task.status.id) {
        $('#btnStatus1').hide();
        $('#btnAddMessage').show();
        $('#btnStatus2').show();
    }
    else if (3 === incident.status.id) {
        $('#btnStatus1').hide();
        $('#btnAddMessage').hide();
        $('#btnStatus2').hide();
    }

    renderMessagesList(task.messages)
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON(taskStatusId) {
    console.log('formToJSON');
    var taskId = $('#taskId').val();

    return JSON.stringify({
        "id": taskId === "" ? Number("0") : Number(taskId),
        "status-id": taskStatusId === "" ? Number("0") : Number(taskStatusId),
        "message": $('#message-text').val()
    });
}

function find() {
    findAll();
}

// Retrieve tasks list when application starts
jQuery(document).ready(find());
