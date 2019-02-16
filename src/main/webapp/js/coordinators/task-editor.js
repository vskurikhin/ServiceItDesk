/*
 * task-editor.js
 * This file was last modified at 2019-02-16 13:18 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

var currentTask;

function setTriggers() {
    // Nothing to delete in initial application state
    var btnDelete = $('#btnDelete');
    btnDelete.hide();

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
        newTask();
        return false;
    });

    $('#btnSave').click(function() {
        if ($('#taskId').val() === '')
            addTask();
        else
            updateTask();
        return false;
    });

    btnDelete.click(function() {
        deleteTask();
        return false;
    });

    $('#asideList a').click(function() {
        findById($(this).data('identity'));
    });
}

function search(searchKey) {
	if (searchKey === '')
		findAll();
	else
		findByName(searchKey);
}

function newTask() {
	$('#btnDelete').hide();
    $('#btnSave').html('Add');
	currentTask = {};
	renderDetails(currentTask); // Display empty form
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
			currentTask = data;
			renderDetails(currentTask);
		}
	});
}

function addTask() {
	console.log('addTask');
	// noinspection JSUnusedLocalSymbols
    $.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
        statusCode: {
		    201: function(data, textStatus, jqXHR){
                console.log("Task created successfully" + textStatus);
                $('#btnDelete').show();
                $('#btnSave').html('Save');
                $('#taskId').val(data.id);
                setTimeout(function(){location.reload();}, 750);
		    },
            406: function(data, textStatus, jqXHR){
                alert('addTask error: ' + textStatus);
            },
            500: function(data, textStatus, jqXHR){
                alert('addTask FATAL error: ' + textStatus);
            }
        }
	});
}

function updateTask() {
	console.log('updateTask');
	// noinspection JSUnusedLocalSymbols
    $.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
            console.log("Task updated successfully: " + textStatus);
            $('#btnDelete').show();
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateTask error: ' + textStatus);
		}
	});
}

function deleteTask() {
	console.log('deleteTask');
	// noinspection JSUnusedLocalSymbols
    $.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#taskId').val(),
		success: function(data, textStatus, jqXHR){
			alert('Task deleted successfully');
            setTimeout(function(){location.reload();}, 750);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteTask error');
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
        input: '<input  maxLength="20" placeholder="Search">'
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

function renderDetails(task) {
    function renderDropdown(num, selectId, selectName, id, name) {
        var divDropdownSin = $('#div-dropdown-sin-' + num);

        divDropdownSin.empty();
        divDropdownSin.html(
            '<div class="dropdown-sin-' + num + ' dropdown-single">' +
            '<select form="taskForm" id="' +
            selectId + '" name="' + selectName +
            '" style="display:none" placeholder="Select"></select></div>'
        );
        $('#' + selectId).append('<option value="' + id + '" selected>' + name + '</option>');
    }

    console.log('renderDetails');

    $('#taskId').val(task.id);
    $('#title').val(task.title);

    renderDropdown('1', 'consumer', 'consumer', task.consumer.id, task.consumer.name);
    findAllUsers();
    renderDropdown('2', 'status', 'status', task.status.id, task.status.status);
    findAllStatuses();

    $('#description').val(task.description);

    renderMessagesList(task.messages)
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var taskId = $('#taskId').val();

    var consumer = $('#consumer');
    var taskConsumerId = consumer.find('option:selected').val();
    var taskConsumerName = consumer.find('option:selected').text();

    var status = $('#status');
    var taskStatusId = status.find('option:selected').val();
    var taskStatusName = status.find('option:selected').text();

    return JSON.stringify({
        "id": taskId === "" ? Number("0") : Number(taskId),
        "title": $('#title').val(),
        "consumer": {"id": Number(taskConsumerId), "name": taskConsumerName, "description": null},
        "status": {"id": Number(taskStatusId), "status": taskStatusName, "description": null},
        "description": $('#description').val()
    });
}

// Retrieve task list when application starts
jQuery(document).ready(find());

